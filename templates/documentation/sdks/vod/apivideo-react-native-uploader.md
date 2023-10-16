---
title: "api.video React Native video uploader"
slug: "react-native-uploader"
hidden: false
metadata: 
  description: "The official api.video React Native video uploader for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video React Native video uploader

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

## Project description

This module is an easy way to upload video with delegated token to api.video

## Getting started

### Installation

```sh
npm install @api.video/react-native-video-uploader
```

or

```sh
yarn add @api.video/react-native-video-uploader
```

_Note: if you are on iOS, you will need two extra steps:_

1. Don't forget to install the native dependencies with Cocoapods

```sh
cd ios && pod install
```

2. This project contains Swift code, and if it's your first dependency with Swift code, you need to create an empty Swift file in your project (with the bridging header) from XCode. [Find how to do that](https://github.com/apivideo/api.video-reactnative-uploader/blob/main/docss/install_swift_dependency.md)

### Code sample

```js
import ApiVideoUploader from '@api.video/react-native-video-uploader';

ApiVideoUploader.uploadWithUploadToken('MY_VIDEO_TOKEN', 'my-video.mp4')
  .then((value: Video) => {
   // Manages success here
  })
  .catch((e: any) => {
    // Manages error here
  });
```

## Example

An example that demonstrates how to use the API is provided in folder [example/](https://github.com/apivideo/api.video-reactnative-uploader/tree/master/example).

## Plugins

api.video-reactnative-uploader is using external native library for upload

| Plugin                 | README                   |
| ---------------------- | ------------------------ |
| swift-video-uploader     | [swift-video-uploader]     |
| android-video-uploader | [android-video-uploader] |

## FAQ

If you have any questions, ask [our community](https://community.api.video).
Or use [Issues].

[//]: # "These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax"
[videouploaderswift]: https://github.com/apivideo/swift-video-uploader
[android-video-uploader]: https://github.com/apivideo/android-video-uploader
[issues]: https://github.com/apivideo/api.video-reactnative-uploader/issues