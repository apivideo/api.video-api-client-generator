---
title: Download & disable download with api.video
meta:
  description: With api.video you can control whether your users can download videos.
---

# Video Download

After videos have been transcoded on [api.video](http://api.video), it’s natural that you want to give your users the ability to download their videos, or just download the video for your own use, possibly for backup.

[api.video](http://api.video) allows you and your users to download videos directly from the player or programmatically through the API.

{% include "_partials/dark-light-image.md" dark: "/_assets/vod/video-download/video-download-diagram-dark.svg", light: "/_assets/vod/video-download/video-download-diagram-light.svg", alt: "A diagram that shows the process of video download" %}

## Download a video from [api.video](http://api.video) player

The video object can be created or updated with the `mp4Support` tag. The tag will make the video downloadable and display a button on the [api.video](http://api.video) player.

![](/_assets/vod/video-download/download-video-1.png)
![](/_assets/vod/video-download/download-video-2.png)

{% capture content %}
* By default, the `mp4Support` tag is set to `true`, hence the video is downloadable. If you want to disable the download ability from the [api.video](http://api.video) player, you have to set the `mp4Support` tag to `false`
* If you don’t want the user to be able to download the video, do not serve the mp4 asset.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Disabling download

You can use one of the API clients offered by [api.video](http://api.video). You can find the list of clients in the [api.video API Client catalog](https://docs.api.video/sdks/api-clients).

You can also find more information on the `/videos` endpoints on the [API reference page](https://docs.api.video/reference/api/Videos#create-a-video-object).

### Disable download for a video object

How to create a video object with disabled download

{% capture samples %}
```javascript
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#create

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

// create a video using all available attributes
const video = await client.videos.create({
  title: "My video", // The title of your new video.
  m4Support: false
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
		->setMp4Support(False)
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
        mp4Support=False,
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
		videoCreationPayload := apivideosdk.VideoCreationPayload{}
		videoCreationPayload.SetTitle("my title")
		videoCreationPayload.SetMp4Support(false)
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

    VideoCreationPayload videoCreationPayload = new VideoCreationPayload(); // video to create
    videoCreationPayload.setTitle("Maths video"); // The title of your new video.
    videoCreationPayload.setMp4Support(false); // video clip
   
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
```go
videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" 
// string | Enter the videoId you want to use to upload your video.
file := os.NewFile(1234, "some_file") 
// *os.File | The path to the video you would like to upload. The path must be local. If you want to use a video from an online source, you must use the "/videos" endpoint and add the "source" parameter when you create a new video.
res, err := client.Videos.UploadFile(videoId, file)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

### Disable download for an existing video object

It’s also possible to disable the download ability for the users, after the video was already uploaded and transcoded. For that, you just need to update the video object.

{% capture samples %}
```javascript
/ First install the "@api.video/nodejs-client" npm package
// Documentation: https://github.com/apivideo/api.video-nodejs-client/blob/main/doc/api/VideosApi.md#update

const client = new ApiVideoClient({ apiKey: "YOUR_API_KEY" });

const videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // The video ID for the video you want to update.

// define the value you want to update
const videoUpdatePayload = {
    mp4Support: false, // Whether the player supports the mp4 format.
}; 
const updatedVideo = await client.videos.update(videoId, videoUpdatePayload);
```

```go
// First install the go client with "go get github.com/apivideo/api.video-go-client"
// Documentation: https://github.com/apivideo/api.video-go-client/blob/main/docs/VideosApi.md#update

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
        
    videoId := "vi4k0jvEUuaTdRAEjQ4Jfrgz" // string | The video ID for the video you want to delete.
    videoUpdatePayload := *apivideosdk.VideoUpdatePayload{} // VideoUpdatePayload | 
		videoUpdatePayload.SetMp4Support(false)
    
    res, err := client.Videos.Update(videoId, videoUpdatePayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.Update``: %v\
", err)
    }
    // response from `Update`: Video
    fmt.Fprintf(os.Stdout, "Response from `Videos.Update`: %v\
", res)
}
```
```python
# First install the api client with "pip install api.video"
# Documentation: https://github.com/apivideo/api.video-python-client/blob/main/docs/VideosApi.md#update

import apivideo
from apivideo.api import videos_api
from apivideo.model.video_update_payload import VideoUpdatePayload
from apivideo.model.bad_request import BadRequest
from apivideo.model.not_found import NotFound
from apivideo.model.video import Video
from pprint import pprint

# Enter a context with an instance of the API client
with apivideo.AuthenticatedApiClient(__API_KEY__) as api_client:
    # Create an instance of the API class
    api_instance = videos_api.VideosApi(api_client)
    video_id = "vi4k0jvEUuaTdRAEjQ4Jfrgz" # str | The video ID for the video you want to delete.
    video_update_payload = VideoUpdatePayload(
        mp4_support=False,
    ) # VideoUpdatePayload | 

    # example passing only required values which don't have defaults set
    try:
        # Update a video
        api_response = api_instance.update(video_id, video_update_payload)
        pprint(api_response)
    except apivideo.ApiException as e:
        print("Exception when calling VideosApi->update: %s\
" % e)
```
```java
// First add the "video.api:java-api-client" maven dependency to your project
// Documentation: https://github.com/apivideo/api.video-java-client/blob/main/docs/VideosApi.md#update

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
    
    String videoId = "vi4k0jvEUuaTdRAEjQ4Jfrgz"; // The video ID for the video you want to delete.
    VideoUpdatePayload videoUpdatePayload = new VideoUpdatePayload(); // 
    videoUpdatePayload.setMp4Support(false); // Whether the player supports the mp4 format.
  
    try {
      Video result = apiInstance.update(videoId, videoUpdatePayload);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling VideosApi#update");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getMessage());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```
```php
<?php
// First install the api client: "composer require api-video/php-api-client"
// Documentation: https://github.com/apivideo/api.video-php-client/blob/main/docs/Api/VideosApi.md#update

require __DIR__ . '/vendor/autoload.php';

$client = new \ApiVideo\Client\Client(
    'https://ws.api.video',
    'YOUR_API_KEY',
    new \Symfony\Component\HttpClient\Psr18Client()
);

$videoId = 'vi4k0jvEUuaTdRAEjQ4Jfrgz'; // The video ID for the video you want to update.

$client->videos()->update($videoId, (new \ApiVideo\Client\Model\VideoUpdatePayload())->setMp4Support(false) // Whether the player supports the mp4 format.
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Creating a custom download button

Most players support download, however, if you would like to add a download button to a custom player that doesn’t have a download button, it’s also possible.

By leveraging the API, you can create a request to [api.video](http://api.video), which will get the video id and then render a download button serving the mp4 asset.

In this example, you will learn how to create a custom download button with React and Node.js backend

### Prerequisites

1. **[api.video](https://api.video/) API key**, you can find the information on how to retrieve the **[API key in the Retrieve your api.video API key guide](https://docs.api.video/reference/authentication-guide#retrieve-your-apivideo-api-key)**
2. Make sure that you have [Node.js, npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) and React installed
3. Create a [React project](https://react.dev/learn/start-a-new-react-project)

### React Button Component

After you have the React project ready, navigate to the project root folder:

```bash
$ cd my-react-project
```

Install the `file-saver` module dependency

```bash
$ npm install --save file-saver
```

We will need to add a new component to React, that will make a request and get the video mp4 asset url. For your convenience, we’ve created the component for you. All you have to do is add the component below.

```javascript
import { saveAs } from 'file-saver';

// here you specify how you want to call the downloaded file
const videoFileName = "source.mp4";
const serverHostname = "localhost:5500"

// on the click handle, get the mp4 asset of the video
const handleClick = async (sourceVideoId) => {
  try {
		// replace the server hostname with your 
    const response = await fetch(`http://${serverHostname}/download?videoId=${sourceVideoId}`, {
      method: 'GET',
    });
    if (!response.ok) {
      throw new Error(`Error! status: ${response.status}`);
    }
    const result = await response.json();
    if(result.videoUrl) {
      await fetchVideoUrl(result.videoUrl);
    }
  } catch (err) {
    console.error(`error: ${err}`);
  }
};

// download the mp4 asset using file-saver library
const fetchVideoUrl = async (mp4Uri) => {
  try {
    const response = await fetch(mp4Uri, {
      method: "GET",
      header: {
        "Content-Type": "video/mp4",
      }
    })
    if (!response.ok) {
      throw new Error(`Error while fetching file`);
    }

  const blob = await response.blob()
  // here we specify the name of the  
  saveAs(blob, videoFileName);
  } catch (error) {
    console.error(`Unable to download video. ${error}`);
  }
}

const DownloadVideo = ({videoId}) => {
  return (
    <div>
      <header>
      <div>
      <button onClick={() => handleClick(videoId)}>Download Video</button>
      </div>
      </header>
    </div>
  );
}
export default DownloadVideo;
```

As a best practice, you need to add the component in the `Component` folder (inside `src`).

Now let’s add the component to our `App.js`.  

You will notice that you pass a `videoId` as a prop, for this example, just grab any video id from your dashboard or from the API.

```javascript
const App = () => {
  return (
    <DownloadVideo videoId="the_video_id_you_would_like_to_download"></DownloadVideo>
  );
}
```

### Serving the url from the backend server

In order to make sure that your request is not made from the frontend which might expose your API key, we need to create a server that will make the actual API request.

In the example, we are using Node.js with express server. Let’s create a Node.js server first

```bash
$ mkdir apivideo-server
```

Then navigate to the directory

```bash
$ cd apivideo-server
```

[Initialise npm](https://docs.npmjs.com/cli/v10/commands/npm-init) in that directory 

```bash
$ npm init
```

Now edit the index.js file that you’ve created, and copy this code. 

{% capture content %}
Make sure that you copy your API key from the [api.video](http://api.video) dashboard and replace the port to the port you would like to run the server on.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}


```javascript
const express = require('express');
// cors can be commented out in prod
const cors = require('cors')
const ApiVideoClient = require('@api.video/nodejs-client')
const app = express()
// change the port to whatever port you would like to run the server on
const port = 5500

app.use(cors())

const apivideoClient = new ApiVideoClient({ apiKey: "you_api_key" });

// get the video id from the request
const getVideoIdFromReq = (req) => {
  if (req && req.query && req.query.videoId) {
    return req.query.videoId
  } else {
    throw new Error(`Unable to find video id in the request`)
  }
}

// get the video mp4 asset url
const getVideoUrlAsset = async (videoId) => {
  if(videoId) {
    try {
      console.log(videoId)
      const videoMp4Asset = await apivideoClient.videos.get(videoId);
      if(videoMp4Asset && videoMp4Asset.assets && videoMp4Asset.assets.mp4) {
        return videoMp4Asset.assets.mp4;
      } else {
        throw new Error(`Mp4 asset not found`);
      }
    } catch (error) {
      console.error(`Unable to retrieve video. ${error}`)
    }
  }
}

app.get('/download', async (req,res) => {
   const videoId = getVideoIdFromReq(req);
   const mp4VideoAssetUrl = await getVideoUrlAsset(videoId);
   res.json({videoUrl: mp4VideoAssetUrl});
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
```

The next step is to install the modules. Make sure that you are in the server directory

```bash
$ npm install express
$ npm install @api.video/nodejs-client
$ npm install cors
```

Run the server

```bash
$ node index.js
```

### Starting up React

Depending on the port and hostname that you’ve set, replace the port in the React component (DownloadVideo) with the port that you’ve set for the server:

```javascript
const serverHostname = "localhost:5500"
```

Start the React frontend from the React project directory

```bash
$ npm start
```

The result that you should see is a button that will download the `source.mp4` of the video once the button is clicked.