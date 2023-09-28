---
title: "Progressive video upload"
---
Progressive video upload
=============================

api.video provides different ways to upload your videos. There are two ways to upload with tokens, and then there are two ways to upload depending on whether your video is over 200MB or under. This guide walks through how to do progressive video upload. Options covered in this guide include:

- Progressive upload for a video that's 200MiB or more
- Progressive upload for a video that's 200MiB or more using `byte` range in the `Content-Range` header

{% capture content %}
Megabyte (MB) and Mebibyte (MiB) are both used to measure units of information on computer storage. 1 MB is 1000Kb (kilobytes), and 1 MiB is 1048.576Kb. api.video uses MiB.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## API documentation

- [Create a video](/reference/api/Videos#create-a-video-object)
- [Upload a video](/reference/api/Videos#upload-a-video)

## Create an account

Before you can start uploading your first video, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

## Choose an API client

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [iOS](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)

## Install

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
{% include "_partials/code-tabs.html" content: samples %}

## Progressive upload in file chunks

When the video file you want to upload is too large to send in one request, you need to do a progressive upload. To upload the entire video, you must broken into chunks, and send each chunk in separate requests. The smallest chunk size allowed is **5 MiB**. 

- When you do a progressive upload using one of the api.video clients, you must indicate when you send the last part.
- If you implement this functionality without an api.video client, you do not need to indicate the last part of the progressive upload. api.video can track the chunk numbers based on your header which indicates `Content-Range`, for example : part 3/3, according to the number of chunks you sent. 

{% capture samples %}

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

# Retrieve the access_token from the response, then include in your next request.

curl --request POST \
     --url https://ws.api.video/videos//source \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MjQzMTkuMDk2NjY1LCJuYmYiOjE2NDI4MjQzMTkuMDk2NjY1LCJleHAiOjE2NDI4Mjc5MTkuMDk2NjY1LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.rfchf3btbMTzSukcwhUS0u4fNY4Q3g1JpoMeIz_Dls1ADmqDdKw7yBOE893C7cagb0lpuvUJvhuhgusLStsJ4nqzTveDeM2oPBQBNJjzwaJZNrImTPD4mif7Tzgxvn1_jQJA5L4gQhjd7frCIJW1yAwywrtiDPbxiWNp8fVl7r_QILjZZfslxy-kblPrHJ20Zix9VURqkGIORY5G_457nHSV9Atks1sUlt49E8b_g3jORja3MnznXBS0-0dksz2K62-QMe1_dk78V9JwbLeydqcr15M1jDLA3H6qFGI7GTsTDdZ5jKLhg5OR6yeSHFysqr3kOteTqAGdp3JuTrpZIA' \
     --header 'Content-Range: part%201%2F3' \
     --header 'Content-Type: multipart/form-data'

# You would send each part until the last part is sent. In order to do this with a cURL request, you must break your video into chunks ahead of time.
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

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


## Progressive upload with byte range in the content-range header

A slightly more complicated way to upload a video that's 200 MiB or larger is to use `bytes` in the `content-range` header. This method requires that you define the exact byte ranges correctly, otherwise the entire upload to fail. When done correctly, your header will look like this: 

```
Content-Range: bytes 0-5242879
```

And then continue from there. By default, the api.video clients handle uploads for you using this method. If you want to try it yourself without the client, the sample will look like this in cURL: 

{% capture samples %}

```curl
curl -X POST \
  https://sandbox.api.video/auth/api-key \
  -H 'Content-Type: application/json' \
  -d '{"apiKey": "your_api_key"}'
  
# Retrieve the access_token from the response, then include in your next request.

curl -X POST https://sandbox.api.video/videos \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFiYjcxNmNiY2ZiNmY4MDc2OWEzZmQ1MjlhMjZiZWRkY2EwMzhlYzA3NDk5M2ZiMTA0YjhiZGMwOTI5MzgxN2M3NmNkNzI4ZDIzOGMzZmNlIn0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiYWJiNzE2Y2JjZmI2ZjgwNzY5YTNmZDUyOWEyNmJlZGRjYTAzOGVjMDc0OTkzZmIxMDRiOGJkYzA5MjkzODE3Yzc2Y2Q3MjhkMjM4YzNmY2UiLCJpYXQiOjE1MjY1NDgzMDEsIm5iZiI6MTUyNjU0ODMwMSwiZXhwIjoxNTI2NTUxOTAxLCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.jWHC18iEur69FzD5dm78wAwNzh2cPKTRvKuspyQNQKPvhEbYa2v4XhqVNh0TTw8JeNxBtcePBTMHl4S9nWsw7pW4KD8zbqzUjCZNYlaYDpu8vu_tmWVO2JccglJIjuQEaiTbkUsfLdgtsb_9DJ3frk1-WgAKuzu0HewhcGb80xivdJPqNYA6I1Ig8GOief9LTUNNJoqqZn1A1-UiGRTXDag7_yODuxzpMFaAzbaisfK0gYti-PnjyHGWhpGwRplMKPPJk6rSAp1d9TWWXVgg-bNqUzz4_sr33ICJTx7_qZzfamMqk5PDZbHOwpIj8L2DBfo3isvt6QliWmgFEOuvog' \
  -d '{
    "title":"This is a title",
    "description":"My video description", 
    "source":"https://example.com/myVideo.mp4"
  }'
  
# Split your video into chunks, if you're on a mac you can use this command
  
split -b 100m source.mp4.mp4 file_chunk_

# Split your video into chunks, if you're using Linux you can use this command

/path/to/source.mp4` is a 289MB (`281905832 B`) file .

split --bytes=100M source.mp4 file_chunk_

# You'll need to send a cURL request for each chunk, with the proper Content-Range header

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

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


{% capture content %}
All api.video clients automatically use the `Content-Range: bytes` method to upload big videos for you. You don't have to set it up yourself if you use a client!
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Resources

Check out api.video's blog content and tutorials about [video uploads](https://api.video/blog/endpoints/video-upload/), [private videos](https://api.video/blog/endpoints/private-videos/), and delegated uploads](https://api.video/blog/endpoints/delegated-upload/)!