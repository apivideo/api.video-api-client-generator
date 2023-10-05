---
title: "api.video Flutter video uploader"
slug: "flutter-uploader"
hidden: false
metadata: 
  description: "The official api.video Flutter video uploader for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

api.video Flutter video uploader
==============

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

# Table of contents

- [Table of contents](#table-of-contents)
- [Project description](#project-description)
- [Getting started](#getting-started)
    - [Installation](#installation)
      - [For web usage](#for-web-usage)
    - [Code sample](#code-sample)
- [Dependencies](#dependencies)
- [FAQ](#faq)
- [Found this video uploader useful?](#found-this-video-uploader-useful)

# Project description

api.video's Flutter uploader uploads videos to api.video using delegated upload token or API Key.

It allows you to upload videos in two ways:

- standard upload: to send a whole video file in one go
- progressive upload: to send a video file by chunks, without needing to know the final size of the video file

# Getting started

### Installation

Run this command:

```bash
flutter pub add video_uploader
 ```
 
This will add the following lines to your package's pubspec.yaml file:

``` yaml
dependencies:
  video_uploader: ^1.1.0
```

#### For web usage

Add the api.video TypeScript uploader script to your `web/index.html` head.

```html
  <!DOCTYPE html>
<html>
  <head>
    ...
    <script src="https://unpkg.com/@api.video/video-uploader" defer></script>
  </head>
  <body>
    ...
  </body>
</html>
```

### Code sample

```dart
import 'package:video_uploader/video_uploader.dart';

var video = await ApiVideoUploader.uploadWithUploadToken("MY_VIDEO_TOKEN", "path/to/my-video.mp4");
```

# Dependencies

This project is using external library

| Plugin | README |
| ------ | ------ |
| iOS-video-uploader | [iOS-video-uploader](https://github.com/apivideo/api.video-ios-uploader) |
| android-video-uploader | [android-video-uploader](https://github.com/apivideo/api.video-android-uploader) |

# FAQ

If you have any questions, ask us here: [https://community.api.video](https://community.api.video).
Or use [Issues](https://github.com/apivideo/api.video-flutter-uploader/issues).

# Found this video uploader useful?

Please star ⭐ the repo to help others find it.