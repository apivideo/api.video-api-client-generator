---
title: "Watermarks"
---

Add A Permanent Watermark
=========================

api.video gives you the ability to add permanent watermarks to your videos.

Watermarks provide benefits in various contexts. Watermarks can protect your intellectual property, prevent fraud, and promote branding. Here are some of the key benefits of using watermarks:

- Deter unauthorized use
- Prevent copyright infringement
- Brand promotion
- Establish authenticity

Overall, watermarks can be an effective way to protect intellectual property, establish authenticity, and promote branding, among other benefits.

## How watermarks work

Watermarks are uploaded separately from videos. You will need to upload the watermarks you want to utilize beforehand, after the watermark is uploaded it is attached to a video object, and eventually as soon as the video is uploaded to the object, the watermark is embedded in the video

{% capture content %}
- You can only add watermarks when creating a new video object.
- You cannot delete or edit watermarks after you add them to a video.
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}


{% include "_partials/dark-light-image.md" dark: "/_assets/vod/add-watermarks/watermark-dark.svg", light: "/_assets/vod/add-watermarks/watermark-light.svg", alt: "A diagram that shows the process of creating and applying a watermark to a video object" %}

## Supported Image formats

You can upload watermarks either in `jpeg` or `png` format, however it is highly recommended to use `png` due to the fact that `png` supports alpha channel.

| Format | Supported         |
| :----- | :---------------- |
| `png`  | Yes (Recommended) |
| `jpeg` | Yes               |

## Create an account

Before you can start uploading your first video, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

## Installation & Usage

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [iOS](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)

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

### Upload a watermark

First step, would be to upload a watermark. It is recommended to use images that are in `jpeg` or `png` format. Once the watermark has been uploaded, you can proceed in embedding it in a video.

When the upload is complete, the response from the watermark endpoint will be the watermark id. You can either store it on your end or you can consume it from the list of watermarks.

{% capture content %}
api.video will only store the watermark id and the time and date it was uploaded. If you would like to reference the watermark to a specific image name, you would have to do that on your end.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

More information can be found on the [API reference page](/reference/api/Watermarks)

{% capture samples %}
```curl
curl --request POST \
     --url https://ws.api.video/watermarks \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MDUyNzEuOTEyODMsIm5iZiI6MTY0MjgwNTI3MS45MTI4MywiZXhwIjoxNjQyODA4ODcxLjkxMjgzLCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jTiB29R_sg5dqCDBU8wrnz7GRJsCzfVeLVTX-XSctS024B9OmGsuY139s2ua1HzrT63sqkBB1QshrjZbkDLVxSrs0-gt-FaM2bgvCC0lqK1HzEUL4vN2OqPPuM8R2pruj0UdGVaifGqmyfehKcHxuNr0ijGmGIMwSXkabECbXCxm7LraRCgmlobHepuXcUPeUKzKxN5LwPSO1onD684S0FtUUYbVMq9Ik7V8UznbpOjmFaknIZowKKlCkTmgKcyLSq7IaPJd7UuDJVXJDiC49oImEInrjx1xuFbyoBz_wkZlwcgk9GjksTeSz4xzBLcyzVgCwGP2hs8_BtdslXXOrA' \
     --header 'Content-Type: multipart/form-data' \
     --form file=@pic.jpg
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
        
    file := os.NewFile(1234, "some_file") // *os.File | The .jpg or .png image to be added as a watermark.

    
    res, err := client.Watermarks.UploadFile(file)

    // you can also use a Reader instead of a File:
    // client.Watermarks.Upload(fileName, fileReader)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Watermarks.Upload``: %v\n", err)
    }
    // response from `Upload`: Watermark
    fmt.Fprintf(os.Stdout, "Response from `Watermarks.Upload`: %v\n", res)
}
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const file = 'BINARY_DATA_HERE'; // The .jpg or .png image to be added as a watermark.

        // Watermark
        const result = await client.watermarks.upload(file);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
import apivideo
from apivideo.apis import WatermarksApi
from apivideo.apis import VideosApi
from apivideo.model.video import Video
from apivideo.model.bad_request import BadRequest
from pprint import pprint

client = apivideo.AuthenticatedApiClient("your API key here")

client.connect()

watermarks_api = WatermarksApi(client)

file = open('your_watermark_here.png', 'rb') # file_type | The .jpg or .png image to be added as a watermark.

api_response = watermarks_api.upload(file)        
pprint(api_response)
pprint(api_response['watermark_id'])

watermark_id = api_response['watermark_id']

videos_api = VideosApi(client)

video_creation_payload = {
    "title":"Sample video",
    "description": "Show a watermarked video",
    "watermark": {
        "id": watermark_id,
        "top":"20px",
        "left":"20px",
        "opacity":"70%",
    },
}
api_response = videos_api.create(video_creation_payload)
video_id = api_response["video_id"]

file = open("video_to_watermark.mp4", "rb")

video_response = videos_api.upload(video_id, file)
print("Uploaded Video", video_response)
```

### List all watermarks

You can list all of the watermarks that you have uploaded, while also sorting by date or id.

```curl
curl --request GET \
     --url https://ws.api.video/watermarks \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MDk2MzIuNTQ2MDg5LCJuYmYiOjE2NDI4MDk2MzIuNTQ2MDg5LCJleHAiOjE2NDI4MTMyMzIuNTQ2MDg5LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.J0cMjE7DNwTGvu3cprGpg5R5IHVDHip80ruWaCN57e9jDoJ4FK51Zkk2ynxhb1SqvutYi-hpnmOzqbPUFPjspLjTVbn0sUskKvGRst2gO2vNllHtIzNTlKKLMA_Sa_zi7hK5_XfuNod-B0SqeH106oAqe1FWkZjc7PMuqOzKS3dIqih0PczsbBQWWgQ3Fh-LtHxVVmdY_egq9i-t1fD5JCD812CHAK1HsxpAkpcAmpJptomORbq72tmEmgFfFUEJ8lalROM2_7ZDLvCdHxDYpA6_j5Z6k1y6Z_OjOsLg_mmiFymAT-DpUib2oOQyj2efIMo-OvdfNJhQRAcUMAUZyw'
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
    req := apivideosdk.WatermarksApiListRequest{}
    
    req.SortBy("createdAt") // string | Allowed: createdAt. You can search by the time watermark were created at.
    req.SortOrder("asc") // string | Allowed: asc, desc. asc is ascending and sorts from A to Z. desc is descending and sorts from Z to A.
    req.CurrentPage(int32(2)) // int32 | Choose the number of search results to return per page. Minimum value: 1 (default to 1)
    req.PageSize(int32(30)) // int32 | Results per page. Allowed values 1-100, default is 25. (default to 25)

    res, err := client.Watermarks.List(req)
    

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Watermarks.List``: %v\n", err)
    }
    // response from `List`: WatermarksListResponse
    fmt.Fprintf(os.Stdout, "Response from `Watermarks.List`: %v\n", res)
}
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const sortBy = 'createdAt'; // Allowed: createdAt. You can search by the time watermark were created at.
        const sortOrder = 'asc'; // Allowed: asc, desc. asc is ascending and sorts from A to Z. desc is descending and sorts from Z to A.
        const currentPage = '2'; // Choose the number of search results to return per page. Minimum value: 1
        const pageSize = '30'; // Results per page. Allowed values 1-100, default is 25.

        // WatermarksListResponse
        const result = await client.watermarks.list({ sortBy, sortOrder, currentPage, pageSize })
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## List all watermarks 
import apivideo
from apivideo.apis import WatermarksApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

watermark_api = WatermarksApi(client)

## List all watermark details
response = watermark_api.list()
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

More information about the endpoint can be found [here](/reference/api/Watermarks#list-all-watermarks)

### Add a watermark to a video using a watermark ID

Once the watermark is uploaded, you can use the watermark id in order to attach the watermark to a video object. 

The watermark property in the [video object creation payload](/reference/api/Watermarks#upload-a-watermark) is an object, that contains the following fields:

| Field     | Type     | Description                                                                                                                                                                |
| :-------- | :------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **id**      | _string_ | id of the watermark                                                                                                                                                        |
| **top**     | _string_ | Distance expressed in px or % between the top-border of the video and the watermark-image.                                                                                 |
| **left**    | _string_ | Distance expressed in px or % between the left-border of the video and the watermark-image.                                                                                |
| **bottom**  | _string_ | Distance expressed in px or % between the bottom-border of the video and the watermark-image.                                                                              |
| **right**   | _string_ | Distance expressed in px or % between the right-border of the video and the watermark-image.                                                                               |
| **width**   | _string_ | Width of the watermark-image relative to the video if expressed in %. Otherwise a fixed width. _NOTE: To keep the watermark aspect ratio use the initial image width_    |
| **height**  | _string_ | Height of the watermark-image relative to the video if expressed in %. Otherwise a fixed height. _NOTE: To keep the watermark aspect ratio use the initial image height_ |
| **opacity** | _string_ | Opacity expressed in % only to specify the degree of the watermark-image transparency with the video.                                                                      |

{% capture samples %}
```curl
curl --request POST \
     --url https://ws.api.video/videos \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MDk2MzIuNTQ2MDg5LCJuYmYiOjE2NDI4MDk2MzIuNTQ2MDg5LCJleHAiOjE2NDI4MTMyMzIuNTQ2MDg5LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.J0cMjE7DNwTGvu3cprGpg5R5IHVDHip80ruWaCN57e9jDoJ4FK51Zkk2ynxhb1SqvutYi-hpnmOzqbPUFPjspLjTVbn0sUskKvGRst2gO2vNllHtIzNTlKKLMA_Sa_zi7hK5_XfuNod-B0SqeH106oAqe1FWkZjc7PMuqOzKS3dIqih0PczsbBQWWgQ3Fh-LtHxVVmdY_egq9i-t1fD5JCD812CHAK1HsxpAkpcAmpJptomORbq72tmEmgFfFUEJ8lalROM2_7ZDLvCdHxDYpA6_j5Z6k1y6Z_OjOsLg_mmiFymAT-DpUib2oOQyj2efIMo-OvdfNJhQRAcUMAUZyw' \
     --header 'Content-Type: application/json' \
     --data '
{
     "public": true,
     "panoramic": false,
     "mp4Support": true,
     "watermark": {
          "id": "watermark_1Bhes1otLhmPPNenUDyvHV",
          "opacity": "70%",
					"height": "20%",
          "right": "15px",
     },
     "source": "https://cdn.api.video/vod/vi2qyv9Ma7UFyTOexm3JSmD2/mp4/1080/source.mp4",
     "title": "Video with a watermark"
}
'
```
```python
import apivideo
from apivideo.api import videos_api
from apivideo.model.video_creation_payload import VideoCreationPayload
from apivideo.model.bad_request import BadRequest
from apivideo.model.video import Video
from pprint import pprint

## Enter a context with an instance of the API client
with apivideo.AuthenticatedApiClient(__API_KEY__) as api_client:
    # Create an instance of the API class
    api_instance = videos_api.VideosApi(api_client)
    video_creation_payload = VideoCreationPayload(
        title="Maths video",
        description="A video about string theory.",
        source="https://www.myvideo.url.com/video.mp4",
        public=True,
        panoramic=False,
        mp4_support=True,
        player_id="pl45KFKdlddgk654dspkze",
        tags=["maths", "string theory", "video"],
        metadata=[
            Metadata(
                key="Color",
                value="Green",
            ),
        ],
        clip=VideoClip(
            start_timecode="8072",
            end_timecode="8072",
        ),
        watermark=VideoWatermark(
            id="watermark_1BWr2L5MTQwxGkuxKjzh6i",
            top="10px",
            left="10px",
            bottom="10px",
            right="10px",
            width="50%",
            height="50%",
            opacity="70%",
        ),
    ) # VideoCreationPayload | video to create

    # example passing only required values which don't have defaults set
    try:
        # Create a video
        api_response = api_instance.create(video_creation_payload)
        pprint(api_response)
    except apivideo.ApiException as e:
        print("Exception when calling VideosApi->create: %s\n" % e)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

### Delete a watermark

To delete a watermark, send the unique watermark ID to the [watermarks endpoint](/reference/api/Watermarks#delete-a-watermark). Deletion is permanent, you will not be able to retrieve the watermark after completing this request.

{% capture samples %}
```curl
curl --request DELETE \
     --url https://ws.api.video/watermarks/watermark_1BhfLVwM8eEdaN9XFSnxCS \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MDk2MzIuNTQ2MDg5LCJuYmYiOjE2NDI4MDk2MzIuNTQ2MDg5LCJleHAiOjE2NDI4MTMyMzIuNTQ2MDg5LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.J0cMjE7DNwTGvu3cprGpg5R5IHVDHip80ruWaCN57e9jDoJ4FK51Zkk2ynxhb1SqvutYi-hpnmOzqbPUFPjspLjTVbn0sUskKvGRst2gO2vNllHtIzNTlKKLMA_Sa_zi7hK5_XfuNod-B0SqeH106oAqe1FWkZjc7PMuqOzKS3dIqih0PczsbBQWWgQ3Fh-LtHxVVmdY_egq9i-t1fD5JCD812CHAK1HsxpAkpcAmpJptomORbq72tmEmgFfFUEJ8lalROM2_7ZDLvCdHxDYpA6_j5Z6k1y6Z_OjOsLg_mmiFymAT-DpUib2oOQyj2efIMo-OvdfNJhQRAcUMAUZyw'
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const watermarkId = 'watermark_1BWr2L5MTQwxGkuxKjzh6i'; // The watermark ID for the watermark you want to delete.

        // void
        const result = await client.watermarks.delete(watermarkId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Delete a watermark using its ID
import apivideo
from apivideo.apis import WaterkmarksApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
watermark = "your watermark ID here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

watermarks_api = WatermarksApi(client)

## Delete the watermark
response = watermarks_api.delete(watermark)
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## API documentation

- [Watermarks](/reference/api/Watermarks)
