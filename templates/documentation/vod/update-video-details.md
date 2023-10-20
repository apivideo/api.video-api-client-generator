---
title: Update video details
meta:
  description: This guide explains the two methods you can use to update videos - programmatically and through the dashboard.
---

# Update Video Details

You can update details for a video after you've uploaded it. The only things you can't change are the video ID, the content of the video, and the watermark. This guide walks through two ways you can make an update - programmatically and through the dashboard.

## API documentation

- [Update a video](/reference/api/Videos#update-a-video-object)

## Update video details

When you update details about your video, you can choose from a wide variety of settings. Choices include: 

- playerId - Add a player you want to associate with your video.
- title - Update or add a title to your video.
- description - Update or add a description of the contents of your video.
- public - Whether your video is public or private.
- panoramic - Whether the video is 360 degree / immersive.
- mp4Support - Whether the player supports the mp4 format.
- tags - A comma-separated list of words you want to use to tag the video.
- metadata - An array of dictionaries where each dictionary contains a key:value pair that describes the video. 

To update details about a video, use this code sample: 

{% capture samples %}
```curl
curl --request PATCH \
     --url https://ws.api.video/videos/viZxSTFgXZVjFnFCUo363Ie \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI1NDk1NjAuMDE5ODA0LCJuYmYiOjE2NDI1NDk1NjAuMDE5ODA0LCJleHAiOjE2NDI1NTMxNjAuMDE5ODA0LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jjr4YADGbe62RmBBxJXLy1D61Mtfry_dq9nbriBXgkPrdlBJ8ZRP50CyW3AsGD7wSuKp2mXxEYSzj64zelT1IGOwg6KG4Gz9BZ9YWs0GAHKUIdgqn1gzITX5aQljIXx1fquXbawd-axBTi4icmaUjgXjfnyIcWOgHd2D8A3kpKiqiMmluh58JdnwPnH0OyVk0Rk824P0PI6SxfiTHfkCglPL6ixf9OgokMLPoVrsxH5C0xt3Z7lf5TJ0F78-JY-yTKvyaTTIfI6CFOMNaZUlMtgQwq8X93_2FA65Ntw3hdDML8gFKkLUxnBAtZMo9WAjUd30G4OcYasmlkc4Q_JSNw' \
     --header 'Content-Type: application/json' \
     --data '
{
     "title": "New Title"
}
'
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
        
    videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" // string | The video ID for the video you want to delete.
    videoUpdatePayload := *apivideosdk.NewVideoUpdatePayload() // VideoUpdatePayload | 

    
    res, err := client.Videos.Update(videoId, videoUpdatePayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.Update``: %v\n", err)
    }
    // response from `Update`: Video
    fmt.Fprintf(os.Stdout, "Response from `Videos.Update`: %v\n", res)
}
```
```php
<?php

use ApiVideo\Client\Model\Metadata;
use ApiVideo\Client\Model\VideoUpdatePayload;

require __DIR__ . '/../../../vendor/autoload.php';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new \ApiVideo\Client(
                    'https://sandbox.api.video',
                    'YOUR_API_TOKEN',
                    $httpClient
                );

$client->videos()->update("video ID here", (new VideoUpdatePayload())
    ->setTitle("The new title")
    ->setPublic(false)
    ->setDescription("A new description")
    ->setTags(["tag1", "tag2"])
    ->setMetadata(array(
        new Metadata(["key" => "aa", 'value' => "bb"]),
        new Metadata(["key" => "aa2", 'value' => "bb2"]))));
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // The video ID for the video you want to delete.
        const videoUpdatePayload = {
			playerId: "pl4k0jvEUuaTdRAEjQ4Jfrgz", // The unique ID for the player you want to associate with your video.
			title: "title_example", // The title you want to use for your video.
			description: "A film about good books.", // A brief description of the video.
			_public: true, // Whether the video is publicly available or not. False means it is set to private. Default is true. Tutorials on [private videos](https://api.video/blog/endpoints/private-videos/).
			panoramic: false, // Whether the video is a 360 degree or immersive video.
			mp4Support: true, // Whether the player supports the mp4 format.
			tags: ["maths", "string theory", "video"], // A list of terms or words you want to tag the video with. Make sure the list includes all the tags you want as whatever you send in this list will overwrite the existing list for the video.
			metadata: null, // A list (array) of dictionaries where each dictionary contains a key value pair that describes the video. As with tags, you must send the complete list of metadata you want as whatever you send here will overwrite the existing metadata for the video. [Dynamic Metadata](https://api.video/blog/endpoints/dynamic-metadata/) allows you to define a key that allows any value pair.
		}; 

        // Video
        const result = await client.videos.update(videoId, videoUpdatePayload);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

## Set up the authenticated client
client = apivideo.AuthenticatedApiClient(api_key)

## if you rather like to use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)
client.connect()

videos_api = VideosApi(client)

## Create the payload with video details 
video_create_payload = {
    "title": "Client Video Test",
    "description": "Client test",
    "public": True,
    "tags": ["bunny"]
}

## Create the container for your video and print the response
response = videos_api.create(video_create_payload)
print("Video Container", response)

## Retrieve the video ID, you can upload once to a video ID
video_id = response["video_id"]

## Prepare the file you want to upload. Place the file in the same folder as your code.
file = open("sample-mov-file.mov", "rb")

## Upload your video. This handles videos of any size. The video must be in the same folder as your code. 
## If you want to upload from a link online, you need to add the source parameter when you create a new video.
video_response = videos_api.upload(video_id, file)

print("Uploaded Video", video_response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Update video details with your dashboard

If you don't want to send an API request with code, you can update video details from your dashboard. Do the following: 

1. Open your dashboard.

2. From the menu on the left, choose **Videos**. A pop-up containing information about how to embed your video appears. The video ID is included in the embed link at the end (you'll need to strip the rest of the URL to use it, though). You can also continue to the details screen for the video. 

3. From the upper right of the pop-up, click **See details**. You'll land on the details screen for your video, and the video ID appears at the top, above the video. 

   ![Showing the video details screen in the Dashboard](/_assets/update-video-details.png)
