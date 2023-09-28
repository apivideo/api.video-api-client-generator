---
title: "Regular video upload"
---
Regular video upload
=============================

api.video provides different ways to upload your videos. There are two ways to upload with tokens, and then there are two ways to upload depending on whether your video is over 200MiB or under. This guide walks through how to do regular video upload for a video under 200MiB in size.

Check out the guide on [Progressive upload](/vod/progressive-upload.md) to understand how to upload videos larger than 200MiB in size.

{% capture content %}
Megabyte (MB) and Mebibyte (MiB) are both used to measure units of information on computer storage. 1 MB is 1000Kb (kilobytes), and 1 MiB is 1048.576Kb. api.video uses MiB.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## API documentation

- [Create a video](/reference/api/Videos#create-a-video-object)
- [Upload a video](/reference/api/Videos#upload-a-video)

## Video upload overview

This section gives you an overview of your upload options. This guide walks through regular uploads but describes all the available choices here. 

{% capture content %}
If you want to learn about delegated uploads, which are useful for creating private videos, or allowing your viewers to upload content themselves, or even just making it easier for you to do uploads, check out the [Delegated upload tokens](/vod/delegated-upload-tokens) guide.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

The two token-based upload methods are:

- Regular upload - you use an access token you retrieved when you authenticate to do a two-step process where you create a video object with metadata, retrieve the video ID, and use that to upload your video into the object.
- Delegated upload - you create a token and use the token to upload your video in one step. You can change the metadata later on. 

There are two types of upload depending on whether your video is over 200MiB or under. You can use either a regular or delegated upload for a video under 200MiB. If your video is over 200MiB, you can do a [progressive upload](/vod/progressive-upload.md), where you break a video into chunks and then upload the pieces using parts in the header. You can also use bytes in the header, which is a bit more complicated.  If you use one of the clients, big uploads are handled for you using the bytes method. You can also use the progressive upload method if you wish, though the steps require a bit more effort.

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


## Upload a video file

![](/_assets/upload-a-file.png)

### Create the video object

The first step to uploading a video is to create a video object. Once you create the object, you can use it to upload a video. Here is the basic minimal code to create the object:

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

Remember the `videoId`: you will need it to upload your video, in the [next step](#upload-your-file-into-the-video-object). Also, save the value of `assets.player` for [video playback](#watch-and-share-your-video).

{% capture content %}
If you are using one of our API clients, you will find the above information in the returned response's `Video` object.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

### Upload your file into the video object

In the first step, you created the video object. Next, you need to upload the video file into the video object using this API request:

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

### API response

```json
{
  "videoId": "your_video_id",
  "assets": {
	  ...
		"player": "https://embed.api.video/vod/{videoId}",
	  ...  
	}
}
```

## Watch and share your video

The easiest way to play your video is to use the [api.video](http://api.video) player URL that you received in the [ API response](#api-response):

`"player": "https://embed.api.video/vod/{videoId}"`

To watch your video, just paste the link into your favorite browser. Use the same link to share your video.

## Conclusion

If you're just getting started, this two-part upload process is ideal for you. If you choose to use one of our clients, handling large files is done for you by the client. If you want to try a [progressive upload](/vod/progressive-upload.md), these are best when you're working with huge files and you want to send the parts of them concurrently.

## Resources

Check out api.video's blog content and tutorials about [video uploads](https://api.video/blog/endpoints/video-upload/), [private videos](https://api.video/blog/endpoints/private-videos/), and [delegated uploads](https://api.video/blog/endpoints/delegated-upload/)!