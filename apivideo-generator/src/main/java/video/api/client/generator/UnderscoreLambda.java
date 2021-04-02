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
    @Override
    public void execute(Template.Fragment fragment, Writer writer) throws IOException {
        String text = fragment.execute();
        writer.write(underscore(text));
    }
}