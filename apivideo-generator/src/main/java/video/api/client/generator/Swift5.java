package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.Swift5ClientCodegen;
import org.openapitools.codegen.templating.mustache.IndentedLambda;
import org.openapitools.codegen.templating.mustache.TitlecaseLambda;

import static org.openapitools.codegen.utils.StringUtils.camelize;
import static video.api.client.generator.Common.populateOperationResponse;

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
        Common.replaceDescriptionsAndSamples(objs, "swift5");

        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            ops.sort(Common.getCodegenOperationComparator());

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


                String folder = getOutputDir() + "/Tests/TestResources/payloads/" + operation.baseName.toLowerCase() + "/" + camelize((String) operation.vendorExtensions.get("x-client-action"), true) + "/responses/";
                operation.responses.forEach(response -> populateOperationResponse(operation, response, additionalProperties, folder));
            }
        }
        return super.postProcessOperationsWithModels(objs, allModels);
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


    private boolean getVendorExtensionBooleanValue(CodegenParameter parameter, String name) {
        return parameter.vendorExtensions.containsKey(name) && (boolean) parameter.vendorExtensions.get(name);
    }

    @Override
    public void processOpts() {
        super.processOpts();
        additionalProperties.put("titlecase", new TitlecaseLambda());
        additionalProperties.put("unescape", new UnescapeLambda());

        ChangeLog changelog = ChangeLog.parse(additionalProperties);
        additionalProperties.put("podVersion", changelog.getLastVersion().getName());
        changelog.writeTo(this.getOutputDir());

    }


    @Override
    public Map<String, Object> postProcessModels(Map<String, Object> objs) {
        Map<String, Object> stringObjectMap = super.postProcessModels(objs);

        List<Map<String, Object>> models = (List)objs.get("models");
        models.forEach(map -> {
            CodegenModel model = ((CodegenModel)map.get("model"));
            model.vars.forEach(var -> {
                if(var.vendorExtensions.containsKey("x-optional-nullable") && var.dataType.equals("String")) {
                    var.dataType = "NullableString";
                    var.datatypeWithEnum = "NullableString";
                    var.baseType = "NullableString";
                    var.isNullable = true;
                }
            });
        });
        return stringObjectMap;
    }

    public String constructExampleCode(CodegenProperty codegenParameter, HashMap<String, CodegenModel> modelMaps, Set<String> visitedModels) {
        if (codegenParameter.dataType.equals("NullableString")) { // array
            return "NullableString(value: \"" + codegenParameter.example + "\")";
        }
        return super.constructExampleCode(codegenParameter, modelMaps, visitedModels);
    }
}
