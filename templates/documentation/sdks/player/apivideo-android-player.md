---
title: "api.video Android Player"
slug: "apivideo-android-player"
hidden: false
metadata: 
  description: "The official api.video Android Player component for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video Android Player

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast
video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in
your app.

## Project description

Easily integrate a video player for videos from [api.video](https://api.video/) in your Android
application.

## Getting started

### Installation

#### Gradle

In your module `build.gradle`, add the following code in `dependencies`:

```groovy
dependencies {
    implementation 'video.api:android-player:1.3.1'
}
```

### Permissions

In your `AndroidManifest.xml`, add the following code in `<manifest>`:

```xml

<uses-permission android:name="android.permission.INTERNET" />
```

### Retrieve your video Id

At this point, you must have uploaded a least one video to your account. If you haven't
see [how to upload a video](https://docs.api.video/vod/upload-a-video-regular-upload/). You'll need
a video Id to use this component and play a video from api.video. To get yours, follow these steps:

1. [Log into your account](https://dashboard.api.video/login) or create
   one [here](https://dashboard.api.video/register).
2. Copy your API key (sandbox or production if you are subscribed to one of
   our [plan](https://api.video/pricing)).
3. Go to [the official api.video documentation](https://docs.api.video/).
4. Go to API Reference -> Videos -> [List all videos](https://docs.api.video/reference/api/Videos#list-all-video-objects)
5. Create a `get` request to the `/videos` endpoint based on the reference, using a tool like Postman.
6. Copy the "videoId" value of one of elements of the API response.

Alternatively, you can find your video Id in the video details of
your [dashboard](https://dashboard.api.video).

### Code sample

1. Add a `ApiVideoExoPlayerView` to your Activity/Fragment layout:

```xml

<video.api.player.ApiVideoExoPlayerView
    android:id="@+id/playerView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:show_fullscreen_button="true"
    app:show_controls="true"
    app:show_subtitles="true" />
```

You can also use an ExoPlayer `StyledPlayerView` or a `SurfaceView` according to your customisation level.

2. Implements the `ApiVideoPlayerController.Listener` interface:

```kotlin
val playerControllerListener = object : ApiVideoPlayerController.Listener {
    override fun onError(error: Exception) {
        Log.e(TAG, "An error happened", error)
    }
    override fun onReady() {
        Log.I(TAG, "Player is ready")
    }
}
```

3. Instantiate the `ApiVideoPlayerController` in an your Activity/Fragment:

```kotlin
val playerView = findViewById<ApiVideoExoPlayerView>(R.id.playerView)

val player = ApiVideoPlayerController(
    applicationContext,
    VideoOptions("YOUR_VIDEO_ID", VideoType.VOD), // For private video: VideoOptions("YOUR_VIDEO_ID", VideoType.VOD, "YOUR_PRIVATE_VIDEO_TOKEN")
    playerListener,
    playerView
)
```

4. Fullscreen video

If you requires a fullscreen video. You will have to implement the `ApiVideoPlayerController.ViewListener` interface.
Check out for the implementation in the [Sample application](#sample-application).

## Sample application

A demo application demonstrates how to use player.
See [`/example`](https://github.com/apivideo/api.video-android-player/tree/main/example)
folder.

On the first run, you will have to set your video Id:
1. Click on the FloatingActionButton -> Settings
2. Replace "YOUR_VIDEO_ID" by your video Id

## Documentation

* [API documentation](https://apivideo.github.io/api.video-android-player/)
* [api.video documentation](https://docs.api.video/)

## Dependencies

We are using external library

| Plugin                                           | README                                                  |
|--------------------------------------------------|---------------------------------------------------------|
| [Exoplayer](https://github.com/google/ExoPlayer) | [README.md](https://github.com/google/ExoPlayer#readme) |

## FAQ

If you have any questions, ask us here: [https://community.api.video](https://community.api.video).
Or use [Issues].