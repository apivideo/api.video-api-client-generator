---
title: "api.video Swift Player analytics plugin"
slug: "apivideo-swift-player-analytics"
hidden: false
metadata: 
  description: "The official api.video Swift Player analytics plugin for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video Swift Player analytics plugin

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

## Project description
Swift library to manually call the api.video analytics collector.

This is useful if you are using a video player for which we do not yet provide a ready-to-use monitoring module.

## Getting started

### Installation
#### Swift Package Manager
In the Project Navigator select your own project. Then select the project in the Project section and click on the Package Dependencies tab. Click on the "+" button at the bottom. Paste the below url on the search bar on the top right. Finaly click on "Add package" button.
```
 https://github.com/apivideo/api.video-swift-player-analytics
```
Or add this in your Package.swift
```
  dependencies: [
        .package(url: "https://github.com/apivideo/api.video-swift-player-analytics.git", from: "1.1.0"),
    ],
```
#### Cocoapods
Add `pod 'ApiVideoPlayerAnalytics', '1.1.0'` in your `Podfile`

Run `pod install`

## Sample application

A demo application demonstrates how to use player analytics library. See [`/Example`](https://github.com/apivideo/api.video-swift-player-analytics/tree/main/Example) folder.

## Documentation

### Options

The analytics module constructor takes a `Options` parameter that contains the following options:

|         Option name | Mandatory | Type                                            | Description                                                                                                  |
| ------------------: | --------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
|            mediaUrl | **yes**   | String                                          | url of the media (eg. `https://cdn.api.video/vod/vi5oDagRVJBSKHxSiPux5rYD/hls/manifest.m3u8`)                |
|           videoInfo | **yes**   | VideoInfo                                       | information containing analytics collector url, video type (vod or live) and video id                        |
|            metadata | no        | ```[[String:String]]```                       | object containing [metadata](https://api.video/blog/tutorials/dynamic-metadata/)                              |
| onSessionIdReceived | no        | ```((String) -> ())?```            | callback called once the session id has been received                                                        |
|              onPing | no        | ```((PlaybackPingMessage) -> ())?``` | callback called before sending the ping message                                                              |

Options instantiation is made with either mediaUrl or videoInfo.

Once the module is instantiated, the following methods have to be called to monitor the playback events.

### PlayerAnalytics API

| Method | Description |
| ------------------------------------------------------------ | --------------------------------------------------------------------------------------------------- |
| `play(completion: @escaping (Result<Void, Error>) -> Void)`    | Method to call when the video starts playing for the first time (in the case of a resume after paused, use `resume()`)               |
| `resume(completion: @escaping (Result<Void, Error>) -> Void)`  | Method to call when the video playback is resumed after a pause                                      |
| `ready(completion: @escaping (Result<Void, Error>) -> Void)`   | Method to call once the player is ready to play the media                                            |
| `end(completion: @escaping (Result<Void, Error>) -> Void)`     | Method to call when the video is ended                                                               |
| `seek(from: Float, to: Float, completion : @escaping (Result<Void, Error>) -> Void)` | Method to call when a seek event occurs, the `from` and `to` parameters are mandatory and should contain the seek start & end times in seconds |
| `pause(completion: @escaping (Result<Void, Error>) -> Void)`   | Method to call when the video is paused                                                              |
| `destroy(completion: @escaping (Result<Void, VideoError>) -> Void)` | Method to call when the video player is disposed (e.g., when the user closes the navigation tab) |
| `currentTime`                                                 | Field to call each time the playback time changes (it should be called often, the accuracy of the collected data depends on it)       |
