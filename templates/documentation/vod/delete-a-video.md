---
title: "Delete a video"
---
Delete A Video
==============

Sometimes, you need to delete a video permanently. This guide walks you through how to get rid of a video programmatically and through the dashboard. Please be aware that you cannot retrieve a video you delete, so be sure it's what you want to do.

## Associated API reference documentation

- [Delete a video](/reference/api/Videos#delete-a-video-object)

## Choose an api.video client

The clients offered by api.video include:

- [Go](https://github.com/apivideo/api.video-go-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [JavaScript ](https://github.com/apivideo/api.video-nodejs-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)

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

## Retrieve your API key

You'll need your API key to get started. You can sign up for one here: [Get your api.video API key!](https://dashboard.api.video/register). Then do the following: 

1. Log in to the api.video dashboard. 
2. From the list of choices on the left, make sure you are on **API Keys** 
3. You will always be able to choose to use your Sandbox API key. If you want to use the Production API key instead, enter your credit card information. 
4. Grab the key you want, and you're ready to get started! 

## Delete a video

To delete a video, you need to do the following: 

1. Retrieve the video ID for the video you want to delete. You can do this by listing your videos and searching for the one you want, or you can look in your dashboard to find the video you want to delete. 

2. Send a delete request to api.video containing the ID for the video you want to delete. Be sure you want to delete the video - there is no way to retrieve the content you delete. 

{% capture samples %}
```curl
curl --request DELETE \
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
        
    videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" // string | The video ID for the video you want to delete.

    
    err := client.Videos.Delete(videoId)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.Delete``: %v\n", err)
    }
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideosApi;

$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$videos = $client->videos()->delete('video ID here');
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // The video ID for the video you want to delete.

        // void
        const result = await client.videos.delete(videoId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Delete a video using its video ID
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

videos_api = VideosApi(client)

title = "Sample AVI Video"

## List videos that have the exact, unique title you wanted to delete
videos = videos_api.list(title=title)

## Get list of videos out of response object or single item depending on whether you filtered
videos = videos['data']

## In this case, let's assume we know there's only one video with the title we filtered for. 
print(videos[0]['video_id'])
        
## Delete the video
response = videos_api.delete(videos[0]['video_id'])
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Delete a video in the dashboard

You can delete a video using your dashboard by doing the following:

1. Open your dashboard.

2. From the menu on the left, choose **Videos**.

3. On the Video screen, use the filtering and scrolling features to locate the video you want to delete. Place a checkmark next to one or more videos you want to delete.

   ![](/_assets/delete-video.png)

4. In the center of the screen at the bottom, you'll see a trash can icon. Click the **trash can**. A popup appears asking if you're sure you want to delete the video(s) you selected.

5. Click **Confirm** to permanently delete your videos. There is no way to retrieve your content after you confirm, so be sure of your decision!
