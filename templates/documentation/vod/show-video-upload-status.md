---
title: "Video status & details"
---
Video status & details
======================

api.video gives you ways of checking the video upload status and video details.

To check the video upload status or details of the encoding, you can either use webhooks or API polling by making a request to [/videos/{videoId}/status](/reference/api/Videos#retrieve-video-status-and-details) endpoint.

## Listening to Webhooks for Video Status & Encoding

By subscribing to `video.encoding.quality.completed` webhook, each time the video changes its upload status, the URL you've set, will receive a POST request with the following payload.

```json
{ 
  "type": "video.encoding.quality.completed",
  "emittedAt": "2021-01-29T16:46:25.217+01:00", 
  "videoId": "viXXXXXXXX", 
  "encoding": "hls", 
  "quality": "720p"
}
```

You can subscribe to webhooks by making a request to the [/webhooks](/reference/api/Webhooks#create-webhook) endpoint.

### Webhook payload details

|Property   |Type      |Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|-----------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|**emittedAt**|**datetime**|timestamp when the request was emitted from api.video                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
|**videoId**  |**string**  |The ID of the video that was encoded.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
|**encoding** |**string**  |The asset that the encoding was finished for. There are two types of assets that you will receive in the encoding: `HLS`, `MP4`                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|**quality**  |**string**  |The quality of the video was transcoded to the current encoding.<br /><br />The MP4 asset will only be transcoded once, hence you will receive a webhook only once for the MP4 asset with the final source quality. While the HLS asset webhook will be emitted multiple times with each quality (from the lowest 240p) to the highest of the source (up to 4k). <br /><br />For example, if you've uploaded a video where the source quality is 720p. You will receive 5 webhooks in total: <br /><br />- **1 webhook** with 720p quality for the MP4 encoding. <br />- **4 webhooks** for 240p, 360p, 480p and 720p for the HLS encoding|


## Retrieving The Video Status & Details

The other option at your disposal would be to poll the API for the video status and details. After creating the video object, and making a request to upload the video, you can start polling the `/status` endpoint. You will get the video upload status, encoding, source video details, and more with the returned payload.

You can make a request to the `/status` endpoint at any time even after the video was uploaded to get details like the video duration, file size, video size and etc.

The response from the `/status` endpoint will be as follows:

```json
{
    "ingest": {
        "status": "uploaded",
        "filesize": 23037467,
        "receivedBytes": []
    },
    "encoding": {
        "playable": true,
        "qualities": [
            {
                "type": "hls",
                "quality": "240p",
                "status": "waiting"
            },
            {
                "type": "hls",
                "quality": "360p",
                "status": "encoding"
            },
            {
                "type": "hls",
                "quality": "480p",
                "status": "encoding"
            },
            {
                "type": "hls",
                "quality": "720p",
                "status": "waiting"
            },
            {
                "type": "mp4",
                "quality": "720p",
                "status": "encoded"
            }
        ],
        "metadata": {
            "width": 1280,
            "height": 720,
            "bitrate": 766,
            "duration": 200,
            "framerate": 24,
            "samplerate": 44100,
            "videoCodec": "h264",
            "audioCodec": "aac",
            "aspectRatio": "16:9"
        }
    }
}
```

### `ingest`

The ingest property will reflect the status of the ingestion with the file size and if you are using progressive upload, then also the received bytes:

- ### `status`
  Reflects the current status of the video upload. The property can have the following values:
  - `uploading`
  - `last_chunck_uploaded`
  - `merging`
  - `uploaded`
- ### `filesize`
  The size of the file you've uploaded in byte
- ### `receivedBytes`

### `encoding`

The encoding property will provide information about each asset and which quality was already encoded and can be delivered.

- ### `playable`
  Will return a boolean that indicates if the video can already be delivered. The video will be playable as soon as the first asset is encoded.
- ### `qualities`
  An array of objects will provide the type of asset encoding, the different qualities encoded, and the status. The encoding will happen async. The following properties will be present in the object:
  - **type** : The type of the encoding, with possible values of `hls` or `mp4`
  - **quality** : The quality of the encoded asset. Possible values: `240p`, `360p`, `480p`, `720p`, `1080p`, `2160p` (4k)
  - **status** : The status of the current encoding. The status will reflect whether the video is enqueued, encoded, or failed. Possible values:
    - `waiting` - enqueued for encoding.
    - `encoding` - the encoding is in progress.
    - `encoded` - the video has been encoded and is ready for delivery.
    - `failed` - the encoding for this particular quality has failed.
- ### `metadata`

Provides data on the source video 

### Usage

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

#### Retrieve your API key

You'll need your API key to get started. You can sign up for one here: [Get your api.video API key!](https://dashboard.api.video/register). Then do the following: 

#### Retrieve video upload status

To retrieve the status of a video you uploaded, use this code sample:

{% capture samples %}
```curl
curl --request GET \
     --url https://ws.api.video/videos/{video ID here}/status \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI3NDM2MjEuMDAxMTYzLCJuYmYiOjE2NDI3NDM2MjEuMDAxMTYzLCJleHAiOjE2NDI3NDcyMjEuMDAxMTYzLCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.c1rrzpPWno4spJvrlFdgvV7yqRk72lfjd9eXLlvbrCM06WCsJmC0ZJQsA8PcrjDvwNpgQiIwbYaIv7wg8_YvM1aUN4EmEF9JgInzVGQcu4Bluu5pb8P00nWaez4aR9AYkeaIwrsHdsR3kA_68Y7gy5uWzMElLREIT1z7TENW-8aOYv5rhxX502kDSbm8qZyL_2gaNi4WfyuF81Sq2siBlB2t2aGOwc7IB86kuyfWqmtzbwUvOgPcmaCKW9vapZO0WViJoT7pnKx5kIdggZNMH9wOELC_A5AujJBjvlNEmHAJ8xREUrgHTo87Oz920cNFOdj16Wx4OokbyNFJVL3mIA'
```
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
        
    videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" // string | The unique identifier for the video you want the status for.

    
    res, err := client.Videos.GetStatus(videoId)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.GetStatus``: %v\n", err)
    }
    // response from `GetStatus`: VideoStatus
    fmt.Fprintf(os.Stdout, "Response from `Videos.GetStatus`: %v\n", res)
}
```
```php
use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideosApi;
use ApiVideo\Client\Model\UploadTokensApi;
$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$videos = $client->videos()->getStatus('video ID here');
print($videos);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // The unique identifier for the video you want the status for.

        // VideoStatus
        const result = await client.videos.getStatus(videoId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Check the status of a video by video ID

import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
video_id = "video ID to get the status of here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

videos_api = VideosApi(client)

## Get the status of your video
response = videos_api.get_status(video_id)
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## API documentation

- [Show video status](/reference/api/Videos##retrieve-video-status-and-details)
- [Webhooks](/reference/api/Webhooks)
