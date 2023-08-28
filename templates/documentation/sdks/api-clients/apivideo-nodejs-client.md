---
title: "NodeJS API client"
slug: "nodejs-api-client"
hidden: false
metadata: 
  description: "The official NodeJS client for api.video. [api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

NodeJS API Client
==============

[api.video](https://api.video) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

# Table of contents

- [Project description](#project-description)
- [Getting started](#getting-started)
  - [Installation](#installation)
  - [Migration](#migration)
  - [Development](#development)
  - [Code sample](#code-sample)
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
  - [Authorization](#authorization)
    - [API key](#api-key)
    - [Get the access token](#get-the-access-token)
    - [Public endpoints](#public-endpoints)
- [Have you gotten use from this API client?](#have-you-gotten-use-from-this-api-client-)
- [Contribution](#contribution)

# Project description

api.video's Java Node.js is a lightweight client built in `TypeScript` that streamlines the coding process. Chunking files is handled for you, as is pagination and refreshing your tokens.

# Getting started

## Installation
With `npm`:
```
npm install @api.video/nodejs-client --save
```

...or with `yarn`:
```
yarn add @api.video/nodejs-client
```

## Migration

If you're coming from [@api.video/nodejs-sdk](https://github.com/apivideo/nodejs-sdk) make sure to read our [Migration guide](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/MIGRATE.md) first.

## Development

To build and compile the typescript sources to javascript use:
```
npm install
npm run build
```

## Code sample

```typescript
const ApiVideoClient = require('@api.video/nodejs-client');
// or: import ApiVideoClient from '@api.video/nodejs-client';

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

        // create a video
        const videoCreationPayload = {
            title: "Maths video", // The title of your new video.
            description: "A video about string theory.", // A brief description of your video.
        };
        const video = await client.videos.create(videoCreationPayload);

        // upload a video file into the video container
        await client.videos.upload(video.videoId, "my-video-file.mp4");
    } catch (e) {
        console.error(e);
    }
})();
```

# Documentation

## API Endpoints


### AnalyticsApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**getLiveStreamsPlays()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/AnalyticsApi.md#getLiveStreamsPlays) | Get play events for live stream | **GET** /analytics/live-streams/plays
[**getVideosPlays()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/AnalyticsApi.md#getVideosPlays) | Get play events for video | **GET** /analytics/videos/plays


### CaptionsApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**upload()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/CaptionsApi.md#upload) | Upload a caption | **POST** /videos/{videoId}/captions/{language}
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/CaptionsApi.md#get) | Retrieve a caption | **GET** /videos/{videoId}/captions/{language}
[**update()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/CaptionsApi.md#update) | Update a caption | **PATCH** /videos/{videoId}/captions/{language}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/CaptionsApi.md#delete) | Delete a caption | **DELETE** /videos/{videoId}/captions/{language}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/CaptionsApi.md#list) | List video captions | **GET** /videos/{videoId}/captions


### ChaptersApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**upload()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/ChaptersApi.md#upload) | Upload a chapter | **POST** /videos/{videoId}/chapters/{language}
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/ChaptersApi.md#get) | Retrieve a chapter | **GET** /videos/{videoId}/chapters/{language}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/ChaptersApi.md#delete) | Delete a chapter | **DELETE** /videos/{videoId}/chapters/{language}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/ChaptersApi.md#list) | List video chapters | **GET** /videos/{videoId}/chapters


### LiveStreamsApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**create()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#create) | Create live stream | **POST** /live-streams
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#get) | Retrieve live stream | **GET** /live-streams/{liveStreamId}
[**update()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#update) | Update a live stream | **PATCH** /live-streams/{liveStreamId}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#delete) | Delete a live stream | **DELETE** /live-streams/{liveStreamId}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#list) | List all live streams | **GET** /live-streams
[**uploadThumbnail()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#uploadThumbnail) | Upload a thumbnail | **POST** /live-streams/{liveStreamId}/thumbnail
[**deleteThumbnail()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/LiveStreamsApi.md#deleteThumbnail) | Delete a thumbnail | **DELETE** /live-streams/{liveStreamId}/thumbnail


### PlayerThemesApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**create()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#create) | Create a player | **POST** /players
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#get) | Retrieve a player | **GET** /players/{playerId}
[**update()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#update) | Update a player | **PATCH** /players/{playerId}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#delete) | Delete a player | **DELETE** /players/{playerId}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#list) | List all player themes | **GET** /players
[**uploadLogo()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#uploadLogo) | Upload a logo | **POST** /players/{playerId}/logo
[**deleteLogo()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/PlayerThemesApi.md#deleteLogo) | Delete logo | **DELETE** /players/{playerId}/logo


### RawStatisticsApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**listLiveStreamSessions()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/RawStatisticsApi.md#listLiveStreamSessions) | List live stream player sessions | **GET** /analytics/live-streams/{liveStreamId}
[**listSessionEvents()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/RawStatisticsApi.md#listSessionEvents) | List player session events | **GET** /analytics/sessions/{sessionId}/events
[**listVideoSessions()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/RawStatisticsApi.md#listVideoSessions) | List video player sessions | **GET** /analytics/videos/{videoId}


### UploadTokensApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**createToken()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/UploadTokensApi.md#createToken) | Generate an upload token | **POST** /upload-tokens
[**getToken()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/UploadTokensApi.md#getToken) | Retrieve upload token | **GET** /upload-tokens/{uploadToken}
[**deleteToken()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/UploadTokensApi.md#deleteToken) | Delete an upload token | **DELETE** /upload-tokens/{uploadToken}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/UploadTokensApi.md#list) | List all active upload tokens | **GET** /upload-tokens


### VideosApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**create()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create) | Create a video object | **POST** /videos
[**upload()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#upload) | Upload a video | **POST** /videos/{videoId}/source
[**uploadWithUploadToken()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#uploadWithUploadToken) | Upload with an delegated upload token | **POST** /upload
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#get) | Retrieve a video object | **GET** /videos/{videoId}
[**update()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#update) | Update a video object | **PATCH** /videos/{videoId}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#delete) | Delete a video object | **DELETE** /videos/{videoId}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#list) | List all video objects | **GET** /videos
[**uploadThumbnail()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#uploadThumbnail) | Upload a thumbnail | **POST** /videos/{videoId}/thumbnail
[**pickThumbnail()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#pickThumbnail) | Set a thumbnail | **PATCH** /videos/{videoId}/thumbnail
[**getStatus()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#getStatus) | Retrieve video status and details | **GET** /videos/{videoId}/status


### WatermarksApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**upload()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WatermarksApi.md#upload) | Upload a watermark | **POST** /watermarks
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WatermarksApi.md#delete) | Delete a watermark | **DELETE** /watermarks/{watermarkId}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WatermarksApi.md#list) | List all watermarks | **GET** /watermarks


### WebhooksApi

Method | Description | HTTP request
------------- | ------------- | -------------
[**create()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WebhooksApi.md#create) | Create Webhook | **POST** /webhooks
[**get()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WebhooksApi.md#get) | Retrieve Webhook details | **GET** /webhooks/{webhookId}
[**delete()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WebhooksApi.md#delete) | Delete a Webhook | **DELETE** /webhooks/{webhookId}
[**list()**](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/WebhooksApi.md#list) | List all webhooks | **GET** /webhooks



## Models

 - [AccessToken](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AccessToken.md)
 - [AdditionalBadRequestErrors](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AdditionalBadRequestErrors.md)
 - [AnalyticsData](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AnalyticsData.md)
 - [AnalyticsPlays400Error](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AnalyticsPlays400Error.md)
 - [AnalyticsPlaysResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AnalyticsPlaysResponse.md)
 - [AuthenticatePayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/AuthenticatePayload.md)
 - [BadRequest](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/BadRequest.md)
 - [BytesRange](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/BytesRange.md)
 - [Caption](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Caption.md)
 - [CaptionsListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/CaptionsListResponse.md)
 - [CaptionsUpdatePayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/CaptionsUpdatePayload.md)
 - [Chapter](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Chapter.md)
 - [ChaptersListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/ChaptersListResponse.md)
 - [Link](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Link.md)
 - [LiveStream](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStream.md)
 - [LiveStreamAssets](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamAssets.md)
 - [LiveStreamCreationPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamCreationPayload.md)
 - [LiveStreamListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamListResponse.md)
 - [LiveStreamSession](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSession.md)
 - [LiveStreamSessionClient](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSessionClient.md)
 - [LiveStreamSessionDevice](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSessionDevice.md)
 - [LiveStreamSessionLocation](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSessionLocation.md)
 - [LiveStreamSessionReferrer](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSessionReferrer.md)
 - [LiveStreamSessionSession](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamSessionSession.md)
 - [LiveStreamUpdatePayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/LiveStreamUpdatePayload.md)
 - [Metadata](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Metadata.md)
 - [Model403ErrorSchema](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Model403ErrorSchema.md)
 - [NotFound](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/NotFound.md)
 - [Pagination](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Pagination.md)
 - [PaginationLink](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PaginationLink.md)
 - [PlayerSessionEvent](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerSessionEvent.md)
 - [PlayerTheme](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerTheme.md)
 - [PlayerThemeAssets](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerThemeAssets.md)
 - [PlayerThemeCreationPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerThemeCreationPayload.md)
 - [PlayerThemeUpdatePayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerThemeUpdatePayload.md)
 - [PlayerThemesListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/PlayerThemesListResponse.md)
 - [Quality](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Quality.md)
 - [RawStatisticsListLiveStreamAnalyticsResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RawStatisticsListLiveStreamAnalyticsResponse.md)
 - [RawStatisticsListPlayerSessionEventsResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RawStatisticsListPlayerSessionEventsResponse.md)
 - [RawStatisticsListSessionsResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RawStatisticsListSessionsResponse.md)
 - [RefreshTokenPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RefreshTokenPayload.md)
 - [RestreamsRequestObject](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RestreamsRequestObject.md)
 - [RestreamsResponseObject](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/RestreamsResponseObject.md)
 - [TokenCreationPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/TokenCreationPayload.md)
 - [TokenListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/TokenListResponse.md)
 - [UploadToken](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/UploadToken.md)
 - [Video](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Video.md)
 - [VideoAssets](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoAssets.md)
 - [VideoClip](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoClip.md)
 - [VideoCreationPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoCreationPayload.md)
 - [VideoSession](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSession.md)
 - [VideoSessionClient](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionClient.md)
 - [VideoSessionDevice](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionDevice.md)
 - [VideoSessionLocation](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionLocation.md)
 - [VideoSessionOs](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionOs.md)
 - [VideoSessionReferrer](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionReferrer.md)
 - [VideoSessionSession](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSessionSession.md)
 - [VideoSource](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSource.md)
 - [VideoSourceLiveStream](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSourceLiveStream.md)
 - [VideoSourceLiveStreamLink](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoSourceLiveStreamLink.md)
 - [VideoStatus](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoStatus.md)
 - [VideoStatusEncoding](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoStatusEncoding.md)
 - [VideoStatusEncodingMetadata](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoStatusEncodingMetadata.md)
 - [VideoStatusIngest](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoStatusIngest.md)
 - [VideoStatusIngestReceivedParts](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoStatusIngestReceivedParts.md)
 - [VideoThumbnailPickPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoThumbnailPickPayload.md)
 - [VideoUpdatePayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoUpdatePayload.md)
 - [VideoWatermark](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideoWatermark.md)
 - [VideosListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/VideosListResponse.md)
 - [Watermark](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Watermark.md)
 - [WatermarksListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/WatermarksListResponse.md)
 - [Webhook](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/Webhook.md)
 - [WebhooksCreationPayload](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/WebhooksCreationPayload.md)
 - [WebhooksListResponse](https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/model/WebhooksListResponse.md)


## Authorization

### API key

Most endpoints required to be authenticated using the API key mechanism described in our [documentation](https://docs.api.video/reference#authentication).
The access token generation mechanism is automatically handled by the client.

All you have to do is provide an API key when instantiating the ApiVideoClient:
```js
const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });
```

### Get the access token

If you need to access the access-token value obtained using the API key, you can use the getAccessToken() method of the client:
```js
const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });
const accessToken = await client.getAccessToken();
```

### Public endpoints

Some endpoints don't require authentication. These one can be called with an ApiVideoClient instantiated without API key:
```js
const client = new ApiVideoClient();
```

# Have you gotten use from this API client?

Please take a moment to leave a star on the client ⭐

This helps other users to find the clients and also helps us understand which clients are most popular. Thank you!


# Contribution

Since this API client is generated from an OpenAPI description, we cannot accept pull requests made directly to the repository. If you want to contribute, you can open a pull request on the repository of our [client generator](https://github.com/apivideo/api-client-generator). Otherwise, you can also simply open an issue detailing your need on this repository.