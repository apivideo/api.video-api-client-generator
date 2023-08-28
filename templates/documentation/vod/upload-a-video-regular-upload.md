---
title: "Upload a video - advanced guide"
---
Upload A Video Regular Upload
=============================

api.video provides different ways to upload your videos. There are two ways to upload with tokens, and then there are two ways to upload depending on whether your video is over 200MB or under. This guide walks through how to do all the different kinds of regular uploads available to you. Options covered in this guide include:

- Regular upload for a video under 200MiB in size
- Regular upload for a video that's 200MiB or more using a progressive upload
- Regular upload for a video that's 200MiB or more using byte range in the Content-Range header

{% capture content %}
📘 **NOTE**

Megabyte (MB) and Mebibyte (MiB) are both used to measure units of information on computer storage. 1 MB is 1000Kb (kilobytes), and 1 MiB is 1048.576Kb. api.video uses MiB.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Associated API reference documentation

- [Create a video](/reference/api/Videos#create-a-video-object)
- [Upload a video](/reference/api/Videos#upload-a-video)

## Video upload overview

This section gives you an overview of your upload options. This guide walks through regular uploads but describes all the available choices here. 

{% capture content %}
📘 **NOTE**

If you want to learn about delegated uploads, which are useful for creating private videos, or allowing your viewers to upload content themselves, or even just making it easier for you to do uploads, please see the [Create and manage tokens for delegated uploads](/get-started/create-and-manage-tokens-for-delegated-uploads) guide and the [Upload a video with a delegated token](/get-started/upload-a-video-with-a-delegated-token) guide.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

The two token-based upload methods are:

- Regular upload - you use an access token you retrieved when you authenticate to do a two-step process where you create a video container with metadata, retrieve the video ID, and use that to upload your video into the container.
- Delegated upload - you create a token and use the token to upload your video in one step. You can change the metadata later on. 

There are two types of upload depending on whether your video is over 200MB or under. You can use either a regular or delegated upload for a video under 200 MB. If the video is over 200MB, you can do a progressive upload, where you break a video into chunks and then upload the pieces using parts in the header. You can also use bytes in the header, which is a bit more complicated.  If you use one of the clients, big uploads are handled for you using the bytes method. You can also use the progressive upload method if you wish, though the steps require a bit more effort.

## Choose an api.video client

The clients offered by api.video include:

- [Go](https://github.com/apivideo/api.video-go-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [JavaScript ](https://github.com/apivideo/api.video-nodejs-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)

## Installation

To install your selected client, do the following: 

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



## Create a video container and upload a video

When you want to upload a video using the regular way, your video must be under 200 MB. It's a two-step process. First, you create a video container with all your video details and metadata. Then, you retrieve the container's video ID and upload your video into the container using the ID. 

```curl
curl --request POST \
     --url https://ws.api.video/videos \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI1NDk1NjAuMDE5ODA0LCJuYmYiOjE2NDI1NDk1NjAuMDE5ODA0LCJleHAiOjE2NDI1NTMxNjAuMDE5ODA0LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jjr4YADGbe62RmBBxJXLy1D61Mtfry_dq9nbriBXgkPrdlBJ8ZRP50CyW3AsGD7wSuKp2mXxEYSzj64zelT1IGOwg6KG4Gz9BZ9YWs0GAHKUIdgqn1gzITX5aQljIXx1fquXbawd-axBTi4icmaUjgXjfnyIcWOgHd2D8A3kpKiqiMmluh58JdnwPnH0OyVk0Rk824P0PI6SxfiTHfkCglPL6ixf9OgokMLPoVrsxH5C0xt3Z7lf5TJ0F78-JY-yTKvyaTTIfI6CFOMNaZUlMtgQwq8X93_2FA65Ntw3hdDML8gFKkLUxnBAtZMo9WAjUd30G4OcYasmlkc4Q_JSNw' \
     --header 'Content-Type: application/json' \
     --data '
{
     "public": true,
     "panoramic": false,
     "mp4Support": true,
     "title": "HelloWorld"
}
'

------Retrieve the access_token from the response, then include in your next request-----

curl https://ws.api.video/videos/{video ID here}/source \
  -H 'Authorization: Bearer -access token here-' \
  -F file=@yourvideotitle.mp4
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


    //Upload a video
    //First create a container
    create, err := client.Videos.Create(apivideosdk.VideoCreationPayload{Title: "My video title"})

    if err != nil {
        fmt.Println(err)
    }

    //Then open the video file
    videoFile, err := os.Open("path/to/video.mp4")

    if err != nil {
        fmt.Println(err)
    }

    //Finally upload your video to the container with the videoId
    uploadedVideo, err := client.Videos.UploadFile(*create.VideoId, videoFile)

    if err != nil {
        fmt.Println(err)
    }


    //And get the assets
    fmt.Printf("%s\n", *uploadedVideo.Assets.Hls)
    fmt.Printf("%s\n", *uploadedVideo.Assets.Iframe)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideoCreationPayload;

$apiKey = 'yourAPIkey';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$payload = (new VideoCreationPayload())
    ->setTitle('My cool video');

$video = $client->videos()->create($payload);
echo($payload);

$response = $client->videos()->upload(
    $video->getVideoId(),
    new SplFileObject(__DIR__.'/VIDEO_TO_UPLOAD.mp4')
);
echo($response);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        // create a video
        const videoCreationPayload = {
            title: "Maths video", // The title of your new video.
            description: "A video about string theory.", // A brief description of your video.
        };
        const video = await client.videos.create(videoCreationPayload);

        // upload a video file into the video container
        await client.videos.upload(video.videoId, "my-video-file.mp4");
    } catch (e) {
        console.error(e);
    }
})();
```
```python Python
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
```csharp
using System.Collections.Generic;
using System.Diagnostics;
using System;
using System.IO;
using ApiVideo.Api;
using ApiVideo.Client;
using ApiVideo.Model;

namespace Example
{
    public class Example
    {
        public static void Main()
        {
            var apiKey = "YOUR_API_KEY";

            var apiVideoClient = new ApiVideoClient(apiKey);
            // if you rather like to use the sandbox environment:
            // var apiVideoClient = new ApiVideoClient(apiKey, ApiVideo.Client.Environment.SANDBOX);

            var videoPayload = new VideoCreationPayload()
            {
                title = "Example video title",
                description = "Example video description",
                mp4support = true,
                tags = new List<string>()
                {
                    "first","video","test","c#","client"
                }
            };

            var myVideoFile = File.OpenRead("my-video.mp4");

            try {
                var newVideo = apiVideoClient.Videos().create(videoPayload);
                var video = apiVideoClient.Videos().upload(newVideo.videoid,myVideoFile);
                Console.WriteLine(video);
            } catch (ApiException e) {
                Console.WriteLine(e.ErrorCode);
                Console.WriteLine(e.Message);
                Console.WriteLine(e.StackTrace);
            }
        }
    }
}
```



## Create a video container and upload a video over 200 MB with a progressive upload

When you do a progressive upload for a video, it's because the video is too big to send in one request to api.video. To upload the entire video, it must be broken into chunks, and then each chunk is sent. The smallest chunk size allowed is 5 MiB. When you send using one of the clients, you must indicate when you send the last part. If you implement without a client, then when you send the last part, api.video can tell because your header will say Content-Range: part 3/3 (or whatever number of chunks you sent - 4/4, 8/8, etc.) 

```curl
curl --request POST \
     --url https://ws.api.video/videos \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI1NDk1NjAuMDE5ODA0LCJuYmYiOjE2NDI1NDk1NjAuMDE5ODA0LCJleHAiOjE2NDI1NTMxNjAuMDE5ODA0LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jjr4YADGbe62RmBBxJXLy1D61Mtfry_dq9nbriBXgkPrdlBJ8ZRP50CyW3AsGD7wSuKp2mXxEYSzj64zelT1IGOwg6KG4Gz9BZ9YWs0GAHKUIdgqn1gzITX5aQljIXx1fquXbawd-axBTi4icmaUjgXjfnyIcWOgHd2D8A3kpKiqiMmluh58JdnwPnH0OyVk0Rk824P0PI6SxfiTHfkCglPL6ixf9OgokMLPoVrsxH5C0xt3Z7lf5TJ0F78-JY-yTKvyaTTIfI6CFOMNaZUlMtgQwq8X93_2FA65Ntw3hdDML8gFKkLUxnBAtZMo9WAjUd30G4OcYasmlkc4Q_JSNw' \
     --header 'Content-Type: application/json' \
     --data '
{
     "public": true,
     "panoramic": false,
     "mp4Support": true,
     "title": "HelloWorld"
}
'

------Retrieve the access_token from the response, then include in your next request.-----

curl --request POST \
     --url https://ws.api.video/videos//source \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MjQzMTkuMDk2NjY1LCJuYmYiOjE2NDI4MjQzMTkuMDk2NjY1LCJleHAiOjE2NDI4Mjc5MTkuMDk2NjY1LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.rfchf3btbMTzSukcwhUS0u4fNY4Q3g1JpoMeIz_Dls1ADmqDdKw7yBOE893C7cagb0lpuvUJvhuhgusLStsJ4nqzTveDeM2oPBQBNJjzwaJZNrImTPD4mif7Tzgxvn1_jQJA5L4gQhjd7frCIJW1yAwywrtiDPbxiWNp8fVl7r_QILjZZfslxy-kblPrHJ20Zix9VURqkGIORY5G_457nHSV9Atks1sUlt49E8b_g3jORja3MnznXBS0-0dksz2K62-QMe1_dk78V9JwbLeydqcr15M1jDLA3H6qFGI7GTsTDdZ5jKLhg5OR6yeSHFysqr3kOteTqAGdp3JuTrpZIA' \
     --header 'Content-Range: part%201%2F3' \
     --header 'Content-Type: multipart/form-data'

------You would send each part until the last part is sent. In order to do this with
 a cURL request, you must break your video into chunks ahead of time.------
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
        
    videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" // string | Enter the videoId you want to use to upload your video.
  
    part1, err := os.Open("10m.mp4.part.a")
    part2, err := os.Open("10m.mp4.part.b")
    part3, err := os.Open("10m.mp4.part.c")

    stream = client.Videos.CreateUploadStream(videoId)
    _, err = stream.UploadPartFile(part1)
    _, err = stream.UploadPartFile(part2)
    res, err := stream.UploadLastPartFile(part3)

    err = part1.Close()
    err = part2.Close()
    err = part3.Close()
```
```php
<?php

use ApiVideo\Client\Model\VideoCreationPayload;

require __DIR__ . '/vendor/autoload.php';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new \ApiVideo\Client(
                    'https://sandbox.api.video',
                    'YOUR_API_TOKEN',
                    $httpClient
                );

// create a new video &amp; upload a video file
$myVideo = $client->videos()->create((new VideoCreationPayload())->setTitle('Uploaded video'));
$client->videos()->upload($myVideo->getVideoId(), new SplFileObject(__DIR__ . '/../../../tests/resources/558k.mp4'));

// create a new video &amp; upload a video file using progressive upload (the file is uploaded by parts)
$myVideo2 = $client->videos()->create((new VideoCreationPayload())->setTitle('Uploaded video (progressive upload)'));

$progressiveSession = $client->videos()->createUploadProgressiveSession($myVideo2->getVideoId());

$progressiveSession->uploadPart(new SplFileObject(__DIR__ . '/../../../tests/resources/10m.mp4.part.a'));
$progressiveSession->uploadPart(new SplFileObject(__DIR__ . '/../../../tests/resources/10m.mp4.part.b'));

$progressiveSession->uploadLastPart(new SplFileObject(__DIR__ . '/../../../tests/resources/10m.mp4.part.c'));
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
try {

        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });
    
        const videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // Enter the videoId you want to use to upload your video.
    
        const uploadSession = client.createUploadProgressiveSession(videoId);

        await uploadSession.uploadPart('test/data/10m.mp4.part.a');
        await uploadSession.uploadPart('test/data/10m.mp4.part.b');
        const res = await uploadSession.uploadLastPart('test/data/10m.mp4.part.c');  // Video 

        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
import os
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your API key here"

## Set up the authenticated client
client = apivideo.AuthenticatedApiClient(api_key)

## if you rather like to use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)
client.connect()

videos_api = VideosApi(client)

video_create_payload = {
    "title": "Progressive Test",
    "description": "test",
    "public": False,
    "tags": ["nature"]
}
## Create the container for your video and print the response
response = videos_api.create(video_create_payload)
print("Video Container", response)

## Retrieve the video ID, you can upload once to a video ID
video_id = response["video_id"]

session = videos_api.create_upload_progressive_session(video_id)

CHUNK_SIZE = 6000000

## This is our chunk reader. This is what gets the next chunk of data ready to send.
def read_in_chunks(file_object, CHUNK_SIZE):
    while True:
        data = file_object.read(CHUNK_SIZE)
        if not data:
            break
        yield data

## Upload your file by breaking it into chunks and sending each piece
def upload(file):
    content_name = str(file)
    content_path = os.path.abspath(file)

    f = open(content_path, "rb")
    index = 0
    offset = 0
    part_num = 1
    headers = {}

    for chunk in read_in_chunks(f, CHUNK_SIZE):
        offset = index + len(chunk)
        index = offset

        with open('chunk.part.' + str(part_num), 'wb') as chunk_file:
            chunk_file.write(chunk)
            chunk_file.close()

        with open('chunk.part.' + str(part_num), 'rb') as chunk_file:
            try:
                if len(chunk) == CHUNK_SIZE:
                    session.uploadPart(chunk_file)
                elif len(chunk) < CHUNK_SIZE:
                    print(session.uploadLastPart(chunk_file))
                chunk_file.close()
            except Exception as e:
                print(e)

        os.remove('chunk.part.' + str(part_num))
        part_num += 1

upload('VIDEO_FILE.mp4')
```



## Create a video container and upload a video over 200 MiB with bytes in the content-range header

A slightly more complicated way to upload a video that's 200 MiB or larger is to use bytes in the content-range header. This method can be complex because if you miscalculate the bytes, it will cause the entire upload to fail. When done correctly, your header will look like this: 

Content-Range: bytes 0-5242879 

And then continue from there. By default, the api.video clients handle uploads for you using this method. If you want to try it yourself without the client, the sample will look like this in cURL: 

```curl
curl -X POST \
  https://sandbox.api.video/auth/api-key \
  -H 'Content-Type: application/json' \
  -d '{"apiKey": "your_api_key"}'
  
------Retrieve the access_token from the response, then include in your next request.-----

curl -X POST https://sandbox.api.video/videos \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFiYjcxNmNiY2ZiNmY4MDc2OWEzZmQ1MjlhMjZiZWRkY2EwMzhlYzA3NDk5M2ZiMTA0YjhiZGMwOTI5MzgxN2M3NmNkNzI4ZDIzOGMzZmNlIn0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiYWJiNzE2Y2JjZmI2ZjgwNzY5YTNmZDUyOWEyNmJlZGRjYTAzOGVjMDc0OTkzZmIxMDRiOGJkYzA5MjkzODE3Yzc2Y2Q3MjhkMjM4YzNmY2UiLCJpYXQiOjE1MjY1NDgzMDEsIm5iZiI6MTUyNjU0ODMwMSwiZXhwIjoxNTI2NTUxOTAxLCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.jWHC18iEur69FzD5dm78wAwNzh2cPKTRvKuspyQNQKPvhEbYa2v4XhqVNh0TTw8JeNxBtcePBTMHl4S9nWsw7pW4KD8zbqzUjCZNYlaYDpu8vu_tmWVO2JccglJIjuQEaiTbkUsfLdgtsb_9DJ3frk1-WgAKuzu0HewhcGb80xivdJPqNYA6I1Ig8GOief9LTUNNJoqqZn1A1-UiGRTXDag7_yODuxzpMFaAzbaisfK0gYti-PnjyHGWhpGwRplMKPPJk6rSAp1d9TWWXVgg-bNqUzz4_sr33ICJTx7_qZzfamMqk5PDZbHOwpIj8L2DBfo3isvt6QliWmgFEOuvog' \
  -d '{
    "title":"This is a title",
    "description":"My video description", 
    "source":"https://example.com/myVideo.mp4"
  }'
  
------Split your video into chunks, if you're on a mac you can use this command-----
  
split -b 100m source.mp4.mp4 file_chunk_

------Split your video into chunks, if you're using Linux you can use this command-----

/path/to/source.mp4` is a 289MB (`281905832 B`) file .

split --bytes=100M source.mp4 file_chunk_

--You'll need to send a cURL request for each chunk, with the proper Content-Range header--

curl https://sandbox.api.video/videos/vitq4gOj8GyDT9kyxPQoyNJl/source \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFiYjcxNmNiY2ZiNmY4MDc2OWEzZmQ1MjlhMjZiZWRkY2EwMzhlYzA3NDk5M2ZiMTA0YjhiZGMwOTI5MzgxN2M3NmNkNzI4ZDIzOGMzZmNlIn0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiYWJiNzE2Y2JjZmI2ZjgwNzY5YTNmZDUyOWEyNmJlZGRjYTAzOGVjMDc0OTkzZmIxMDRiOGJkYzA5MjkzODE3Yzc2Y2Q3MjhkMjM4YzNmY2UiLCJpYXQiOjE1MjY1NDgzMDEsIm5iZiI6MTUyNjU0ODMwMSwiZXhwIjoxNTI2NTUxOTAxLCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.jWHC18iEur69FzD5dm78wAwNzh2cPKTRvKuspyQNQKPvhEbYa2v4XhqVNh0TTw8JeNxBtcePBTMHl4S9nWsw7pW4KD8zbqzUjCZNYlaYDpu8vu_tmWVO2JccglJIjuQEaiTbkUsfLdgtsb_9DJ3frk1-WgAKuzu0HewhcGb80xivdJPqNYA6I1Ig8GOief9LTUNNJoqqZn1A1-UiGRTXDag7_yODuxzpMFaAzbaisfK0gYti-PnjyHGWhpGwRplMKPPJk6rSAp1d9TWWXVgg-bNqUzz4_sr33ICJTx7_qZzfamMqk5PDZbHOwpIj8L2DBfo3isvt6QliWmgFEOuvog' \
  -H 'Content-Range: bytes 0-104857599/281905832' \
  -F file=@/path/to/file_chunk_aa

curl https://sandbox.api.video/videos/vitq4gOj8GyDT9kyxPQoyNJl/source \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFiYjcxNmNiY2ZiNmY4MDc2OWEzZmQ1MjlhMjZiZWRkY2EwMzhlYzA3NDk5M2ZiMTA0YjhiZGMwOTI5MzgxN2M3NmNkNzI4ZDIzOGMzZmNlIn0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiYWJiNzE2Y2JjZmI2ZjgwNzY5YTNmZDUyOWEyNmJlZGRjYTAzOGVjMDc0OTkzZmIxMDRiOGJkYzA5MjkzODE3Yzc2Y2Q3MjhkMjM4YzNmY2UiLCJpYXQiOjE1MjY1NDgzMDEsIm5iZiI6MTUyNjU0ODMwMSwiZXhwIjoxNTI2NTUxOTAxLCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.jWHC18iEur69FzD5dm78wAwNzh2cPKTRvKuspyQNQKPvhEbYa2v4XhqVNh0TTw8JeNxBtcePBTMHl4S9nWsw7pW4KD8zbqzUjCZNYlaYDpu8vu_tmWVO2JccglJIjuQEaiTbkUsfLdgtsb_9DJ3frk1-WgAKuzu0HewhcGb80xivdJPqNYA6I1Ig8GOief9LTUNNJoqqZn1A1-UiGRTXDag7_yODuxzpMFaAzbaisfK0gYti-PnjyHGWhpGwRplMKPPJk6rSAp1d9TWWXVgg-bNqUzz4_sr33ICJTx7_qZzfamMqk5PDZbHOwpIj8L2DBfo3isvt6QliWmgFEOuvog' \
  -H 'Content-Range: bytes 104857600-209715199/281905832' \
  -F file=@/path/to/file_chunk_ab

curl https://sandbox.api.video/videos/vitq4gOj8GyDT9kyxPQoyNJl/source \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFiYjcxNmNiY2ZiNmY4MDc2OWEzZmQ1MjlhMjZiZWRkY2EwMzhlYzA3NDk5M2ZiMTA0YjhiZGMwOTI5MzgxN2M3NmNkNzI4ZDIzOGMzZmNlIn0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiYWJiNzE2Y2JjZmI2ZjgwNzY5YTNmZDUyOWEyNmJlZGRjYTAzOGVjMDc0OTkzZmIxMDRiOGJkYzA5MjkzODE3Yzc2Y2Q3MjhkMjM4YzNmY2UiLCJpYXQiOjE1MjY1NDgzMDEsIm5iZiI6MTUyNjU0ODMwMSwiZXhwIjoxNTI2NTUxOTAxLCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.jWHC18iEur69FzD5dm78wAwNzh2cPKTRvKuspyQNQKPvhEbYa2v4XhqVNh0TTw8JeNxBtcePBTMHl4S9nWsw7pW4KD8zbqzUjCZNYlaYDpu8vu_tmWVO2JccglJIjuQEaiTbkUsfLdgtsb_9DJ3frk1-WgAKuzu0HewhcGb80xivdJPqNYA6I1Ig8GOief9LTUNNJoqqZn1A1-UiGRTXDag7_yODuxzpMFaAzbaisfK0gYti-PnjyHGWhpGwRplMKPPJk6rSAp1d9TWWXVgg-bNqUzz4_sr33ICJTx7_qZzfamMqk5PDZbHOwpIj8L2DBfo3isvt6QliWmgFEOuvog' \
  -H 'Content-Range: bytes 209715200-281905831/281905832' \
  -F file=@/path/to/file_chunk_ac
```
```text All Clients
All api.video clients automatically use the Content-Range: bytes method to upload
big videos for you. You don't have to set it up yourself if you use a client!
```

## Conclusion

If you're just getting started, the two-part upload process is the simplest. If you choose to use one of our clients, handling large files is done for you by the client. If you want to try a progressive upload, these are best when you're working with huge files and you want to send the parts of them concurrently.

## Resources

We offer blog content on this topic: 

- [Video upload with cURL](https://api.video/blog/tutorials/video-upload-tutorial) - **cURL** 

- [Video upload (large videos) with cURL](https://api.video/blog/tutorials/video-upload-tutorial-large-videos) **cURL** 

- [Upload many files at once with HTML and JavaScript](https://api.video/blog/tutorials/upload-many-files-at-once-with-html-and-javascript) - **JavaScript** Upload many files at once using an HTML form and JavaScript without the api.video client. 

- [Upload a video with Laravel](https://api.video/blog/tutorials/upload-a-video-with-laravel) - **PHP** Create a form that uploads a video to api.video using Laravel. 

- [Upload a video with the api.video PHP client](https://api.video/blog/tutorials/upload-a-video-with-the-api-video-php-client) - **PHP** A tutorial about how to upload a video with the PHP API client, this will work for large or small files and uses the Content-Range header method. 

- [Progressively upload large video files without compromising speed](https://api.video/blog/tutorials/progressively-upload-large-video-files-without-compromising-on-speed) - **Python** A tutorial that walks you through using progressive uploads without using an API client. 

- [Upload a big video file using Python](https://api.video/blog/tutorials/upload-a-big-video-file-using-python) - **Python** Learn how to break a large video file into chunks and upload each chunk using Python.

- [Upload a video from your computer with the api.video API](https://api.video/blog/tutorials/upload-a-video-from-your-computer-with-the-api-video-api-python) - **Python** Upload a video from your computer using Python. 

- [Upload a video to api.video using a public URL](https://api.video/blog/tutorials/upload-a-video-with-the-api-video-api-using-a-public-url-python) - **Python** Learn how to upload a video from a public mp4 link. 

- [Upload a video with the api.video Python Client](https://api.video/blog/tutorials/upload-a-video-with-the-api-video-python-client) - **Python** Upload a video of any size using api.video's Python client.

- [Use Flask with Dropzone.js to upload videos under 200 MiB (no client)](https://api.video/blog/tutorials/use-flask-with-dropzone-js-to-upload-videos-under-128mb-no-client) - **Python** Learn how to do an upload for files under 200 MiB without using an api.video client.
