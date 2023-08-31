---
title: "Private videos with custom players & session retention"
metadata: 
  description: "Private videos are a way to giving access to videos only to certain users and making sure that privacy concerns are covered. This section will provide a detailed explanation on how you can deliver private videos with custom players, and how session retention works."
---

Private Videos With Custom Players Session Retention
====================================================

There are quite a few cases where you would want to include other assets in the session where you play your video. In example:
* Displaying a clickable thumbnail that redirects to your video
* Selection of multiple players with a fallback (hls to mp4 fallback)
* Using a custom built player

In these cases, you will have to make multiple requests to api.video assets. As it's a secured session we are using a Session Token in order to retrain the session and make sure it's continuous.

### What is a [Session Token](/delivery-analytics/private-video-session-tokens)

In short a [Session Token](/delivery-analytics/private-video-session-tokens) is our way of retaining the session for every request you make to each asset. You can find the detailed article about session tokens [here](/delivery-analytics/private-video-session-tokens).

{% capture content %}
**Example for incorrect usage of private videos**

Let's take an example where you want to create a clickable thumbnail which will lead to the video. If we just write the following HTML **it will not work**, you will get a **404 error**:
```html
<html>
  <head>
  </head>
  <body>
    <a href=\"https://vod.api.video/vod/{video id}/token/{private token}/mp4/source.mp4\">
      <img src=\"https://vod.api.video/vod/{video id}/token/{private token}/thumbnail.jpg\" height=\"30%\" width=\"30%\">
    </a>
  </body>
</html>
```
{% endcapture %}
{% include "_partials/callout.html" kind: "error", content: content %}

### How to play private video in a custom built player?

In order to play your private video with a clickable thumbnail or in a custom made player, you will first have to call the session token. Assuming that you are passing an `.mp4` video to the player, the flow will be as follows described in the [Session Flow](/delivery-analytics/private-video-session-tokens#session-flow) document.

### Continuous requests while playing private videos on custom player

In some cases you would need to make further requests to get different assets for the video, for example other qualities or different assets (hls or mp4), while retaining the same session. We've made sure that this task is easy as possible for you by using the session token.

### Examples

Let look at some cases where we can build an application that will use the Session Token when delivering a private video

#### Node.js example: Delivering the video with video.js and adding a thumbnail on the top

{% capture content %}
You can find the source for the example below on [GitHub](https://github.com/apivideo/parivate_video_videojs_node_example).
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

Check out an example of consuming private videos and thumbnail with video.js (Node) below.

* We first build a wrapper that will make requests to the api.video API, while passing it a hardcoded API key in order to reuse that function in the future.

* With the use of the wrapper we then make a request to the /videos endpoint to get the video by id. That function will return the mp4 url of the video from the response.

* We need that url in order to extract the private token from it, the private token is needed in order make the request to the /session endpoint and get the session token.

* With a regex function, we will extract the private token from the url in the response

* Now that we have the private token, we make a request to the /session endpoint
```curl
GET https://vod.api.video/vod/{videoId}/token/{private token}/session
```

* We will get a response with the session token in JSON format. 
```json
{"session_token": "111-222-333"}
```

* We will use the session token and include it as a query string in the GET request to the next asset that we request from the API, in our case it's the thumbnail
```
GET https://vod.api.video/vod/viXXX/token/AAA-BBB-CCC/thumbnail.jpg?avh=111-222-333
```

* Finally, our server will generate an HTML response to the client.

You can find the complete code below:
```javascript
const http = require('http');
const url = require('url');
const base64 = require('base-64');

const vodUrl = 'https://vod.api.video/vod/';
const wsUrl = 'https://ws.api.video/videos/';
//const sample video id = 'xxxxxxxxxxxxxxx';
const apiKey = 'Your API Key';

/************************************************************************
 *  This is an example of how you can handle private videos with the
 *  use of session tokens. In the example we will create an HTTP server
 *  that will handle incoming GET requests, with the video id in the 
 *  query string. Then it will make a request to get the video id from 
 *  the https://ws.api.video/videos/ endpoint, extract the private token
 *  , get the session token from the /session endpoint and finally consume
 *  the video and thumbnail.
 * ***********************************************************************/

// 1 - First create a server that will accept GET requests, with id as query parameter

const server = http.createServer( async (req, res) =>  {
    const videoIdFromReq = url.parse(req.url, true).query.id;
    const videoDetails = await getVideoDetailsById(videoIdFromReq);
    const privateToken = await videoDetails.extractPrivateTokenAssets();
    const sessionToken = await videoDetails.getSessionToken(privateToken);
    const html = await generateHTML(videoDetails.data, sessionToken);
    res.write(html);
});

server.listen(3000);
console.log('Node.js web server at port 3000 is running..')

// 2 - Create a handler function for the requests to api.video endpoints with the basic auth
const apiVideoReq = async (url) => {
    const headers = new Headers();
    headers.append('Authorization', 'Basic' + base64.encode(apiKey + ":"));
    const response = await fetch(url, {headers});
    return {
        httpRawResponse: response,
        processHTTPresponse: async () => {
            if(response.status === 200) {
                const data = await response.json();
                return data;
            } else {
                throw new Error(`unbale to prase JSON, got response status: ${response.status}`)
            }
        }
    }
}

// 3 - Create a function to get the video by id reusing the above functions
const getVideoDetailsById = async (videoId) => {
    const completeUrlWithVideoId = `${wsUrl}${videoId}`
    const apiResponse = await apiVideoReq(completeUrlWithVideoId);
    const videoDetails = await apiResponse.processHTTPresponse();
    return {
        data: videoDetails,
        // 4 - extract the private token from the assets response
        extractPrivateTokenAssets: async () => {
            const regexBtwnTokenMp4 = /(?<=token\/)(.*?)(?=\/mp4)/;
            const regexMatchResults = videoDetails.assets.mp4.match(regexBtwnTokenMp4);
            if(regexMatchResults.length > 0) {
                return regexMatchResults[0]
            } else {
                throw new Error(`Was not able to find the private token the asset url: ${assetUrl}`)
            }
        },
        // 5 - get the session token while passing the private token
        getSessionToken: async (privateToken) => {
            const sessionUrlWithVideoIdAndToken = `${vodUrl}${videoId}/token/${privateToken}/session`
            const res = await apiVideoReq(sessionUrlWithVideoIdAndToken);
            const data = await res.processHTTPresponse(res);
            return data.session_token;   
        }
    }
}

// 6 - generate the HTML with video.js, the mp4 url and the thumbnail, while passing in the session token
const generateHTML = async (data, sessionToken) => {

    if(data.assets && data.assets.mp4 && data.assets.thumbnail) {
        const mp4Url = data.assets.mp4;
        const thumbnailUrl = data.assets.thumbnail;
        return `<html>
        <head>
        <div style="max-width: 150px" >
        <img
        style="max-width:100%;" 
        src="${thumbnailUrl}?avh=${sessionToken}">
        </div>
        </head>
        <body><video
        controls
        preload="auto"
        width="640"
        height="264"
        data-setup="{}"><source src="${mp4Url}?avh=${sessionToken}" type="video/mp4" /></body></html>`
    }
}
```
