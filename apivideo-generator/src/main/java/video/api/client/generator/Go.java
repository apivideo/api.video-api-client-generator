package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import io.swagger.util.Json;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.*;
import org.openapitools.codegen.config.GlobalSettings;
import org.openapitools.codegen.languages.GoClientCodegen;
import org.openapitools.codegen.templating.mustache.LowercaseLambda;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.openapitools.codegen.utils.StringUtils.underscore;

public class Go extends GoClientCodegen {

    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";

    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    public Go() {
        apiNameSuffix = "";
        typeMapping.put("DateTime", "string");
    }

    public CodegenProperty fromProperty(String name, Schema p) {
        if(p == null) {
            return new CodegenProperty();
        }
        return super.fromProperty(name, p);
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
        Common.replaceDescriptionsAndSamples(objs, "go");

        Map<String, CodegenModel> model = new HashMap<>();
        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        Set<String> additionalImports = new HashSet<>();

        Set<String> seenModelNames = new HashSet<>();

        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            ops.sort(Common.getCodegenOperationComparator());

            // if all operations of a path have x-client-hidden, add x-client-hidden on the path
            if(ops.stream().allMatch(op -> Boolean.TRUE.equals(op.vendorExtensions.get(VENDOR_X_CLIENT_HIDDEN)))) {
                objs.put(VENDOR_X_CLIENT_HIDDEN, true);
            }

            for (CodegenOperation operation : ops) {
                seenModelNames.add(operation.returnType);

                operation.vendorExtensions.put("x-client-copy-from-response", operation.allParams.stream()
                        .filter(p -> Boolean.TRUE.equals(p.vendorExtensions.get("x-client-copy-from-response")))
                        .peek(a -> a.vendorExtensions.put("getter", StringUtils.capitalize(a.paramName)))
                        .collect(Collectors.toList()));

                if(operation.formParams.stream().anyMatch(p -> p.dataFormat.equals("binary"))) {
                    operation.vendorExtensions.put("x-upload", true);
                }
                if(operation.vendorExtensions.containsKey("x-client-chunk-upload")) {
                    objs.put("x-client-chunk-upload", true);
                }

                if(operation.vendorExtensions.containsKey("x-group-parameters")) {
                    operation.queryParams.forEach(q -> {
                        q.vendorExtensions.put("x-group-parameters", true);
                    });
                }

                operation.queryParams.forEach(queryParam -> {
                    if(queryParam.vendorExtensions.containsKey("x-is-deep-object")) additionalImports.add("fmt");
                });

                // overwrite operationId & nickname values of the operation with the x-client-action
                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = StringUtils.capitalize((String) operation.vendorExtensions.get("x-client-action"));
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

        Map<String, Object> stringObjectMap = super.postProcessOperationsWithModels(objs, allModels);

        ArrayList<HashMap<String, String>> imports = (ArrayList<HashMap<String, String>>) stringObjectMap.get("imports");
        if(imports != null) {
            if(imports.stream().anyMatch(map -> map.get("import").equals("os"))) {
                HashMap<String, String> ioMap = new HashMap<>();
                ioMap.put("import", "io");
                imports.add(ioMap);
            }
            additionalImports.forEach(imp -> {
                HashMap<String, String> ioMap = new HashMap<>();
                ioMap.put("import", imp);
                imports.add(ioMap);
            });
        }
        //imports.add(Collections.singletonMap("import", "os"))

        //System.out.println(stringObjectMap.get())

        return stringObjectMap;
    }


    private void recursivelyFindModels(List<CodegenModel> models, Set<String> modelClassNames, Set<CodegenModel> result) {
        for (String modelClassName : modelClassNames) {
            List<CodegenModel> toAdd = models
                    .stream()
                    .filter(m -> m.classname.equals(modelClassName))
                    .filter(m -> !result.contains(m))
                    .collect(Collectors.toList());

            result.addAll(toAdd);

            Set<String> collect = toAdd
                    .stream()
                    .flatMap(m -> m.vars
                            .stream()
                            .map(v -> v.dataType.startsWith("[]") ? v.dataType.substring(2) : v.dataType))
                    .collect(Collectors.toSet());

            recursivelyFindModels(models, collect, result);
        }


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

        ChangeLog changelog = ChangeLog.parse(additionalProperties);
        additionalProperties.put("artifactVersion", changelog.getLastVersion().getName());
        changelog.writeTo(this.getOutputDir());

        additionalProperties.put("lower", new LowercaseLambda());
        additionalProperties.put("unescape", new UnescapeLambda());

        List<String> ignoredFiles = (List<String>) Optional.ofNullable(additionalProperties.get("ignoredFiles")).orElse(Collections.emptyList());
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

    @Override
    public Map<String, Object> postProcessModels(Map<String, Object> objs) {
        Map<String, Object> stringObjectMap = super.postProcessModels(objs);

        List<Map<String, Object>> models = (List)objs.get("models");
        models.forEach(map -> {
            CodegenModel model = ((CodegenModel)map.get("model"));
            model.vars.forEach(var -> {
                if(var.dataType.equals("NullableTime")) {
                    var.dataType = "NullableString";
                    var.isString = true;
                    var.isDateTime = false;
                }
                if(var.vendorExtensions.containsKey("x-optional-nullable")) {
                    var.dataType = "Nullable" + Character.toUpperCase(var.dataType.charAt(0))
                            + var.dataType.substring(1);
                    var.isNullable = true;
                }
            });
        });
        return stringObjectMap;
    }
}
