---
title: api.video Android Player analytics plugin
meta: 
  description: The official api.video Android Player analytics plugin for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.
---

# api.video Android Player analytics plugin

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast
video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in
your app.

## Project description

Android library to manually call the api.video analytics collector.

This is useful if you are using a video player for which we do not yet provide a ready-to-use
monitoring module. 

This module also supports ExoPlayer analytics.

## Getting started

### Installation

#### Gradle

In your module `build.gradle`, add the following code in `dependencies`:

```groovy
dependencies {
    implementation 'video.api:android-player-analytics:1.4.0'
}
```

### Permissions

In your `AndroidManifest.xml`, add the following code in `<manifest>`:

```xml

<uses-permission android:name="android.permission.INTERNET" />
```

## Sample application

A demo application demonstrates how to use player analytics library.
See [`/example`](https://github.com/apivideo/api.video-android-player-analytics/tree/main/example)
folder.

## Documentation

### Options

The analytics module constructor takes a `Options` parameter that contains the following options:

|         Option name | Mandatory | Type                                                                                          | Description                                                                     |
|--------------------:|-----------|-----------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
|            mediaUrl | **        |                                                                                               |                                                                                 |
|               yes** | String    | url of the media (eg. `https://vod.api.video/vod/vi5oDagRVJBSKHxSiPux5rYD/hls/manifest.m3u8`) |                                                                                 |
|           videoInfo | **        |                                                                                               |                                                                                 |
|               yes** | VideoInfo | information containing analytics collector url, video type (vod or live) and video id         |                                                                                 |
|            metadata | no        | ```Map<String, String>```                                                                     | object containing [metadata](https://api.video/blog/tutorials/dynamic-metadata/) |
| onSessionIdReceived | no        | ```((sessionId: String) -> Unit)?```                                                          | callback called once the session id has been received                           |
|              onPing | no        | ```((message: PlaybackPingMessage) -> Unit)?```                                               | callback called before sending the ping message                                 |

Options instantiation is made with either mediaUrl or videoInfo.

Once the module is instantiated, the following methods have to be called to monitor the playback
events.

### ApiVideoPlayerAnalytics API

- Event time or current time

If you know the event timestamp, you can use it as the `eventTime` parameter. If you don't know the
event timestamp, you can set the `currentTime` parameter with a scheduler.

#### Methods

| Method | Description |
|----------|----------|
| `play(eventTime: Float = currentTime): Future<void>` | method to call when the video starts playing for the first time (in the case of a resume after paused, use `resume()`) |
| `resume(eventTime: Float = currentTime): Future<void>` | method to call when the video playback is resumed after a pause |
| `ready(eventTime: Float = currentTime): Future<void>` | method to call once the player is ready to play the media |
| `end(eventTime: Float = currentTime): Future<void>` | method to call when the video is ended |
| `seek(from: Float, to: Float): Future<void>` | method to call when a seek event occurs, the `from` and `to` parameters are mandatory and should contain the seek start & end times in seconds |
| `pause(eventTime: Float = currentTime): Future<void>` | method to call when the video is paused |
| `destroy(eventTime: Float = currentTime): Future<void>` | method to call when the video player is disposed (eg. when the use closes the navigation tab) |
| `currentTime` | field to call each time the playback time changes (it should be called often, the accuracy of the collected data depends on it) if you don't know event time. |

### API documentation

A complete [API documentation](https://apivideo.github.io/api.video-android-player-analytics/) is
available.