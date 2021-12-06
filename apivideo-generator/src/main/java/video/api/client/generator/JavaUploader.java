package video.api.client.generator;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class JavaUploader extends Java {

    @Override
    public void processOpts() {
        super.processOpts();

        ChangeLog changelog = ChangeLog.parse(additionalProperties);
        additionalProperties.put("artifactVersion", changelog.getLastVersion().getName());
        changelog.writeTo(this.getOutputDir());

    }

    /**
     * Filter the OpenAPI description file entries in order to keep only stuff related to video upload & authentication
     * @param openAPI
     */
    @Override
    public void preprocessOpenAPI(OpenAPI openAPI) {
        super.preprocessOpenAPI(openAPI);

        Set<String> pathsToRemove = new HashSet<>();

        // rules defining which operations should be kept
        List<Function<Operation, Boolean>> operationsToKeepRules = Arrays.asList(
                o -> o.getExtensions().containsKey("x-client-chunk-upload"),
                o -> o.getTags().contains("Authentication")
        );

        // remove useless paths & methods
        openAPI.getPaths().forEach((path, pathItem) -> {
            Set<PathItem.HttpMethod> methodsToRemove = new HashSet<>();
            Map<PathItem.HttpMethod, Operation> httpMethodOperationMap = pathItem.readOperationsMap();
            for (Map.Entry<PathItem.HttpMethod, Operation> httpMethodOperationEntry : httpMethodOperationMap.entrySet()) {
                Operation operation = httpMethodOperationEntry.getValue();
                PathItem.HttpMethod method = httpMethodOperationEntry.getKey();

                if (!operationsToKeepRules.stream().anyMatch(matcher -> matcher.apply(operation))) {
                    methodsToRemove.add(method);
                }
            }

            // remove useless methods
            methodsToRemove.forEach(httpMethodOperationMap::remove);
            if (httpMethodOperationMap.isEmpty()) {
                pathsToRemove.add(path);
            }
        });
        pathsToRemove.forEach(openAPI.getPaths()::remove);

        // retrieve reference to schemas in the kept operations (we will keep these schemas)
        Set<String> schemaRefsToKeep = new HashSet<>();
        openAPI.getPaths().forEach((path, pathItem) -> {
            Map<PathItem.HttpMethod, Operation> httpMethodOperationMap = pathItem.readOperationsMap();

            for (Map.Entry<PathItem.HttpMethod, Operation> httpMethodOperationEntry : httpMethodOperationMap.entrySet()) {
                Operation operation = httpMethodOperationEntry.getValue();
                operation.getRequestBody().getContent().forEach((mediaTypeName, mediaType) -> {
                    schemaRefsToKeep.add(mediaType.getSchema().get$ref().substring("#/components/schemas/".length()));
                });
                operation.getResponses().forEach((statusCode, apiResponse) -> {
                    apiResponse.getContent().forEach((mediaTypeName, mediaType) -> {
                        schemaRefsToKeep.add(mediaType.getSchema().get$ref().substring("#/components/schemas/".length()));
                    });
                });
            }
        });

        // interate recursively in the kept schemas to find sub-schemas, sub-sub-schemas, etc. and keep them
        Map<String, Schema> schemas = openAPI.getComponents().getSchemas();
        schemas.forEach((s, schema) -> {
            if (schemaRefsToKeep.contains(s)) {
                Map<String, Schema> properties = schema.getProperties();
                recursivelyAddSchemas(schemas, schema, schemaRefsToKeep);
            }
        });

        // remove useless schemas
        Set<String> schemasToRemove = new HashSet<>();
        schemas.forEach((s, schema) -> {
            if (!schemaRefsToKeep.contains(s)) {
                schemasToRemove.add(s);
            }
        });
        schemasToRemove.forEach(schemas::remove);
    }

    private void recursivelyAddSchemas (Map<String, Schema> schemas, Schema schema, Set<String> toKeep) {
        Map<String, Schema> properties = schema.getProperties();
        for (Map.Entry<String, Schema> o : properties.entrySet()) {
            Schema v = o.getValue();

            String ref;
            if(v instanceof ArraySchema) {
                Schema<?> arrayItemsSchema = ((ArraySchema) v).getItems();
                ref = arrayItemsSchema.get$ref();
            } else {
                ref = v.get$ref();
            }
            if(ref != null) {
                schemas.forEach((s, schema1) -> {
                    if (s.equals(ref.substring("#/components/schemas/".length())) && !toKeep.contains(s)) {
                        recursivelyAddSchemas(schemas, schema1, toKeep);
                    }
                });
                toKeep.add(ref.substring("#/components/schemas/".length()));
            }
        }
    }

}
