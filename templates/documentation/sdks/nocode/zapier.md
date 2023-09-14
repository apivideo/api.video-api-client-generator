---
title: "Zapier"
slug: "zapier"
excerpt: "Connect api.video with your favorite apps thanks to Zapier"
metadata:
  title: "Zapier <> api.video • Plugins • api.video Documentation"
  description: "Thanks to Zapier, connect api.video with your favorite apps to trigger events when a video or a live stream is uploaded or edited"
---

# Zapier

[Zapier](https://zapier.com) is a no-code solution that allows fast integration with thousands of applications. Each automation created at Zapier is called a "Zap" and has a trigger (to kick off the automation) and an action (the thing that is done after the trigger).

{% capture content %}
For example, a trigger might be "When a new encoding has been created at api.video" and the action would be to "share the playback link on Twitter." So now, whenever a new encoding is created, the zap will fire and automatically tweet the link to your followers.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

The api.video integration features both triggers and actions, which we will outline here:

## Triggers

A Zapier Trigger is an event that kicks off an automation process. There are 5 Triggers in the api.video integration:
![](/_assets/Zapier_1.png)

### Non instant:

- New Video Created: A new videoId has been created.
- New Live Stream Created: A new LiveStreamId has been created.

### Instant

- Live stream started: The live stream _broadcast_ has started.
- Live stream ended: The live stream _broadcast_ has ended.
- Video encoding completed: Triggers when a specified encoding (based on size of the video) has been encoded and is ready to play.
- All live events: Three triggers in one endpoint: broadcast started, stopped and when the specified recording is ready for playback.

## Actions

- Create Video: Will create a video at api.video when triggered.

The following pages will describe each of the Zapier triggers and actions in greater detail.

