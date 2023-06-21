package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.utils.ModelUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Common {

    public static Comparator<CodegenOperation> getCodegenOperationComparator() {
        Map<String, Integer> operationIndexes = new HashMap<>();
        operationIndexes.put("create", 1);
        operationIndexes.put("createToken", 2);
        operationIndexes.put("upload", 50);
        operationIndexes.put("uploadWithUploadToken", 75);
        operationIndexes.put("get", 100);
        operationIndexes.put("getToken", 100);
        operationIndexes.put("update", 200);
        operationIndexes.put("delete", 300);
        operationIndexes.put("deleteToken", 300);
        operationIndexes.put("list", 400);
        operationIndexes.put("uploadThumbnail", 500);
        operationIndexes.put("pickThumbnail", 501);
        operationIndexes.put("deleteThumbnail", 502);
        operationIndexes.put("uploadLogo", 600);
        operationIndexes.put("deleteLogo", 601);


        return (o1, o2) -> {
            String firstOpName = (String) o1.vendorExtensions.get("x-client-action");
            String secondOpName = (String) o2.vendorExtensions.get("x-client-action");

            Integer firstOpIndex = Optional.ofNullable(operationIndexes.get(firstOpName)).orElse(5000);
            Integer secondOpIndex = Optional.ofNullable(operationIndexes.get(secondOpName)).orElse(5000);

            if (!firstOpIndex.equals(secondOpIndex)) {
                return firstOpIndex - secondOpIndex;
            }
            return firstOpName.compareTo(secondOpName);
        };
    }


    public static void replaceDescriptionsAndSamples(Map<String, Object> objs, String language) {
        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");
            for (CodegenOperation operation : ops) {
                boolean done = false;
                if (operation.vendorExtensions.containsKey("x-client-description")) {
                    Map<String, Object> xClientDescription = (Map<String, Object>) operation.vendorExtensions.get("x-client-description");
                    if (xClientDescription.containsKey("per-language")) {
                        ArrayList<Map<String, String>> perLanguage = (ArrayList<Map<String, String>>) xClientDescription.get("per-language");
                        for (Map<String, String> stringStringMap : perLanguage) {
                            if (stringStringMap.containsKey(language)) {
                                operation.notes = stringStringMap.get(language);
                                operation.unescapedNotes = stringStringMap.get(language);
                                done = true;
                                break;
                            }
                        }
                    }

                    if (!done && xClientDescription.containsKey("default")) {
                        String desc = (String) xClientDescription.get("default");
                        operation.notes = desc;
                        operation.unescapedNotes = desc;
                    }
                }

                if (operation.notes != null) {
                    operation.notes = operation.notes.replaceAll("\n", "\n\n");
                    operation.unescapedNotes = operation.unescapedNotes.replaceAll("\n", "\n\n");
                }

                if (operation.vendorExtensions.containsKey("x-readme")) {
                    Map<String, List> xReadme = (Map<String, List>) operation.vendorExtensions.get("x-readme");
                    if (xReadme.containsKey("code-samples")) {
                        List<Map<String, String>> codeSamples = xReadme.get("code-samples");
                        Optional<Map<String, String>> first = codeSamples.stream().filter(codeSample -> language.equals(codeSample.get("language"))).findFirst();
                        first.ifPresent(map -> {
                            String code = map.get("code");
                            String[] lines = code.split("\n");

                            List<String> list = Arrays.stream(lines).filter(l -> !l.startsWith("// Documentation:") && !l.startsWith("# Documentation:")).collect(Collectors.toList());

                            int toSkip = 0;
                            for (String line : list) {
                                if (!StringUtils.isBlank(line)
                                        && !line.startsWith("// First")
                                        && !line.startsWith("// Documentation")) {
                                    break;
                                }
                                toSkip++;
                            }

                            if (!Arrays.stream(lines).map(String::trim).allMatch(l -> l.startsWith("//") || l.startsWith("#") || l.length() == 0)) {
                                operation.vendorExtensions.put("code-sample", String.join("\n", list.subList(toSkip, list.size())));
                            }

                        });
                    }
                }
            }
        }
    }

    public static void populateOperationResponse(OpenAPI openApi, CodegenOperation operation, CodegenResponse response, Map<String, Object> additionalProperties, String folder) {
        response.vendorExtensions.put("allParams", operation.allParams);
        response.vendorExtensions.put("x-client-action", operation.vendorExtensions.get("x-client-action"));
        response.vendorExtensions.put("x-group-parameters", operation.vendorExtensions.get("x-group-parameters"));
        response.vendorExtensions.put("x-client-paginated", operation.vendorExtensions.get("x-client-paginated"));
        response.vendorExtensions.put("x-pagination", operation.vendorExtensions.get("x-pagination"));
        response.vendorExtensions.put("x-is-error", response.is4xx || response.is5xx);
        response.vendorExtensions.put("lambda", additionalProperties.get("lambda"));

        List<String> responseExamples = getResponseExample(openApi, response);
        if (responseExamples != null) {
            int index = 0;
            for (String responseExample : responseExamples) {
                try {
                    Map<String, String> exampleMap = Json.mapper().readerFor(Map.class).readValue(responseExample);
                    if (exampleMap.containsKey("title")) {
                        response.vendorExtensions.put("x-example-response", exampleMap);
                    }
                    response.vendorExtensions.put("x-example-response-json", responseExample);
                } catch (JsonProcessingException ignored) {
                }

                if(folder != null) {
                    try {
                        Files.createDirectories(Paths.get(folder));
                        PrintWriter out = new PrintWriter(folder + response.code + (responseExamples.size() > 1 ? "-" + index : "") + ".json");
                        out.print(responseExample);
                        out.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    index++;
                }
            }
        }
    }

    /**
     * returns the JSON example from an openapi response
     */
    public static List<String> getResponseExample(OpenAPI openAPI, CodegenResponse response) {
        Map map;
        try {
            map = Json.mapper().readerFor(Map.class).readValue(response.jsonSchema);
        } catch (JsonProcessingException e) {
            return null;
        }
        Map content = (Map) map.get("content");
        if (content == null) {
            return null;
        }
        Collection<Map> values = content.values();
        for (Map v : values) {
            Map examples = (Map<String, Map>) v.get("examples");
            if (examples == null) continue;

            Collection<Map> exampleValues = examples.values();

            return exampleValues.stream().map(exampleValue -> {
                if(exampleValue.containsKey("$ref")) {
                    String ref = (String) exampleValue.get("$ref");
                    String prefix = "#/components/examples/";
                    if(ref.startsWith(prefix)) {
                        return Json.pretty(openAPI.getComponents().getExamples().get(ref.substring(prefix.length())));
                    }
                }
                return Json.pretty(exampleValue.get("value"));
            }).collect(Collectors.toList());
        }

        return null;
    }
}
