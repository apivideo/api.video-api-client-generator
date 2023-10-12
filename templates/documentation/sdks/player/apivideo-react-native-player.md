---
title: "api.video React Native Player"
slug: "apivideo-react-native-player"
hidden: false
metadata: 
  description: "The official api.video React Native Player for api.video. [api.video](https://api./) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app."
---

# api.video React Native Player

[api.video](https://api.video/) is the video infrastructure for product builders. Lightning fast video APIs for integrating, scaling, and managing on-demand & low latency live streaming features in your app.

## Project description

React Native video player to play vod and lives from api.video 

## Getting started

### Installation

```sh
npm install @api.video/react-native-player
```
or
```sh
yarn add @api.video/react-native-player
```
_Note: if you are on iOS, you will need this extra step:_
Install the native dependencies with Cocoapods
```sh
cd ios && pod install
```

### Limitations

For the moment, this player component is based on the api.video's javascript player (displayed in a [react-native-webview](https://github.com/react-native-webview/react-native-webview)), and therefore it suffers from the same limitation as every players displayed in browsers. 

We plan to base the player component on natives video players in a further release to avoid this kind of limitation. Stay tuned!

### Code sample

```jsx
import React from 'react';
import ApiVideoPlayer from '@api.video/react-native-player';

const App = () => 
    <ApiVideoPlayer videoId="vi2G6Qr8ZVE67dWLNymk7qbc" />
  
export default App;
```

## Documentation

### Props & Methods

```ts
// props:
type PlayerProps = {
    // the id of the video (required)
    videoId: string;
    // if the video is private, the private token of the video
    privateToken?: string;
    // if the video is private, the session token for the video (avh)
    sessionToken?: string;
    // whether the video is vod or live (default is 'vod')
    type?: 'vod' | 'live';
    // the controls are hidden (default false)
    hideControls?: boolean;
    // the video title is hidden (default false)
    hideTitle?: boolean;
    // start playing the video as soon as it is loaded (default false)
    autoplay?: boolean;
    // once the video is finished it automatically starts again (default false)
    loop?: boolean;
    // the video is muted (default false)
    muted?: boolean;
    // style to apply to the player component
    style?: StyleProp<ViewStyle>;

    onControlsDisabled?: () => void;
    onControlsEnabled?: () => void;
    onEnded?: () => void;
    onError?: () => void;
    onFirstPlay?: () => void;
    onFullScreenChange?: () => void;
    onPause?: () => void;
    onPlay?: () => void;
    onPlayerResize?: () => void;
    onQualityChange?: (resolution: { height: number, width: number }) => void;
    onRateChange?: () => void;
    onReady?: () => void;
    onResize?: () => void;
    onSeeking?: () => void;
    onTimeUpdate?: ( currentTime: number ) => void;	
    onUserActive?: () => void;
    onUserInactive?: () => void;
    onVolumeChange?: ( volume: number ) => void;
}

// methods:
play(): void;
pause(): void;
requestFullscreen(): void;
seek(time: number): void;
setCurrentTime(time: number): void;
setPlaybackRate(rate: number): void;
setVolume(volume: number): void;
```

### Using methods

{% raw %}

```jsx
import React, { Component } from 'react';
import ApiVideoPlayer from '@api.video/react-native-player';
import { Button, View } from 'react-native';

export default class App extends Component {

  render() {
    return (
      <View style={{ flex: 1 }}>
        <ApiVideoPlayer
          ref={(r) => (this.player = r)}
          muted={true}
          videoId="vi2G6Qr8ZVE67dWLNymk7qbc" />

        <Button onPress={() => this.player.play()} title="Play" />
        <Button onPress={() => this.player.pause()} title="Pause" />

      </View>
    )
  }
}
```

{% endraw %}

## FAQ
If you have any questions, ask us here:  https://community.api.video .
Or use [Issues].


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Issues]: <https://github.com/apivideo/api.video-reactnative-player/issues>