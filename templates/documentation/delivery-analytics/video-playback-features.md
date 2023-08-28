---
title: "HTML5 Player URL Fragments"
slug: "video-playback-features"
hidden: false
metadata: 
  title: "HTML5 Player URL Fragments • api.video Documentation"
  description: "URL fragments let you complete the api.video embed source URL with query parameters introduced by a hash (#). The HTML5 Player URL Fragments page shows you how to use them with your project."
---

HTML5 Player URL Fragments
==========================

## Introduction

Some features of the api.video player may be activated with `URL fragments`.  
In other words, it means that the Api Video embed (iFrame) source URL may be completed by query parameters introduced by a hash (#).  

Here is the example of a Api Video embed code:

```html
<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp" class="av_player" width="1280" height="720" frameborder="0" scrolling="no" allowfullscreen></iframe>
```

The source URL is `https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp`.  
Any fragment must be appended to the end of this URL after a hash `#`.

Example : `https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay`

Multiple fragments may be concatenated with a semi-colon `;`.  For example, this video will autoplay and loop.  
Example : `https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay;loop`

### 1. Autoplay

To launch video as soon as the player can, use `#autoplay`.  
<https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay>

<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay" width="100%" height="300" frameborder="0" scrolling="no" allowfullscreen></iframe>

### 2. Defined playback times

To start a video from `x`, use: `#t=x`.  
To start a video from `x` and pause it at `y`, use: `#t=x,y`.  
To start from the beginning and pause at `y`, use: `#t=,y`.  
Time may be expressed in the following formats:

- `ss` (eg. `120` for 2 minutes)
- `mm:ss` (ex. `2:30` for two minutes and a half)
- `hh:mm:ss` (ex. `1:30:00` for one hour and a half)

For example, this video will play from time =10 -> 15.  
<https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#t=10,15>

<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#t=10,15" class="av_player" width="100%" height="300" frameborder="0" scrolling="no" allowfullscreen></iframe>

### 3. Allow API

To allow player to listen to API calls, use `#api`

### 4. Hide video title

To hide the Player's title that is displayed on the bottom left corner of the poster, use `#hide-title`.

<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#hide-title" class="av_player" width="100%" height="300" frameborder="0" scrolling="no" allowfullscreen></iframe>

### 5. Hide controls

To hide the Player's control bar, use `#hide-controls`.

> Caution as you should integrate your own controls if you prevent users from accessing Api Video Player native ones.

<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#hide-controls" class="av_player" width="100%" height="300" frameborder="0" scrolling="no" allowfullscreen></iframe>

### 6. Loop

Video will play to end and automatically restart. This example will autoplay and loop, imitating an animated GIF:  
`https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay;loop`

<iframe src="https://embed.api.video/vod/vi54sj9dAakOHJXKrUycCQZp#autoplay;loop" class="av_player" width="100%" height="300" frameborder="0" scrolling="no" allowfullscreen></iframe>

### 7. Show captions

If your video has captions, you can make them appear by default by adding the #show-subtitles url parameter to the player url:

![](/_assets/show-captions.png)

### 8. Download

For mp4 links, adding the dl=1 parameter will force the video to download, instead of playing in the browser.

### 9. In-stream ads

You can pass the VAST, VMAP or VPAID tags with the player URL. With the embedded player link, just pass in the additional URL fragment:  

`#adTagUrl:[urlEncodedTagUrl]`

For example:  

```
https://embed.api.video/vod/<videoID>#adTagUrl:https%3A%2F%2Fpubads.g.doubleclick.ne[…]tart%3D1%26env%3Dvp%26impl%3Ds%26correlator%3D`
```

For more details, navigate to the dedicated documentation page for [in-stream ads](/delivery-analytics/ads).

