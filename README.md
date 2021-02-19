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
| Java    |  java      | private beta | [java-api-client](https://github.com/apivideo/java-api-client) |
| TypeScript        | typescript        | started | [typescript-api-client](https://github.com/apivideo/typescript-api-client) |
| Php | php | to be started | - |
| C# | csharp | to be started | - |
| Go | go | to be started | - |
| Python | python | to be started | - |

## Template files

Mustache template files can be found in `templates/[profile]`.

Templates are based on default OpenAPI Generator [templates](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources).

## Configuration files  

Each target language has it's own configuration file located at `config/[profile].yaml`.

## Generator sub-class

Some target language require tweaks that can't be done through the config file or the templates. In this case, a subclass of the generator is created at `apivideo-generator/src/main/java/video/api/client/generator/[pascal-case-profile].java`.  