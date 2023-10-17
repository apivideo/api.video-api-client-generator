---
title: "clip and trim videos with api.video"
slug: "clip-a-video"
meta:
  description: Clip and trim a video while uploading to api.video
---

# Video clipping & Trimming

In some cases, you just need to upload a smaller piece of a video, but you would like to do that programmatically, without resorting to editing programs.

[api.video](http://api.video) allows you to clip a video to upload only specific parts of the video. You will be able to specify the start and end time of the clip before you upload the source video to api.video.

{% include "_partials/dark-light-image.md" dark: "/_assets/vod/video-clip/video-clipping-dark.svg", light: "/_assets/vod/video-clip/video-clipping-light.svg", alt: "A diagram that shows the process of creating and applying a watermark to a video object" %}


## How does video clipping work?

The videos are clipped by the definition of the object. When you create a video object, you can pass the `clip` parameter, which will take the start and end time of the clip. Once the object is created with the clip time, you can upload the video to the video object.

## Usage

You can use one of the API clients offered by [api.video](http://api.video). You can find the list of clients in the [api.video API Client catalog](https://docs.api.video/sdks/api-clients).

You can also find more information on the `/videos` endpoints on the [API reference page](https://docs.api.video/reference/api/Videos#create-a-video-object).

For this example, let’s assume that we have a video file that is `00:10:00` in length and we would like to get only 5 seconds of the clip between the 20th to the 22nd second of the video.

### Clipping and trimming videos from local file or URL source

Once you’ve determined what video you would like to clip and trim, you need to create a [video object](https://docs.api.video/vod/video-object) with the `clip` parameter.

{% capture samples %}
```javascript
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

// create a video using all available attributes
const video = await client.videos.create({
  title: "My video", // The title of your new video.
  clip: { "startTimecode" : "00:00:20", "endTimecode": "00:00:22" } // the clip start and end time
});
```
```php
<?php
// First install the api client: "composer require api-video/php-api-client"
// Documentation: https://github.com/apivideo/api.video-php-client/blob/main/docs/Api/VideosApi.md#create

require __DIR__ . '/vendor/autoload.php';

$client = new \ApiVideo\Client\Client(
    'https://ws.api.video',
    'YOUR_API_KEY',
    new \Symfony\Component\HttpClient\Psr18Client()
);

// create a private video
$video = $client->videos()->create((new \ApiVideo\Client\Model\VideoCreationPayload())
    ->setTitle("Maths video")
		->setClip(new \ApiVideo\Client\Model\VideoClip('startTimecode' => '00:00:20', 'endTimecode' => '00:00:22' ));
```
```python
# First install the api client with "pip install api.video"
# Documentation: https://github.com/apivideo/api.video-python-client/blob/main/docs/VideosApi.md#create

import apivideo
from apivideo.api import videos_api
from apivideo.model.video_creation_payload import VideoCreationPayload
from apivideo.model.bad_request import BadRequest
from apivideo.model.video import Video
from pprint import pprint

# Enter a context with an instance of the API client
with apivideo.AuthenticatedApiClient(__API_KEY__) as api_client:
    # Create an instance of the API class
    api_instance = videos_api.VideosApi(api_client)
    video_creation_payload = VideoCreationPayload(
        title="Maths video",
        "clip": {
	        "start_timecode": "00:00:20",
	        "end_timecode": "00:00:22"
			  }
    )
	# example passing only required values which don't have defaults set
 try:
    # Create a video
    api_response = api_instance.create(video_creation_payload)
    pprint(api_response)
    except apivideo.ApiException as e:
      print("Exception when calling VideosApi->create: %s\n" % e)
```
```go
/ First install the go client with "go get github.com/apivideo/api.video-go-client"
// Documentation: https://github.com/apivideo/api.video-go-client/blob/main/docs/VideosApi.md#create

package main

import (
    "context"
    "fmt"
    "os"
    apivideosdk "github.com/apivideo/api.video-go-client"
)

func main() {
    client := apivideosdk.ClientBuilder("YOUR_API_KEY").Build()
    // if you rather like to use the sandbox environment:
    // client := apivideosdk.SandboxClientBuilder("YOUR_SANDBOX_API_KEY").Build()
    video clip := apivideosdk.VideoClip{}
		videoClip.SetStartTimecode("00:00:20")
		videoClip.SetEndTimecode("00:00:22")
		videoCreationPayload := apivideosdk.VideoCreationPayload{}
		videoCreationPayload.SetTitle("my title")
		videoCreationPayload.SetClip(videoClip)
    res, err := client.Videos.Create(videoCreationPayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.Create``: %v\
", err)
    }
    // response from `Create`: Video
    fmt.Fprintf(os.Stdout, "Response from `Videos.Create`: %v\
", res)
}
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/VideosApi.md#create

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.models.*;
import video.api.client.api.clients.VideosApi;
import java.util.*;

public class Example {
  public static void main(String[] args) {
    ApiVideoClient client = new ApiVideoClient("YOUR_API_KEY");
    // if you rather like to use the sandbox environment:
    // ApiVideoClient client = new ApiVideoClient("YOUR_SANDBOX_API_KEY", ApiVideoClient.Environment.SANDBOX);

    VideosApi apiInstance = client.videos();

		VideoClip clip = new VideoClip()
    
		clip.setStartTimecode("00:00:20")
		clip.setStartTimecode("00:00:22")

    VideoCreationPayload videoCreationPayload = new VideoCreationPayload(); // video to create
    videoCreationPayload.setTitle("Maths video"); // The title of your new video.
    videoCreationPayload.setClip(clip); // video clip
   
    try {
      Video result = apiInstance.create(videoCreationPayload);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VideosApi#create");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getMessage());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

Once the object is created, all you have to do is just upload the video to the object:

{% capture samples %}

```javascript
const file = './my-video.mp4'; // The path to the video you would like to upload. The path must be local. If you want to use a video from an online source, you must use the "/videos" endpoint and add the "source" parameter when you create a new video.
const videoId =  video.id    
const upload = await client.videos.upload(videoId, file)
```
```php
$client->videos()->upload($myVideo->getVideoId(), new SplFileObject(__DIR__ . '/558k.mp4'));
```
```python
video_id = api_response.id # str | Enter the videoId you want to use to upload your video.
file = open('/path/to/file', 'rb') # file_type | The path to the video you would like to upload. The path must be local. If you want to use a video from an online source, you must use the "/videos" endpoint and add the "source" parameter when you create a new video.

# example passing only required values which don't have defaults set
try:
   # Upload a video
	 api_response = api_instance.upload(video_id, file)
   pprint(api_response)
   except apivideo.ApiException as e:
     print("Exception when calling VideosApi->upload: %s\n" % e)
```
```go
videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" 
// string | Enter the videoId you want to use to upload your video.
file := os.NewFile(1234, "some_file") 
// *os.File | The path to the video you would like to upload. The path must be local. If you want to use a video from an online source, you must use the "/videos" endpoint and add the "source" parameter when you create a new video.
res, err := client.Videos.UploadFile(videoId, file)
```
```java
String videoId = Video.id // Enter the videoId you want to use to upload your video.
File file = new File("/path/to/file"); // The path to the video you would like to upload. The path must be local. If you want to use a video from an online source, you must use the "/videos" endpoint and add the "source" parameter when you create a new video.

    try {
      Video result = apiInstance.upload(videoId, file);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VideosApi#upload");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getMessage());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

Once the video is uploaded and trancoded it will only include the segment of the clip that you have configured.