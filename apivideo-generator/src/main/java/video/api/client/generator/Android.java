package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.AndroidClientCodegen;
import org.openapitools.codegen.templating.mustache.IndentedLambda;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.*;
import org.openapitools.codegen.meta.features.*;
import org.openapitools.codegen.utils.ModelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class Android extends Java {
    // requestPackage and authPackage are used by the "volley" template/library
    protected String requestPackage = "org.openapitools.client.request";
    protected String authPackage = "org.openapitools.client.auth";

    public Android() {
        super();
        
        supportedLibraries.clear();
        supportedLibraries.put("volley", "HTTP client: Volley 1.0.19 (default)");
        supportedLibraries.put("httpclient", "HTTP client: Apache HttpClient 4.3.6. JSON processing: Gson 2.3.1. IMPORTANT: Android client using HttpClient is not actively maintained and will be deprecated in the next major release.");
        CliOption library = new CliOption(CodegenConstants.LIBRARY, "library template (sub-template) to use");
        library.setEnum(supportedLibraries);

        setSerializationLibrary(SERIALIZATION_LIBRARY_GSON);
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(CodegenConstants.INVOKER_PACKAGE)) {
            this.setInvokerPackage((String) additionalProperties.get(CodegenConstants.INVOKER_PACKAGE));
            this.setRequestPackage(invokerPackage + ".request");
            this.setAuthPackage(invokerPackage + ".auth");
        } else {
            //not set, use default to be passed to template
            additionalProperties.put(CodegenConstants.INVOKER_PACKAGE, invokerPackage);
            additionalProperties.put("requestPackage", requestPackage);
            additionalProperties.put("authPackage", authPackage);
        }

        supportingFiles.clear();
        // determine which file (mustache) to add based on library
        if ("volley".equals(getLibrary())) {
            addSupportingFilesForVolley();
        } else if ("httpclient".equals(getLibrary())) {
            addSupportingFilesForHttpClient();
        } else {
            throw new IllegalArgumentException("Invalid 'library' option specified: '" + getLibrary() + "'. Must be 'httpclient' or 'volley' (default)");
        }
        supportingFiles.add(new SupportingFile("JSON.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "JSON.java")); // Replace JsonUtil.java
    }

    public void setRequestPackage(String requestPackage) {
        this.requestPackage = requestPackage;
    }

    public void setAuthPackage(String authPackage) {
        this.authPackage = authPackage;
    }


    private void addSupportingFilesForVolley() {
        // documentation files
        modelDocTemplateFiles.put("model_doc.mustache", ".md");
        apiDocTemplateFiles.put("api_doc.mustache", ".md");
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));

        supportingFiles.add(new SupportingFile("git_push.sh.mustache", "", "git_push.sh"));
        supportingFiles.add(new SupportingFile("gitignore.mustache", "", ".gitignore"));
        supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml"));
        supportingFiles.add(new SupportingFile("settings.gradle.mustache", "", "settings.gradle"));
        supportingFiles.add(new SupportingFile("build.mustache", "", "build.gradle"));
        supportingFiles.add(new SupportingFile("manifest.mustache", projectFolder, "AndroidManifest.xml"));
        supportingFiles.add(new SupportingFile("apiInvoker.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiInvoker.java"));
        //supportingFiles.add(new SupportingFile("jsonUtil.mustache",
        //        (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "JsonUtil.java")); // Not needed. Use JSON.java instead
        supportingFiles.add(new SupportingFile("apiException.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiException.java"));
        supportingFiles.add(new SupportingFile("Pair.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "Pair.java"));
        supportingFiles.add(new SupportingFile("request/getrequest.mustache",
                (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "GetRequest.java"));
        supportingFiles.add(new SupportingFile("request/postrequest.mustache",
                (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PostRequest.java"));
        supportingFiles.add(new SupportingFile("request/putrequest.mustache",
                (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PutRequest.java"));
        supportingFiles.add(new SupportingFile("request/deleterequest.mustache",
                (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "DeleteRequest.java"));
        supportingFiles.add(new SupportingFile("request/patchrequest.mustache",
                (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PatchRequest.java"));
        supportingFiles.add(new SupportingFile("auth/apikeyauth.mustache",
                (sourceFolder + File.separator + authPackage).replace(".", File.separator), "ApiKeyAuth.java"));
        supportingFiles.add(new SupportingFile("auth/httpbasicauth.mustache",
                (sourceFolder + File.separator + authPackage).replace(".", File.separator), "HttpBasicAuth.java"));
        supportingFiles.add(new SupportingFile("auth/authentication.mustache",
                (sourceFolder + File.separator + authPackage).replace(".", File.separator), "Authentication.java"));

        // gradle wrapper files
        supportingFiles.add(new SupportingFile("gradlew.mustache", "", "gradlew"));
        supportingFiles.add(new SupportingFile("gradlew.bat.mustache", "", "gradlew.bat"));
        supportingFiles.add(new SupportingFile("gradle-wrapper.properties.mustache",
                gradleWrapperPackage.replace(".", File.separator), "gradle-wrapper.properties"));
        supportingFiles.add(new SupportingFile("gradle-wrapper.jar",
                gradleWrapperPackage.replace(".", File.separator), "gradle-wrapper.jar"));
    }

    private void addSupportingFilesForHttpClient() {
        // documentation files
        modelDocTemplateFiles.put("model_doc.mustache", ".md");
        apiDocTemplateFiles.put("api_doc.mustache", ".md");
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));

        supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml"));
        supportingFiles.add(new SupportingFile("settings.gradle.mustache", "", "settings.gradle"));
        supportingFiles.add(new SupportingFile("build.mustache", "", "build.gradle"));
        supportingFiles.add(new SupportingFile("manifest.mustache", projectFolder, "AndroidManifest.xml"));
        supportingFiles.add(new SupportingFile("apiInvoker.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiInvoker.java"));
        supportingFiles.add(new SupportingFile("httpPatch.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "HttpPatch.java"));
        //supportingFiles.add(new SupportingFile("jsonUtil.mustache",
        //        (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "JsonUtil.java"));
        supportingFiles.add(new SupportingFile("apiException.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiException.java"));
        supportingFiles.add(new SupportingFile("Pair.mustache",
                (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "Pair.java"));
        supportingFiles.add(new SupportingFile("git_push.sh.mustache", "", "git_push.sh"));
        supportingFiles.add(new SupportingFile("gitignore.mustache", "", ".gitignore"));

        // gradle wrapper files
        supportingFiles.add(new SupportingFile("gradlew.mustache", "", "gradlew"));
        supportingFiles.add(new SupportingFile("gradlew.bat.mustache", "", "gradlew.bat"));
        supportingFiles.add(new SupportingFile("gradle-wrapper.properties.mustache",
                gradleWrapperPackage.replace(".", File.separator), "gradle-wrapper.properties"));
        supportingFiles.add(new SupportingFile("gradle-wrapper.jar",
                gradleWrapperPackage.replace(".", File.separator), "gradle-wrapper.jar"));

    }

}
