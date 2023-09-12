---
title: "Private Video Session Tokens"
metadata: 
  description: "Private videos are a way to giving access to videos only to certain users and making sure that privacy concerns are covered. This section will provide a detailed explanation on how private video session tokens work."
---

Private Video Session Tokens
============================

In order to preserve the session and give the ability to make requests to multiple endpoints while opening the private videos. The session preservation is done via session token. When you are building your own player or you would like to consume multiple assets in the same session, like the thumbnail, you will have to use the session token to retain the session.

### Session Flow

The session starts with the /session endpoint where we get the session token from which then cached in the browser cache and used to make the requests to multiple assets later.

Once the session token is acquired, it is reused in all other requests to the different assets.

{% include "_partials/dark-light-image.md" dark: "/_assets/delivery-analytics/private-videos/session-token-dark.png", light: "/_assets/delivery-analytics/private-videos/session-token-light.png" %}

{% capture content %}
**Time to Live**

The session token TTL is 24 hours. Once the session token has expired, you will have to get a new private token and create a new session.
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

### Using the session token (getting thumbnail example)
  
For this purpose, we've introduced an endpoint which will provide you with a session token:

#### Request

```curl
GET https://vod.api.video/vod/{video_id}/token/{private_token}/session
```

#### Response

```json
{"session_token": "111-222-333"}
```

#### Avh parameter

The session token is then added to the query string of the GET request to the thumbnail asset as `avh` parameter. For example:

```curl
GET https://vod.api.video/vod/viXXX/token/AAA-BBB-CCC/thumbnail.jpg?avh=111-222-333
```
