---
title: "Upload your first video"
slug: "upload-your-first-video"
excerpt: "In this guide, you will learn how to upload your first video to api.video, and play it back."
hidden: false
metadata: 
  title: "Video uploader"
  description: "Upload your videos in minutes with api.video. Blazing fast delivery and low cost hosting."
createdAt: "2022-05-24T08:37:34.962Z"
updatedAt: "2023-05-24T10:34:39.667Z"
---
Upload Your First Video
=======================

## 1. Create an **[api.video](https://api.video/) account**

Before you can start uploading your first video, you need to [create an account](https://dashboard.api.video/register). 

Once you are logged in to dashboard.api.video, select the environment of your choice (sandbox or production), and copy your API key.

## 2. Upload a video

You can choose to upload a video [from your computer](#upload-a-file-from-your-computer), or [from a URL](#upload-a-file-from-a-url).

### Upload a file from your computer

![](/_assets/upload-a-file.png)

#### a. Create the video object

The first step to uploading a video is to create a video object.  
Once you create the object, you can use it to upload a video. Here is the basic minimal code to create the object:

{% capture samples %}
```curl
curl --user *your_api_key*: \
--request POST \
--url https://ws.api.video/videos \
--header 'Content-Type: application/json' \
--data '
{
  "title": "My First Video"
}
'
```
```javascript
// First install the "@api.video/nodejs-client" npm package
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

const video = await client.videos.create({ title: "My First Video" });
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
    ->setTitle("My First Video"));
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/LiveStreamsApi.md#create
                  
ApiVideoClient client = new ApiVideoClient("YOUR_API_KEY");

VideoCreationPayload videoCreationPayload = new VideoCreationPayload(); 
videoCreationPayload.setTitle("My First Video"); 

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
        title="My First Video"
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

video, err := client.Videos.Create(videosCreationPayload)

if video != nil {
    // Manage error here
}
```
```swift
// First install the api client: https://github.com/apivideo/api.video-ios-client#getting-started
// Documentation: https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#create

ApiVideoClient.apiKey = "YOUR_API_KEY"

let videoCreationPayload = VideoCreationPayload(
    title: "My First Video"
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
```c#
// First add the "ApiVideo" NuGet package to your project
// Documentation: https://github.com/apivideo/api.video-csharp-client/blob/main/docs/VideosApi.md#create
                  
var apiVideoClient = new ApiVideoClient("YOUR_API_KEY");

var videoCreationPayload = new VideoCreationPayload()
{
    title = "My First Video"
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
Replace `your_api_key` by the key you have already copied from dashboard.api.video
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

The response to your API request will return the following, along with other params: 

```json
{
  "videoId": "your_video_id_here",
  "assets": {
	  ...
		"player": "https://embed.api.video/vod/{videoId}",
	  ...  
	}
}
```

Remember the `videoId`: you will need it to upload your video, in [step b](#b-upload-the-file-into-the-video-object).  
Also, you will need the `assets.player` to playback your video in [step 3](#3-watch-your-video).

{% capture content %}
If you are using one of our API clients, you will find the above information in the returned response's `Video` object.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

#### b. Upload the file into the video object

In the first step, you created the video object. Now, we enter the second step: you have to upload the video file into the video object. The below API request will do this for you.

{% capture samples %}
```curl
curl --user *your_api_key*:
--url https://ws.api.video/videos/{videoId}/source
--form file=@/path/to/video.mp4
```
```javascript
// First install the "@api.video/nodejs-client" npm package
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#upload

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

// Enter the videoId you want to use to upload your video.
const videoId = 'YOUR_VIDEO_ID'; 

// The path to the video you would like to upload. The path must be local. 
// Instead of a path, you can also use a Readable object, or Buffer.
const file = './my-video.mp4'; 
      
const video = await client.videos.upload(videoId, file);
```
```php
// First install the api client: "composer require api-video/php-api-client"
// Documentation: https://github.com/apivideo/api.video-php-client/blob/main/docs/Api/VideosApi.md#upload---upload-a-video

$client = new \ApiVideo\Client\Client(
    'https://ws.api.video',
    'YOUR_API_KEY',
    new \Symfony\Component\HttpClient\Psr18Client()
);

$client->videos()->upload('YOUR_VIDEO_ID', new SplFileObject(__DIR__ . '/my-video.mp4'));
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/VideosApi.md#upload
                  
ApiVideoClient client = new ApiVideoClient("YOUR_API_KEY");

try {
  Video video = client.videos().upload("YOUR_VIDEO_ID", new File("/path/to/my-video.mp4"));
  System.out.println(video);
} catch (ApiException e) {
	// Manage error here
}
```
```kotlin
// First add the "video.api:android-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-android-client/blob/main/docs/VideosApi.md#upload
                  
val client = ApiVideoClient("YOUR_API_KEY")

try {
  // Notice: you must not call API from the UI/main thread. Dispatch with Thread, Executors or Kotlin coroutines.
  val video = client.videos().upload("YOUR_VIDEO_ID", File("/path/to/my-video.mp4"))
} catch (e: ApiException) {
	// Manage error here
}
```
```python
## First install the api client with "pip install api.video"
## Documentation: https://github.com/apivideo/api.video-python-client/blob/main/docs/VideosApi.md#upload

import apivideo
from apivideo.api import videos_api
from apivideo.model.video import Video

with apivideo.AuthenticatedApiClient("YOUR_API_KEY") as api_client:
		video_id = "YOUR_VIDEO_ID" # Enter the videoId you want to use to upload your video.
    video_file = open("/path/to/my-video.mp4", "rb") # The path to the video you would like to upload. The path must be local. 

    try:
				video = videos_api.VideosApi(api_client).upload(video_id, video_file)
    except ApiException as e:
        # Manage error here
```
```go
// First install the go client with go get github.com/apivideo/api.video-go-client
// Documentation: https://github.com/apivideo/api.video-go-client/blob/main/docs/Videos.md#Upload
                  
client := apivideosdk.ClientBuilder("YOUR_API_KEY").Build()

videoId := "YOUR_VIDEO_ID" 
file, _ := os.Open("./my-video.mp4")
  
video, err := client.Videos.UploadFile(videoId, file)

if err != nil {
    // Manage error here
}
```
```swift
// First install the api client: https://github.com/apivideo/api.video-ios-client#getting-started
// Documentation: https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#upload

ApiVideoClient.apiKey = "YOUR_API_KEY"

VideosAPI.upload(videoId: "YOUR_VIDEO_ID", file: URL(string: "./my-video.mov")) { video, error in
            if let video = video {
                // Manage upload success here
            }
            if let error = error {
                // Manage upload error here
            }
        }
```
```csharp
// First add the "ApiVideo" NuGet package to your project
// Documentation: https://github.com/apivideo/api.video-csharp-client/blob/main/docs/VideosApi.md#Upload
                  
var apiVideoClient = new ApiVideoClient("YOUR_API_KEY");

var videoFile = File.OpenRead("./my-video.mp4");

try
{
	var video = apiVideoClient.Videos().upload("YOUR_VIDEO_ID", videoFile)
}
catch (ApiException e)
{
    // Manage error here
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

API response

```json
{
  "videoId": "your_video_id_here",
  "assets": {
	  ...
		"player": "https://embed.api.video/vod/{videoId}",
	  ...  
	}
}
```

### Upload a file from a URL

#### Create the video object

Uploading a video from a video URL allows you to do the whole video upload process in only 1 step. Just paste your video URL as a source. The request below shows you how to do it.

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
// First install the api client: https://github.com/apivideo/api.video-ios-client#getting-started
// Documentation: https://github.com/apivideo/api.video-ios-client/blob/main/docs/VideosAPI.md#create

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
Replace the link in the example above with your video URL. If you don’t have a video URL to test this, you can use [this one](http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4) for example.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

API response

```json
{
  "videoId": "your_video_id_here",
  "assets": {
	  ...
		"player": "https://embed.api.video/vod/{videoId}",
	  ...  
	}
}
```

## 3. Watch your video

The easiest way to play your video is to use the [api.video](https://api.video/) player URL that you received in [step 2](#2-upload-a-video):

`"player": "https://embed.api.video/vod/{videoId}"`

To watch your video, just paste the link into your favorite browser.

## 4. Manage your video

You can do many things to manage existing videos in your [api.video](https://api.video/) environment, among which:

- [Update a video](/reference/api/Videos#update-a-video-object) (API)
- [List all videos](/reference/api/Videos#list-all-video-objects) (API)
- [Delete a video](/reference/api/Videos#delete-a-video-object) (API)
- [Upload a thumbnail to a video](/reference/api/Videos#upload-a-thumbnail) (API)
- [Pick a thumbnail for a video](/reference/api/Videos#set-a-thumbnail) (API)
- [Retrieve a video](/reference/api/Videos#retrieve-a-video-object) (API)
- [Retrieve a video status](/reference/api/Videos#retrieve-video-status-and-details) (API)
