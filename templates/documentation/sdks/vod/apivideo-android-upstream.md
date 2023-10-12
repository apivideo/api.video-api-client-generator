---
title: "api.video Android Upstream"
slug: "android-upstream"
hidden: false
metadata: 
  description: "The official api.video Android Upstream package for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video Android Upstream: camera + progressive upload

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast
video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in
your app.

## Project description

This library is an easy way to capture your video and microphone and upload it
to [api.video](https://api.video/) at the same time.

## Getting started

### Installation

#### Gradle

On build.gradle add the following code in dependencies:

```groovy
dependencies {
    implementation 'video.api:android-upstream:1.1.0'
}
```

### Permissions

```xml

<manifest>
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
</manifest>
```

Your application must dynamically require `android.permission.CAMERA`
and `android.permission.RECORD_AUDIO`.

### Code sample

1. Add [permissions](#permissions) to your `AndroidManifest.xml` and request them in your
   Activity/Fragment.
2. Add a `ApiVideoView` to your Activity/Fragment layout for the camera preview.

```xml

<video.api.upstream.views.ApiVideoView android:id="@+id/apiVideoView"
    android:layout_width="match_parent" android:layout_height="match_parent" />
```

3. Create an `ApiVideoUpstream` instance in your fragment or activity.

```kotlin
class MyFragment : Fragment() {
    private var apiVideoView: ApiVideoView? = null
    private lateinit var upstream: ApiVideoUpstream

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiVideoView = view.findViewById(R.id.apiVideoView)
        val audioConfig = AudioConfig(
            bitrate = 128 * 1000, // 128 kbps
            sampleRate = 44100, // 44.1 kHz
            stereo = true,
            echoCanceler = true,
            noiseSuppressor = true
        )
        val videoConfig = VideoConfig(
            bitrate = 4 * 1000 * 1000, // 4 Mbps
            resolution = Resolution.RESOLUTION_720,
            fps = 30
        )
        ApiVideoUpstream(
            context = requireContext(),
            apiKey = apiKey,
            timeout = 60000, // 1 min
            initialAudioConfig = audioConfig,
            initialVideoConfig = videoConfig,
            apiVideoView = apiVideoView
        )
    }
}
```

4. [Create or get your video id](https://github.com/apivideo/api.video-android-client#videosapi)
   or [create or get an upload token](https://github.com/apivideo/api.video-android-client#uploadtokensapi)
   from [api.video](https://api.video/)
   Alternatively, you can create or get an upload token in
   the [dashboard](https://dashboard.api.video/upload-tokens).

5. Start your record

If you are using video id:

```kotlin
upstream.startStreamingForVideoId("YOUR_VIDEO_ID")
```

If you are using an upload token:

```kotlin
upstream.startStreamingForToken("YOUR_UPLOAD_TOKEN")
```

For detailed information on this upstream library API, refers
to [API documentation](https://apivideo.github.io/api.video-android-upstream/).

## Tips

* If a part of the video is not uploaded, you can resume the upload by creating a
  new `MultiFileUploader` with `ApiVideoUpstream.loadExistingSession`.
* You can check device supported configurations by using the helper: `Helper`

## Documentation

* [API documentation](https://apivideo.github.io/api.video-android-upstream/)
* [api.video documentation](https://docs.api.video/)

## Sample application

A demo application demonstrates how to use this upstream library. See `/example` folder.

## Dependencies

We are using external library

| Plugin                                                  | README                                                                       |
|---------------------------------------------------------|------------------------------------------------------------------------------|
| [StreamPack](https://github.com/ThibaultBee/StreamPack) | [README.md](https://github.com/ThibaultBee/StreamPack/blob/master/README.md) |

## FAQ

If you have any questions, ask us in the [community](https://community.api.video) or
use [issues](https://github.com/apivideo/api.video-android-upstream/issues).