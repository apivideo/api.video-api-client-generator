package video.api.client.generator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.CSharpClientCodegen;
import org.openapitools.codegen.languages.JavaClientCodegen;
import org.openapitools.codegen.templating.mustache.IndentedLambda;
import org.openapitools.codegen.templating.mustache.LowercaseLambda;
import org.openapitools.codegen.templating.mustache.TitlecaseLambda;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Csharp extends CSharpClientCodegen {

    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
        this.modelTestTemplateFiles.remove("model_test.mustache");
    }

    @Override
    public void processOpts() {
        super.processOpts();
        additionalProperties.put("unescape", new UnescapeLambda());
        additionalProperties.put("indented_16", new IndentedLambda(16, " "));
        additionalProperties.put("titlecase", new TitlecaseLambda());
        additionalProperties.put("lower", new LowercaseLambda());

        List<String> skippedFiles = Arrays.asList(
                "ExceptionFactory.mustache",
                "GlobalConfiguration.mustache",
                "IApiAccessor.mustache",
                "OpenAPIDateConverter.mustache",
                "IReadableConfiguration.mustache",
                "Configuration.mustache");
        supportingFiles.removeIf(e -> skippedFiles.contains(e.getTemplateFile()));
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            if(ops.stream().allMatch(op -> Boolean.TRUE.equals(op.vendorExtensions.get("x-client-hidden")))) {
                objs.put("x-client-hidden", true);
            }

            for (CodegenOperation operation : ops) {
                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = (String) operation.vendorExtensions.get("x-client-action");
                    operation.nickname = operation.operationId;
                } else {
                    throw new RuntimeException("Missing x-client-action value for operation " + operation.operationId);
                }

                operation.vendorExtensions.put("x-client-copy-from-response", operation.allParams.stream()
                        .filter(p -> Boolean.TRUE.equals(p.vendorExtensions.get("x-client-copy-from-response")))
                        .peek(a -> a.vendorExtensions.put("getter", toGetter(a.paramName)))
                        .collect(Collectors.toList()));


                applyToAllParams(operation, (params) -> params.removeIf(pp -> getVendorExtensionBooleanValue(pp, VENDOR_X_CLIENT_IGNORE)) );

                applyToAllParams(operation, (params) ->
                        params.stream()
                                .flatMap(p -> p.vars.stream())
                                .filter(p -> PARAMETERS_TO_HIDE_IN_CLIENT_DOC.contains(p.baseName))
                                .forEach(p -> p.vendorExtensions.put("x-client-doc-hidden", true))
                );

                operation.allParams.stream()
                        .flatMap(p -> p.vars.stream())
                        .forEach(v -> {
                            if (v.dataType.equals("List<String>")) {
                                v.example = "Arrays.asList(" + v.example
                                        .replaceAll("\\[", "")
                                        .replaceAll("\\]", "")
                                        .replaceAll("\\\\\"", "\"") + ")";
                            } else if (v.isArray) {
                                v.example = "Collections.<" + v.items.dataType + ">emptyList()";
                            } else if (v.isString) {
                                v.example = "\"" + v.example + "\"";
                            } else if (v.isDateTime) {
                                v.example = "OffsetDateTime.parse(\"" + v.example + "\")";
                            }
                        });

                if(getVendorExtensionBooleanValue(operation, "x-client-paginated")) {
                    handlePagination(allModels, operation);
                }

                operation.allParams.stream().forEach(param -> {
                    switch(param.dataType) {
                        case "URI":
                            param.vendorExtensions.put("testConstructor", "URI.create(\"https://api.video\")");
                        case "File":
                            param.vendorExtensions.put("testConstructor", "new File(\"\")");
                            break;
                        case "String":
                            param.vendorExtensions.put("testConstructor", Optional.ofNullable(param.example).orElse("\"\""));
                            break;
                        case "Integer":
                            param.vendorExtensions.put("testConstructor", "123");
                            break;
                        default:
                            param.vendorExtensions.put("testConstructor", "new " + param.dataType + "()");
                    }
                });

                operation.responses.forEach(response -> populateOperationResponse(operation, response));

            }
        }
        return super.postProcessOperationsWithModels(objs, allModels);
    }

    @Override
    public Map<String, Object> postProcessModels(Map<String, Object> objs) {
        Map<String, Object> res = super.postProcessModels(objs);
        List<Map> models = (List<Map>) res.get("models");

        models.forEach(model -> {
            ((CodegenModel)model.get("model")).vars.forEach(var -> {
                if (var.defaultValue != null) {
                    ((CodegenModel)model.get("model")).vendorExtensions.put("x-has-defaults", true);
                }
            });
        });
        return res;
    }

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
                String folder = getOutputDir() + "/tests/resources/payloads/" + operation.baseName.toLowerCase() + "/" + operation.vendorExtensions.get("x-client-action") + "/responses/";
                Files.createDirectories(Paths.get(folder));
                PrintWriter out = new PrintWriter(folder + response.code + ".json");
                out.print(responseExample);
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
            return Json.pretty((Map) res.get("value"));
        }

        return null;
    }

    private void handlePagination(List<Object> allModels, CodegenOperation operation) {
        Optional<Map> map = allModels.stream().filter(m -> ((CodegenModel) ((Map) m).get("model")).classname.equals(operation.returnType)).map(a -> (Map) a).findFirst();
        map.ifPresent(a -> {
            CodegenModel model = (CodegenModel) a.get("model");
            System.out.println(model);
            model.allVars.stream().filter(v -> v.name.equals("Data")).findFirst().ifPresent(codegenProperty -> {
                Map<String, String> paginationProperties = new HashMap<>();
                paginationProperties.put("type", codegenProperty.complexType);
                paginationProperties.put("getter", codegenProperty.getter);
                operation.vendorExtensions.put("x-pagination", paginationProperties);
            });
        });
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

    private boolean getVendorExtensionBooleanValue(CodegenOperation operation, String name) {
        return operation.vendorExtensions.containsKey(name) && (boolean) operation.vendorExtensions.get(name);
    }

}
