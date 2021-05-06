# API client generator

## Description

API client generation tool based on [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator). Generates client source code in several languages based on [mustache templates](https://mustache.github.io/).

## Requirements

- Java 1.8+
- Maven

## Usage

To generate an API client:

```sh
mvn package -P [profile]
```

To see the list of available profiles, see the "profile" column in the table of [supported languages](#supported-languages). 

<a name="supported-languages"></a>
## Supported languages

| Language | Profile | Status | Repository |
| ------- | ------ | ---- | ---- |
| Java    |  java      | released | [java-api-client](https://github.com/apivideo/java-api-client) |
| TypeScript        | typescript        | started | [typescript-api-client](https://github.com/apivideo/typescript-api-client) |
| Php | php | to be started | - |
| C# | csharp | private beta | [csharp-api-client](https://github.com/apivideo/csharp-api-client) |
| Go | go | private beta |  [go-api-client](https://github.com/apivideo/go-api-client) |
| Python | python | private beta | [python-api-client](https://github.com/apivideo/python-api-client) |

## Template files

Mustache template files can be found in `templates/[profile]`.

Templates are based on default OpenAPI Generator [templates](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources).

## Configuration files  

Each target language has it's own configuration file located at `config/[profile].yaml`.

```yml
apiPackage: video.api.client.api.clients # API files output folder
modelPackage: folder.subfolder.subsubfolder # model output folder
files:
  page.mustache:
    folder: src/main/java/video/api/client/api/models
    destinationFilename: Page.java
  ApiVideoAuthInterceptor.mustache:
    folder: src/main/java/video/api/client/api/auth
    destinationFilename: ApiVideoAuthInterceptor.java
  EmptyArrayFixTypeAdapterFactory.mustache:
    folder: src/main/java/video/api/client/api
    destinationFilename: EmptyArrayFixTypeAdapterFactory.java
  UploadChunkRequestBody.mustache:
    folder: src/main/java/video/api/client/api/upload
    destinationFilename: UploadChunkRequestBody.java
  UploadProgressListener.mustache:
    folder: src/main/java/video/api/client/api/upload
    destinationFilename: UploadProgressListener.java
  ApiVideoClient.mustache:
    folder: src/main/java/video/api/client
    destinationFilename: ApiVideoClient.java
  pagination.md.mustache:
    folder: docs
    destinationFilename: Pagination.md
  post-generate.sh:
    destinationFilename: post-generate.sh
```

> - https://openapi-generator.tech/docs/globals/
> - https://openapi-generator.tech/docs/configuration/
> - https://openapi-generator.tech/docs/customization#user-defined-templates
> - https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/java/org/openapitools/codegen/DefaultCodegen.java#L106-L111


## Generator sub-class

Some target language require tweaks that can't be done through the config file or the templates. In this case, a subclass of the generator is created at `apivideo-generator/src/main/java/video/api/client/generator/[pascal-case-profile].java`.  


## Reverse

The following command:
```
mvn package -P [generatorName]
```

`maven` use the config from `pom.xml` and run the `generate` command from `openapi-generator-cli` with the following options:
```xml
<configuration>
    <inputSpec>oas_apivideo.yaml</inputSpec>
    <generatorName>${generatorName}</generatorName>
    <templateDirectory>${project.basedir}/templates/${folder}</templateDirectory>
    <configurationFile>${project.basedir}/config/${folder}.yaml</configurationFile>
    <output>generated-clients/${folder}</output>
</configuration>
```

## Generation 

### Configuration

Looking at `Generate.class`, the following key are different, but the rest seem consistent:
- `templateDirectory` -> `templateDir`
- `configurationFile` -> `configFile`

The `configFile` -- our `yml` config file -- is loaded by the `CodegenConfigurator.fromFile` method.
> A class which manages the contextual configuration for code generation. 
> This includes configuring the generator, templating, and the workflow which orchestrates these. 
> This helper also enables the deserialization of GeneratorSettings via application-specific Jackson JSON usage (see DynamicSettings.
 
Having a look at `DynamicSettings.getFiles` give us a clue on how the `files` key (user defined files) are treated.
```
 TemplateDefinition file = kvp.getValue();
            String templateFile = kvp.getKey();
            String destination = file.getDestinationFilename();
            if (TemplateFileType.SupportingFiles.equals(file.getTemplateType()) && StringUtils.isBlank(destination)) {
                // this special case allows definitions such as LICENSE:{}
                destination = templateFile;
            }
            TemplateDefinition definition = new TemplateDefinition(templateFile, file.getFolder(), destination);
            definition.setTemplateType(file.getTemplateType());
            return definition;
```
And `TemplateDefinition` show us how a template is defined.


After loading all the configs from CLI and/or? from the file into the `CodegenConfigurator`,



all options ar turned into a `ClientOptInput` via the `toClientOptInput` method,
This is when our custom codegen is involved. It implements `CodegenConfig` interface
In the ends only methods present in this interface are used by the generatpr
it then instantiate a `DefaultGenerator` class and pass these options via `opts` method.
and finally calls `generate` method.

### Generate

The `DefaultGenerator` generate method will mainly :
- `processUserDefinedTemplates`
- `generateModels` given the list of `files` and available `models`
- `generateApis` given the list of `files` and available `operations`
- `generateSupportingFiles` given available `models` and `operations`

Where does `files` come from?
Where the codegen is involved?

// use this test to launch you code generator in the debugger.
// this allows you to easily set break points in MyclientcodegenGenerator.
@Test
public void launchCodeGenerator() {
// to understand how the 'openapi-generator-cli' module is using 'CodegenConfigurator', have a look at the 'Generate' class:
// https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-cli/src/main/java/org/openapitools/codegen/cmd/Generate.java
final CodegenConfigurator configurator = new CodegenConfigurator()
.setGeneratorName("{{name}}") // use this codegen library
.setInputSpec("../../../modules/openapi-generator/src/test/resources/2_0/petstore.yaml") // sample OpenAPI file
// .setInputSpec("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/2_0/petstore.yaml") // or from the server
.setOutputDir("out/{{name}}"); // output directory

    final ClientOptInput clientOptInput = configurator.toClientOptInput();
    DefaultGenerator generator = new DefaultGenerator();
    generator.opts(clientOptInput).generate();
}

# https://openapi-generator.tech/docs/configuration/
# https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/java/org/openapitools/codegen/languages/TypeScriptClientCodegen.java
# https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources/typescript
# https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/java/org/openapitools/codegen/DefaultCodegen.java