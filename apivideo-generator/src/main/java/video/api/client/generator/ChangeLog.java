package video.api.client.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChangeLog {

    private List<Version> versions = new ArrayList<>();

    public void writeTo(String outputDir) {
        Path outputFile = Paths.get(outputDir, "CHANGELOG.md");

        String content = "# Changelog\nAll changes to this project will be documented in this file.\n\n" +
                this.versions.stream().map(v -> {
            String changes = v.changes.stream().map(c -> "- " + c).collect(Collectors.joining("\n"));
            return (v.getDate() != null)
                    ? String.format("## [%s] - %s\n%s", v.name, v.date, changes)
                    : String.format("## [%s]\n%s", v.name, changes);
        }).collect(Collectors.joining("\n\n")) + "\n";

        try {
            FileWriter fileWriter = new FileWriter(outputFile.toFile());
            fileWriter.write(content);
            fileWriter.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Version {
        private String name;
        private String date;
        private List<String> changes;

        public Version(String name, String date, List<String> changes) {
            this.name = name;
            this.date = date;
            this.changes = changes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getChanges() {
            return changes;
        }

        public void setChanges(List<String> changes) {
            this.changes = changes;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private ChangeLog() {
    }

    private ChangeLog(ArrayList<HashMap> changelogEntries) {
        changelogEntries.forEach(map -> {
            Set<Map.Entry<String, ArrayList<String>>> entrySet = map.entrySet();
            entrySet.forEach(entry -> {
                Pattern pattern = Pattern.compile("(.*)[\\s]+\\((.*)\\)");
                Matcher matcher = pattern.matcher(entry.getKey());
                if(matcher.matches()) {
                    versions.add(new Version(matcher.group(1), matcher.group(2), entry.getValue()));
                } else {
                    versions.add(new Version(entry.getKey(), null, entry.getValue()));
                }
            });
        });
    }

    public Version getLastVersion() {
        return versions.isEmpty() ? null : versions.get(0);
    }

    public static ChangeLog parse(Map<String, Object> additionalProperties) {
        if (!additionalProperties.containsKey("changelog")) {
            throw new IllegalArgumentException("Missing 'versions' entry in config file");
        }

        ChangeLog changelog = new ChangeLog((ArrayList<HashMap>) additionalProperties.get("changelog"));

        if (changelog.versions.isEmpty()) {
            throw new IllegalArgumentException("'versions' entry in config file is empty");
        }

        return changelog;
    }
}
