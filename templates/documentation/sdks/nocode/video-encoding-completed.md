---
title: Video encoding completed
meta:
    description: Zapier lets you connect APIs and applications together without coding. The video encoding completed webhook will fire when the video.encoding.quality.completed webhook alert is sent.
---

# Video Encoding Completed

The video encoding completed endpoint is an instant Zapier trigger. It is powered by api.video's [webhooks](/reference/api/Webhooks#list-all-webhooks). Each api.video account has 10 webhooks available, and they will push the data to Zapier immediately - leading to the "instant trigger" nomenclature.

The video encoding completed webhook will fire when the `video.encoding.quality.completed` webhook alert is sent. This is sent for each quality of the video created. Currently api.video encodes videos at to to the maximum size of the uploaded video, and all smaller versions of the video (240p, 360p, 480p, 720p, 1080p and 2160p).

For example, a video uploaded at 480p will have 240p, 360p, and 480p webhook alerts, whereas a 4K video will be encoded in all 6 different variations.

When you choose this trigger, you'll authenticate your api.video account with your api key (which you can find on the [dashboard](https://my.api.video)). The next request is which encoding size you'd like the Zap to trigger on:

![Setting up a Video Encoding Completed trigger using the api.video Zapier plugin](/_assets/Zapier_3.png)

In the screenshot above, the zap will fire whenever a 1080p video is encoded at api.video. (This does mean that if a 720p video is uploaded, the Zap will not fire). So choose the size of the video based on what size you'd prefer the Zap to fire on - based on the types of videos uploaded by your users.

When this trigger fires, you know that your video is ready for playback at the given resolution

Now you're ready to add an action!

1. Send a text message with a link to the video.
2. Post the video on your site.
3. Embed the video in a Tweet.

Pick one of the thousands of Zapier integrations and instantly share your video with them!
