---
title: "Android API client"
slug: "android-api-client"
hidden: false
metadata: 
  description: "The official Android client for api.video. [api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

Android Api Client
==============

[api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

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
    - [AnalyticsApi](#analyticsapi)
    - [CaptionsApi](#captionsapi)
    - [ChaptersApi](#chaptersapi)
    - [LiveStreamsApi](#livestreamsapi)
    - [PlayerThemesApi](#playerthemesapi)
    - [RawStatisticsApi](#rawstatisticsapi)
    - [UploadTokensApi](#uploadtokensapi)
    - [VideosApi](#videosapi)
    - [WatermarksApi](#watermarksapi)
    - [WebhooksApi](#webhooksapi)
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
  <artifactId>android-api-client</artifactId>
  <version>1.4.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "video.api:android-api-client:1.4.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/android-api-client-1.4.0.jar`
* `target/lib/*.jar`

## Code sample

Please follow the [installation](#installation) instruction and execute the following Kotlin code:

```kotlin

val apiVideoClient = ApiVideoClient("YOUR_API_KEY")
// if you rather like to use the sandbox environment:
// val apiVideoClient = ApiVideoClient("YOU_SANDBOX_API_KEY", Environment.SANDBOX)

/**
 * This example uses an Android specific API called WorkManager to dispatch upload.
 * We initialize it before using it.
 */
VideosApiStore.initialize(apiVideoClient.videos())
val workManager = WorkManager.getInstance(context) // WorkManager comes from package "androidx.work:work-runtime"

val myVideoFile = File("my-video.mp4")

/**
 * You must not call API from the UI/main thread on Android. Dispatch with Thread, Executors, 
 * Kotlin coroutines or asynchroneous API (such as `createAsync` instead of `create`).
 */
executor.execute {
    try {
        val video = apiVideoClient.videos().create(VideoCreationPayload().title("my video"))
        Log.i("Example", "Video created: $video")
        workManager.upload(video.videoId, myVideoFile)
    } catch (e: ApiException) {
        Log.e("Example", "Exception when calling VideoApi", e)
    }
}

```

### Example

Examples that demonstrate how to use the API is provided in folder `examples/`.

## Upload methods

To upload a video, you have 3 differents methods:
* `WorkManager`: preferred method: Upload with Android WorkManager API. It supports progress notifications. Directly use, WorkManager extensions. See [example](https://github.com/apivideo/api.video-android-client/tree/main/examples/workmanager) for more details.
* `UploadService`: Upload with an Android Service. It supports progress notifications. You have to extend the `UploadService` and register it in your `AndroidManifest.xml`. See [example](https://github.com/apivideo/api.video-android-client/tree/main/examples/service) for more details.
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


### AnalyticsApi


#### Retrieve an instance of AnalyticsApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val analytics = client.analytics()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLiveStreamsPlays**](https://github.com/apivideo/api.video-android-client/blob/main/docs/AnalyticsApi.md#getLiveStreamsPlays) | **GET** /analytics/live-streams/plays | Get play events for live stream
[**getVideosPlays**](https://github.com/apivideo/api.video-android-client/blob/main/docs/AnalyticsApi.md#getVideosPlays) | **GET** /analytics/videos/plays | Get play events for video


### CaptionsApi


#### Retrieve an instance of CaptionsApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val captions = client.captions()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsApi.md#upload) | **POST** /videos/{videoId}/captions/{language} | Upload a caption
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsApi.md#get) | **GET** /videos/{videoId}/captions/{language} | Retrieve a caption
[**update**](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsApi.md#update) | **PATCH** /videos/{videoId}/captions/{language} | Update a caption
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsApi.md#delete) | **DELETE** /videos/{videoId}/captions/{language} | Delete a caption
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsApi.md#list) | **GET** /videos/{videoId}/captions | List video captions


### ChaptersApi


#### Retrieve an instance of ChaptersApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val chapters = client.chapters()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-android-client/blob/main/docs/ChaptersApi.md#upload) | **POST** /videos/{videoId}/chapters/{language} | Upload a chapter
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/ChaptersApi.md#get) | **GET** /videos/{videoId}/chapters/{language} | Retrieve a chapter
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/ChaptersApi.md#delete) | **DELETE** /videos/{videoId}/chapters/{language} | Delete a chapter
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/ChaptersApi.md#list) | **GET** /videos/{videoId}/chapters | List video chapters


### LiveStreamsApi


#### Retrieve an instance of LiveStreamsApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val liveStreams = client.liveStreams()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#create) | **POST** /live-streams | Create live stream
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#get) | **GET** /live-streams/{liveStreamId} | Retrieve live stream
[**update**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#update) | **PATCH** /live-streams/{liveStreamId} | Update a live stream
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#delete) | **DELETE** /live-streams/{liveStreamId} | Delete a live stream
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#list) | **GET** /live-streams | List all live streams
[**uploadThumbnail**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#uploadThumbnail) | **POST** /live-streams/{liveStreamId}/thumbnail | Upload a thumbnail
[**deleteThumbnail**](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamsApi.md#deleteThumbnail) | **DELETE** /live-streams/{liveStreamId}/thumbnail | Delete a thumbnail


### PlayerThemesApi


#### Retrieve an instance of PlayerThemesApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val playerThemes = client.playerThemes()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#create) | **POST** /players | Create a player
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#get) | **GET** /players/{playerId} | Retrieve a player
[**update**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#update) | **PATCH** /players/{playerId} | Update a player
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#delete) | **DELETE** /players/{playerId} | Delete a player
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#list) | **GET** /players | List all player themes
[**uploadLogo**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#uploadLogo) | **POST** /players/{playerId}/logo | Upload a logo
[**deleteLogo**](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesApi.md#deleteLogo) | **DELETE** /players/{playerId}/logo | Delete logo


### RawStatisticsApi


#### Retrieve an instance of RawStatisticsApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val rawStatistics = client.rawStatistics()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**listLiveStreamSessions**](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsApi.md#listLiveStreamSessions) | **GET** /analytics/live-streams/{liveStreamId} | List live stream player sessions
[**listSessionEvents**](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsApi.md#listSessionEvents) | **GET** /analytics/sessions/{sessionId}/events | List player session events
[**listVideoSessions**](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsApi.md#listVideoSessions) | **GET** /analytics/videos/{videoId} | List video player sessions


### UploadTokensApi


#### Retrieve an instance of UploadTokensApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val uploadTokens = client.uploadTokens()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**createToken**](https://github.com/apivideo/api.video-android-client/blob/main/docs/UploadTokensApi.md#createToken) | **POST** /upload-tokens | Generate an upload token
[**getToken**](https://github.com/apivideo/api.video-android-client/blob/main/docs/UploadTokensApi.md#getToken) | **GET** /upload-tokens/{uploadToken} | Retrieve upload token
[**deleteToken**](https://github.com/apivideo/api.video-android-client/blob/main/docs/UploadTokensApi.md#deleteToken) | **DELETE** /upload-tokens/{uploadToken} | Delete an upload token
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/UploadTokensApi.md#list) | **GET** /upload-tokens | List all active upload tokens


### VideosApi


#### Retrieve an instance of VideosApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val videos = client.videos()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#create) | **POST** /videos | Create a video object
[**upload**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#upload) | **POST** /videos/{videoId}/source | Upload a video
[**uploadWithUploadToken**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#uploadWithUploadToken) | **POST** /upload | Upload with an delegated upload token
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#get) | **GET** /videos/{videoId} | Retrieve a video object
[**update**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#update) | **PATCH** /videos/{videoId} | Update a video object
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#delete) | **DELETE** /videos/{videoId} | Delete a video object
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#list) | **GET** /videos | List all video objects
[**uploadThumbnail**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#uploadThumbnail) | **POST** /videos/{videoId}/thumbnail | Upload a thumbnail
[**pickThumbnail**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#pickThumbnail) | **PATCH** /videos/{videoId}/thumbnail | Set a thumbnail
[**getStatus**](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#getStatus) | **GET** /videos/{videoId}/status | Retrieve video status and details


### WatermarksApi


#### Retrieve an instance of WatermarksApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val watermarks = client.watermarks()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WatermarksApi.md#upload) | **POST** /watermarks | Upload a watermark
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WatermarksApi.md#delete) | **DELETE** /watermarks/{watermarkId} | Delete a watermark
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WatermarksApi.md#list) | **GET** /watermarks | List all watermarks


### WebhooksApi


#### Retrieve an instance of WebhooksApi:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
val webhooks = client.webhooks()
```



#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksApi.md#create) | **POST** /webhooks | Create Webhook
[**get**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksApi.md#get) | **GET** /webhooks/{webhookId} | Retrieve Webhook details
[**delete**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksApi.md#delete) | **DELETE** /webhooks/{webhookId} | Delete a Webhook
[**list**](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksApi.md#list) | **GET** /webhooks | List all webhooks



## Documentation for Models

 - [AccessToken](https://github.com/apivideo/api.video-android-client/blob/main/docs/AccessToken.md)
 - [AdditionalBadRequestErrors](https://github.com/apivideo/api.video-android-client/blob/main/docs/AdditionalBadRequestErrors.md)
 - [AnalyticsData](https://github.com/apivideo/api.video-android-client/blob/main/docs/AnalyticsData.md)
 - [AnalyticsPlays400Error](https://github.com/apivideo/api.video-android-client/blob/main/docs/AnalyticsPlays400Error.md)
 - [AnalyticsPlaysResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/AnalyticsPlaysResponse.md)
 - [AuthenticatePayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/AuthenticatePayload.md)
 - [BadRequest](https://github.com/apivideo/api.video-android-client/blob/main/docs/BadRequest.md)
 - [BytesRange](https://github.com/apivideo/api.video-android-client/blob/main/docs/BytesRange.md)
 - [Caption](https://github.com/apivideo/api.video-android-client/blob/main/docs/Caption.md)
 - [CaptionsListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsListResponse.md)
 - [CaptionsUpdatePayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/CaptionsUpdatePayload.md)
 - [Chapter](https://github.com/apivideo/api.video-android-client/blob/main/docs/Chapter.md)
 - [ChaptersListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/ChaptersListResponse.md)
 - [Link](https://github.com/apivideo/api.video-android-client/blob/main/docs/Link.md)
 - [LiveStream](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStream.md)
 - [LiveStreamAssets](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamAssets.md)
 - [LiveStreamCreationPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamCreationPayload.md)
 - [LiveStreamListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamListResponse.md)
 - [LiveStreamSession](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSession.md)
 - [LiveStreamSessionClient](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSessionClient.md)
 - [LiveStreamSessionDevice](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSessionDevice.md)
 - [LiveStreamSessionLocation](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSessionLocation.md)
 - [LiveStreamSessionReferrer](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSessionReferrer.md)
 - [LiveStreamSessionSession](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamSessionSession.md)
 - [LiveStreamUpdatePayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/LiveStreamUpdatePayload.md)
 - [Metadata](https://github.com/apivideo/api.video-android-client/blob/main/docs/Metadata.md)
 - [Model403ErrorSchema](https://github.com/apivideo/api.video-android-client/blob/main/docs/Model403ErrorSchema.md)
 - [NotFound](https://github.com/apivideo/api.video-android-client/blob/main/docs/NotFound.md)
 - [Pagination](https://github.com/apivideo/api.video-android-client/blob/main/docs/Pagination.md)
 - [PaginationLink](https://github.com/apivideo/api.video-android-client/blob/main/docs/PaginationLink.md)
 - [PlayerSessionEvent](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerSessionEvent.md)
 - [PlayerTheme](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerTheme.md)
 - [PlayerThemeAssets](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemeAssets.md)
 - [PlayerThemeCreationPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemeCreationPayload.md)
 - [PlayerThemeUpdatePayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemeUpdatePayload.md)
 - [PlayerThemesListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/PlayerThemesListResponse.md)
 - [Quality](https://github.com/apivideo/api.video-android-client/blob/main/docs/Quality.md)
 - [RawStatisticsListLiveStreamAnalyticsResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsListLiveStreamAnalyticsResponse.md)
 - [RawStatisticsListPlayerSessionEventsResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsListPlayerSessionEventsResponse.md)
 - [RawStatisticsListSessionsResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/RawStatisticsListSessionsResponse.md)
 - [RefreshTokenPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/RefreshTokenPayload.md)
 - [RestreamsRequestObject](https://github.com/apivideo/api.video-android-client/blob/main/docs/RestreamsRequestObject.md)
 - [RestreamsResponseObject](https://github.com/apivideo/api.video-android-client/blob/main/docs/RestreamsResponseObject.md)
 - [TokenCreationPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/TokenCreationPayload.md)
 - [TokenListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/TokenListResponse.md)
 - [UploadToken](https://github.com/apivideo/api.video-android-client/blob/main/docs/UploadToken.md)
 - [Video](https://github.com/apivideo/api.video-android-client/blob/main/docs/Video.md)
 - [VideoAssets](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoAssets.md)
 - [VideoClip](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoClip.md)
 - [VideoCreationPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoCreationPayload.md)
 - [VideoSession](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSession.md)
 - [VideoSessionClient](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionClient.md)
 - [VideoSessionDevice](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionDevice.md)
 - [VideoSessionLocation](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionLocation.md)
 - [VideoSessionOs](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionOs.md)
 - [VideoSessionReferrer](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionReferrer.md)
 - [VideoSessionSession](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSessionSession.md)
 - [VideoSource](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSource.md)
 - [VideoSourceLiveStream](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSourceLiveStream.md)
 - [VideoSourceLiveStreamLink](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoSourceLiveStreamLink.md)
 - [VideoStatus](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoStatus.md)
 - [VideoStatusEncoding](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoStatusEncoding.md)
 - [VideoStatusEncodingMetadata](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoStatusEncodingMetadata.md)
 - [VideoStatusIngest](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoStatusIngest.md)
 - [VideoStatusIngestReceivedParts](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoStatusIngestReceivedParts.md)
 - [VideoThumbnailPickPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoThumbnailPickPayload.md)
 - [VideoUpdatePayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoUpdatePayload.md)
 - [VideoWatermark](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideoWatermark.md)
 - [VideosListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosListResponse.md)
 - [Watermark](https://github.com/apivideo/api.video-android-client/blob/main/docs/Watermark.md)
 - [WatermarksListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/WatermarksListResponse.md)
 - [Webhook](https://github.com/apivideo/api.video-android-client/blob/main/docs/Webhook.md)
 - [WebhooksCreationPayload](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksCreationPayload.md)
 - [WebhooksListResponse](https://github.com/apivideo/api.video-android-client/blob/main/docs/WebhooksListResponse.md)


## Documentation for Authorization

### API key

Most endpoints required to be authenticated using the API key mechanism described in our [documentation](https://docs.api.video/reference#authentication).
The access token generation mechanism is automatically handled by the client. All you have to do is provide an API key when instantiating the `ApiVideoClient`:
```kotlin
val client = ApiVideoClient("YOUR_API_KEY")
```

### Public endpoints

Some endpoints don't require authentication. These one can be called with a client instantiated without API key:
```kotlin
val client = ApiVideoClient()
```

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
For direct call with `ApiClient`: Do not call API from the main thread, otherwise you will get a `android.os.NetworkOnMainThreadException`. Dispatch API calls with Thread, Executors or Kotlin coroutine to avoid this. Alternatively, APIs come with an asynchronous counterpart (`createAsync` for `create`) except for the upload endpoint.

## Have you gotten use from this API client?

Please take a moment to leave a star on the client ⭐

This helps other users to find the clients and also helps us understand which clients are most popular. Thank you!

# Contribution

Since this API client is generated from an OpenAPI description, we cannot accept pull requests made directly to the repository. If you want to contribute, you can open a pull request on the repository of our [client generator](https://github.com/apivideo/api-client-generator). Otherwise, you can also simply open an issue detailing your need on this repository.