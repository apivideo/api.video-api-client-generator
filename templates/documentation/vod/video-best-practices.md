Video best practices
====================

## How to create & upload a video

The concept of creating and uploading a video is pretty simple:

1.  **Create a video object:** Think about it as a box or shell for the video. Once you've created a video object, it's an empty shell, however, it has an id and assets.
2.  **Upload a video:** Now you can upload your video into that object.

A tutorial for uploading a video with curl can be found [here](https://api.video/blog/tutorials/video-upload-tutorial/)

A more visual representation can be found below:

{% include "_partials/dark-light-image.md" dark: "/_assets/vod/video-best-practices/create-a-video-dark.svg", light: "/_assets/vod/video-best-practices/create-a-video-light.svg", alt: "A diagram that shows the steps of creating a video object, and uploading a video" %}

{% capture content %}
**Important things to know**
* **All qualities encoding:** The video object will include up to 6 responsive video streams from 240p to 4K
* **Video size:** There's no file size limitation but the files will be compressed to fit delivery needs (4k max def with x264 + aac at 60fps max )
* **Highest quality encoding by default:** Mp4 encoded versions are created at the highest quality (max 4K) by default.
* **360° videos support:** Panoramic videos are videos recorded in 360°. You can toggle this after your 360° video upload.
* **Video Access Management:** When creating a video object, it can be either private or public. The feature allows you to change it later by updating the video object.
* **Progressive Upload:** Ability to upload videos in a progressive manner while they are being recorded.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Recommendations

{% capture content %}
For optimal ingestion, the video should use:

- Resolution ≤ 4k
- Video codec: **h264**
    - ≤ 1080p max bitrate: 16Mbps
    - \> 1080p  ≤ 4k max bitrate: 50Mbps
- Standard Dynamic Range: **yuv420p**
- Framerate ≤ **60fps**
- Audio Codec: **aac**
{% endcapture %}
{% include "_partials/callout.html" kind: "success", content: content %}


## Limitations

{% capture content %}
* When using [progressive upload](/vod/progressive-upload):
   * a video uploaded in a single chunk **must be between 0 and 200MiB**
   * a video uploaded in several chunks must use at most **10 000 chunks each between 5MiB and 200MiB except the last chunk which must be between 0 and 200MiB**
* a video **must be uploaded/downloaded/copied in at most 7 days (168 hours)**
* an **uploaded/downloaded/copied video** must be:
    - **at most 10GiB**
    - **of at most 24h**
* **ProRes RAW** video codec is currently not supported.
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

## Progressive video upload & uploading large videos

Some videos you might upload could be very large in size. We made sure that this is addressed and that there's a convenient way to upload large videos while breaking them into chunks. Please be sure to read the guide on how to make [progressive video upload](/vod/progressive-upload).

## Video Access Management

The video object will be created as public by default. If you want to make the video private, you just need to create a private video object or update it later. You can read all about Private Videos [here](/delivery-analytics/video-privacy-access-management.md)

* Searchable parameters: title, description, tags and metadata

```shell
$ curl https://ws.api.video/videos \
    -H 'Authorization: Bearer {access_token} \

-d '{"title" : "My video", "description" : "so many details", "mp4Support" : true }'` 
```

## Create a video from a URL

You can also create a video directly from a video hosted on a third-party server by giving its URI in `source` parameter:

```shell
$ curl https://ws.api.video/videos \
   -H 'Authorization: Bearer {access_token} \

-d '{"source":"http://uri/to/video.mp4", "title":"My video"}'` 
```

In this case, the service will respond `202 Accepted` and ingest the video asynchronously.

## Track users with Dynamic Metadata

Metadata values can be a key:value where the values are predefined, but Dynamic metadata allows you to enter _any_ value for a defined key. To defined a dynamic metadata pair use:

```
"metadata":[{"dynamicKey": "__dynamicKey__"}]
```

The double underscore on both sides of the value allows any variable to be added for a given video session. Added the the url you might have:

```html
<iframe type="text/html" src="https://embed.api.video/vod/vi6QvU9dhYCzW3BpPvPsZUa8?metadata[classUserName]=Doug" width="960" height="320" frameborder="0" scrolling="no"></iframe>
```

This video session will be tagged as watched by Doug - allowing for in-depth analysis on how each viewer interacts with the videos.

## Resources

Check out api.video's blog content and tutorials about:

* [Creating](https://api.video/blog/endpoints/video-create/) and [uploading](https://api.video/blog/endpoints/video-upload/) videos
  
* [Uploading large videos](https://api.video/blog/tutorials/video-upload-tutorial-large-videos/)
  
* [Using tags with videos](https://api.video/blog/tutorials/video-tagging-best-practices/)
  
* [Private videos](https://api.video/blog/product-updates/video-access-management-how-to-create-deliver-and-manage-private-videos-and-what/)