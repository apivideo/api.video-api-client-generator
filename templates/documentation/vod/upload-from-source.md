---
title: Upload video from source
meta:
  description: This guide walks through how to clone one of your existing videos, and upload videos via a URL with api.video.
---

# Upload video from source

api.video enables you to upload a video container file [via a URL](#upload-a-file-from-url), or [clone one of your existing videos](#clone-an-existing-video). 

## Upload a file from a URL

Uploading a video from a video URL enables you go through the whole video upload process in only 1 step. Paste a URL that points to your video container (for example, the `.mp4` file of your video) into the `source` field in your request:

{% capture samples %}
```curl
curl --user *your_api_key*: \
--request POST \
--url https://ws.api.video/videos \
--header 'Content-Type: application/json' \
--data '
{
	"title": "My First Video",
	"source": "https://www.myvideourl.com/video.mp4"
}
'
```
```javascript
// First install the "@api.video/nodejs-client" npm package
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

const video = await client.videos.create({ 
	title: "My First Video",
	source: "https://www.myvideourl.com/video.mp4" 
});
```
```php
// First install the api client: "composer require api-video/php-api-client"
// Documentation: https://github.com/apivideo/api.video-php-client/blob/main/docs/Api/VideosApi.md#create

$client = new \ApiVideo\Client\Client(
    'https://ws.api.video',
    'YOUR_API_KEY',
    new \Symfony\Component\HttpClient\Psr18Client()
);

$video = $client->videos()->create((new \ApiVideo\Client\Model\VideoCreationPayload())
    ->setTitle("My First Video")
		->setSource("https://www.myvideourl.com/video.mp4"));
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/LiveStreamsApi.md#create
                  
ApiVideoClient client = new ApiVideoClient("YOUR_API_KEY");

VideoCreationPayload videoCreationPayload = new VideoCreationPayload(); 
videoCreationPayload.setTitle("My First Video"); 
videoCreationPayload.setSource("https://www.myvideourl.com/video.mp4");

try {
  Video video = client.videos().create(videoCreationPayload);
} catch (ApiException e) {
	// Manage error here
}
```
```kotlin
// First add the "video.api:android-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#create
                  
val client = ApiVideoClient("YOUR_API_KEY")

val videoCreationPayload = VideoCreationPayload()
videoCreationPayload.title = "My First Video"
videoCreationPayload.source = "https://www.myvideourl.com/video.mp4"

try {
  val video = client.videos().create(videoCreationPayload)
} catch (e: ApiException) {
	// Manage error here
}
```
```python
## First install the api client with "pip install api.video"
## Documentation: https://github.com/apivideo/api.video-python-client/blob/main/docs/VideosApi.md#create

from apivideo.api.videos_api import VideosApi
from apivideo.model.video_creation_payload import VideoCreationPayload
from apivideo import AuthenticatedApiClient, ApiException

with AuthenticatedApiClient("YOUR_API_KEY") as api_client:
    video_creation_payload = VideoCreationPayload(
        title="My First Video",
				source="https://www.myvideourl.com/video.mp4"
    ) 

    try:
        video = VideosApi(api_client).create(video_creation_payload)
    except ApiException as e:
        # Manage error here
```
```go
// First install the go client with go get github.com/apivideo/api.video-go-client
// Documentation: https://github.com/apivideo/api.video-go-client/blob/main/docs/Videos.md#Create
                  
client := apivideosdk.ClientBuilder("YOUR_API_KEY").Build()

videoCreationPayload := apivideosdk.VideoCreationPayload{}
videoCreationPayload.SetTitle("My First Video") 
videoCreationPayload.SetSource("https://www.myvideourl.com/video.mp4")

video, err := client.Videos.Create(videosCreationPayload)

if video != nil {
    // Manage error here
}
```
```swift
// First install the api client: https://github.com/apivideo/api.video-swift-client#getting-started
// Documentation: https://github.com/apivideo/api.video-swift-client/blob/main/docs/VideosAPI.md#create

ApiVideoClient.apiKey = "YOUR_API_KEY"

let videoCreationPayload = VideoCreationPayload(
    title: "My First Video",
		source: "https://www.myvideourl.com/video.mp4"
)

VideosAPI.create(videoCreationPayload: videoCreationPayload) { video, error in
   if let video = video  {
       // Do something with the video
   }
   if let error = error {
      // Manage error here
   }
}
```
```csharp
// First add the "ApiVideo" NuGet package to your project
// Documentation: https://github.com/apivideo/api.video-csharp-client/blob/main/docs/VideosApi.md#create
                  
var apiVideoClient = new ApiVideoClient("YOUR_API_KEY");

var videoCreationPayload = new VideoCreationPayload()
{
    title = "My First Video",
		source = "https://www.myvideourl.com/video.mp4"
};

try
{
    var video = apiVideoClient.Videos().create(videoCreationPayload);
}
catch (ApiException e)
{
    // Manage error here
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

{% capture content %}
Replace the link in the example above with your video container (for example, the `.mp4` file of your video). If you don’t have a video URL to test this, you can use [this one](http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4) for example.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Clone an existing video

You can clone a video that already exists in your api.video project. Just paste your video's `videoId` into the `source` field in your request:

{% capture samples %}
```curl
curl --user *your_api_key*: \
--request POST \
--url https://ws.api.video/videos \
--header 'Content-Type: application/json' \
--data '
{
	"title": "My Cloned Video",
	"source": "your_video_id_here"
}
'
```
```javascript
// First install the "@api.video/nodejs-client" npm package
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

const video = await client.videos.create({ 
	title: "My Cloned Video",
	source: "your_video_id_here" 
});
```
```php
// First install the api client: "composer require api-video/php-api-client"
// Documentation: https://github.com/apivideo/api.video-php-client/blob/main/docs/Api/VideosApi.md#create

$client = new \ApiVideo\Client\Client(
    'https://ws.api.video',
    'YOUR_API_KEY',
    new \Symfony\Component\HttpClient\Psr18Client()
);

$video = $client->videos()->create((new \ApiVideo\Client\Model\VideoCreationPayload())
    ->setTitle("My Cloned Video")
		->setSource("your_video_id_here"));
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/LiveStreamsApi.md#create
                  
ApiVideoClient client = new ApiVideoClient("YOUR_API_KEY");

VideoCreationPayload videoCreationPayload = new VideoCreationPayload(); 
videoCreationPayload.setTitle("My Cloned Video"); 
videoCreationPayload.setSource("your_video_id_here");

try {
  Video video = client.videos().create(videoCreationPayload);
} catch (ApiException e) {
	// Manage error here
}
```
```kotlin
// First add the "video.api:android-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#create
                  
val client = ApiVideoClient("YOUR_API_KEY")

val videoCreationPayload = VideoCreationPayload()
videoCreationPayload.title = "My Cloned Video"
videoCreationPayload.source = "your_video_id_here"

try {
  val video = client.videos().create(videoCreationPayload)
} catch (e: ApiException) {
	// Manage error here
}
```
```python
## First install the api client with "pip install api.video"
## Documentation: https://github.com/apivideo/api.video-python-client/blob/main/docs/VideosApi.md#create

from apivideo.api.videos_api import VideosApi
from apivideo.model.video_creation_payload import VideoCreationPayload
from apivideo import AuthenticatedApiClient, ApiException

with AuthenticatedApiClient("YOUR_API_KEY") as api_client:
    video_creation_payload = VideoCreationPayload(
        title="My Cloned Video",
				source="your_video_id_here"
    ) 

    try:
        video = VideosApi(api_client).create(video_creation_payload)
    except ApiException as e:
        # Manage error here
```
```go
// First install the go client with go get github.com/apivideo/api.video-go-client
// Documentation: https://github.com/apivideo/api.video-go-client/blob/main/docs/Videos.md#Create
                  
client := apivideosdk.ClientBuilder("YOUR_API_KEY").Build()

videoCreationPayload := apivideosdk.VideoCreationPayload{}
videoCreationPayload.SetTitle("My Cloned Video") 
videoCreationPayload.SetSource("your_video_id_here")

video, err := client.Videos.Create(videosCreationPayload)

if video != nil {
    // Manage error here
}
```
```swift
// First install the api client: https://github.com/apivideo/api.video-swift-client#getting-started
// Documentation: https://github.com/apivideo/api.video-swift-client/blob/main/docs/VideosAPI.md#create

ApiVideoClient.apiKey = "YOUR_API_KEY"

let videoCreationPayload = VideoCreationPayload(
    title: "My Cloned Video",
		source: "your_video_id_here4"
)

VideosAPI.create(videoCreationPayload: videoCreationPayload) { video, error in
   if let video = video  {
       // Do something with the video
   }
   if let error = error {
      // Manage error here
   }
}
```
```csharp
// First add the "ApiVideo" NuGet package to your project
// Documentation: https://github.com/apivideo/api.video-csharp-client/blob/main/docs/VideosApi.md#create
                  
var apiVideoClient = new ApiVideoClient("YOUR_API_KEY");

var videoCreationPayload = new VideoCreationPayload()
{
    title = "My Cloned Video",
		source = "your_video_id_here"
};

try
{
    var video = apiVideoClient.Videos().create(videoCreationPayload);
}
catch (ApiException e)
{
    // Manage error here
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## API response

```json
202 - Accepted video object creation from source URL or source Video ID (for cloning)

{
  "videoId": "your_video_id_here",
  "assets": {
	  ...
		"player": "https://embed.api.video/vod/{videoId}",
	  ...  
	}
}
```

## Watch and share your video

The easiest way to play your video is to use the [api.video](https://api.video/) player URL that you received in the [API response](#api-response):

`"player": "https://embed.api.video/vod/{videoId}"`

To watch your video, just paste the link into your favorite browser. Use the same link to share your video.

## Manage your video

You can do many things to manage existing videos in your [api.video](https://api.video/) environment:

- [Update a video](/reference/api/Videos#update-a-video-object)
- [List all videos](/reference/api/Videos#list-all-video-objects)
- [Delete a video](/reference/api/Videos#delete-a-video-object)
- [Upload a thumbnail to a video](/reference/api/Videos#upload-a-thumbnail)
- [Pick a thumbnail for a video](/reference/api/Videos#set-a-thumbnail)
- [Retrieve a video](/reference/api/Videos#retrieve-a-video-object)
- [Retrieve a video status](/reference/api/Videos#retrieve-video-status-and-details)
