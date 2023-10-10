---
title: "Get Started with VOD in 5 Minutes"
---
Get Started In 5 Minutes
========================

In order to get the concept of building on top of api.video, we've created a get started guide that will show you the concept of building a video uploader and a video listing on the same page. 

You can either follow the video that you see below or you can follow the text guide, which also has examples not only in Node.js but also in Python and Go.

## Get started in 5 minutes with api.video (Next.js + Node.js video)

<iframe src="https://embed.api.video/vod/vi1nvTSmZZmZnk2D7YA8W817" width="100%" height="500px" allowfullscreen></iframe>

## Sign up with api.video

If you haven't done so already, you will need to signup with api.video to get the API key and access to the sandbox account.

Navigate to the [Dashboard](https://dashboard.api.video/register) and register with your Google account, Github, email, or any otheroptions that you prefer.

## Get the API key from the dashboard

All you need now is to get your API key from the dashboard. Navigate to the [API keys page](https://dashboard.api.video/apikeys) and grab your API key from there.

{% capture content %}
**API Key Security**

Make sure to reach our security recommendation to avoid exposing your API key and causing a security breach on your account. You can find the security best practices [here](/reference/README.md#security)
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

## Clone the front-end repo

In order to save you some time, we've prepared a front-end on Next.js that will include all of the buttons and logic. As a fist step, clone this repo from Github:

```shell
$ git clone https://github.com/apivideo/get-started-video-uploader.git
```



After you cloned the Next.js frontend, we need to install the dependencies, navigate to the cloned directory

```shell
$ cd get-started-video-uploader
```



Install the dependencies 

```shell
$ npm install
```



Now we can run it and test it out

```shell
$ npm run dev
```



The server will be running on `localhost:3000` by default

{% capture content %}
**Changing the default port**

You can change the default port of 3000 to something of your liking by editing the `package.json` file and adding the custom port like so:

```
"scripts": {
  "dev": "next -p 3002",
  "build": "next build",
  "start": "next start -p 3002",
},
```
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

When you navigate to `http://localhost:3000` with your browser, you should get this screen:

![Loading the Next.js video uploader frontend](/_assets/select-file-to-upload.png)

If you edit `src/pages/index.tsx` you'll find the whole logic of the frontend. The thing you are looking for is the `serverUrl`. This is where you can point the requests to your server, you'll be able to configure the port or the whole url to your server url (by default it's pointing to `http://localhost:5500`)

```typescript
// src/pages/index.tsx
export default function Home() {
  const serverUrl = 'http://localhost:{port}';
```

## Building out the server

### Understanding the endpoints

On the frontend, we are making a request to 3 main endpoints from the server. If you edit `src/pages/index.tsx` again and  you can find these values

#### Uploading a video
```typescript
// Uploading videos - src/pages/index.tsx
axios.post(`${serverUrl}/upload`, data).then( async res => {
```

#### Check upload status

```typescript
// Getting video status - src/pages/index.tsx
const checkUploadStatus = async (videoId: string) => {
    return axios.post(`${serverUrl}/uploadStatus`, {videoId: videoId}).then( res => {
```



#### List all videos on the workspace

```typescript
// Listing Videos - src/pages/index.tsx
const ListVideoPage = (page: number) => {
    axios.post(`${serverUrl}/videos`, {page: page}).then(res => {
```



#### Server Hello World

{% capture content %}
**Building the backend with the language of your choice**

This tutorial is designed to get you started with our client libraries, you can choose which client library you want to work with. For each example, please make sure to choose the language of your choice in the code snippet pane.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

Now we need to build a server that will accept the requests from our frontend. Let's build a small application sever that if taking requests on port 5500

{% capture samples %}
```javascript
$ mkdir apivideo-node-server
$ cd apivideo-node-server
$ npm init
$ touch index.js
```
```Python
$ mkdir apivideo-python-server
$ cd apivideo-python-server
$ touch app.py
```
```go
$ mkdir apivideo-go-server
$ cd apivideo-go-server
$ touch app.go
$ go mod init apivideo-go-server
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

{% capture samples %}
```javascript
const express = require('express')
const app = express()
const port = 5500

app.post('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
```
```python
from flask import Flask

app = Flask(__name__)

@app.route('/')
def index():
    return 'Web App with Python Flask!'

app.run(host='0.0.0.0', port=5500)
```
```go
package main

import (
    "fmt"
    "net/http"
)

func main() {
    router := http.NewServeMux()
  	router.HandleFunc("/", HelloServer)
    http.ListenAndServe(":5500", nil)
}

func HelloServer(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintf(w, "Hello, %s!", r.URL.Path[1:])
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}



Now we have a server that takes post requests on port `5500`.

The next step is to utilize the client library that api.video is offering in order to handle the requests that the front end is giving us.

### Installing dependencies

For the server, we will need to install all the dependent libraries including the api.video client library. Run the following command in the server app folder.

{% capture samples %}
```javascript
//express server
$ npm install express --save
// the api.video client library
$ npm install @api.video/nodejs-client --save
// cors needed to make cross domain requests
$ npm install cors --save
// multer will read the file buffer from the request
$ npm install multer --save
// the body-preser function is to parse the incoming form request body
$ npm install body-parser --save
```
```python
## flask server
$ pip3 install flask
## the api.video client library
$ pip3 install api.video
## cors needed to make cross domain requests
$ pip3 install flask-cors
```
```go
$ go get github.com/apivideo/api.video-go-client
$ go get github.com/rs/cors
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


### Adding the api.video client library

Let's add the api.video client library to our server code. You can find all the available client libraries [here](/sdks/README.md) 

{% capture samples %}
```javascript
const express = require('express');
const ApiVideoClient = require('@api.video/nodejs-client')
const app = express()
const port = 5500
```
```python
from flask import Flask
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

app = Flask(__name__)

@app.route('/')
def index():
    return 'Web App with Python Flask!'

app.run(host='0.0.0.0', port=5500)
```
```go
package main

import (
  // add the api.video library 
	apivideosdk "github.com/apivideo/api.video-go-client"
  "net/http"
	"fmt"
)

func main() {
	router := http.NewServeMux()
  router.HandleFunc("/", HelloServer)
	handler := cors.Default().Handler(router)
  http.ListenAndServe(":5500", handler)
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


### api.video client initialization

The client library takes your api key, which you can [find here](https://dashboard.api.video/apikeys). Let's pass it the api key and initialize the client. 

Learn more about [Basic Authentication](/reference/basic-authentication) and [Bearer Token Authentication](/reference/disposable-bearer-token-authentication). 

{% capture samples %}
```javascript
const apivideoClient = new ApiVideoClient({ apiKey: "replace with your api key" });
```
```python
## setup your api key
api_key = "Your api key"
app = Flask(__name__)

## initialize the client
apivideo_client = apivideo.AuthenticatedApiClient(api_key)
## connect the client
apivideo_client.connect()
## initiliaze the Videos API endpoint
videos_api = VideosApi(apivideo_client)
```
```go Go
// create a function that will initialize the client
func InitApiVideoClient() (*apivideosdk.Client) {
  // initialize the client
	client := apivideosdk.ClientBuilder("Your api key").Build()
  // return the client object
	return client
}
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


### File handler & CORS

We will add a file handler library that will intake the file stream from the request form. If you are using localhost it's a good idea to add a CORS

{% capture samples %}
```javascript
const express = require('express');
const ApiVideoClient = require('@api.video/nodejs-client')
const app = express()
const multer = require('multer');
const bodyParser = require('body-parser');
const cors = require('cors')
const port = 5500

let upload = multer({ dest: 'upload' })
app.use(cors())
app.use(bodyParser.json());
```
```python
import apivideo
## import request and jsonify from the flask lib
from flask import Flask, request, jsonify
## import CORS
from flask_cors import CORS
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "Your api key"
app = Flask(__name__)
CORS(app)
```
```go
package main

import (
	apivideosdk "github.com/apivideo/api.video-go-client"
  "net/http"
  // add json encoder
	"encoding/json"
  // add cor dependancy
	"github.com/rs/cors"
	"fmt"
)

func main() {
	router := http.NewServeMux()
  router.HandleFunc("/", HelloServer)
  // add cors handler
	handler := cors.Default().Handler(router)
  http.ListenAndServe(":5500", handler)
}

func InitApiVideoClient() (*apivideosdk.Client) {
	client := apivideosdk.ClientBuilder("Your api key").Build()
	return client
}

```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


## api.video Uploader

### [Creating the video object](/reference/api/Videos#create-a-video-object) & [uploading the file](/reference/api/Videos#upload-a-video)

In this step, we will need to create a handler for the incoming POST request from the front end. Let's change the endpoint to `/upload` and add the logic of video object creation. You can find the visual flowchart of video object creation and upload [here](/vod/video-best-practices#how-to-create--upload-a-video) if you want to understand it a bit better.

In this instance we are referring to the Videos endpoint. First we create a video object. You can find more about the endpoint [here](/reference/api/Videos#create-a-video-object) 

{% capture samples %}
```javascript
app.post('/upload', upload.single('file'), async (req, res, next) => {
  	// Grab the file from the request
    const file = req.file;
    try {
      	// the video title and description
        const videoCreationPayload = {
            title: "My Video Test in Node",
            Description: "Something I wanted to share",
        }
        // create a video object first
        const video = await apivideoClient.videos.create(videoCreationPayload);
    } catch (error) {
        console.log(error)
    }
    if (!file) {
      const error = new Error('No File')
      error.httpStatusCode = 400
      return next(error)
    }
```
```python
## create a new route for the /upload endpoint
@app.route('/upload', methods=['POST'])
def video_upload():
   # define the video object payload
    video_object_payload = {
        "title": "Demo video",
        "description": "Something I wanted to share",
    }
    # grab the file from the frontend request
    file = request.files['file']
    file_name = file.filename
    # save the file locally 
    file.save('./temp/' + file_name)
    saved_file = open('./temp/' + file_name, "rb")
    # create the video object with api.video
    video_object = videos_api.create(video_object_payload)
    video_id = video_object["video_id"]
  	print(video_id)
```
```go
// Video object creation function
func CreateVideoObject() (*apivideosdk.Video) {
  // init the api.video client
	apiVideoClient := InitApiVideoClient()
  // created the payload with the video object name
	videoCreationPayload := *apivideosdk.NewVideoCreationPayload("My test video")
  // create the video object and get the response payload
	videoCreated, err := apiVideoClient.Videos.Create(videoCreationPayload)
	if err != nil {
		panic(err)
	}
	// return the video object payload
	return videoCreated
}

func UploadVideoToapivideo(videoId *apivideosdk.Video, videoFile *os.File) map[string]string {
  
	apiVideoClient := InitApiVideoClient()
	videoIdString := videoId.VideoId
	uploadRes, err := apiVideoClient.Videos.UploadFile(videoIdString, videoFile)
	if err != nil {
		panic(err)
	}
	var resVideoId = map[string]string{"videoId": uploadRes.VideoId}
	fmt.Println(resVideoId)
	return resVideoId
} 
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


Next up, after we created the video object, we can now upload the actual video file. So add these lines after the video creation. 

{% capture samples %}
```javascript
// create a video object first      	
const video = await apivideoClient.videos.create(videoCreationPayload);
// upload the video
const uploadVideoRes = await apivideoClient.videos.upload(video.videoId, file.path)
// Add the response to the frontend request
res.json(uploadVideoRes.videoId);
```
```python
## create the video object with api.video
 video_object = videos_api.create(video_object_payload)
 # grab the video id from the response
 video_id = video_object["video_id"]
 # upload the video file
 upload_video_result = videos_api.upload(video_id, saved_file)
 uploaded_video_id = upload_video_result['video_id']
 # return the video id to the frontend
 return jsonify(uploaded_video_id)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


In this step we are leveraging the upload a video endpoint, this endpoint enables you not only to [upload a video](/reference/api/Videos#upload-a-video), but also to upload a video from a [url source](/vod/upload-from-source), or do a [progressive video upload](/vod/progressive-upload).

### [Upload status](/reference/api/Videos#retrieve-video-status-and-details)

Let's add the upload status, so we will be able to indicate to the user that the video has been uploaded, or the upload is in progress. Add another POST request handler function that will intake the video id and return the video status

{% capture samples %}
```javascript
app.post('/uploadStatus', async (req, res) => {
  	// Get the video id from the request 
    const videoId = req.body.videoId
    // Get the video status from api.video by video id
    const videoStatus = await apivideoClient.videos.getStatus(videoId)
    res.json(videoStatus)
})
```
```python
## create a new route that is called /uploadStatus
@app.route('/uploadStatus', methods=['POST'])
def video_upload_status():
  	# grab the request data from the frontend
    request_data = request.json
    # get the video id from the request and pass it to the status endpoint
    video_upload_status_raw_response = videos_api.get_status(request_data.get('videoId'))
    # jsonify the returned status
    video_upload_status_jsonified = json.dumps(video_upload_status_raw_response.to_dict(), indent=4, sort_keys=True, default=str)
    # return the jsonified status payload to the frontend
    return video_upload_status_jsonified
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}


We've leveraged the `/status` endpoint in this step, you can find more information about the endpoint [here](/reference/api/Videos#retrieve-video-status-and-details)

### [Return the video list to the workspace](/reference/api/Videos#list-all-video-objects)

The last thing we need to get the list of videos and assets. Just add another request handler that will return a list of 10 videos per page from the workspace

{% capture samples %}
```javascript
app.post('/videos', async (req, res) => {
    const page = req.body.page
    console.log(page)
    const listOfVideos = await apivideoClient.videos.list({currentPage: page, pageSize: 10});
    res.json(listOfVideos)
})
```
```python
## create a new route /videos
@app.route('/videos', methods=['POST'])
def get_video_list():
  	# grab the page value from the frontend request
    page = request.json.get('page')
    # get the list of video from api.video
    video_list_raw_response = videos_api.list(current_page=page, page_size=10)
    # jsonify the response list
    json_video_list = json.dumps(video_list_raw_response.to_dict(), indent=4, sort_keys=True, default=str)
    # return the list to the frontend
    return json_video_list
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

This steps utilizes the List all videos endpoint. You can also use different filters to retrieve video lists, you can find more information about the endpoint [here](/reference/api/Videos#list-all-video-objects)

## Running the server

As we already have the React frontend running on localhost:3000, we will need to run the server on a different port. If you followed the instructions, we've set the server port to 5500 by default and the code you've copied is running the server on port 5500. The only thing left is to run our server. Go to the server app folder and run it

{% capture samples %}
```javascript
node index.js
```
```python
python3 app.py
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}
