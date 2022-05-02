package video.api.client.generator;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenOperation;

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
}
