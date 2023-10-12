---
title: "api.video iOS video uploader"
slug: "ios-uploader"
hidden: false
metadata: 
  description: "The official api.video iOS video uploader for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video iOS video uploader

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

## Project description

api.video's iOS  uploads videos to api.video using delegated upload token or API Key.

It allows you to upload videos in two ways:
- standard upload: to send a whole video file in one go
- progressive upload: to send a video file by chunks, without needing to know the final size of the video file

## Getting started

### Installation

#### Carthage

Specify it in your `Cartfile`:

```
github "apivideo/api.video-ios-uploader" ~> 1.2.1
```

Run `carthage update`

#### CocoaPods

Add `pod 'ApiVideoUploader', '1.2.1'` in your `Podfile`

Run `pod install`

### Code sample

Please follow the [installation](#installation) instruction and execute the following Swift code:
```swift
import ApiVideoUploader


    // If you rather like to use the sandbox environment:
    // ApiVideoUploader.basePath = Environment.sandbox.rawValue
    // If you rather like to upload with your "YOUR_API_KEY" (upload)
    // ApiVideoUploader.apiKey = "YOUR_API_KEY"

    try VideosAPI.uploadWithUploadToken(token: "MY_VIDEO_TOKEN", file: url) { video, error in
        if let video = video {
            // Manage upload with upload token success here
        }
        if let error = error {
            // Manage upload with upload token error here
        }
    }

```

## Documentation

### API Endpoints

All URIs are relative to *https://ws.api.video*


### VideosAPI

#### Retrieve an instance of VideosAPI:

```swift
VideosAPI
```

#### Endpoints

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideosAPI.md#upload) | **POST** /videos/{videoId}/source | Upload a video
[**uploadWithUploadToken**](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideosAPI.md#uploadWithUploadToken) | **POST** /upload | Upload with an delegated upload token



### Models

 - [AccessToken](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/AccessToken.md)
 - [AdditionalBadRequestErrors](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/AdditionalBadRequestErrors.md)
 - [AuthenticatePayload](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/AuthenticatePayload.md)
 - [BadRequest](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/BadRequest.md)
 - [Metadata](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/Metadata.md)
 - [NotFound](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/NotFound.md)
 - [RefreshTokenPayload](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/RefreshTokenPayload.md)
 - [Video](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/Video.md)
 - [VideoAssets](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideoAssets.md)
 - [VideoSource](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideoSource.md)
 - [VideoSourceLiveStream](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideoSourceLiveStream.md)
 - [VideoSourceLiveStreamLink](https://github.com/apivideo/api.video-ios-uploader/blob/main/docs/VideoSourceLiveStreamLink.md)


## Documentation for Authorization

### API key

Most endpoints required to be authenticated using the API key mechanism described in our [documentation](https://docs.api.video/reference#authentication).
The access token generation mechanism is automatically handled by the client. All you have to do is provide an API key:
```swift
ApiVideoUploader.apiKey = YOUR_API_KEY
```

### Public endpoints

Some endpoints don't require authentication. These one can be called without setting `ApiVideoUploader.apiKey`:

## Have you gotten use from this API client?

Please take a moment to leave a star on the client ⭐

This helps other users to find the clients and also helps us understand which clients are most popular. Thank you!

## Contribution

Since this API client is generated from an OpenAPI description, we cannot accept pull requests made directly to the repository. If you want to contribute, you can open a pull request on the repository of our [client generator](https://github.com/apivideo/api-client-generator). Otherwise, you can also simply open an issue detailing your need on this repository.