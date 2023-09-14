---
title: "In-stream ads"
---

In-stream ads
=============

In-stream ads are a very common and powerful tool for monetizing your project, by including inline ads to your video and live streams. api.video give you the ability to leverage a 3rd party ad server that supports [IAB standards](https://iabtechlab.com/standards/), in order to include linear, non-linear, VAST, VPAID and VMAP ads.

In-stream ads are a very convenient way of monetization of videos and streams. [You can read more about in-stream ads in more detail here](https://www.adbutler.com/blog/article/Guide-To-Video-Ad-Serving-and-VAST).

## Getting Started

Including ads in your videos and stream is easy. There are multiple ways you can do that:

- [api.video Player features](/delivery-analytics/video-playback-features.md)
- [api.video Player SDK](/sdks/player/apivideo-player-sdk)
- [React Player SDK](/sdks/player/apivideo-react-player)

### What are VAST, VPAID, and VMAP url tags?

In order to specify how to serve your ads, you will be able to include an `adTagUrl` with a video object. This will allow you to specify the ad server URL and include the tags to give the server the instruction on how to play the ad (non-linear, linear and etc.). You can find more about which tags are available in the [Google IMA SDK documentation](https://developers.google.com/interactive-media-ads/docs/sdks/html5/client-side/tags).

## Implementation

### HTML URL fragments

You can pass the VAST or VPAID tags with the player URL. With the embedded player link, just pass in the additional URL fragment:

```
#adTagUrl:[urlEncodedTagUrl]
```

For example:  

```
https://embed.api.video/vod/<videoID>#adTagUrl:https%3A%2F%2Fpubads.g.doubleclick.ne[…]tart%3D1%26env%3Dvp%26impl%3Ds%26correlator%3D
```

### React Native Player SDK

Using the React Native Player SDK, you will be able to pass the `ads` component, with the `adTagUrl` that will include the URL tags string. For example:

{% raw %}
```javascript
<ApiVideoPlayer
  ads={{
    adTagUrl:
              "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
  }}
  ...
```
{% endraw %}

#### React Player Installation

Install the React Player SDK dependencies in order to import them to your project:

```shell
$ npm install --save @api.video/react-player
```

#### React player ads implementation

 Once you've imported the library into your project, you can then implement the library and pass in the the video or stream id with the ad component: 

{% raw %}
```
import ApiVideoPlayer from '@api.video/react-player'

// ...

<ApiVideoPlayer 
		video={{id: "<videoId>"}} 
    ads={{
    	adTagUrl:
              "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
  	}} 
  />
```
{% endraw %}

### Javascript Player SDK

When using the [Javascript player SDK](/sdks/player/apivideo-player-sdk.md), you will be able to pass the `ads` object. The object intakes a key-value pair `adTagUrl: <string>`. In the `adTagUrl` you can pass URL tags.

For example:

```javascript
ads: {
          adTagUrl: "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
        }
```

You can consume the Player SDK through `commonjs`, `ES6` or directly through CDN. 

#### Player SDK Installation

Add the player sdk dependency to your project. You can also consume the sdk directly from the CDN, scroll down to `CDN + HTML` for further instruction 

```shell
$ npm install --save @api.video/player-sdk
```

#### commonjs

You can then use the SDK in your script:

```javascript
var { PlayerSdk } = require('@api.video/player-sdk');

const sdk = new PlayerSdk("#target", {  
    id: "\<VIDEO_ID>",
  	ads: {
          adTagUrl: "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
        }
    // ... other optional options  
});
```

#### ES6 or Typescript

You can then use the SDK in your script:

```typescript
import { PlayerSdk } from '@api.video/player-sdk'

const sdk = new PlayerSdk("#target", {  
    id: "\<VIDEO_ID>",
  	ads: {
          adTagUrl: "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
        }
    // ... other optional options  
});
```

#### HTML only

Include the SDK in your HTML file like so:

```html
<head>
    ...
    <script src="https://unpkg.com/@api.video/player-sdk" defer></script>
</head>
```

Then, once the `window.onload` event has been triggered, create your player using `new PlayerSdk():`

```html
<script type="text/javascript">
    window.player = new PlayerSdk("#target", { 
        id: "<VIDEO_ID>",
   			ads: {
          adTagUrl: "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
        }
        // ... other optional options 
    });
</script>
```
