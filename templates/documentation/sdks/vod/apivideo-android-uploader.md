---
title: "api.video Android video uploader"
slug: "android-uploader"
hidden: false
metadata: 
  description: "The official api.video Android video uploader for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

api.video Android video uploader
==============

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

# Table of contents

- [Project description](#project-description)
- [Getting started](#getting-started)
  - [Requirements](#requirements)
  - [Installation](#installation)
    - [Maven users](#maven-users)
    - [Gradle users](#gradle-users)
    - [Others](#others)
  - [Code sample](#code-sample)
  - [Upload options](#upload-options)
  - [Permissions](#permissions)
- [Documentation](#documentation)
  - [API Endpoints](#api-endpoints)
    - [VideosApi](#videosapi)
  - [Models](#models)
  - [Authorization](#documentation-for-authorization)
    - [API key](#api-key)
    - [Public endpoints](#public-endpoints)
  - [Recommendation](#recommendation)
- [Have you gotten use from this API client?](#have-you-gotten-use-from-this-api-client-)
- [Contribution](#contribution)

# Project description

api.video's Android  streamlines the coding process. Chunking files is handled for you, as is pagination and refreshing your tokens.

# Getting started

## Requirements

Building the API client library requires:
1. Java 1.8+
2. Maven/Gradle

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>video.api</groupId>
  <artifactId>android-video-uploader</artifactId>
  <version>1.2.4</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "video.api:android-video-uploader:1.2.4"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/android-video-uploader-1.2.4.jar`
* `target/lib/*.jar`

## Code sample

Please follow the [installation](#installation) instruction and execute the following Kotlin code:

```kotlin
// If you want to upload a video with an upload token (uploadWithUploadToken):
VideosApiStore.initialize()
// if you rather like to use the sandbox environment:
// VideosApiStore.initialize(environment = Environment.SANDBOX)
// If you rather like to upload with your "YOUR_API_KEY" (upload)
// VideosApiStore.initialize("YOUR_API_KEY", Environment.PRODUCTION)
// if you rather like to use the sandbox environment:
// VideosApiStore.initialize("YOU_SANDBOX_API_KEY", Environment.SANDBOX)


val myVideoFile = File("my-video.mp4")

val workManager = WorkManager.getInstance(context) // WorkManager comes from package "androidx.work:work-runtime"
workManager.uploadWithUploadToken("MY_UPLOAD_TOKEN", myVideoFile) // Dispatch the upload with the WorkManager
// if your rather like to use your API key:
// workManager.upload("MY_VIDEO_ID", myVideoFile)
```

### Example

Examples that demonstrate how to use the API is provided in folder `examples/`.

## Upload methods

To upload a video, you have 3 differents methods:
* `WorkManager`: preferred method: Upload with Android WorkManager API. It supports progress notifications. Directly use, WorkManager extensions. See [example](https://github.com/apivideo/api.video-android-uploader/tree/main/examples/workmanager) for more details.
* `UploadService`: Upload with an Android Service. It supports progress notifications. You have to extend the `UploadService` and register it in your `AndroidManifest.xml`. See [example](https://github.com/apivideo/api.video-android-uploader/tree/main/examples/service) for more details.
* Direct call with `ApiClient`: Do not call API from the main thread, otherwise you will get a android.os.NetworkOnMainThreadException. Dispatch API calls with Thread, Executors or Kotlin coroutine to avoid this.

## Permissions

You have to add the following permissions in your `AndroidManifest.xml`:

```xml
    <uses-permission android:name="android.permission.INTERNET" />
<!-- Application requires android.permission.READ_EXTERNAL_STORAGE to upload videos` -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
``` 

Your application also has to dynamically request the `android.permission.READ_EXTERNAL_STORAGE` permission to upload videos.

# Documentation

## API Endpoints

All URIs are relative to *https://ws.api.video*


### VideosApi


#### Retrieve an instance of VideosApi:
```kotlin
val videosApi = VideosApi("YOUR_API_KEY", Environment.PRODUCTION)
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideosApi.md#upload) | **POST** /videos/{videoId}/source | Upload a video
[**uploadWithUploadToken**](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideosApi.md#uploadWithUploadToken) | **POST** /upload | Upload with an delegated upload token



## Documentation for Models

 - [AccessToken](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/AccessToken.md)
 - [AdditionalBadRequestErrors](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/AdditionalBadRequestErrors.md)
 - [AuthenticatePayload](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/AuthenticatePayload.md)
 - [BadRequest](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/BadRequest.md)
 - [Metadata](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/Metadata.md)
 - [NotFound](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/NotFound.md)
 - [RefreshTokenPayload](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/RefreshTokenPayload.md)
 - [Video](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/Video.md)
 - [VideoAssets](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideoAssets.md)
 - [VideoSource](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideoSource.md)
 - [VideoSourceLiveStream](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideoSourceLiveStream.md)
 - [VideoSourceLiveStreamLink](https://github.com/apivideo/api.video-android-uploader/blob/main/docs/VideoSourceLiveStreamLink.md)


## Documentation for Authorization

### API key

Most endpoints required to be authenticated using the API key mechanism described in our [documentation](https://docs.api.video/reference#authentication).
The access token generation mechanism is automatically handled by the client. All you have to do is provide an API key when instantiating the `ApiClient`:
```kotlin
val videosApi = VideosApi("YOUR_API_KEY", Environment.PRODUCTION)
```

### Public endpoints

Some endpoints don't require authentication. These one can be called with a client instantiated without API key:
```kotlin
val videosApi = VideosApi()
```

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
For direct call with `ApiClient`: Do not call API from the main thread, otherwise you will get a `android.os.NetworkOnMainThreadException`. Dispatch API calls with Thread, Executors or Kotlin coroutine to avoid this. Alternatively, APIs come with an asynchronous counterpart (`createAsync` for `create`) except for the upload endpoint.

## Have you gotten use from this API client?

Please take a moment to leave a star on the client ⭐

This helps other users to find the clients and also helps us understand which clients are most popular. Thank you!

# Contribution

Since this API client is generated from an OpenAPI description, we cannot accept pull requests made directly to the repository. If you want to contribute, you can open a pull request on the repository of our [client generator](https://github.com/apivideo/api-client-generator). Otherwise, you can also simply open an issue detailing your need on this repository.