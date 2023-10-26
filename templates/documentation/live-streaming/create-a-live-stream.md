---
title: Get started with live stream in 5 minutes
meta: 
    description: This guide will cover how to set up your live stream with api.video and then use OBS to broadcast.
---

# Get started with live stream in 5 minutes

Creating a live stream is simple with api.video. After you create it, you have a variety of options for connecting live video and beginning broadcasting. This guide will cover how to set up your live stream with api.video and then use OBS to broadcast. 

## Associated API reference documentation

- [Create live stream](/reference/api/Live-Streams#create-live-stream)

## Choose an api.video client

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)

## Installation

To install your selected client, do the following: 

{% capture samples %}
```go
go get github.com/apivideo/api.video-go-client
```
```php
composer require api-video/php-api-client
```
```javascript
npm install @api.video/nodejs-client --save

...or with yarn: 
  
yarn add @api.video/nodejs-client
```
```python
pip install api.video
```
```csharp
Using Nuget
  
Install-Package ApiVideo
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Create an account

Before you can start streaming, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

## Create a live stream container

The first part of setting up your live stream to broadcast is to create a live stream container. Once you create the container, you can use it for live streaming. Here is the code to create the container: 

{% capture samples %}
```go
package main

import (
    "context"
    "fmt"
    "os"
    apivideosdk "github.com/apivideo/api.video-go-client"
)

func main() {
    client := apivideosdk.ClientBuilder("YOUR_API_TOKEN").Build()
    // if you rather like to use the sandbox environment:
    // client := apivideosdk.SandboxClientBuilder("YOU_SANDBOX_API_TOKEN").Build()
        
    liveStreamCreationPayload := *apivideosdk.NewLiveStreamCreationPayload("My Live Stream Video") // LiveStreamCreationPayload | 

    
    res, err := client.LiveStreams.Create(liveStreamCreationPayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.Create``: %v\n", err)
    }
    // response from `Create`: LiveStream
    fmt.Fprintf(os.Stdout, "Response from `LiveStreams.Create`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\LiveStreamsApi;

$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$payload =(new \ApiVideo\Client\Model\LiveStreamCreationPayload())->setName("Live Stream")->setPublic(true);
$livestream = $client->liveStreams()->create($payload);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const liveStreamCreationPayload = {
			name: "My Live Stream Video", // Add a name for your live stream here.
			_public: true, // Whether your video can be viewed by everyone, or requires authentication to see it. A setting of false will require a unique token for each view.
			playerId: "pl4f4ferf5erfr5zed4fsdd", // The unique identifier for the player.
		}; 

        // LiveStream
        const result = await client.liveStreams.create(liveStreamCreationPayload);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Create a live stream. 
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

live_stream_creation_payload = {
    "name": "My live stream"
}

## Create the live stream
response = live_stream_api.create(live_stream_creation_payload)
print(response)
```
```curl
curl --request POST \
     --url https://ws.api.video/live-streams \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ' \
     --header 'Content-Type: application/json' \
     --data '
{
     "name": "My Live Stream",
     "public": true,
     "playerId": "pt240hxAaDBLCYxUIPh0Fb2"
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Connect live video to your live stream container

You'll want to grab your live stream's streamKey from the response. It's required for use with OBS. To get started: 

1. Install OBS. You can grab the software here: [OBS Studio](https://obsproject.com/)

2. Establish a source. In the sources section, choose a video feed to share. You could choose to share your screen or to share your camera (on a Mac: Video Capture Device, and then in the "Device" dropdown, select the camera). You should now see the video source in the main OBS window. 

3. Connect OBS to api.video. Under Settings, choose **Stream**. You'll see a choice for service - select **api.video**.

4. The server should be **Default**, and the streamKey is the value you received in the JSON response when you created the stream container. 

5. Click **OK** to accept the changes.

6. Press **Start Streaming**.

7. Share the embed, iFrame, so that others can watch your stream!

If you can't find **api.video** in the list of services, you can choose **custom** and **rtmp://broadcast.api.video/s** for the server.

## Live stream immediately from your dashboard

If you don't want to set up your live stream programmatically, api.video allows you to demo live streaming from the dashboard with the click of a button. Do the following: 

1. Log in to your dashboard 

2. Navigate to Livestreams

3. Click **Create a live stream**

   ![api.video dashboard](/_assets/create-a-live-stream.png)

4. Enter the title and choose the remaining configuration items. Click on **Create** to create the live stream object.

   ![Create a live stream dialog](/_assets/image.png)

5. Once the live stream object is created you can either click on the **Start livestream** button and test it out from the dashboard.

The live stream object contains all the information you'll need if you choose to broadcast using something else like OBS. You can pause the live stream at any time by clicking pause on the video. If you don't see your live stream start right away, give it a few minutes to get ready.

## Conclusion

There are many ways to set up a live stream. OBS is one of the most popular ways to get started.