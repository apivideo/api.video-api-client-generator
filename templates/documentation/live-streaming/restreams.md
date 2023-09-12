<div class=callout>

**Beta Feature**

Please note that Restreaming is currently a beta feature. The api.video engineering team is continuously working on improving the feature, thus issues may occur.

If you are experiencing an issue, please do not hesitate to contact our support team.

</div>

api.video gives you the ability to broadcast a live stream to multiple platforms simultaneously. You'll be able to stream to Youtube, Facebook, Twitch, and any other platform that supports [RTMP](https://api.video/blog/video-trends/what-is-rtmp-and-why-do-we-use-it-for-live-streaming/) from one source.

## How it works

{% include "_partials/dark-light-image.md" dark: "/_assets/live-streaming/restreams/restreams-dark.png", light: "/_assets/live-streaming/restreams/restreams-light.png" %}

You can add restreams to an existing live stream or you can create a live stream that already includes the restream list.   

The Live stream endpoint has an optional field called `restreams` which is an array of objects. For example:

```json
"restreams": [
      {
          "name": "Youtube",
          "serverUrl": "rtmp://a.rtmp.youtube.com/live2",
          "streamKey": "xxxxx-yyyyy-zzzzz"
      },
      {
          "name": "Twitch",
          "serverUrl": "rtmp://cdg02.contribute.live-video.net/app/live_923901944_8N3NXy5Kk1qC64cjdT2zCoAk6e7b0t",
          "streamKey": "live_0000-11111111111"
      },
   ]
}
```

| Field       | Type     | Details                                                                                           |
| :---------- | :------- | :------------------------------------------------------------------------------------------------ |
| `name`      | `string` | Arbitrary value for the name of the provider streaming provider. For example: `Youtube`, `Twitch` |
| `serverUrl` | `string` | The RTMP url of the streaming server.                                                             |
| `streamKey` | `string` | Stream key provided by the streaming provider.                                                    |

{% capture content %}
Please note that:

- Currently api.video supports up to 5 restreams
- At this time, it's only possible to restream to RTMP
- The names of the providers are arbitrary, you can provide any name you like
- When updating the restreams object, it's important to make sure that you are passing the previous values if you would like to keep them
- Modifying the restreams array while the live stream is already broadcasting will only take only effect after the stream has been restarted
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

## Supported Platforms

api.video currently supports RTMP restreaming destinations. Providers that you can restream to include, and are not limited to:

- YouTube
- Twitch
- Facebook
- LinkedIn
- Instagram (through [YellowDuck](https://yellowduck.tv/))

Providers that use RTMPS, SRT or any other protocol (like Kick or Telegram) are currently not supported.

## Getting Started

In order to get started with restream, you will need to create a live stream. Check out the guide on [how to create a live stream in 5 minutes](https://docs.api.video/docs/create-a-live-stream) with api.video. You can either add the restreams directly with the live stream creation or you can [edit the live stream with the PATCH method](https://docs.api.video/reference/patch_live-streams-livestreamid) later and add the restream.

## Usage

{% capture content %}
**Limitations**

Restreaming in Sandbox mode is limited to 2 minutes. The original live stream is not limited, only the restreams are limited.
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

### Creating a live stream with restreams

It's possible to include the providers you want to restream to in the creation payload of the live stream endpoint. You can do that by making a [POST request to the live stream endpoint ](https://docs.api.video/reference/post_live-streams) or using one of api.video's modules.

```curl
curl --request POST \
     --url https://ws.api.video/live-streams \
     --header 'accept: application/json' \
     --header 'authorization: Basic bW9CRTdQbmMxRWFmSWNUTXpRMkdmd2VJQ3NvUDQ1WVl3NW5IS0Z1d3RHcjo=' \
     --header 'content-type: application/json' \
     --data '{
"name": "TEST-LIVE-STREAM",
"restreams": [
      {
          "name": "Youtube",
          "serverUrl": "rtmp://a.rtmp.youtube.com/live2",
          "streamKey": "xxxxx-yyyyy-zzzzz"
      },
      {
          "name": "Twitch",
          "serverUrl": "rtmp://cdg02.contribute.live-video.net/app/live_923901944_8N3NXy5Kk1qC64cjdT2zCoAk6e7b0t",
          "streamKey": "live_0000-11111111111"
      },
   ]
}'
```

### Updating or editing restreams

Sometimes you will need to change the stream key, stream URL or add a new restream. In this case, you can make a PATCH request to the live stream endpoint, or use one of api.video's modules.

It's important to remember that if you would like to add a restream to the array, you should pull the live stream ID first to get the original payload and then add the new restream to the payload:

#### Get the restreams payload data from live streams

```curl
curl --request GET \
     --url https://ws.api.video/live-streams/<live-stream id> \
     --header 'accept: application/json' \
     --header 'authorization: Basic bW9CRTdQbmMxRWFmSWNUTXpRMkdmd2VJQ3NvUDQ1WVl3NW5IS0Z1d3RHcjo='
```

**Response**

```json
{
    "liveStreamId": "li6N18RfQG0lvsZ5tYBdIdny",
    "createdAt": "2023-06-19T09:11:20+00:00",
    "updatedAt": "2023-06-19T09:11:20+00:00",
    "streamKey": "77b8b72d-25eb-469a-be9d-aa9c8deae361",
    "name": "TEST",
    "public": true,
    "broadcasting": false,
    "assets": {
        "iframe": "<iframe src=\"https://embed.staging.api.video/live/li6N18RfQG0lvsZ5tYBdIdny\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"true\"></iframe>",
        "player": "https://embed.staging.api.video/live/li6N18RfQG0lvsZ5tYBdIdny",
        "hls": "https://live.staging.api.video/li6N18RfQG0lvsZ5tYBdIdny.m3u8",
        "thumbnail": "https://live.staging.api.video/li6N18RfQG0lvsZ5tYBdIdny/thumbnail.jpg"
    },
    "restreams": [
    {
          "name": "Youtube",
          "serverUrl": "rtmp://a.rtmp.youtube.com/live2",
          "streamKey": "xxxxx-yyyyy-zzzzz"
      },
    ]
}
```

#### Update the restreams with the new entry

Take the `restreams` array parameters from the response payload and include them in the new payload in order to maintain the previous parameters.

```curl
curl --request PATCH \
     --url https://ws.api.video/live-streams/<live stream id> \
     --header 'accept: application/json' \
     --header 'authorization: Basic bW9CRTdQbmMxRWFmSWNUTXpRMkdmd2VJQ3NvUDQ1WVl3NW5IS0Z1d3RHcjo=' \
     --header 'content-type: application/json' \
     --data '{
"restreams": [
      {
          "name": "Youtube",
          "serverUrl": "rtmp://a.rtmp.youtube.com/live2",
          "streamKey": "xxxxx-yyyyy-zzzzz"
      },
      {
          "name": "Twitch",
          "serverUrl": "rtmp://cdg02.contribute.live-video.net/app/live_0000-11111111111",
          "streamKey": "live_0000-11111111111"
      },
   ]
}'
```

### Removing all restreams

If you would like to remove all the restreams, you can simply make a request with an empty `restreams` object. For example:

```curl
curl --request PATCH \
     --url https://ws.api.video/live-streams/<live stream id> \
     --header 'accept: application/json' \
     --header 'authorization: Basic bW9CRTdQbmMxRWFmSWNUTXpRMkdmd2VJQ3NvUDQ1WVl3NW5IS0Z1d3RHcjo=' \
     --header 'content-type: application/json' \
     --data '{
"restreams": [
   ]
}'
```