package video.api.client.generator;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import static org.openapitools.codegen.utils.StringUtils.underscore;

/**
 * Converts CamelCase text in a fragment to underscore.
 *
 * Register:
 * <pre>
 * additionalProperties.put("underscore", new UnderscoreLambda());
 * </pre>
 *
 * Use:
 * <pre>
 * {{#underscore}}{{summary}}{{/underscore}}
 * </pre>
 */
public class UnderscoreLambda implements Mustache.Lambda {

    private final boolean isForPath;

    public UnderscoreLambda() {
        this(false);
    }

    public UnderscoreLambda(boolean isForPath) {
        this.isForPath = isForPath;
    }

    @Override
    public void execute(Template.Fragment fragment, Writer writer) throws IOException {
        String text = fragment.execute();
        if (this.isForPath) {
            String[] arrOfStr = text.split("((?=\\{.+\\}))");
            for (String a: arrOfStr) {
                if (a.charAt(0) == '{') {
                    writer.write(underscore(a));
                } else {
                    writer.write(a);
                }
            }
        } else {
            writer.write(underscore(text));
        }
    }
}