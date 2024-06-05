package video.api.client.generator;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.util.Json;
import io.swagger.v3.oas.models.media.*;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Lambda;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenParameter;
import org.openapitools.codegen.CodegenResponse;
import org.openapitools.codegen.languages.PythonClientCodegen;
import org.openapitools.codegen.utils.ModelUtils;
import org.openapitools.codegen.*;
import org.openapitools.codegen.templating.mustache.*;
import org.openapitools.codegen.CodegenDiscriminator.MappedModel;
import com.github.curiousoddman.rgxgen.RgxGen;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;

import static org.openapitools.codegen.utils.StringUtils.underscore;
import static video.api.client.generator.Common.populateOperationResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import video.api.client.generator.UnderscoreLambda;

public class Python extends PythonClientCodegen {
    private final Logger LOGGER = LoggerFactory.getLogger(PythonClientCodegen.class);
    public static final String VENDOR_X_CLIENT_IGNORE = "x-client-ignore";
    public static final String VENDOR_X_CLIENT_HIDDEN = "x-client-hidden";
    public static final List<String> PARAMETERS_TO_HIDE_IN_CLIENT_DOC = Arrays.asList("currentPage", "pageSize");

    public Python() {
        super();
        modelTestTemplateFiles.clear();
    }

    /**
     * Remove files listed in the "ignoredFiles" list of the config file from the list of files to generate
     */
    @Override
    public void processOpts() {
        super.processOpts();

        ChangeLog changelog = ChangeLog.parse(additionalProperties);
        additionalProperties.put("packageVersion", changelog.getLastVersion().getName());
        additionalProperties.put("unescape", new UnescapeLambda());
        changelog.writeTo(this.getOutputDir());

        List<String> ignoredFiles = (List<String>) additionalProperties.get("ignoredFiles");
        supportingFiles.removeIf(e -> ignoredFiles.contains(e.getTemplateFile()));
        supportingFiles.add(new SupportingFile("auth_api_client.mustache", packagePath(), "auth_api_client.py"));
        supportingFiles.add(new SupportingFile("endpoint.mustache", packagePath(), "endpoint.py"));
        supportingFiles.add(new SupportingFile("helper.mustache", packagePath() + "/../test/", "helper.py"));
        supportingFiles.add(new SupportingFile("test_auth.mustache", packagePath() + "/../test/", "test_auth.py"));
        supportingFiles.add(new SupportingFile("test_file", packagePath() + "/../test/", "test_file"));

        supportingFiles.removeIf(a -> a.getFolder().equals("apivideo/models"));
    }


    public CodegenProperty fromProperty(String name, Schema p) {
        if(p == null) return null;
        return super.fromProperty(name, p);
    }

    public void preprocessOpenAPI(OpenAPI openAPI) {
        Common.preprocessOpenAPI(openAPI);
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
                                if (extensions == null || !extensions.containsKey(VENDOR_X_CLIENT_IGNORE) ||
                                    !(boolean) extensions.get(VENDOR_X_CLIENT_IGNORE)) {
                                    parameters.add(param);
                                }
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

    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        objs.put("vendorExtensions", vendorExtensions);
        return super.postProcessSupportingFileData(objs);
    }

    @Override
    public Map<String, Object> postProcessOperationsWithModels(Map<String, Object> objs, List<Object> allModels) {
        Common.replaceDescriptionsAndSamples(objs, "python");

        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");

            ops.sort(Common.getCodegenOperationComparator());

            // if all operations of a path have x-client-hidden, add x-client-hidden on the path
            if(ops.stream().allMatch(op -> Boolean.TRUE.equals(op.vendorExtensions.get(VENDOR_X_CLIENT_HIDDEN)))) {
                objs.put(VENDOR_X_CLIENT_HIDDEN, true);
            }

            for (CodegenOperation operation : ops) {
                operation.vendorExtensions.put("x-client-copy-from-response", operation.allParams.stream()
                        .filter(p -> Boolean.TRUE.equals(p.vendorExtensions.get("x-client-copy-from-response")))
                        .peek(a -> a.vendorExtensions.put("getter", toGetter(a.paramName)))
                        .collect(Collectors.toList()));

                if(StringUtils.isNotBlank((String) operation.vendorExtensions.get("x-client-action"))) {
                    operation.operationId = underscore((String) operation.vendorExtensions.get("x-client-action"));
                    operation.nickname = operation.operationId;
                } else {
                    throw new RuntimeException("Missing x-client-action value for operation " + operation.operationId);
                }

                applyToAllParams(operation, (params) -> params.forEach(pp -> {
                    if("deepObject".equals(pp.style)) pp.collectionFormat = "deepObject";
                }));
                applyToAllParams(operation, (params) -> params.removeIf(pp -> getVendorExtensionBooleanValue(pp, VENDOR_X_CLIENT_IGNORE)) );

                String folder = getOutputDir() + "/test/payloads/" + operation.baseName.toLowerCase() + "/" + underscore((String) operation.vendorExtensions.get("x-client-action")) + "/responses/";
                operation.responses.forEach(response -> populateOperationResponse(openAPI, operation, response, additionalProperties, folder));
            }
        }
        return super.postProcessOperationsWithModels(objs, allModels);
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

    protected ImmutableMap.Builder<String, Lambda> addMustacheLambdas() {
        return new ImmutableMap.Builder<String, Mustache.Lambda>()
                .put("lowercase", new LowercaseLambda().generator(this))
                .put("underscore", new UnderscoreLambda())
                .put("underscorePath", new UnderscoreLambda(true))
                .put("lower", new LowercaseLambda());
    }

    @Override
    public String sanitizeTag(String tag) {
        return sanitizeName(tag.replaceAll(" - ", " ")); // the dash messes with the underscore in the naming style of Python
    }

    private boolean getVendorExtensionBooleanValue(CodegenParameter parameter, String name) {
        return parameter.vendorExtensions.containsKey(name) && (boolean) parameter.vendorExtensions.get(name);
    }

    @Override
    public String toExampleValue(Schema schema, Object objExample) {
        String modelName = getModelName(schema);
        return toExampleValueRecursive(modelName, schema, objExample, 1, "", 0, Sets.newHashSet());
    }

    /***
     * Recursively generates string examples for schemas
     *
     * @param modelName the string name of the refed model that will be generated for the schema or null
     * @param schema the schema that we need an example for
     * @param objExample the example that applies to this schema, for now only string example are used
     * @param indentationLevel integer indentation level that we are currently at
     *                         we assume the indentaion amount is 4 spaces times this integer
     * @param prefix the string prefix that we will use when assigning an example for this line
     *               this is used when setting key: value, pairs "key: " is the prefix
     *               and this is used when setting properties like some_property='some_property_example'
     * @param exampleLine this is the current line that we are generatign an example for, starts at 0
     *                    we don't indentin the 0th line because using the example value looks like:
     *                    prop = ModelName( line 0
     *                        some_property='some_property_example' line 1
     *                    ) line 2
     *                    and our example value is:
     *                    ModelName( line 0
     *                        some_property='some_property_example' line 1
     *                    ) line 2
     * @param seenSchemas This set contains all the schemas passed into the recursive function. It is used to check
     *                    if a schema was already passed into the function and breaks the infinite recursive loop. The
     *                    only schemas that are not added are ones that contain $ref != null
     * @return the string example
     */
    private String toExampleValueRecursive(String modelName, Schema schema, Object objExample, int indentationLevel, String prefix, Integer exampleLine, Set<Schema> seenSchemas) {
        final String indentionConst = "    ";
        String currentIndentation = "";
        String closingIndentation = "";
        for (int i = 0; i < indentationLevel; i++) currentIndentation += indentionConst;
        if (exampleLine.equals(0)) {
            closingIndentation = currentIndentation;
            currentIndentation = "";
        } else {
            closingIndentation = currentIndentation;
        }
        String openChars = "";
        String closeChars = "";
        if (modelName != null) {
            openChars = modelName + "(";
            closeChars = ")";
        }

        String fullPrefix = currentIndentation + prefix + openChars;

        String example = null;
        if (objExample != null) {
            example = objExample.toString();
        }
        // checks if the current schema has already been passed in. If so, breaks the current recursive pass
        if (seenSchemas.contains(schema)) {
            if (modelName != null) {
                return fullPrefix + modelName + closeChars;
            } else {
                // this is a recursive schema
                // need to add a reasonable example to avoid
                // infinite recursion
                if (ModelUtils.isNullable(schema)) {
                    // if the schema is nullable, then 'None' is a valid value
                    return fullPrefix + "None" + closeChars;
                } else if (ModelUtils.isArraySchema(schema)) {
                    // the schema is an array, add an empty array
                    return fullPrefix + "[]" + closeChars;
                } else {
                    // the schema is an object, make an empty object
                    return fullPrefix + "{}" + closeChars;
                }
            }
        }

        if (null != schema.get$ref()) {
            Map<String, Schema> allDefinitions = ModelUtils.getSchemas(this.openAPI);
            String ref = ModelUtils.getSimpleRef(schema.get$ref());
            Schema refSchema = allDefinitions.get(ref);
            if (null == refSchema) {
                LOGGER.warn("Unable to find referenced schema " + schema.get$ref() + "\n");
                return fullPrefix + "None" + closeChars;
            }
            String refModelName = getModelName(schema);
            return toExampleValueRecursive(refModelName, refSchema, objExample, indentationLevel, prefix, exampleLine, seenSchemas);
        } else if (ModelUtils.isNullType(schema) || isAnyTypeSchema(schema)) {
            // The 'null' type is allowed in OAS 3.1 and above. It is not supported by OAS 3.0.x,
            // though this tooling supports it.
            return fullPrefix + "None" + closeChars;
        } else if (ModelUtils.isBooleanSchema(schema)) {
            if (objExample == null) {
                example = "True";
            } else {
                if ("false".equalsIgnoreCase(objExample.toString())) {
                    example = "False";
                } else {
                    example = "True";
                }
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isDateSchema(schema)) {
            if (objExample == null) {
                example = pythonDate("1970-01-01");
            } else {
                example = pythonDate(objExample);
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isDateTimeSchema(schema)) {
            if (objExample == null) {
                example = pythonDateTime("1970-01-01T00:00:00.00Z");
            } else {
                example = pythonDateTime(objExample);
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isBinarySchema(schema)) {
            if (objExample == null) {
                example = "/path/to/file";
            }
            example = "open('" + example + "', 'rb')";
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isByteArraySchema(schema)) {
            if (objExample == null) {
                example = "'YQ=='";
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isStringSchema(schema)) {
            if (objExample == null) {
                // a BigDecimal:
                if ("Number".equalsIgnoreCase(schema.getFormat())) {
                    example = "2";
                    return fullPrefix + example + closeChars;
                } else if (StringUtils.isNotBlank(schema.getPattern())) {
                    String pattern = schema.getPattern();
                    RgxGen rgxGen = new RgxGen(pattern);
                    // this seed makes it so if we have [a-z] we pick a
                    Random random = new Random(18);
                    String sample = rgxGen.generate(random);
                    // omit leading / and trailing /, omit trailing /i
                    Pattern valueExtractor = Pattern.compile("^/?(.+?)(/[i]?)?$");
                    Matcher m = valueExtractor.matcher(sample);
                    if (m.find()) {
                        example = m.group(1);
                    } else {
                        example = "";
                    }
                } else if (schema.getMinLength() != null) {
                    example = "";
                    int len = schema.getMinLength().intValue();
                    for (int i = 0; i < len; i++) example += "a";
                } else if (ModelUtils.isUUIDSchema(schema)) {
                    example = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
                } else {
                    example = "string_example";
                }
            }
            return fullPrefix + ensureQuotes(example) + closeChars;
        } else if (ModelUtils.isIntegerSchema(schema)) {
            if (objExample == null) {
                if (schema.getMinimum() != null) {
                    example = schema.getMinimum().toString();
                } else {
                    example = "1";
                }
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isNumberSchema(schema)) {
            if (objExample == null) {
                if (schema.getMinimum() != null) {
                    example = schema.getMinimum().toString();
                } else {
                    example = "3.14";
                }
            }
            return fullPrefix + example + closeChars;
        } else if (ModelUtils.isArraySchema(schema)) {
            ArraySchema arrayschema = (ArraySchema) schema;
            Schema itemSchema = arrayschema.getItems();
            String itemModelName = getModelName(itemSchema);
            if (objExample instanceof Iterable && itemModelName == null) {
                // If the example is already a list, return it directly instead of wrongly wrap it in another list
                return fullPrefix + objExample.toString() + closeChars;
            }
            seenSchemas.add(schema);
            if (ModelUtils.isStringSchema(itemSchema) && objExample.toString().charAt(0) == '[') {
                return fullPrefix + objExample;
            } else {
                String itemValue = toExampleValueRecursive(itemModelName, itemSchema, objExample, indentationLevel + 1, "", exampleLine + 1, seenSchemas);
                example = fullPrefix + "[" + "\n" + itemValue + ",\n" + closingIndentation + "]" + closeChars;
                return example;
            }
        } else if (ModelUtils.isMapSchema(schema)) {
            if (modelName == null) {
                fullPrefix += "{";
                closeChars = "}";
            }
            Object addPropsObj = schema.getAdditionalProperties();
            // TODO handle true case for additionalProperties
            if (addPropsObj instanceof Schema) {
                Schema addPropsSchema = (Schema) addPropsObj;
                String key = "key";
                Object addPropsExample = getObjectExample(addPropsSchema);
                if (addPropsSchema.getEnum() != null && !addPropsSchema.getEnum().isEmpty()) {
                    key = addPropsSchema.getEnum().get(0).toString();
                }
                addPropsExample = exampleFromStringOrArraySchema(addPropsSchema, addPropsExample, key);
                String addPropPrefix = key + "=";
                if (modelName == null) {
                    addPropPrefix = ensureQuotes(key) + ": ";
                }
                String addPropsModelName = getModelName(addPropsSchema);
                seenSchemas.add(schema);
                example = fullPrefix + "\n" + toExampleValueRecursive(addPropsModelName, addPropsSchema, addPropsExample, indentationLevel + 1, addPropPrefix, exampleLine + 1, seenSchemas) + ",\n" + closingIndentation + closeChars;
            } else {
                example = fullPrefix + closeChars;
            }
            return example;
        } else if (ModelUtils.isObjectSchema(schema)) {
            if (modelName == null) {
                fullPrefix += "{";
                closeChars = "}";
            }
            CodegenDiscriminator disc = createDiscriminator(modelName, schema, openAPI);
            if (disc != null) {
                MappedModel mm = getDiscriminatorMappedModel(disc);
                if (mm != null) {
                    String discPropNameValue = mm.getMappingName();
                    String chosenModelName = mm.getModelName();
                    // TODO handle this case in the future, this is when the discriminated
                    // schema allOf includes this schema, like Cat allOf includes Pet
                    // so this is the composed schema use case
                } else {
                    return fullPrefix + closeChars;
                }
            }
            // Adds schema to seenSchemas before running example model function. romoves schema after running
            // the function. It also doesnt keep track of any schemas within the ObjectModel.
            seenSchemas.add(schema);
            String exampleForObjectModel = exampleForObjectModel(schema, fullPrefix, closeChars, null, indentationLevel, exampleLine, closingIndentation, seenSchemas);
            seenSchemas.remove(schema);
            return exampleForObjectModel;
        } else if (ModelUtils.isComposedSchema(schema)) {
            // TODO add examples for composed schema models without discriminators

            CodegenDiscriminator disc = createDiscriminator(modelName, schema, openAPI);
            if (disc != null) {
                MappedModel mm = getDiscriminatorMappedModel(disc);
                if (mm != null) {
                    String discPropNameValue = mm.getMappingName();
                    String chosenModelName = mm.getModelName();
                    Schema modelSchema = getModelNameToSchemaCache().get(chosenModelName);
                    CodegenProperty cp = new CodegenProperty();
                    cp.setName(disc.getPropertyName());
                    cp.setExample(discPropNameValue);
                    // Adds schema to seenSchemas before running example model function. romoves schema after running
                    // the function. It also doesnt keep track of any schemas within the ObjectModel.
                    seenSchemas.add(modelSchema);
                    String exampleForObjectModel = exampleForObjectModel(modelSchema, fullPrefix, closeChars, cp, indentationLevel, exampleLine, closingIndentation, seenSchemas);
                    seenSchemas.remove(modelSchema);
                    return exampleForObjectModel;
                } else {
                    return fullPrefix + closeChars;
                }
            }
            return fullPrefix + closeChars;
        } else {
            LOGGER.warn("Type " + schema.getType() + " not handled properly in toExampleValue");
        }

        return example;
    }

    private String ensureQuotes(String in) {
        Pattern pattern = Pattern.compile("\r\n|\r|\n");
        Matcher matcher = pattern.matcher(in);
        if (matcher.find()) {
            // if a string has a new line in it add triple quotes to make it a python multiline string
            return "'''" + in + "'''";
        }
        String strPattern = "^['\"].*?['\"]$";
        if (in.matches(strPattern)) {
            return in;
        }
        return "\"" + in + "\"";
    }

    private Object exampleFromStringOrArraySchema(Schema sc, Object currentExample, String propName) {
        if (currentExample != null) {
            return currentExample;
        }
        Schema schema = sc;
        String ref = sc.get$ref();
        if (ref != null) {
            schema = ModelUtils.getSchema(this.openAPI, ModelUtils.getSimpleRef(ref));
        }
        Object example = getObjectExample(schema);
        if (example != null) {
            return example;
        } else if (simpleStringSchema(schema)) {
            return propName + "_example";
        } else if (ModelUtils.isArraySchema(schema)) {
            ArraySchema arraySchema = (ArraySchema) schema;
            Schema itemSchema = arraySchema.getItems();
            example = getObjectExample(itemSchema);
            if (example != null) {
                return example;
            } else if (simpleStringSchema(itemSchema)) {
                return propName + "_example";
            }
        }
        return null;
    }

    private MappedModel getDiscriminatorMappedModel(CodegenDiscriminator disc) {
        for (MappedModel mm : disc.getMappedModels()) {
            String modelName = mm.getModelName();
            Schema modelSchema = getModelNameToSchemaCache().get(modelName);
            if (ModelUtils.isObjectSchema(modelSchema)) {
                return mm;
            }
        }
        return null;
    }

    private String exampleForObjectModel(Schema schema, String fullPrefix, String closeChars, CodegenProperty discProp, int indentationLevel, int exampleLine, String closingIndentation, Set<Schema> seenSchemas) {
        Map<String, Schema> requiredAndOptionalProps = schema.getProperties();
        if (requiredAndOptionalProps == null || requiredAndOptionalProps.isEmpty()) {
            return fullPrefix + closeChars;
        }

        String example = fullPrefix + "\n";
        for (Map.Entry<String, Schema> entry : requiredAndOptionalProps.entrySet()) {
            String propName = entry.getKey();
            Schema propSchema = entry.getValue();
            propName = toVarName(propName);
            String propModelName = null;
            Object propExample = null;
            if (discProp != null && propName.equals(discProp.name)) {
                propModelName = null;
                propExample = discProp.example;
            } else {
                propModelName = getModelName(propSchema);
                propExample = exampleFromStringOrArraySchema(propSchema, null, propName);
            }
            example += toExampleValueRecursive(propModelName, propSchema, propExample, indentationLevel + 1, propName + "=", exampleLine + 1, seenSchemas) + ",\n";
        }
        // TODO handle additionalProperties also
        example += closingIndentation + closeChars;
        return example;
    }

    private Boolean simpleStringSchema(Schema schema) {
        Schema sc = schema;
        String ref = schema.get$ref();
        if (ref != null) {
            sc = ModelUtils.getSchema(this.openAPI, ModelUtils.getSimpleRef(ref));
        }
        if (ModelUtils.isStringSchema(sc) && !ModelUtils.isDateSchema(sc) && !ModelUtils.isDateTimeSchema(sc) && !"Number".equalsIgnoreCase(sc.getFormat()) && !ModelUtils.isByteArraySchema(sc) && !ModelUtils.isBinarySchema(sc) && schema.getPattern() == null) {
            return true;
        }
        return false;
    }

}
