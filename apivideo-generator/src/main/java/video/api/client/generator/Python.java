package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.util.Json;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Lambda;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.PythonClientCodegen;
import org.openapitools.codegen.*;
import org.openapitools.codegen.templating.mustache.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

import static org.openapitools.codegen.utils.StringUtils.underscore;
import org.apache.commons.lang3.StringUtils;

import video.api.client.generator.UnderscoreLambda;

public class Python extends PythonClientCodegen {

    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";
    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    public Python() {
        super();
        modelTestTemplateFiles.clear();
    }

    /**
     * Remove files listed in the "ignoredFiles" list of the config file from the list of files to generate
     */
    @Override
    public void processOpts() {
        super.processOpts();

        List<String> ignoredFiles = (List<String>) additionalProperties.get("ignoredFiles");
        supportingFiles.removeIf(e -> ignoredFiles.contains(e.getTemplateFile()));
        supportingFiles.add(new SupportingFile("auth_api_client.mustache", packagePath(), "auth_api_client.py"));
        supportingFiles.add(new SupportingFile("endpoint.mustache", packagePath(), "endpoint.py"));
        supportingFiles.add(new SupportingFile("helper.mustache", packagePath() + "/../test/", "helper.py"));
        supportingFiles.add(new SupportingFile("test_auth.mustache", packagePath() + "/../test/", "test_auth.py"));
    }

    public void preprocessOpenAPI(OpenAPI openAPI) {
        Map<String, PathItem> pathItems = openAPI.getPaths();
        io.swagger.v3.oas.models.Paths newPaths = new io.swagger.v3.oas.models.Paths();

        // remove Auth endpoints
        if (pathItems != null) {
            for (Map.Entry<String, PathItem> e : pathItems.entrySet()) {
                if (!e.getKey().startsWith("/auth/")) {
                    newPaths.addPathItem(e.getKey(), e.getValue());
                }
            }
        }
        openAPI.setPaths(newPaths);
        super.preprocessOpenAPI(openAPI);
    }

    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        objs.put("vendorExtensions", vendorExtensions);
        return super.postProcessSupportingFileData(objs);
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            // if all operations of a path have x-client-hidden, add x-client-hidden on the path
            if(ops.stream().allMatch(op -> Boolean.TRUE.equals(op.vendorExtensions.get(VENDOR_X_CLIENT_HIDDEN)))) {
                objs.put(VENDOR_X_CLIENT_HIDDEN, true);
            }

            for (CodegenOperation operation : ops) {
                // overwrite operationId with the x-client-action
                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = underscore((String) operation.vendorExtensions.get("x-client-action"));
                } else {
                    throw new RuntimeException("Missing x-client-action value for operation " + operation.operationId);
                }

                // remove ignored params from operation parameters
                applyToAllParams(operation, (params) -> params.removeIf(pp ->
                        pp.vendorExtensions.containsKey(VENDOR_X_CLIENT_IGNORE) && (boolean) pp.vendorExtensions.get(VENDOR_X_CLIENT_IGNORE)
                ));

                applyToAllParams(operation, (params) ->
                    params.stream()
                            .flatMap(p -> p.vars.stream())
                            .filter(p -> PARAMETERS_TO_HIDE_IN_CLIENT_DOC.contains(p.baseName))
                            .forEach(p -> p.vendorExtensions.put("x-client-doc-hidden", true))
                );

                operation.responses.forEach(response -> populateOperationResponse(operation, response));
            }
        }
        return super.postProcessOperationsWithModels(objs, allModels);
    }

    /**
     * - Move up some attributes from the operation to the response
     * - Generate payload JSON files from the operation response example (to be used in unit tests)
     * @param operation
     * @param response
     */
    private void populateOperationResponse(CodegenOperation operation, CodegenResponse response) {
        response.vendorExtensions.put("allParams", operation.allParams);
        response.vendorExtensions.put("x-client-action", operation.vendorExtensions.get("x-client-action"));
        response.vendorExtensions.put("x-group-parameters", operation.vendorExtensions.get("x-group-parameters"));
        response.vendorExtensions.put("x-client-paginated", operation.vendorExtensions.get("x-client-paginated"));
        response.vendorExtensions.put("x-pagination", operation.vendorExtensions.get("x-pagination"));
        response.vendorExtensions.put("x-is-error", response.is4xx || response.is5xx);
        response.vendorExtensions.put("lambda", additionalProperties.get("lambda"));

        String responseExample = getResponseExample(response);
        if(responseExample != null) {
            try {
                Map<String, String> exampleMap = Json.mapper().readerFor(Map.class).readValue(responseExample);
                if(exampleMap.containsKey("title")) {
                    response.vendorExtensions.put("x-example-response", exampleMap);
                }
                response.vendorExtensions.put("x-example-response-json", responseExample);
            } catch (JsonProcessingException ignored) {
            }


            try {
                String folder = getOutputDir() + "/test/payloads/" + operation.baseName.toLowerCase() + "/" + operation.vendorExtensions.get("x-client-action") + "/responses/";
                Files.createDirectories(Paths.get(folder));
                PrintWriter out = new PrintWriter(folder + response.code + ".json");
                out.print(responseExample);
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * returns the JSON example from an openapi response
     * @param response
     * @return
     */
    private String getResponseExample(CodegenResponse response) {
        Map map;
        try {
            map = Json.mapper().readerFor(Map.class).readValue(response.jsonSchema);
        } catch (JsonProcessingException e) {
            return null;
        }
        Map content = (Map) map.get("content");
        if(content == null) {
            return null;
        }
        Collection<Map> values = content.values();
        for (Map v : values) {
            Map examples = (Map) v.get("examples");
            if(examples == null) continue;
            Map res = (Map) examples.get("response");
            if(res == null) continue;
            return Json.pretty(res.get("value"));
        }

        return null;
    }

    private void applyToAllParams(CodegenOperation operation, Consumer<List<CodegenParameter>> consumer) {
        if (operation.allParams != null) {
            consumer.accept(operation.headerParams);
            consumer.accept(operation.bodyParams);
            consumer.accept(operation.pathParams);
            consumer.accept(operation.formParams);
            consumer.accept(operation.cookieParams);
            consumer.accept(operation.allParams);
        }
    }

    protected ImmutableMap.Builder<String, Lambda> addMustacheLambdas() {
        return new ImmutableMap.Builder<String, Mustache.Lambda>()
                .put("lowercase", new LowercaseLambda().generator(this))
                .put("underscore", new UnderscoreLambda());
    }

    @Override
    public String sanitizeTag(String tag) {
        return sanitizeName(tag.replaceAll(" - ", " ")); // the dash messes with the underscore in the naming style of Python
    }

}
