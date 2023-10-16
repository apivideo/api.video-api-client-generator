---
title: "Demo - Start a live stream from your browser"
meta:
  description: This demo enables you to use your webcam, your browser, and NodeJS to stream live video from a webpage to your users.
---

# Live streaming from your browser

api.video offers a simple demo that enables you to use your webcam, your browser, and NodeJS to stream live video from a webpage directly to your users. You can try out the demo on this page: [livestream.a.video](https://livestream.a.video/)

{% capture content %}
You may need to grant your browser permission to use your webcam and microphone.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

In the demo, you can adjust the live stream's resolution, frame rate, and audio bitrate. The speed of transcoding depends on the adjustments you make.

The default RTMP destination URL used in this demo belongs to an api.video test account. To test streaming with your own account and your own RTMP destination, simply [sign up to api.video](https://dashboard.api.video/register) and create a live stream on the Dashboard. Check out the [Live stream quickstart](https://docs.api.video/live-streaming/create-a-live-stream#live-stream-immediately-from-your-dashboard) for more details about creating live streams.

## Features

- Live streaming webcam via from your browser
- Adjusting stream resolution, frame rate, and audio bitrate
- Setting up custom RTMP destination

## GitHub repo

Check out the demo's repository GitHub [here](https://github.com/apivideo/browserLiveStream).

To clone the repo, run this command in your git command line:

```
$ git clone https://github.com/apivideo/browserLiveStream
```

## Usage

Once you have cloned the repository, follow the [installation steps](https://github.com/apivideo/browserLiveStream#installation) on GitHub to set the demo up.