---
title: api.video Player analytics plugin
meta: 
  description: The official api.video Player analytics plugin for api.video. [api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.
---

# api.video Player analytics plugin

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

## Project description

Javascript module to manually call the api.video analytics collector. 

This is useful if you are using a video player for which we do not yet provide a ready-to-use monitoring module.

If you use one of the following video player, you should rather use the associated packaged monitoring module:

| Player   | monitoring module                                                                           |
| -------- | ------------------------------------------------------------------------------------------- |
| video.js | [@api.video/api.video-videojs-analytics](https://github.com/apivideo/api.video-videojs-analytics) |
| hls.js   | [@api.video/api.video-hlsjs-analytics](https://github.com/apivideo/api.video-hlsjs-analytics)     |


This module is compatible with React Native.

## Getting started

### Installation 

#### Method #1: requirejs

If you use requirejs you can add the module as a dependency to your project with 

```sh
$ npm install --save @api.video/player-analytics
```

You can then use the module in your script: 

```javascript
var { PlayerAnalytics } = require('@api.video/player-analytics');


const playerAnalytics = new PlayerAnalytics({
    ...options // see below for available options
});
```

#### Method #2: typescript

If you use Typescript you can add the SDK as a dependency to your project with 

```sh
$ npm install --save @api.video/player-analytics
```

You can then use the SDK in your script: 

```typescript
import { PlayerAnalytics } from '@api.video/player-analytics'

const playerAnalytics = new PlayerAnalytics({
    ...options // see below for available options
});
```

#### Method #3: imple include in a javascript project

Include the SDK in your HTML file like so:

```html
<head>
    ...
    <script src="https://unpkg.com/@api.video/player-analytics" defer></script>
</head>
```

Then, once the `window.onload` event has been trigered, instanciate the module with `new PlayerAnalytics()`:
```html
<script type="text/javascript">
    var playerAnalytics = new PlayerAnalytics("#target", { 
        ...options // see below for available options
    });
</script>
```

## Documentation

### Instantiation options

The analytics module constructor takes a `PlayerAnalyticsOptions` parameter that contains the following options:

 
|         Option name | Mandatory | Type                                  | Description                                                                                                  |
| ------------------: | --------- | ------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
|            mediaUrl | **yes**   | string                                | url of the media (eg. `https://cdn.api.video/vod/vi5oDagRVJBSKHxSiPux5rYD/hls/manifest.m3u8`)                |
|        userMetadata | no        | ```{ [name: string]: string }[]```    | object containing [metadata](https://api.video/blog/tutorials/dynamic-metadata/) (see **Full example** below) |
|            sequence | no        | ```{start: number; end?: number;} ``` | if only a sequence of the video is going to be played                                                        |
| onSessionIdReceived | no        | ```(sessionId: string) => void```     | callback to be called once the session id is reveiced                                                        |
 

Once the module is instanciated, the following methods have to be called to monitor the playback events.

### Module methods

| Method | Description |
|----------|----------|
| `play(): Promise<void>` | method to call when the video starts playing for the first time (in the case of a resume after paused, use `resume()`) |
| `resume(): Promise<void>` | method to call when the video playback is resumed after a pause |
| `ready(): Promise<void>` | method to call once the player is ready to play the media |
| `end(): Promise<void>` | method to call when the video is ended |
| `seek(from: number, to: number): Promise<void>` | method to call when a seek event occurs, the `from` and `to` parameters are mandatory and should contain the seek start & end times in seconds |
| `pause(): Promise<void>` | method to call when the video is paused |
| `destroy(): Promise<void>` | method to call when the video player is disposed (eg. when the use closes the navigation tab) |
| `updateTime(time: number): Promise<void>` | method to call each time the playback time changes (it should be called often, the accuracy of the collected data depends on it) |