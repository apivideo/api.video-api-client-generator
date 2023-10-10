---
title: "Live stream started"
slug: "live-stream-started"
excerpt: "Zapier lets you connect APIs and applications together without coding. The live stream started Zapier trigger fires when a new live stream is started at api.video."
---

Live Stream Started
===================

The live stream started endpoint is an instant Zapier trigger. It is powered by api.video's [webhooks](/reference/api/Webhooks#list-all-webhooks). Each api.video account has 10 webhooks available, and they will push the data to Zapier immediately - leading to the "instant trigger" nomenclature.

The live stream started webhook will fire when the `live-stream.broadcast.started` webhook alert is sent. This means that one of the live streams in your api.video account has begun broadcasting (the `broadcasting` switches from false to true.)

When you choose this trigger, you'll authenticate your api.video account with your api key (which you can find on the [dashboard](https://dashboard.api.video/)). When you test the Zap - Zapier uses sample data provided by api.video (no webhook has been created yet)


![Setting up a Live Stream Started trigger using the api.video Zapier plugin](/_assets/Zapier_6.png)

Now you can create an action to occur. Here are a few ideas that might be useful:

1. Tell a slack/Discord/chat channel that your live stream has started.
2. Send an email that the stream has started.
3. Add a link to the video player on your webpage - so that your customers can watch the live stream.
