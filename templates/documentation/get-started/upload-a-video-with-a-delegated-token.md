---
title: "Upload a video with a delegated token"
---

Upload A Video With A Delegated Token
=====================================

You can upload videos using the traditional two-step process where you create a video container then upload your video into your container. You can also upload a video using a delegated token. In this type of upload, you retrieve a token from the tokens endpoint. You can then directly upload using the token upload endpoint. It's one step, and you don't provide anything except the file. You can update the metadata for the upload later. A benefit of a delegated token is that you can make it, so it doesn't expire. You can always use the token to upload. You can also create delegated tokens for others to use to do uploads. Anyone who has a token can make an upload, so be careful how you set and manage tokens. 

{% capture content %}
If you do a progressive upload with a delegated token, you have to include the video ID you are uploading to after it comes back from the first request. If the video you're uploading is under 200 MiB, you don't need to worry.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Associated API reference documentation

- [Upload Tokens](/reference/api/Upload-Tokens) 
- [Upload with an upload token](/reference/api/Videos#upload-with-an-delegated-upload-token)

## Resources

We offer blog content on this topic: 

- [Delegated uploads](https://api.video/blog/tutorials/delegated-uploads) - **cURL** Walk through how to do a delegated upload using cURL. 
- [Delegated uploads for videos large and small](https://api.video/blog/tutorials/delegated-uploads-for-videos-large-and-small-python) - **Python** Learn how to do a delegated upload using Python. 

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
{% include "_partials/code-tabs.html" content: samples %}


## Retrieve your API key

You'll need your API key to get started. You can sign up for one here: [Get your api.video API key!](https://dashboard.api.video/register). Then do the following: 

1. Log in to the api.video dashboard. 
2. From the list of choices on the left, make sure you are on **API Keys** 
3. You will always be able to choose to use your Sandbox API key. If you want to use the Production API key instead, enter your credit card information. 
4. Grab the key you want, and you're ready to get started! 

## Delegated upload

You must first create a token and get the unique token ID to do a delegated upload. Then, you include it in your request as a query parameter. In the body, you place the path to the file you want to upload. If you are uploading a file that's 200 MiB or larger, to do a progressive upload, you will need to break the file into smaller pieces (no smaller than 5 MiB). Then send a request containing the first piece of your upload. Subsequent pieces must be sent with the video ID included in the body along with the file chunk. Retrieve the video ID from the response that comes back after your first request to upload. 

{% capture samples %}

```curl
curl --request POST \
     --url 'https://ws.api.video/upload?token=__TOKENIDHERE__' \
     --header 'Accept: application/json' \
     --header 'Content-Type: multipart/form-data' \
     --form file=@FILEPATH.mp4
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
        
    token := "to1tcmSFHeYY5KzyhOqVKMKb" // string | The unique identifier for the token you want to use to upload a video.
    file := os.NewFile(1234, "some_file") // *os.File | The path to the video you want to upload.

    
    res, err := client.Videos.UploadWithUploadTokenFile(token, file)

    // you can also use a Reader instead of a File:
    // client.Videos.UploadWithUploadToken(token, fileName, fileReader, fileSize)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Videos.UploadWithUploadToken``: %v\n", err)
    }
    // response from `UploadWithUploadToken`: Video
    fmt.Fprintf(os.Stdout, "Response from `Videos.UploadWithUploadToken`: %v\n", res)
}
```
```php
use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideosApi;
use ApiVideo\Client\Model\UploadTokensApi;
$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$videos = $client->videos()->upLoadWithUploadToken('token here', new SplFileObject('small_vid.mp4') );
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
				const apiVideoClient = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const token = 'to1tcmSFHeYY5KzyhOqVKMKb'; // The unique identifier for the token you want to use to upload a video.
        const file = 'BINARY_DATA_HERE'; // The path to the video you want to upload.

        // Video
        const result = await client.videos.uploadWithUploadToken(token, file);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Use a delegated token to upload a video
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException 

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()
videos_api = VideosApi(client)

## Setting up variables for the path to the video and the token you already created
path = 'path to video'
token = 'your token'

## Opening your vieo file so it can be sent
file = open(path, "rb")

## Sending file. 
response = videos_api.upload_with_upload_token(token, file)
print(response)
© 2022 GitHub, Inc.
```
{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


## Conclusion

Delegated uploads are faster uploads, once you create a token you can use it over and over if you want. This type of upload only takes one API call.
