---
title: Show video details
meta:
  description: This guide explains how retrieve video details programmatically and through the api.video dashboard.
---

# Show Video Details

You can retrieve details for a specific video if you have the video ID. In this guide, we'll walk through the code to retrieve it and how to retrieve it from the dashboard.

## API documentation

- [Show a video](/reference/api/Videos#retrieve-a-video-object)

## Retrieve video info

To retrieve information about any of your videos, use this code sample:

{% capture samples %}
```curl
curl --request GET \
     --url https://ws.api.video/videos/viZxSTFgXZVjFnFCUo363Ie \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI1NDk1NjAuMDE5ODA0LCJuYmYiOjE2NDI1NDk1NjAuMDE5ODA0LCJleHAiOjE2NDI1NTMxNjAuMDE5ODA0LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jjr4YADGbe62RmBBxJXLy1D61Mtfry_dq9nbriBXgkPrdlBJ8ZRP50CyW3AsGD7wSuKp2mXxEYSzj64zelT1IGOwg6KG4Gz9BZ9YWs0GAHKUIdgqn1gzITX5aQljIXx1fquXbawd-axBTi4icmaUjgXjfnyIcWOgHd2D8A3kpKiqiMmluh58JdnwPnH0OyVk0Rk824P0PI6SxfiTHfkCglPL6ixf9OgokMLPoVrsxH5C0xt3Z7lf5TJ0F78-JY-yTKvyaTTIfI6CFOMNaZUlMtgQwq8X93_2FA65Ntw3hdDML8gFKkLUxnBAtZMo9WAjUd30G4OcYasmlkc4Q_JSNw'
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
        
    videoId := "videoId_example" // string | The unique identifier for the video you want details about.

    
    res, err := client.Videos.Get(videoId)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.Get``: %v\n", err)
    }
    // response from `Get`: Video
    fmt.Fprintf(os.Stdout, "Response from `Videos.Get`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideosApi;

$apiKey = 'api key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$videos = $client->videos()->get('video ID here');
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const videoId = 'videoId_example'; // The unique identifier for the video you want details about.

        // Video
        const result = await client.videos.get(videoId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Get details about a single video using its video ID
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
video_id = "video ID for video you want info about here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

videos_api = VideosApi(client)

## Get details about a single video by video ID
response = videos_api.get(video_id)
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Retrieve video info from your dashboard

If you don't want to retrieve video details programmatically, you can also view videos in your dashboard. To do that, do the following: 

1. Log in to your dashboard.

2. From the menu on the left, choose **Videos**. 

   ![Showing the list of videos on the dashboard](/_assets/video-selection.png)

3. Click on the video for which you want to view details. You can use the filtering options to help you find the one you want. 

4. When the pop-up for the individual video you chose appears, click **See details** to review all the information about the video. 

   ![Showing video details on the dashboard](/_assets/video-details.png)

## Conclusion

It can be useful to retrieve a single video's details to determine if anything needs updating, or to collect key information like the video ID or mp4 link.
