---
title: "Live stream ended"
slug: "live-stream-ended"
excerpt: "Zapier lets you connect APIs and applications together without coding. The live stream ended Zapier trigger fires when an api.video live stream ends."
---
Live Stream Ended
=================

The live stream-ended endpoint is an instant Zapier trigger. It is powered by api.video's [webhooks](/reference/api/Webhooks#list-all-webhooks). Each api.video account has 10 webhooks available, and they will push the data to Zapier immediately - leading to the "instant trigger" nomenclature.

The live stream started webhook will fire when the `live-stream.broadcast.ended` webhook alert is sent. This means that one of the live streams in your api.video account has ended its broadcast (the `broadcasting` switches from true to false.). It is the opposite alert of the life stream started zap trigger.

When you choose this trigger, you'll authenticate your api.video account with your api key (which you can find on the [dashboard](https://dashboard.api.video/)). When you test the Zap - Zapier uses sample data provided by api.video (no webhook has been created yet)

![](/_assets/Zapier_7.png)

Now you can create an action to occur. Here are a few ideas that might be useful (They are all the opposite of the started ideas):

1. Tell a slack/Discord/chat channel that your live stream has ended (and we hope you'll join next time!).
2. Remove the video player from your webpage - now that the stream is ending.
