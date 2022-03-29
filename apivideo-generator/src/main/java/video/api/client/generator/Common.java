package video.api.client.generator;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Common {
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

                if(operation.notes != null) {
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
                            int toSkip = 0;
                            for (String line : lines) {
                                if (!StringUtils.isBlank(line)
                                        && !line.startsWith("// First")
                                        && !line.startsWith("// Documentation")) {
                                    break;
                                }
                                toSkip++;
                            }

                            if(!Arrays.stream(lines).map(String::trim).allMatch(l -> l.startsWith("//") || l.startsWith("#") || l.length() == 0 )) {
                                operation.vendorExtensions.put("code-sample", String.join("\n", Arrays.asList(lines).subList(toSkip, lines.length)));
                            }

                        });
                    }
                }
            }
        }
    }
}
