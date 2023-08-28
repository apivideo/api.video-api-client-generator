---
title: "List videos"
---

List Videos
===========

You will often need to retrieve a list of all your videos or some videos to work with them. You might need to do this on the fly to see what you have, or you might want to pull the list into a database you can then pull from to create different kinds of displays. There are two ways to retrieve your videos: 

- List all videos
- Filter videos to a subset or single video

This guide walks through the different options for retrieving videos and covers a method for searching for them using your dashboard. 

## Associated API reference documentation

- [List all videos](/reference/api/Videos#list-all-video-objects) 

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
{% include "_partials/code-tabs.html" samples: content %}

## List all videos

You can list all videos by not using any filters when sending your request. The code sample looks like this:

{% capture samples %}
```curl
curl --request GET \
     --url 'https://ws.api.video/videos?currentPage=1&pageSize=25' \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI1NDk1NjAuMDE5ODA0LCJuYmYiOjE2NDI1NDk1NjAuMDE5ODA0LCJleHAiOjE2NDI1NTMxNjAuMDE5ODA0LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jjr4YADGbe62RmBBxJXLy1D61Mtfry_dq9nbriBXgkPrdlBJ8ZRP50CyW3AsGD7wSuKp2mXxEYSzj64zelT1IGOwg6KG4Gz9BZ9YWs0GAHKUIdgqn1gzITX5aQljIXx1fquXbawd-axBTi4icmaUjgXjfnyIcWOgHd2D8A3kpKiqiMmluh58JdnwPnH0OyVk0Rk824P0PI6SxfiTHfkCglPL6ixf9OgokMLPoVrsxH5C0xt3Z7lf5TJ0F78-JY-yTKvyaTTIfI6CFOMNaZUlMtgQwq8X93_2FA65Ntw3hdDML8gFKkLUxnBAtZMo9WAjUd30G4OcYasmlkc4Q_JSNw' \
```
```go
package main

import (
    "fmt"
    "os"
    apivideosdk "github.com/apivideo/api.video-go-client"
)

func main() {
    //Connect to production environment
    client := apivideosdk.ClientBuilder("YOUR_API_TOKEN").Build()

    // if you rather like to use the sandbox environment:
    // client := apivideosdk.SandboxClientBuilder("YOU_SANDBOX_API_TOKEN").Build()


    //List Videos
   
    result, err := client.Videos.List()

    if err != nil {
        fmt.Println(err)
    }

}
```
```php
<?php

require __DIR__ . '/vendor/autoload.php';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new \ApiVideo\Client(
                    'https://sandbox.api.video',
                    'YOUR_API_TOKEN',
                    $httpClient
                );

// list all videos (all pages)
$allVideos = [];
do {
    $currentPage = $client->videos()->list([]);
    $allVideos = array_merge($allVideos, $currentPage->getData());
} while($currentPage->getPagination()->getCurrentPage() < $currentPage->getPagination()->getPagesTotal());
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });
        // VideosListResponse
        const result = await client.videos.list()
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

client = apivideo.AuthenticatedApiClient(api_key)

## If you rather like to use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

## Retrieve a list of all videos. 

videos_api = VideosApi(client)
videos = videos_api.list()

print(videos)
```
{% endcapture %}
{% include "_partials/code-tabs.html" samples: content %}

## List videos using query parameters

If you want to retrieve a specific subset of videos, this code sample shows you how to filter:

{% capture samples %}
```curl
curl --request GET \
     --url 'https://ws.api.video/videos?title=videotitle.mp4&currentPage=1&pageSize=25' \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer access_token_here'
```
```go
package main

import (
    "fmt"
    "os"
    apivideosdk "github.com/apivideo/api.video-go-client"
)

func main() {
    //Connect to production environment
    client := apivideosdk.ClientBuilder("YOUR_API_TOKEN").Build()

    // if you rather like to use the sandbox environment:
    // client := apivideosdk.SandboxClientBuilder("YOU_SANDBOX_API_TOKEN").Build()


    //List Videos
    //First create the url options for searching
    opts := apivideosdk.VideosApiListRequest{}.
        CurrentPage(1).
        PageSize(25).
        SortBy("publishedAt").
        SortOrder("desc")

    //Then call the List endpoint with the options
    result, err := client.Videos.List(opts)

    if err != nil {
        fmt.Println(err)
    }

    for _, video := range result.Data {
        fmt.Printf("%s\n", *video.VideoId)
        fmt.Printf("%s\n", *video.Title)
    }
}
```
```php
<?php

require __DIR__ . '/vendor/autoload.php';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new \ApiVideo\Client(
                    'https://sandbox.api.video',
                    'YOUR_API_TOKEN',
                    $httpClient
                );

// list videos that have all the given tags (only first results page)
$videosWithTag = $client->videos()->list(['tags' => ['TAG2','TAG1']]);

// list videos that have all the given metadata values (only first results page)
$videosWithMetadata = $client->videos()->list(['metadata' => ['key1' => 'key1value1', 'key2' => 'key2value1']]);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });
				const videos = apiVideoClient.videos;
        const title = 'My Video.mp4'; // The title of a specific video you want to find. The search will match exactly to what term you provide and return any videos that contain the same term as part of their titles.
        const tags = '["captions", "dialogue"]'; // A tag is a category you create and apply to videos. You can search for videos with particular tags by listing one or more here. Only videos that have all the tags you list will be returned.
        const metadata = 'metadata[Author]=John Doe&metadata[Format]=Tutorial'; // Videos can be tagged with metadata tags in key:value pairs. You can search for videos with specific key value pairs using this parameter. [Dynamic Metadata](https://api.video/blog/endpoints/dynamic-metadata) allows you to define a key that allows any value pair.
        const description = 'New Zealand'; // If you described a video with a term or sentence, you can add it here to return videos containing this string.
        const liveStreamId = 'li400mYKSgQ6xs7taUeSaEKr'; // If you know the ID for a live stream, you can retrieve the stream by adding the ID for it here.
        const sortBy = 'publishedAt'; // Allowed: publishedAt, title. You can search by the time videos were published at, or by title.
        const sortOrder = 'asc'; // Allowed: asc, desc. asc is ascending and sorts from A to Z. desc is descending and sorts from Z to A.
        const currentPage = '2'; // Choose the number of search results to return per page. Minimum value: 1
        const pageSize = '30'; // Results per page. Allowed values 1-100, default is 25.

        // VideosListResponse
        const result = await client.videos.list({ title, tags, metadata, description, liveStreamId, sortBy, sortOrder, currentPage, pageSize })
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

client = apivideo.AuthenticatedApiClient(api_key)

## If you rather like to use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

videos_api = VideosApi(client)
videos = videos_api.list(title='your title')

print(videos)
```
{% endcapture %}
{% include "_partials/code-tabs.html" samples: content %}

## See a list of videos in the dashboard

If you don't want to retrieve a list of videos programmatically, you can log in to your dashboard and click **Videos**. This will show you a complete list of everything you have.

## Conclusion

Listing videos is useful for figuring out what you have and retrieving specific videos for your projects based on their attributes.
