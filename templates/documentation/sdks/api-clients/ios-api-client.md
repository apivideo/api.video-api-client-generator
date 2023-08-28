---
title: "iOS API client"
slug: "ios-api-client"
hidden: false
metadata: 
  description: "The official iOS client for api.video. [api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

iOS Api Client
==============

[api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

# Table of contents

- [Project description](#project-description)
- [Getting started](#getting-started)
  - [Installation](#installation)
    - [Carthage](#carthage)
    - [CocoaPods](#cocoaPods)
  - [Code sample](#code-sample)
- [Documentation](#documentation)
  - [API Endpoints](#api-endpoints)
    - [AnalyticsAPI](#AnalyticsAPI)
    - [CaptionsAPI](#CaptionsAPI)
    - [ChaptersAPI](#ChaptersAPI)
    - [LiveStreamsAPI](#LiveStreamsAPI)
    - [PlayerThemesAPI](#PlayerThemesAPI)
    - [RawStatisticsAPI](#RawStatisticsAPI)
    - [UploadTokensAPI](#UploadTokensAPI)
    - [VideosAPI](#VideosAPI)
    - [WatermarksAPI](#WatermarksAPI)
    - [WebhooksAPI](#WebhooksAPI)
  - [Models](#models)
  - [Authorization](#documentation-for-authorization)
    - [API key](#api-key)
    - [Public endpoints](#public-endpoints)
- [Have you gotten use from this API client?](#have-you-gotten-use-from-this-api-client)
- [Contribution](#contribution)

# Project description
api.video's iOS  streamlines the coding process. Chunking files is handled for you, as is pagination and refreshing your tokens.

# Getting started

## Installation

### Carthage

Specify it in your `Cartfile`:

```
github "apivideo/api.video-ios-client" ~> 1.2.0
```

Run `carthage update`

### CocoaPods

Add `pod 'ApiVideoClient', '1.2.0'` in your `Podfile`

Run `pod install`

## Code sample

Please follow the [installation](#installation) instruction and execute the following Swift code:
```swift
import ApiVideoClient

    ApiVideoClient.apiKey = "YOUR_API_KEY"
    // if you rather like to use the sandbox environment:
    // ApiVideoClient.basePath = Environment.sandbox.rawValue

    let url = URL(string: "My video.mov")

    VideosAPI.create(videoCreationPayload: VideoCreationPayload(title: "my video")) { video, error in
    if let video = video {
        do {
            try VideosAPI.upload(videoId: video.videoId,
                             file: url) { video, error in
                if let video = video {
                    // Manage upload success here
                }
                if let error = error {
                    // Manage upload error here
                }
            }
        } catch {
            // Manage error on file here
        }
    }
    if let error = error {
       // Manage create error here
    }
}
```

# Documentation

## API Endpoints

All URIs are relative to *https://ws.api.video*


### AnalyticsAPI

#### Retrieve an instance of AnalyticsAPI:

```swift
AnalyticsAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLiveStreamsPlays**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AnalyticsAPI.md#getLiveStreamsPlays) | **GET** /analytics/live-streams/plays | Get play events for live stream
[**getVideosPlays**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AnalyticsAPI.md#getVideosPlays) | **GET** /analytics/videos/plays | Get play events for video


### CaptionsAPI

#### Retrieve an instance of CaptionsAPI:

```swift
CaptionsAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsAPI.md#upload) | **POST** /videos/{videoId}/captions/{language} | Upload a caption
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsAPI.md#get) | **GET** /videos/{videoId}/captions/{language} | Retrieve a caption
[**update**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsAPI.md#update) | **PATCH** /videos/{videoId}/captions/{language} | Update a caption
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsAPI.md#delete) | **DELETE** /videos/{videoId}/captions/{language} | Delete a caption
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsAPI.md#list) | **GET** /videos/{videoId}/captions | List video captions


### ChaptersAPI

#### Retrieve an instance of ChaptersAPI:

```swift
ChaptersAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/ChaptersAPI.md#upload) | **POST** /videos/{videoId}/chapters/{language} | Upload a chapter
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/ChaptersAPI.md#get) | **GET** /videos/{videoId}/chapters/{language} | Retrieve a chapter
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/ChaptersAPI.md#delete) | **DELETE** /videos/{videoId}/chapters/{language} | Delete a chapter
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/ChaptersAPI.md#list) | **GET** /videos/{videoId}/chapters | List video chapters


### LiveStreamsAPI

#### Retrieve an instance of LiveStreamsAPI:

```swift
LiveStreamsAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#create) | **POST** /live-streams | Create live stream
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#get) | **GET** /live-streams/{liveStreamId} | Retrieve live stream
[**update**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#update) | **PATCH** /live-streams/{liveStreamId} | Update a live stream
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#delete) | **DELETE** /live-streams/{liveStreamId} | Delete a live stream
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#list) | **GET** /live-streams | List all live streams
[**uploadThumbnail**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#uploadThumbnail) | **POST** /live-streams/{liveStreamId}/thumbnail | Upload a thumbnail
[**deleteThumbnail**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamsAPI.md#deleteThumbnail) | **DELETE** /live-streams/{liveStreamId}/thumbnail | Delete a thumbnail


### PlayerThemesAPI

#### Retrieve an instance of PlayerThemesAPI:

```swift
PlayerThemesAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#create) | **POST** /players | Create a player
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#get) | **GET** /players/{playerId} | Retrieve a player
[**update**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#update) | **PATCH** /players/{playerId} | Update a player
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#delete) | **DELETE** /players/{playerId} | Delete a player
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#list) | **GET** /players | List all player themes
[**uploadLogo**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#uploadLogo) | **POST** /players/{playerId}/logo | Upload a logo
[**deleteLogo**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesAPI.md#deleteLogo) | **DELETE** /players/{playerId}/logo | Delete logo


### RawStatisticsAPI

#### Retrieve an instance of RawStatisticsAPI:

```swift
RawStatisticsAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**listLiveStreamSessions**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsAPI.md#listLiveStreamSessions) | **GET** /analytics/live-streams/{liveStreamId} | List live stream player sessions
[**listSessionEvents**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsAPI.md#listSessionEvents) | **GET** /analytics/sessions/{sessionId}/events | List player session events
[**listVideoSessions**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsAPI.md#listVideoSessions) | **GET** /analytics/videos/{videoId} | List video player sessions


### UploadTokensAPI

#### Retrieve an instance of UploadTokensAPI:

```swift
UploadTokensAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**createToken**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/UploadTokensAPI.md#createToken) | **POST** /upload-tokens | Generate an upload token
[**getToken**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/UploadTokensAPI.md#getToken) | **GET** /upload-tokens/{uploadToken} | Retrieve upload token
[**deleteToken**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/UploadTokensAPI.md#deleteToken) | **DELETE** /upload-tokens/{uploadToken} | Delete an upload token
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/UploadTokensAPI.md#list) | **GET** /upload-tokens | List all active upload tokens


### VideosAPI

#### Retrieve an instance of VideosAPI:

```swift
VideosAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#create) | **POST** /videos | Create a video object
[**upload**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#upload) | **POST** /videos/{videoId}/source | Upload a video
[**uploadWithUploadToken**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#uploadWithUploadToken) | **POST** /upload | Upload with an delegated upload token
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#get) | **GET** /videos/{videoId} | Retrieve a video object
[**update**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#update) | **PATCH** /videos/{videoId} | Update a video object
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#delete) | **DELETE** /videos/{videoId} | Delete a video object
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#list) | **GET** /videos | List all video objects
[**uploadThumbnail**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#uploadThumbnail) | **POST** /videos/{videoId}/thumbnail | Upload a thumbnail
[**pickThumbnail**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#pickThumbnail) | **PATCH** /videos/{videoId}/thumbnail | Set a thumbnail
[**getStatus**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#getStatus) | **GET** /videos/{videoId}/status | Retrieve video status and details


### WatermarksAPI

#### Retrieve an instance of WatermarksAPI:

```swift
WatermarksAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WatermarksAPI.md#upload) | **POST** /watermarks | Upload a watermark
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WatermarksAPI.md#delete) | **DELETE** /watermarks/{watermarkId} | Delete a watermark
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WatermarksAPI.md#list) | **GET** /watermarks | List all watermarks


### WebhooksAPI

#### Retrieve an instance of WebhooksAPI:

```swift
WebhooksAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksAPI.md#create) | **POST** /webhooks | Create Webhook
[**get**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksAPI.md#get) | **GET** /webhooks/{webhookId} | Retrieve Webhook details
[**delete**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksAPI.md#delete) | **DELETE** /webhooks/{webhookId} | Delete a Webhook
[**list**](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksAPI.md#list) | **GET** /webhooks | List all webhooks



## Models

 - [AccessToken](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AccessToken.md)
 - [AdditionalBadRequestErrors](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AdditionalBadRequestErrors.md)
 - [AnalyticsData](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AnalyticsData.md)
 - [AnalyticsPlays400Error](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AnalyticsPlays400Error.md)
 - [AnalyticsPlaysResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AnalyticsPlaysResponse.md)
 - [AuthenticatePayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/AuthenticatePayload.md)
 - [BadRequest](https://github.com/apivideo/api.video-ios-client/blob/main/docs/BadRequest.md)
 - [BytesRange](https://github.com/apivideo/api.video-ios-client/blob/main/docs/BytesRange.md)
 - [Caption](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Caption.md)
 - [CaptionsListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsListResponse.md)
 - [CaptionsUpdatePayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/CaptionsUpdatePayload.md)
 - [Chapter](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Chapter.md)
 - [ChaptersListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/ChaptersListResponse.md)
 - [Link](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Link.md)
 - [LiveStream](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStream.md)
 - [LiveStreamAssets](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamAssets.md)
 - [LiveStreamCreationPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamCreationPayload.md)
 - [LiveStreamListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamListResponse.md)
 - [LiveStreamSession](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSession.md)
 - [LiveStreamSessionClient](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSessionClient.md)
 - [LiveStreamSessionDevice](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSessionDevice.md)
 - [LiveStreamSessionLocation](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSessionLocation.md)
 - [LiveStreamSessionReferrer](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSessionReferrer.md)
 - [LiveStreamSessionSession](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamSessionSession.md)
 - [LiveStreamUpdatePayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/LiveStreamUpdatePayload.md)
 - [Metadata](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Metadata.md)
 - [Model403ErrorSchema](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Model403ErrorSchema.md)
 - [NotFound](https://github.com/apivideo/api.video-ios-client/blob/main/docs/NotFound.md)
 - [Pagination](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Pagination.md)
 - [PaginationLink](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PaginationLink.md)
 - [PlayerSessionEvent](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerSessionEvent.md)
 - [PlayerTheme](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerTheme.md)
 - [PlayerThemeAssets](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemeAssets.md)
 - [PlayerThemeCreationPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemeCreationPayload.md)
 - [PlayerThemeUpdatePayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemeUpdatePayload.md)
 - [PlayerThemesListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/PlayerThemesListResponse.md)
 - [Quality](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Quality.md)
 - [RawStatisticsListLiveStreamAnalyticsResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsListLiveStreamAnalyticsResponse.md)
 - [RawStatisticsListPlayerSessionEventsResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsListPlayerSessionEventsResponse.md)
 - [RawStatisticsListSessionsResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RawStatisticsListSessionsResponse.md)
 - [RefreshTokenPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RefreshTokenPayload.md)
 - [RestreamsRequestObject](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RestreamsRequestObject.md)
 - [RestreamsResponseObject](https://github.com/apivideo/api.video-ios-client/blob/main/docs/RestreamsResponseObject.md)
 - [TokenCreationPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/TokenCreationPayload.md)
 - [TokenListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/TokenListResponse.md)
 - [UploadToken](https://github.com/apivideo/api.video-ios-client/blob/main/docs/UploadToken.md)
 - [Video](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Video.md)
 - [VideoAssets](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoAssets.md)
 - [VideoClip](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoClip.md)
 - [VideoCreationPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoCreationPayload.md)
 - [VideoSession](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSession.md)
 - [VideoSessionClient](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionClient.md)
 - [VideoSessionDevice](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionDevice.md)
 - [VideoSessionLocation](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionLocation.md)
 - [VideoSessionOs](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionOs.md)
 - [VideoSessionReferrer](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionReferrer.md)
 - [VideoSessionSession](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSessionSession.md)
 - [VideoSource](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSource.md)
 - [VideoSourceLiveStream](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSourceLiveStream.md)
 - [VideoSourceLiveStreamLink](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoSourceLiveStreamLink.md)
 - [VideoStatus](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoStatus.md)
 - [VideoStatusEncoding](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoStatusEncoding.md)
 - [VideoStatusEncodingMetadata](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoStatusEncodingMetadata.md)
 - [VideoStatusIngest](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoStatusIngest.md)
 - [VideoStatusIngestReceivedParts](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoStatusIngestReceivedParts.md)
 - [VideoThumbnailPickPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoThumbnailPickPayload.md)
 - [VideoUpdatePayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoUpdatePayload.md)
 - [VideoWatermark](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideoWatermark.md)
 - [VideosListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosListResponse.md)
 - [Watermark](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Watermark.md)
 - [WatermarksListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WatermarksListResponse.md)
 - [Webhook](https://github.com/apivideo/api.video-ios-client/blob/main/docs/Webhook.md)
 - [WebhooksCreationPayload](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksCreationPayload.md)
 - [WebhooksListResponse](https://github.com/apivideo/api.video-ios-client/blob/main/docs/WebhooksListResponse.md)


## Documentation for Authorization

### API key

Most endpoints required to be authenticated using the API key mechanism described in our [documentation](https://docs.api.video/reference#authentication).
The access token generation mechanism is automatically handled by the client. All you have to do is provide an API key:
```swift
ApiVideoClient.apiKey = YOUR_API_KEY
```

### Public endpoints

Some endpoints don't require authentication. These one can be called without setting `ApiVideoClient.apiKey`:

## Have you gotten use from this API client?

Please take a moment to leave a star on the client ⭐

This helps other users to find the clients and also helps us understand which clients are most popular. Thank you!

# Contribution

Since this API client is generated from an OpenAPI description, we cannot accept pull requests made directly to the repository. If you want to contribute, you can open a pull request on the repository of our [client generator](https://github.com/apivideo/api-client-generator). Otherwise, you can also simply open an issue detailing your need on this repository.