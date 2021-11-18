package video.api.client.generator;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.meta.features.*;
import org.openapitools.codegen.languages.AbstractPhpCodegen;
import org.openapitools.codegen.templating.mustache.LowercaseLambda;
import org.openapitools.codegen.templating.mustache.TitlecaseLambda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.openapitools.codegen.utils.StringUtils.camelize;


public class Php extends AbstractPhpCodegen {
    @SuppressWarnings("hiding")
    private final Logger LOGGER = LoggerFactory.getLogger(Php.class);

    public Php() {
        super();

        modifyFeatureSet(features -> features
                // .includeDocumentationFeatures(DocumentationFeature.Readme)
                .wireFormatFeatures(EnumSet.of(WireFormatFeature.JSON, WireFormatFeature.XML))
                .securityFeatures(EnumSet.noneOf(SecurityFeature.class))
                .excludeGlobalFeatures(
                        GlobalFeature.XMLStructureDefinitions,
                        GlobalFeature.Callbacks,
                        GlobalFeature.LinkObjects,
                        GlobalFeature.ParameterStyling
                )
                .excludeSchemaSupportFeatures(
                        SchemaSupportFeature.Polymorphism
                )
        );

        // clear import mapping (from default generator) as php does not use it
        // at the moment
        importMapping.clear();

        setInvokerPackage("OpenAPI\\Client");
        setApiPackage(getInvokerPackage() + "\\" + apiDirName);
        setModelPackage(getInvokerPackage() + "\\" + modelDirName);
        setPackageName("OpenAPIClient-php");
        supportsInheritance = true;
        setOutputDir("generated-code" + File.separator + "php");
        modelTestTemplateFiles.put("model_test.mustache", ".php");
        embeddedTemplateDir = templateDir = "php";

        // default HIDE_GENERATION_TIMESTAMP to true
        hideGenerationTimestamp = Boolean.TRUE;

        // provide primitives to mustache template
        List sortedLanguageSpecificPrimitives = new ArrayList(languageSpecificPrimitives);
        Collections.sort(sortedLanguageSpecificPrimitives);
        String primitives = "'" + StringUtils.join(sortedLanguageSpecificPrimitives, "', '") + "'";
        additionalProperties.put("primitives", primitives);
        this.languageSpecificPrimitives.remove("\\DateTime");
        cliOptions.add(new CliOption(CodegenConstants.HIDE_GENERATION_TIMESTAMP, CodegenConstants.ALLOW_UNICODE_IDENTIFIERS_DESC)
                .defaultValue(Boolean.TRUE.toString()));
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    @Override
    public String getName() {
        return "php";
    }

    @Override
    public String getHelp() {
        return "Generates a PHP client library.";
    }

    public void preprocessOpenAPI(OpenAPI openAPI) {
        Map<String, PathItem> pathItems = openAPI.getPaths();
        io.swagger.v3.oas.models.Paths newPaths = new io.swagger.v3.oas.models.Paths();

        // remove Auth endpoints
        if (pathItems != null) {
            for (Map.Entry<String, PathItem> e : pathItems.entrySet()) {
                List<Operation> ops = e.getValue().readOperations();
                if (!e.getKey().startsWith("/auth/")) {
                    newPaths.addPathItem(e.getKey(), e.getValue());

                    for (Operation operation : ops) {
                        List<Parameter> parameters = new ArrayList<Parameter>();
                        if (operation.getParameters() != null) {

                            // remove ignored params from operation parameters
                            for (Parameter param: operation.getParameters()) {
                                Map<String, Object> extensions = param.getExtensions();
                                parameters.add(param);
                            }
                            operation.setParameters(parameters);
                        }
                    }
                }
            }
        }
        openAPI.setPaths(newPaths);
        super.preprocessOpenAPI(openAPI);
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
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {

        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");


            for (CodegenOperation operation : ops) {
                if(operation.vendorExtensions.containsKey("x-readme")) {
                    Map<String, List> xReadme = (Map<String, List>) operation.vendorExtensions.get("x-readme");
                    if(xReadme.containsKey("code-samples")) {
                        List<Map<String, String>> codeSamples = xReadme.get("code-samples");
                        Optional<Map<String, String>> first = codeSamples.stream().filter(codeSample -> "php".equals(codeSample.get("language"))).findFirst();
                        first.ifPresent(map -> operation.vendorExtensions.put("code-sample", map.get("code")));
                    }
                }

                operation.vendorExtensions.put("x-client-copy-from-response", operation.allParams.stream()
                        .filter(p -> Boolean.TRUE.equals(p.vendorExtensions.get("x-client-copy-from-response")))
                        .peek(a -> a.vendorExtensions.put("getter", toGetter(a.paramName)))
                        .collect(Collectors.toList()));

                // overwrite operationId & nickname values of the operation with the x-client-action
                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = camelize((String) operation.vendorExtensions.get("x-client-action"));
                } else {
                    throw new RuntimeException("Missing x-client-action value for operation " + operation.operationId);
                }

                operation.queryParams.forEach(param -> {
                    if("form".equals(param.style)) {
                        param.vendorExtensions.put("isArray", param.isArray);
                        param.vendorExtensions.put("isForm", true);
                        param.vendorExtensions.put("isFormOrDeepObject", true);
                    }
                    else if("deepObject".equals(param.style)) {
                        param.vendorExtensions.put("isDeepObject", true);
                        param.vendorExtensions.put("isFormOrDeepObject", true);
                    }
                });
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
                if(var.isArray && var.complexType != null) {
                    var.vendorExtensions.put("x-type-complex-array", true);
                } else if(var.isPrimitiveType) {
                    var.vendorExtensions.put("x-type-primitive", true);
                } else {
                    var.vendorExtensions.put("x-type-other", true);
                }
            });
        });
        return res;
    }

    @Override
    public void processOpts() {
        super.processOpts();

        additionalProperties.put("titlecase", new TitlecaseLambda());
        additionalProperties.put("unescape", new UnescapeLambda());
        additionalProperties.put("lower", new LowercaseLambda());

        supportingFiles.add(new SupportingFile("src/Request.php", toSrcPath(invokerPackage, srcBasePath), "Request.php"));
        supportingFiles.add(new SupportingFile("src/Authenticator.php", toSrcPath(invokerPackage, srcBasePath), "Authenticator.php"));
        supportingFiles.add(new SupportingFile("src/Client.mustache", toSrcPath(invokerPackage, srcBasePath), "Client.php"));
        supportingFiles.add(new SupportingFile("src/BaseClient.mustache", toSrcPath(invokerPackage, srcBasePath), "BaseClient.php"));
        supportingFiles.add(new SupportingFile("src/ObjectSerializer.mustache", toSrcPath(invokerPackage, srcBasePath), "ObjectSerializer.php"));
        supportingFiles.add(new SupportingFile("src/VideoUploader.php", toSrcPath(invokerPackage, srcBasePath), "VideoUploader.php"));
        supportingFiles.add(new SupportingFile("src/ProgressiveUploadSession.php", toSrcPath(invokerPackage, srcBasePath), "ProgressiveUploadSession.php"));

        // Exceptions
        supportingFiles.add(new SupportingFile("src/Exception/ExceptionInterface.php", toSrcPath(invokerPackage, srcBasePath), "Exception/ExceptionInterface.php"));
        supportingFiles.add(new SupportingFile("src/Exception/HttpException.php", toSrcPath(invokerPackage, srcBasePath), "Exception/HttpException.php"));
        supportingFiles.add(new SupportingFile("src/Exception/ExpiredAuthTokenException.php", toSrcPath(invokerPackage, srcBasePath), "Exception/ExpiredAuthTokenException.php"));

        // Api
        supportingFiles.add(new SupportingFile("src/Api/ApiInterface.php", toSrcPath(invokerPackage, srcBasePath), "Api/ApiInterface.php"));

        // Model
        supportingFiles.add(new SupportingFile("src/Model/ModelInterface.mustache", toSrcPath(modelPackage, srcBasePath), "ModelInterface.php"));
        supportingFiles.add(new SupportingFile("src/Model/ModelDefinition.mustache", toSrcPath(modelPackage, srcBasePath), "ModelDefinition.php"));

        // Tests
        supportingFiles.add(new SupportingFile("tests/Api/AbstractApiTest.php", "", "tests/Api/AbstractApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/CaptionsApiTest.php", "", "tests/Api/CaptionsApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/ChaptersApiTest.php", "", "tests/Api/ChaptersApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/Helper.php", "", "tests/Api/Helper.php"));
        supportingFiles.add(new SupportingFile("tests/Api/LiveStreamsApiTest.php", "", "tests/Api/LiveStreamsApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/PlayerThemesApiTest.php", "", "tests/Api/PlayerThemesApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/RawStatisticsApiTest.php", "", "tests/Api/RawStatisticsApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/UploadTokensApiTest.php", "", "tests/Api/UploadTokensApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/VideosApiTest.php", "", "tests/Api/VideosApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Api/WebhooksApiTest.php", "", "tests/Api/WebhooksApiTest.php"));
        supportingFiles.add(new SupportingFile("tests/Model/VideoTest.php", "", "tests/Model/VideoTest.php"));
        supportingFiles.add(new SupportingFile("phpunit.xml.dist", "", "phpunit.xml.dist"));

        // Tests resources
        supportingFiles.add(new SupportingFile("tests/resources/caption.vtt", "", "tests/resources/caption.vtt"));
        supportingFiles.add(new SupportingFile("tests/resources/chapters.vtt", "", "tests/resources/chapters.vtt"));
        supportingFiles.add(new SupportingFile("tests/resources/logo.png", "", "tests/resources/logo.png"));
        supportingFiles.add(new SupportingFile("tests/resources/thumbnail.jpeg", "", "tests/resources/thumbnail.jpeg"));

        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));
        supportingFiles.add(new SupportingFile("post-generate.sh", "", "post-generate.sh"));

        supportingFiles.add(new SupportingFile("composer.json", "", "composer.json"));
    }
}