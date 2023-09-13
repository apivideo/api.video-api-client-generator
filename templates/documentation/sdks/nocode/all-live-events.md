---
title: "All live events"
slug: "all-live-events"
excerpt: "Zapier lets you connect APIs and applications together without coding. The all live events Zapier trigger is a combination of 3 alerts - live stream created, live stream ended, and video source recorded."
---

All Live Events
===============

The All live events endpoint is an instant Zapier trigger. It is powered by api.video's [webhooks](/reference/api/Webhooks#list-all-webhooks). Each api.video account has 10 webhooks available, and they will push the data to Zapier immediately - leading to the "instant trigger" nomenclature.

The "all live events" is a combination of 3 webhook alerts. Two of them have been described in other Zapier triggers:

* [Live stream created](/sdks/nocode/live-stream-started.md)
* [Live stream ended](/sdks/nocode/live-stream-ended.md)
* video.source.recorded

"video.source.recorded" is a webhook endpoint **very similar** to [video encoded complete](/sdks/nocode/video-encoding-completed).  Video encoding completed tells you when a recorded video is ready for playback at a given resolution. The video.source.recorded alert will only tell you when a recorded live stream video has been successfully encoded. Your [livestream](/reference/api/Live-Streams#create-live-stream) will be recorded for watching after the live stream has ended. This alert will tell you when the selected video quality has been encoded and is ready for playback.

This Zapier trigger is great for building around the entire lifecycle of a livestream in a single Zapier integration. On Professional and higher Zapier plans, there is a "path" argument that allows you to perform different actions depending on the inputs. Using a path structure and the "All live events" trigger, you could:

1. Announce when the stream is live.
2. Announce that the stream is over, but the video will be ready soon.
3. Send a 3rd announcement when the recording is ready for watching.
