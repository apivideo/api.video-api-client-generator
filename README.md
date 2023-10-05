[![badge](https://img.shields.io/twitter/follow/api_video?style=social)](https://twitter.com/intent/follow?screen_name=api_video) &nbsp; [![badge](https://img.shields.io/github/stars/apivideo/api.video-api-client-generator?style=social)](https://github.com/apivideo/api.video-api-client-generator) &nbsp; [![badge](https://img.shields.io/discourse/topics?server=https%3A%2F%2Fcommunity.api.video)](https://community.api.video)
![](https://github.com/apivideo/.github/blob/main/assets/apivideo_banner.png)
<h1 align="center">api.video API client generator</h1>

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.


# Table of contents

- [Project description](#project-description)
- [Getting started](#getting-started)
  - [Requirements](#requirements)
  - [Usage](#usage)
- [Documentation](#documentation)
  - [Supported languages](#supported-languages)
  - [Template files](#template-files)
  - [Configuration files](#configuration-files)
  - [Generator sub-class](#generator-sub-class)
  - [Development](#development)
    - [Configuration](#configuration)
      - [CLI](#cli)
      - [File](#file)
- [Contribution](#contribution)

# Project description

API client generation tool based on [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator). Generates client source code in several languages based on [mustache templates](https://mustache.github.io/).


# Getting started

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

# Documentation 

## Supported languages

| Language          | Profile          | CI tests           | CI package release | Repository                                                                       |
|-------------------|------------------|--------------------|--------------------|----------------------------------------------------------------------------------|
| Java              | java             | :white_check_mark: | :white_check_mark: | [java-api-client](https://github.com/apivideo/api.video-java-client)             |
| Android           | android          | :white_check_mark: | :white_check_mark: | [android-api-client](https://github.com/apivideo/api.video-android-client)       |
| Android           | android-uploader | :white_check_mark: | :white_check_mark: | [android-video-uploader](https://github.com/apivideo/api.video-android-uploader) |
| NodeJs/Typescript | nodejs           | :white_check_mark: | :white_check_mark: | [nodejs-api-client](https://github.com/apivideo/api.video-nodejs-client)         |
| Php               | php              | :white_check_mark: | -                  | [php-api-client](https://github.com/apivideo/api.video-php-client)               |
| C#                | csharp           | :white_check_mark: | :white_check_mark: | [csharp-api-client](https://github.com/apivideo/api.video-csharp-client)         |
| Go                | go               | :white_check_mark: | -                  | [go-api-client](https://github.com/apivideo/api.video-go-client)                 |
| Python            | python           | :white_check_mark: | :white_check_mark: | [python-api-client](https://github.com/apivideo/api.video-python-client)         |
| Swift5            | swift5           | :white_check_mark: | :white_check_mark: | [ios-api-client](https://github.com/apivideo/api.video-ios-client)               |
| Swift5            | swift5-uploader  | :white_check_mark: | :white_check_mark: | [ios-video-uploader](https://github.com/apivideo/api.video-ios-uploader)         |
| Documentation     | documentation    | -                  | -                  | [documentation](https://github.com/apivideo/api.video-documentation)             |

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

## Development

We recommend the usage of a dedicated [Java IDE](https://en.wikipedia.org/wiki/Comparison_of_integrated_development_environments#Java) to work on the development of this project.
Especially if you want to tweak one of the language specific `Codegen` written in `Java` you'll need to navigate through the class hierarchy.

### Configuration

A `CodegenConfigurator` instance is built from configurations coming from:
  - the `CLI`
  - a `yml` configuration file.

This `CodegenConfigurator` is then turned into a `ClientOptInput` (via the `toClientOptInput` method) to be fed to a `Generator`.
> __This is when our custom codegen, which implements `CodegenConfig` interface is involved.__

The generation is actually done by the `generate` method of the `DefaultGenerator` class.
This method will mainly :
- `processUserDefinedTemplates`
- `generateModels` given the list of `files` and available `models`
- `generateApis` given the list of `files` and available `operations`
- `generateSupportingFiles` given available `models` and `operations`

#### CLI

The profile configuration is first read from `pom.xml`, see java for example :
```xml
<profile>
    <id>java</id>
    <properties>
        <folder>java</folder>
        <generatorName>video.api.client.generator.Java</generatorName>
        <postGenerationScript>./post-generate.sh</postGenerationScript>
    </properties>
</profile>
```

When generating a client with the following command:
```
mvn package -P [profile]
```

Internally the `generate` command of the `openapi-generator-cli` run with the following options:
```xml
<configuration>
    <inputSpec>oas_apivideo.yaml</inputSpec>
    <generatorName>${generatorName}</generatorName>
    <templateDirectory>${project.basedir}/templates/${folder}</templateDirectory>
    <configurationFile>${project.basedir}/config/${folder}.yaml</configurationFile>
    <output>generated-clients/${folder}</output>
</configuration>
```

#### File

The `yml` config file -- is loaded in the `CodegenConfigurator` class via the `fromFile` method.
> A class which manages the contextual configuration for code generation. 
> This includes configuring the generator, templating, and the workflow which orchestrates these. 
> 
> This helper also enables the deserialization of `GeneratorSettings` via application-specific Jackson JSON usage (see `DynamicSettings`.

Each entry under the `files` key of the `yml` configuration file is used to build a `TemplateDefinition` object.
If not specified otherwise the file is treated as a `SupportingFile`.

 
