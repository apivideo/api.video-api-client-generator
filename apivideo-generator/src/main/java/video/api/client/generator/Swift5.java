package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.Swift5ClientCodegen;
import org.openapitools.codegen.templating.mustache.IndentedLambda;
import org.openapitools.codegen.templating.mustache.TitlecaseLambda;

import static org.openapitools.codegen.utils.StringUtils.camelize;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Swift5 extends Swift5ClientCodegen {
    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";
    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    public Swift5() {
        super();
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
                operation.vendorExtensions.put("x-client-copy-from-response", operation.allParams.stream()
                        .filter(p -> Boolean.TRUE.equals(p.vendorExtensions.get("x-client-copy-from-response")))
                        .peek(a -> a.vendorExtensions.put("getter", toGetter(a.paramName)))
                        .collect(Collectors.toList()));

                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = camelize((String) operation.vendorExtensions.get("x-client-action"), true);
                    operation.nickname = operation.operationId;
                } else {
                    throw new RuntimeException("Missing x-client-action value for operation " + operation.operationId);
                }

                applyToAllParams(operation, (params) -> params.forEach(pp -> {
                    if("deepObject".equals(pp.style)) pp.collectionFormat = "deepObject";
                }));
                applyToAllParams(operation, (params) -> params.removeIf(pp -> getVendorExtensionBooleanValue(pp, VENDOR_X_CLIENT_IGNORE)) );
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
                String folder = getOutputDir() + "/Tests/TestResources/payloads/" + operation.baseName.toLowerCase() + "/" + camelize((String) operation.vendorExtensions.get("x-client-action"), true) + "/responses/";
                Files.createDirectories(Paths.get(folder));
                PrintWriter out = new PrintWriter(folder + response.code + ".json");
                out.print(responseExample);
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    private boolean getVendorExtensionBooleanValue(CodegenParameter parameter, String name) {
        return parameter.vendorExtensions.containsKey(name) && (boolean) parameter.vendorExtensions.get(name);
    }

    @Override
    public void processOpts() {
        super.processOpts();
        additionalProperties.put("titlecase", new TitlecaseLambda());

        ChangeLog changelog = ChangeLog.parse(additionalProperties);
        additionalProperties.put("podVersion", changelog.getLastVersion().getName());
        changelog.writeTo(this.getOutputDir());

    }
}
