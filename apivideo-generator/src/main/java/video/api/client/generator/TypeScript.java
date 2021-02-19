package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.TypeScriptClientCodegen;
import org.openapitools.codegen.meta.features.DocumentationFeature;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TypeScript extends TypeScriptClientCodegen {

    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";

    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");
    public static final String DOC_SUBFOLDER = "doc/";

    public TypeScript() {
        super();

        typeMapping.put("AnyType", "any");
        typeMapping.put("URI", "string");
        typeMapping.put("set", "Array");
        typeMapping.put("HttpFile", "File");

        modifyFeatureSet(features -> features.includeDocumentationFeatures(DocumentationFeature.Api, DocumentationFeature.Model));

        apiDocTemplateFiles.put("doc/api_doc.mustache", ".md");
        modelDocTemplateFiles.put("doc/model_doc.mustache", ".md");

        additionalProperties.put("apiDocPath", DOC_SUBFOLDER);
        additionalProperties.put("modelDocPath", DOC_SUBFOLDER);
    }

    @Override
    public String apiDocFileFolder() {
        return super.apiDocFileFolder() + "/" + DOC_SUBFOLDER;
    }

    @Override
    public String modelDocFileFolder() {
        return super.modelDocFileFolder() + "/" + DOC_SUBFOLDER;
    }

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
    }

    @Override
    public String getTypeDeclaration(Schema p) {
        String res = super.getTypeDeclaration(p);
        if(res.equals("HttpFile")) {
            return "File";
        }
        return res;
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
                // overwrite operationId & nickname values of the operation with the x-client-action
                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = (String) operation.vendorExtensions.get("x-client-action");
                    operation.nickname = operation.operationId;
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
                String folder = getOutputDir() + "/tests/payloads/" + operation.baseName.toLowerCase() + "/" + operation.vendorExtensions.get("x-client-action") + "/responses/";
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

    /**
     * Remove files listed in the "ignoredFiles" list of the config file from the list of files to generate
     */
    @Override
    public void processOpts() {
        super.processOpts();

        List<String> ignoredFiles = (List<String>) additionalProperties.get("ignoredFiles");
        supportingFiles.removeIf(e -> ignoredFiles.contains(e.getTemplateFile()));
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
}
