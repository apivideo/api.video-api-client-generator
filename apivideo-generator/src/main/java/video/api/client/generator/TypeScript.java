package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import io.swagger.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.*;
import org.openapitools.codegen.meta.GeneratorMetadata;
import org.openapitools.codegen.meta.Stability;
import org.openapitools.codegen.meta.features.DocumentationFeature;
import org.openapitools.codegen.utils.ModelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

import static org.openapitools.codegen.utils.StringUtils.camelize;
import static org.openapitools.codegen.utils.StringUtils.underscore;

public class TypeScript extends DefaultCodegen {
    private static final String X_DISCRIMINATOR_TYPE = "x-discriminator-value";
    private static final String UNDEFINED_VALUE = "undefined";

    private static final String PLATFORM_SWITCH = "platform";
    private static final String PLATFORM_SWITCH_DESC = "Specifies the platform the code should run on. The default is 'node' for the 'request' framework and 'browser' otherwise.";
    private static final String[] PLATFORMS = { "browser", "node", "deno" };
    private static final String FILE_CONTENT_DATA_TYPE = "fileContentDataType";
    private static final String FILE_CONTENT_DATA_TYPE_DESC = "Specifies the type to use for the content of a file - i.e. Blob (Browser, Deno) / Buffer (node)";

    public static final String TEST_PACKAGE = "testPackage";
    public static final String DOC_PACKAGE = "docPackage";

    public static final String API_TEST_FILE_FOLDER = "apiTestFileFolder";
    public static final String MODEL_TEST_FILE_FOLDER = "modelTestFileFolder";
    public static final String API_DOC_FILE_FOLDER = "apiDocFileFolder";
    public static final String MODEL_DOC_FILE_FOLDER = "modelDocFileFolder";

    protected String docPackage = "";
    protected String apiDocFileFolder = "";
    protected String modelDocFileFolder = "";
    protected String apiTestFileFolder = "";
    protected String modelTestFileFolder = "";

    // npm options
    private static final String NPM_NAME = "npmName";
    private static final String NPM_VERSION = "npmVersion";
    private static final String NPM_REPOSITORY = "npmRepository";

    // npm Option Values
    protected String npmName = null;
    protected String npmVersion = "1.0.0";
    protected String npmRepository = null;

    protected String modelPropertyNaming = "camelCase";
    protected HashSet<String> languageGenericTypes;

    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";

    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeScript.class);

    public TypeScript() {
        super();
        this.generatorMetadata = GeneratorMetadata.newBuilder(generatorMetadata).stability(Stability.EXPERIMENTAL).build();

        // clear import mapping (from default generator) as TS does not use it at the moment
        importMapping.clear();
        outputFolder = "generated-code" + File.separator + templateDir;
        embeddedTemplateDir = templateDir;

        supportsInheritance = true;

        // NOTE: TypeScript uses camel cased reserved words, while models are title cased. We don't want lowercase comparisons.
        reservedWords.addAll(Arrays.asList(
                // local variable names used in API methods (endpoints)
                "localVarPath", "queryParameters", "headerParams", "formParams", "formData", "useFormData", "varLocalDeferred",
                "requestOptions",
                // Typescript reserved words
                "abstract", "await", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "debugger", "default", "delete", "do", "double", "else", "enum", "export", "extends", "false", "final", "finally", "float", "for", "function", "goto", "if", "implements", "import", "in", "instanceof", "int", "interface", "let", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "throw", "transient", "true", "try", "typeof", "var", "void", "volatile", "while", "with", "yield"
        ));

        languageSpecificPrimitives = new HashSet<>(Arrays.asList(
                "string",
                "String",
                "boolean",
                "Boolean",
                "Double",
                "Integer",
                "Long",
                "Float",
                "Object",
                "Array",
                "Date",
                "number",
                "any",
                "File",
                "Error",
                "Map"
        ));

        languageGenericTypes = new HashSet<String>(Arrays.asList(
                "Array"
        ));

        instantiationTypes.put("array", "Array");

        typeMapping = new HashMap<String, String>();
        typeMapping.put("Array", "Array");
        typeMapping.put("array", "Array");
        typeMapping.put("List", "Array");
        typeMapping.put("boolean", "boolean");
        typeMapping.put("string", "string");
        typeMapping.put("int", "number");
        typeMapping.put("float", "number");
        typeMapping.put("number", "number");
        typeMapping.put("long", "number");
        typeMapping.put("short", "number");
        typeMapping.put("char", "string");
        typeMapping.put("double", "number");
        typeMapping.put("object", "any");
        typeMapping.put("integer", "number");
        typeMapping.put("Map", "any");
        typeMapping.put("date", "string");
        typeMapping.put("DateTime", "Date");
        typeMapping.put("binary", "any");
        typeMapping.put("File", "any");
        typeMapping.put("ByteArray", "string");
        typeMapping.put("UUID", "string");
        typeMapping.put("Error", "Error");
        typeMapping.put("AnyType", "any");
        typeMapping.put("URI", "string");
        typeMapping.put("set", "Array");
        typeMapping.put("HttpFile", "File");

        cliOptions.add(new CliOption(NPM_NAME, "The name under which you want to publish generated npm package." +
                " Required to generate a full package"));
        cliOptions.add(new CliOption(NPM_VERSION, "The version of your npm package. If not provided, using the version from the OpenAPI specification file.").defaultValue(this.getNpmVersion()));
        cliOptions.add(new CliOption(NPM_REPOSITORY, "Use this property to set an url your private npmRepo in the package.json"));
        cliOptions.add(new CliOption(CodegenConstants.MODEL_PROPERTY_NAMING, CodegenConstants.MODEL_PROPERTY_NAMING_DESC).defaultValue("camelCase"));
        cliOptions.add(new CliOption(CodegenConstants.SUPPORTS_ES6, CodegenConstants.SUPPORTS_ES6_DESC).defaultValue("false"));
        cliOptions.add(new CliOption(FILE_CONTENT_DATA_TYPE,FILE_CONTENT_DATA_TYPE_DESC).defaultValue("Buffer"));

        CliOption platformOption = new CliOption(PLATFORM_SWITCH, PLATFORM_SWITCH_DESC);
        for (String option: PLATFORMS) {
            platformOption.addEnum(option, option);
        }
        platformOption.defaultValue(PLATFORMS[0]);

        cliOptions.add(platformOption);

        modifyFeatureSet(features -> features.includeDocumentationFeatures(DocumentationFeature.Api, DocumentationFeature.Model));
    }

    @Override
    public void setParameterExampleValue(CodegenParameter codegenParameter) {
        super.setParameterExampleValue((codegenParameter));
        if(codegenParameter.example == null) {
            if(codegenParameter.isBodyParam) {
                List<CodegenProperty> vars = codegenParameter.vars;
                StringBuilder stringBuilder = new StringBuilder("{\n");
                vars.stream().forEach(v -> {
                    stringBuilder.append("\t\t\t");
                    stringBuilder.append(v.name);
                    stringBuilder.append(": ");
                    stringBuilder.append(getPropertyExample(v));
                    stringBuilder.append(",");
                    if(v.description != null) {
                        stringBuilder.append(" // ");
                        stringBuilder.append(v.description);
                    }
                    stringBuilder.append("\n");
                });
                stringBuilder.append("\t\t}");
                codegenParameter.example = stringBuilder.toString();
            }
        }
    }


    private String getPropertyExample(CodegenProperty prop) {

        if(prop.example != null && !prop.example.equals("null")) {
            if(prop.isFile || prop.isDateTime || prop.isDateTime || prop.isUuid || prop.isUri || prop.isString) {
                return "\"" + prop.example + "\"";
            }
            if(prop.isArray) {
                System.out.println(prop.example);
            }
            return prop.example;
        }
        if (prop.vendorExtensions != null && prop.vendorExtensions.containsKey("x-example")) {
            return io.swagger.v3.core.util.Json.pretty(prop.vendorExtensions.get("x-example"));
        } else if (Boolean.TRUE.equals(prop.isBoolean)) {
            return "true";
        } else if (Boolean.TRUE.equals(prop.isLong)) {
            return "789";
        } else if (Boolean.TRUE.equals(prop.isInteger)) {
            return "56";
        } else if (Boolean.TRUE.equals(prop.isFloat)) {
            return "3.4";
        } else if (Boolean.TRUE.equals(prop.isDouble)) {
            return "1.2";
        } else if (Boolean.TRUE.equals(prop.isNumber)) {
            return "8.14";
        } else if (Boolean.TRUE.equals(prop.isBinary)) {
            return "BINARY_DATA_HERE";
        } else if (Boolean.TRUE.equals(prop.isByteArray)) {
            return "BYTE_ARRAY_DATA_HERE";
        } else if (Boolean.TRUE.equals(prop.isFile)) {
            return "\"/path/to/file.txt\"";
        } else if (Boolean.TRUE.equals(prop.isDate)) {
            return "\"2013-10-20\"";
        } else if (Boolean.TRUE.equals(prop.isDateTime)) {
            return "\"2013-10-20T19:20:30+01:00\"";
        } else if (Boolean.TRUE.equals(prop.isUuid)) {
            return "\"38400000-8cf0-11bd-b23e-10b96e4ef00d\"";
        } else if (Boolean.TRUE.equals(prop.isUri)) {
            return "\"https://openapi-generator.tech\"";
        } else if (Boolean.TRUE.equals(prop.isString)) {
            return "\"" + prop.name + "_example\"";
        } else if (Boolean.TRUE.equals(prop.isFreeFormObject)) {
            return "Object";
        }
        return "null";
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            // if all operations of a path have x-client-hidden, add x-client-hidden on the path
            if (ops.stream().allMatch(op -> Boolean.TRUE.equals(op.vendorExtensions.get(VENDOR_X_CLIENT_HIDDEN)))) {
                objs.put(VENDOR_X_CLIENT_HIDDEN, true);
            }

            for (CodegenOperation operation : ops) {
                // overwrite operationId & nickname values of the operation with the x-client-action
                if (StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = (String) operation.vendorExtensions.get("x-client-action");
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

                operation
                    .allParams
                    .stream()
                    .filter(p -> p.isModel && p.getRequiredVars().size() == 0)
                    .forEach(p -> p.vendorExtensions.put("x-optional-object", true))
                ;

                operation.responses.forEach(response -> populateOperationResponse(operation, response));

            }
        }
        return legacyPostProcessOperationsWithModels(objs, allModels);
    }


    /**
     * - Move up some attributes from the operation to the response
     * - Generate payload JSON files from the operation response example (to be used in unit tests)
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
        if (responseExample != null) {
            try {
                Map<String, String> exampleMap = Json.mapper().readerFor(Map.class).readValue(responseExample);
                if (exampleMap.containsKey("title")) {
                    response.vendorExtensions.put("x-example-response", exampleMap);
                }
                response.vendorExtensions.put("x-example-response-json", responseExample);
            } catch (JsonProcessingException ignored) {
            }

            try {
                Path folder = Paths.get(
                        getOutputDir(),
                        testPackage(),
                        "payloads",
                        operation.baseName.toLowerCase(),
                        (String) operation.vendorExtensions.get("x-client-action"),
                        "responses"
                );
                Files.createDirectories(folder);
                PrintWriter out = new PrintWriter(folder + response.code + ".json");
                out.print(responseExample);
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * returns the JSON example from an openapi response
     */
    private String getResponseExample(CodegenResponse response) {
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
            Map examples = (Map) v.get("examples");
            if (examples == null) continue;
            Map res = (Map) examples.get("response");
            if (res == null) continue;
            return Json.pretty(res.get("value"));
        }

        return null;
    }

    /**
     * Create a supporting file from a template file path.
     * The template filename should includes the expected extension (or none for no extension).
     * Destination path is guessed from template path.
     *
     * @param templateFile String
     * @return SupportingFile
     */
    SupportingFile createSupportingFileTemplateDefinition(String templateFile) {
        Path templatePath = Paths.get(templateFile);
        LOGGER.info("templateFile="+templateFile);

        String destinationFilename = templatePath.getFileName().toString().replace(".mustache", "");
        LOGGER.info("destinationFilename="+destinationFilename);

        Path parent = templatePath.getParent();

        String folder = parent != null ? parent.toString() : "";
        LOGGER.info("folder="+folder);

        return new SupportingFile(templateFile, folder, destinationFilename);
    }

    /**
     * Remove files listed in the "ignoredFiles" list of the config file from the list of files to generate
     */
    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(TypeScript.TEST_PACKAGE)) {
            testPackage = (String) additionalProperties.get(TypeScript.TEST_PACKAGE);
        }

        if (additionalProperties.containsKey(TypeScript.DOC_PACKAGE)) {
            docPackage = (String) additionalProperties.get(TypeScript.DOC_PACKAGE);
        }

        if (additionalProperties.containsKey(TypeScript.API_DOC_FILE_FOLDER)) {
            apiDocFileFolder = (String) additionalProperties.get(TypeScript.API_DOC_FILE_FOLDER);
        }

        if (additionalProperties.containsKey(TypeScript.MODEL_DOC_FILE_FOLDER)) {
            modelDocFileFolder = (String) additionalProperties.get(TypeScript.MODEL_DOC_FILE_FOLDER);
        }
    
        if (additionalProperties.containsKey(TypeScript.API_TEST_FILE_FOLDER)) {
            apiTestFileFolder = (String) additionalProperties.get(TypeScript.API_TEST_FILE_FOLDER);
        }
    
        if (additionalProperties.containsKey(TypeScript.MODEL_TEST_FILE_FOLDER)) {
            modelTestFileFolder = (String) additionalProperties.get(TypeScript.MODEL_TEST_FILE_FOLDER);
        }

        if (additionalProperties.containsKey(CodegenConstants.MODEL_PROPERTY_NAMING)) {
            setModelPropertyNaming((String) additionalProperties.get(CodegenConstants.MODEL_PROPERTY_NAMING));
        }

        convertPropertyToBooleanAndWriteBack(CodegenConstants.SUPPORTS_ES6);


        // platform
        Object propPlatform = additionalProperties.get(PLATFORM_SWITCH);
        if (propPlatform == null) {
            propPlatform = "node";
            additionalProperties.put("platform", propPlatform);
        }
        Map<String, Boolean> platforms = new HashMap<>();
        for (String platform: PLATFORMS) {
            platforms.put(platform, platform.equals(propPlatform));
        }
        additionalProperties.put("platforms", platforms);
        additionalProperties.putIfAbsent(FILE_CONTENT_DATA_TYPE, propPlatform.equals("node") ? "Buffer" : "Blob");

        // NPM Settings
        if (additionalProperties.containsKey(NPM_NAME)) {
            setNpmName(additionalProperties.get(NPM_NAME).toString());
        }

        if (additionalProperties.containsKey(NPM_VERSION)) {
            setNpmVersion(additionalProperties.get(NPM_VERSION).toString());
        }

        if (additionalProperties.containsKey(NPM_REPOSITORY)) {
            setNpmRepository(additionalProperties.get(NPM_REPOSITORY).toString());
        }

        String configFile = Paths.get("/config/", templateDir, ".yaml").toString();
//        if (isNotEmpty(configFile)) {
//            DynamicSettings settings = CodegenConfigurator.fromFile(configFile, []);
//        }

        supportingFiles.clear();

        LOGGER.info("templateDir()="+templateDir());

        try {
            Files
                    .walk(Paths.get(templateDir()))
                    .filter(Files::isRegularFile)
                    .forEach(x -> supportingFiles.add(createSupportingFileTemplateDefinition(x.toString().replace(templateDir()+File.separator, ""))))
            ;
        } catch (IOException ioException) {
            LOGGER.error(ioException.getMessage());
        }

        // @todo could probably use something similar to remove templates identified as specific templates
        // remove ignored files from the list of supporting files
        List<String> ignoredFiles = (List<String>) additionalProperties.get("ignoredFiles");
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

    public String getNpmName() {
        return npmName;
    }

    public void setNpmName(String npmName) {
        this.npmName = npmName;
    }

    public String getNpmRepository() {
        return npmRepository;
    }

    public void setNpmRepository(String npmRepository) {
        this.npmRepository = npmRepository;
    }

    public String getNpmVersion() {
        return npmVersion;
    }

    public void setNpmVersion(String npmVersion) {
        this.npmVersion = npmVersion;
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    @Override
    public void preprocessOpenAPI(OpenAPI openAPI) {
        if (additionalProperties.containsKey(NPM_NAME)) {
            // If no npmVersion is provided in additional properties, version from API specification is used.
            // If none of them is provided then fallbacks to default version
            if (additionalProperties.containsKey(NPM_VERSION)) {
                this.setNpmVersion(additionalProperties.get(NPM_VERSION).toString());
            } else if (openAPI.getInfo() != null && openAPI.getInfo().getVersion() != null) {
                this.setNpmVersion(openAPI.getInfo().getVersion());
            }
            additionalProperties.put(NPM_VERSION, npmVersion);
        }
    }

    @Override
    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        objs.put("fileContentDataType", additionalProperties.get(FILE_CONTENT_DATA_TYPE));

        return objs;
    }

    public Map<String, Object> legacyPostProcessOperationsWithModels(Map<String, Object> operations, List<Object> models) {
        // Add additional filename information for model imports in the apis
        List<Map<String, Object>> imports = (List<Map<String, Object>>) operations.get("imports");
        for (Map<String, Object> im : imports) {
            im.put("filename", ((String) im.get("import")).replace(".", "/"));
            im.put("classname", getModelnameFromModelFilename(im.get("import").toString()));
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> operationsMap = (Map<String, Object>) operations.get("operations");
        List<CodegenOperation> operationList = (List<CodegenOperation>) operationsMap.get("operation");
        for (CodegenOperation operation : operationList) {
            List<CodegenResponse> responses = operation.responses;
            operation.returnType = this.getReturnType(responses);
        }
        return operations;
    }

    /**
     * Returns the correct return type based on all 2xx HTTP responses defined for an operation.
     *
     * @param responses all CodegenResponses defined for one operation
     * @return TypeScript return type
     */
    private String getReturnType(List<CodegenResponse> responses) {
        Set<String> returnTypes = new HashSet<String>();
        for (CodegenResponse response : responses) {
            if (response.is2xx) {
                if (response.dataType != null) {
                    returnTypes.add(response.dataType);
                } else {
                    returnTypes.add("void");
                }
            }
        }

        if (returnTypes.size() == 0) {
            return null;
        }

        return String.join(" | ", returnTypes);
    }

    private String getModelnameFromModelFilename(String filename) {
        String name = filename.substring((modelPackage() + File.separator).length());
        return camelize(name);
    }

    @Override
    public String escapeReservedWord(String name) {
        if (this.reservedWordsMappings().containsKey(name)) {
            return this.reservedWordsMappings().get(name);
        }
        return "_" + name;
    }

    @Override
    public String toParamName(String name) {
        // should be the same as variable name
        return toVarName(name);
    }

    @Override
    public String toVarName(String name) {
        // sanitize name
        name = sanitizeName(name);

        if ("_" .equals(name)) {
            name = "_u";
        }

        // if it's all uppper case, do nothing
        if (name.matches("^[A-Z_]*$")) {
            return name;
        }

        name = getNameUsingModelPropertyNaming(name);

        // for reserved word or word starting with number, append _
        if (isReservedWord(name) || name.matches("^\\d.*")) {
            name = escapeReservedWord(name);
        }

        return name;
    }

    @Override
    public String toModelName(String name) {
        name = sanitizeName(name); // FIXME: a parameter should not be assigned. Also declare the methods parameters as 'final'.

        if (!StringUtils.isEmpty(modelNamePrefix)) {
            name = modelNamePrefix + "_" + name;
        }

        if (!StringUtils.isEmpty(modelNameSuffix)) {
            name = name + "_" + modelNameSuffix;
        }

        // model name cannot use reserved keyword, e.g. return
        if (isReservedWord(name)) {
            String modelName = camelize("model_" + name);
            LOGGER.warn(name + " (reserved word) cannot be used as model name. Renamed to " + modelName);
            return modelName;
        }

        // model name starts with number
        if (name.matches("^\\d.*")) {
            String modelName = camelize("model_" + name); // e.g. 200Response => Model200Response (after camelize)
            LOGGER.warn(name + " (model name starts with number) cannot be used as model name. Renamed to " + modelName);
            return modelName;
        }

        if (languageSpecificPrimitives.contains(name)) {
            String modelName = camelize("model_" + name);
            LOGGER.warn(name + " (model name matches existing language type) cannot be used as a model name. Renamed to " + modelName);
            return modelName;
        }

        // camelize the model name
        // phone_number => PhoneNumber
        return camelize(name);
    }

    @Override
    public String toModelFilename(String name) {
        // should be the same as the model name
        return toModelName(name);
    }


    @Override
    protected String getParameterDataType(Parameter parameter, Schema p) {
        // handle enums of various data types
        Schema inner;
        if (ModelUtils.isArraySchema(p)) {
            ArraySchema mp1 = (ArraySchema) p;
            inner = mp1.getItems();
            return this.getSchemaType(p) + "<" + this.getParameterDataType(parameter, inner) + ">";
        } else if (ModelUtils.isMapSchema(p)) {
            inner = (Schema) p.getAdditionalProperties();
            return "{ [key: string]: " + this.getParameterDataType(parameter, inner) + "; }";
        } else if (ModelUtils.isStringSchema(p)) {
            // Handle string enums
            if (p.getEnum() != null) {
                return enumValuesToEnumTypeUnion(p.getEnum(), "string");
            }
        } else if (ModelUtils.isIntegerSchema(p)) {
            // Handle integer enums
            if (p.getEnum() != null) {
                return numericEnumValuesToEnumTypeUnion(new ArrayList<Number>(p.getEnum()));
            }
        } else if (ModelUtils.isNumberSchema(p)) {
            // Handle double enums
            if (p.getEnum() != null) {
                return numericEnumValuesToEnumTypeUnion(new ArrayList<Number>(p.getEnum()));
            }
        }
        return this.getTypeDeclaration(p);
    }

    /**
     * Converts a list of strings to a literal union for representing enum values as a type.
     * Example output: 'available' | 'pending' | 'sold'
     *
     * @param values   list of allowed enum values
     * @param dataType either "string" or "number"
     * @return a literal union for representing enum values as a type
     */
    protected String enumValuesToEnumTypeUnion(List<String> values, String dataType) {
        StringBuilder b = new StringBuilder();
        boolean isFirst = true;
        for (String value : values) {
            if (!isFirst) {
                b.append(" | ");
            }
            b.append(toEnumValue(value.toString(), dataType));
            isFirst = false;
        }
        return b.toString();
    }

    /**
     * Converts a list of numbers to a literal union for representing enum values as a type.
     * Example output: 3 | 9 | 55
     *
     * @param values a list of numbers
     * @return a literal union for representing enum values as a type
     */
    protected String numericEnumValuesToEnumTypeUnion(List<Number> values) {
        List<String> stringValues = new ArrayList<>();
        for (Number value : values) {
            stringValues.add(value.toString());
        }
        return enumValuesToEnumTypeUnion(stringValues, "number");
    }

    @Override
    public String toDefaultValue(Schema p) {
        if (ModelUtils.isBooleanSchema(p)) {
            return UNDEFINED_VALUE;
        } else if (ModelUtils.isDateSchema(p)) {
            return UNDEFINED_VALUE;
        } else if (ModelUtils.isDateTimeSchema(p)) {
            return UNDEFINED_VALUE;
        } else if (ModelUtils.isNumberSchema(p)) {
            if (p.getDefault() != null) {
                return p.getDefault().toString();
            }
            return UNDEFINED_VALUE;
        } else if (ModelUtils.isIntegerSchema(p)) {
            if (p.getDefault() != null) {
                return p.getDefault().toString();
            }
            return UNDEFINED_VALUE;
        } else if (ModelUtils.isStringSchema(p)) {
            if (p.getDefault() != null) {
                return "'" + (String) p.getDefault() + "'";
            }
            return UNDEFINED_VALUE;
        } else {
            return UNDEFINED_VALUE;
        }

    }

    @Override
    protected boolean isReservedWord(String word) {
        // NOTE: This differs from super's implementation in that TypeScript does _not_ want case insensitive matching.
        return reservedWords.contains(word);
    }

    @Override
    public String getSchemaType(Schema p) {
        String openAPIType = super.getSchemaType(p);
        String type = null;
        if (typeMapping.containsKey(openAPIType)) {
            type = typeMapping.get(openAPIType);
            if (languageSpecificPrimitives.contains(type))
                return type;
        } else
            type = openAPIType;
        return toModelName(type);
    }

    @Override
    public String toOperationId(String operationId) {
        // throw exception if method name is empty
        if (StringUtils.isEmpty(operationId)) {
            throw new RuntimeException("Empty method name (operationId) not allowed");
        }

        // method name cannot use reserved keyword, e.g. return
        // append _ at the beginning, e.g. _return
        if (isReservedWord(operationId)) {
            return escapeReservedWord(camelize(sanitizeName(operationId), true));
        }

        return camelize(sanitizeName(operationId), true);
    }

    public void setModelPropertyNaming(String naming) {
        if ("original" .equals(naming) || "camelCase" .equals(naming) ||
                "PascalCase" .equals(naming) || "snake_case" .equals(naming)) {
            this.modelPropertyNaming = naming;
        } else {
            throw new IllegalArgumentException("Invalid model property naming '" +
                    naming + "'. Must be 'original', 'camelCase', " +
                    "'PascalCase' or 'snake_case'");
        }
    }

    public String getModelPropertyNaming() {
        return this.modelPropertyNaming;
    }

    public String getNameUsingModelPropertyNaming(String name) {
        switch (CodegenConstants.MODEL_PROPERTY_NAMING_TYPE.valueOf(getModelPropertyNaming())) {
            case original:
                return name;
            case camelCase:
                return camelize(name, true);
            case PascalCase:
                return camelize(name);
            case snake_case:
                return underscore(name);
            default:
                throw new IllegalArgumentException("Invalid model property naming '" +
                        name + "'. Must be 'original', 'camelCase', " +
                        "'PascalCase' or 'snake_case'");
        }

    }

    @Override
    public String toEnumValue(String value, String datatype) {
        if ("number" .equals(datatype)) {
            return value;
        } else {
            return "\'" + escapeText(value) + "\'";
        }
    }

    @Override
    public String toEnumDefaultValue(String value, String datatype) {
        return datatype + "_" + value;
    }

    @Override
    public String toEnumVarName(String name, String datatype) {
        if (name.length() == 0) {
            return "Empty";
        }

        // for symbol, e.g. $, #
        if (getSymbolName(name) != null) {
            return camelize(getSymbolName(name));
        }

        // number
        if ("number" .equals(datatype)) {
            String varName = "NUMBER_" + name;

            varName = varName.replaceAll("-", "MINUS_");
            varName = varName.replaceAll("\\+", "PLUS_");
            varName = varName.replaceAll("\\.", "_DOT_");
            return varName;
        }

        // string
        String enumName = sanitizeName(name);
        enumName = enumName.replaceFirst("^_", "");
        enumName = enumName.replaceFirst("_$", "");

        // camelize the enum variable name
        // ref: https://basarat.gitbooks.io/typescript/content/docs/enums.html
        enumName = camelize(enumName);

        if (enumName.matches("\\d.*")) { // starts with number
            return "_" + enumName;
        } else {
            return enumName;
        }
    }

    @Override
    public String toEnumName(CodegenProperty property) {
        String enumName = toModelName(property.name) + "Enum";

        if (enumName.matches("\\d.*")) { // starts with number
            return "_" + enumName;
        } else {
            return enumName;
        }
    }

    @Override
    public Map<String, Object> postProcessModels(Map<String, Object> objs) {
        // process enum in models
        List<Map<String, Object>> models = (List<Map<String, Object>>) postProcessModelsEnum(objs).get("models");
        for (Object _mo : models) {
            Map<String, Object> mo = (Map<String, Object>) _mo;
            CodegenModel cm = (CodegenModel) mo.get("model");
            cm.imports = new TreeSet(cm.imports);
            // name enum with model name, e.g. StatusEnum => Pet.StatusEnum
            for (CodegenProperty var : cm.vars) {
                if (Boolean.TRUE.equals(var.isEnum)) {
                    var.datatypeWithEnum = var.datatypeWithEnum.replace(var.enumName, cm.classname + var.enumName);
                }
            }
            if (cm.parent != null) {
                for (CodegenProperty var : cm.allVars) {
                    if (Boolean.TRUE.equals(var.isEnum)) {
                        var.datatypeWithEnum = var.datatypeWithEnum
                                .replace(var.enumName, cm.classname + var.enumName);
                    }
                }
            }
        }
        for (Map<String, Object> mo : models) {
            CodegenModel cm = (CodegenModel) mo.get("model");
            // Add additional filename information for imports
            mo.put("tsImports", toTsImports(cm, cm.imports));
        }
        return objs;
    }

    private List<Map<String, String>> toTsImports(CodegenModel cm, Set<String> imports) {
        List<Map<String, String>> tsImports = new ArrayList<>();
        for (String im : imports) {
            if (!im.equals(cm.classname)) {
                HashMap<String, String> tsImport = new HashMap<>();
                // TVG: This is used as class name in the import statements of the model file
                tsImport.put("classname", im);
                tsImport.put("filename", toModelFilename(im));
                tsImports.add(tsImport);
            }
        }
        return tsImports;
    }


    @Override
    public Map<String, Object> postProcessAllModels(Map<String, Object> objs) {
        Map<String, Object> result = super.postProcessAllModels(objs);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            Map<String, Object> inner = (Map<String, Object>) entry.getValue();
            List<Map<String, Object>> models = (List<Map<String, Object>>) inner.get("models");
            for (Map<String, Object> mo : models) {
                CodegenModel cm = (CodegenModel) mo.get("model");
                if (cm.discriminator != null && cm.children != null) {
                    for (CodegenModel child : cm.children) {
                        this.setDiscriminatorValue(child, cm.discriminator.getPropertyName(), this.getDiscriminatorValue(child));
                    }
                }
            }
        }
        return result;
    }

    private void setDiscriminatorValue(CodegenModel model, String baseName, String value) {
        for (CodegenProperty prop : model.allVars) {
            if (prop.baseName.equals(baseName)) {
                prop.discriminatorValue = value;
            }
        }
        if (model.children != null) {
            final boolean newDiscriminator = model.discriminator != null;
            for (CodegenModel child : model.children) {
                this.setDiscriminatorValue(child, baseName, newDiscriminator ? value : this.getDiscriminatorValue(child));
            }
        }
    }

    private String getDiscriminatorValue(CodegenModel model) {
        return model.vendorExtensions.containsKey(X_DISCRIMINATOR_TYPE) ?
                (String) model.vendorExtensions.get(X_DISCRIMINATOR_TYPE) : model.classname;
    }

    /**
     * Remove ', " to avoid code injection.
     */
    @Override
    public String escapeQuotationMark(String input) {
        return input.replace("\"", "").replace("'", "");
    }

    @Override
    public String escapeUnsafeCharacters(String input) {
        return input.replace("*/", "*_/").replace("/*", "/_*");
    }

    @Override
    public String getName() {
        return "typescript";
    }

    @Override
    public String getHelp() {
        return "Generates a TypeScript client library.";
    }

    @Override
    public String getTypeDeclaration(Schema p) {
        Schema inner;
        if (ModelUtils.isArraySchema(p)) {
            inner = ((ArraySchema) p).getItems();
            return this.getSchemaType(p) + "<" + this.getTypeDeclaration(ModelUtils.unaliasSchema(this.openAPI, inner)) + ">";
        } else if (ModelUtils.isMapSchema(p)) {
            inner = (Schema) p.getAdditionalProperties();
            return "{ [key: string]: " + this.getTypeDeclaration(ModelUtils.unaliasSchema(this.openAPI, inner)) + "; }";
        } else if (ModelUtils.isFileSchema(p)) {
            return "string";
        } else if (ModelUtils.isBinarySchema(p)) {
            return "any";
        } else {
            String res = super.getTypeDeclaration(p);
            if (res.equals("HttpFile")) {
                return "File";
            }
            return res;
        }
    }

    @Override
    protected void addAdditionPropertiesToCodeGenModel(CodegenModel codegenModel, Schema schema) {
        codegenModel.additionalPropertiesType = getTypeDeclaration((Schema) schema.getAdditionalProperties());
        addImport(codegenModel, codegenModel.additionalPropertiesType);
    }

    @Override
    public String apiTestFileFolder() {
        return Paths.get(outputFolder, packageNameToPath(testPackage()), apiTestFileFolder).toString();
    }

    @Override
    public String modelTestFileFolder() {
        return Paths.get(outputFolder, packageNameToPath(testPackage()), modelTestFileFolder).toString();
    }

    @Override
    public String apiDocFileFolder() {
        return Paths.get(super.apiDocFileFolder(), packageNameToPath(docPackage), apiDocFileFolder).toString();
    }

    @Override
    public String modelDocFileFolder() {
        return Paths.get(super.modelDocFileFolder(), packageNameToPath(docPackage), modelDocFileFolder).toString();
    }

    private String packageNameToPath(String packageName) {
        return packageName.replace('.', File.separatorChar);
    }
}
