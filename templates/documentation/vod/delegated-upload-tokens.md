---
title: "Delegated upload tokens"
slug: "delegated-upload-tokens"
metadata: 
  description: This guide explains how to create, list, retrieve, and delete delegated upload tokens, and how to upload videos with them.
---

# Using delegated upload tokens

You can upload videos using the traditional two-step process where you create a video container then upload your video into your container. You can also upload a video using a **delegated upload token**. This guide explains how to create, list, retrieve, and delete delegated upload tokens, and how to upload videos with them.

{% include "_partials/dark-light-image.md" dark: "/_assets/vod/upload-tokens/upload-token-dark.svg", light: "/_assets/vod/upload-tokens/upload-token-light.svg" %}

In this type of upload, you retrieve a token from the tokens endpoint. You can then directly upload using the token upload endpoint. It's one step, and you don't provide anything except the file. You can update the metadata for the upload later. A benefit of a delegated token is that you define the TTL (time-to-live) value, so it expires only when you need it to. You can always use the token to upload. You can also create delegated tokens for others to use to do uploads.

* Anyone who has a token can make an upload, so be careful how you set and manage tokens.
* You can create as many tokens as you need.
* If you do not include a TTL (time-to-live) value for a token, it lasts until you delete it. 

{% capture content %}
If you want to learn about regular or progressive uploads, check out the [regular](/vod/upload-a-video-regular-upload) and [progressive](/vod/progressive-upload) video upload guides.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## API documentation

- [Upload Tokens](/reference/api/Upload-Tokens)
- [Upload with an upload token](/reference/api/Videos#upload-with-an-delegated-upload-token)

## Resources

We offer blog content on this topic: 

- [Delegated uploads](https://api.video/blog/tutorials/delegated-uploads/) - **cURL** Walk through how to do a delegated upload using cURL. 
- [Delegated uploads for videos large and small](https://api.video/blog/tutorials/delegated-uploads-for-videos-large-and-small-python/) - **Python** Learn how to do a delegated upload using Python. 

## Create an account

Before you can start uploading your first video, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

![Managing the API keys in the Dashboard](/_assets/retrieve-api-key.png)

## Choose an api.video client

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [iOS](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)

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

## Generate a token for delegated upload

Use this code sample to generate a token for use with a delegated upload. You can include a TTL (time-to-live) if you like. The token will expire after exceeding the set TTL. If you don't send in a TTL, your token will last until you choose to delete it.

{% capture samples %}

```curl
curl --request POST \
     --url https://ws.api.video/upload-tokens \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ' \
     --header 'Content-Type: application/json' \
     --data '{"ttl":100}'
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
        
    tokenCreationPayload := *apivideosdk.NewTokenCreationPayload() // TokenCreationPayload | 

    
    res, err := client.UploadTokens.CreateToken(tokenCreationPayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `UploadTokens.CreateToken``: %v\n", err)
    }
    // response from `CreateToken`: UploadToken
    fmt.Fprintf(os.Stdout, "Response from `UploadTokens.CreateToken`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

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

$payload = (new \ApiVideo\Client\Model\TokenCreationPayload())->setTtl(0);
$tokens = $client->uploadTokens()->createToken($payload);

print($tokens);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const tokenCreationPayload = {
			ttl: 56, // Time in seconds that the token will be active. A value of 0 means that the token has no exipration date. The default is to have no expiration.
		}; 

        // UploadToken
        const result = await client.uploadTokens.createToken(tokenCreationPayload);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Create a token for use with delegate uploads
import apivideo
from apivideo.apis import UploadTokensApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

## List the time to live (ttl) in seconds for the token you create.
token_creation_payload = {"ttl": 1000}

tokens_api = UploadTokensApi(client)

## Create the token
response = tokens_api.create_token(token_creation_payload)
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}

## Upload a video with delegated tokens

{% capture content %}
If you do a progressive upload with a delegated token, you have to include the video ID you are uploading to after it comes back from the first request. If the video you're uploading is under 200 MiB, you don't need to worry.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

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

## Token operations

### List all tokens you created

If a token is compromised, or you want to see how many tokens you have, you will need to retrieve a list of them programmatically. Here is the code sample for that:

{% capture samples %}

```curl
curl --request GET \
     --url 'https://ws.api.video/upload-tokens?currentPage=1&pageSize=25' \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ'
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
    req := apivideosdk.UploadTokensApiListRequest{}
    
    req.SortBy("ttl") // string | Allowed: createdAt, ttl. You can use these to sort by when a token was created, or how much longer the token will be active (ttl - time to live). Date and time is presented in ISO-8601 format.
    req.SortOrder("asc") // string | Allowed: asc, desc. Ascending is 0-9 or A-Z. Descending is 9-0 or Z-A.
    req.CurrentPage(int32(2)) // int32 | Choose the number of search results to return per page. Minimum value: 1 (default to 1)
    req.PageSize(int32(30)) // int32 | Results per page. Allowed values 1-100, default is 25. (default to 25)

    res, err := client.UploadTokens.List(req)
    

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `UploadTokens.List``: %v\n", err)
    }
    // response from `List`: TokenListResponse
    fmt.Fprintf(os.Stdout, "Response from `UploadTokens.List`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

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

$tokens = $client->uploadTokens()->list();

print($tokens);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const sortBy = 'ttl'; // Allowed: createdAt, ttl. You can use these to sort by when a token was created, or how much longer the token will be active (ttl - time to live). Date and time is presented in ISO-8601 format.
        const sortOrder = 'asc'; // Allowed: asc, desc. Ascending is 0-9 or A-Z. Descending is 9-0 or Z-A.
        const currentPage = '2'; // Choose the number of search results to return per page. Minimum value: 1
        const pageSize = '30'; // Results per page. Allowed values 1-100, default is 25.

        // TokenListResponse
        const result = await client.uploadTokens.list({ sortBy, sortOrder, currentPage, pageSize })
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## List all delegated tokens that are active
import apivideo
from apivideo.apis import UploadTokensApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

tokens_api = UploadTokensApi(client)

## List all delegated tokens
response = tokens_api.list()
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


### Show details about a specific token

Retrieve information about a specific token. To do this, you send a request containing the token ID for the token you need details about.

{% capture samples %}

```curl
curl --request GET \
     --url https://ws.api.video/upload-tokens/to40nBwUZJGnuW8THBZwPqtL \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ'
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
        
    uploadToken := "to1tcmSFHeYY5KzyhOqVKMKb" // string | The unique identifier for the token you want information about.

    
    res, err := client.UploadTokens.GetToken(uploadToken)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `UploadTokens.GetToken``: %v\n", err)
    }
    // response from `GetToken`: UploadToken
    fmt.Fprintf(os.Stdout, "Response from `UploadTokens.GetToken`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

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

$tokens = $client->uploadTokens()->getToken('token here');

print($tokens);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const uploadToken = 'to1tcmSFHeYY5KzyhOqVKMKb'; // The unique identifier for the token you want information about.

        // UploadToken
        const result = await client.uploadTokens.getToken(uploadToken);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Get information about a single token using the token ID
import apivideo
from apivideo.apis import UploadTokensApi
from apivideo.exceptions import ApiAuthException

## Set variables
api_key = "your api key here"
token = "your token ID here"

## Set up the client
client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

tokens_api = UploadTokensApi(client)

## Send your request to retrieve information about a specific token
response = tokens_api.get_token(token)
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


### Delete a token

If you create a token that's compromised, you may want to remove it. Or, you might want to clean up how many tokens you have in general. All you need to do to delete a token is send a request containing the token ID for the token you want to remove.

{% capture samples %}

```curl
curl --request DELETE \
     --url https://ws.api.video/upload-tokens/curl%20--request%20GET%20%5C%20%20%20%20%20%20--url%20https%3A%2F%2Fws.api.video%2Fupload-tokens%2Fto40nBwUZJGnuW8THBZwPqtL%20%5C%20%20%20%20%20%20--header%20%27Accept%3A%20application%2Fjson%27%20%5C%20%20%20%20%20%20--header%20%27Authorization%3A%20Bearer%20eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ%27 \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ'
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
        
    uploadToken := "to1tcmSFHeYY5KzyhOqVKMKb" // string | The unique identifier for the upload token you want to delete. Deleting a token will make it so the token can no longer be used for authentication.

    
    err := client.UploadTokens.DeleteToken(uploadToken)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `UploadTokens.DeleteToken``: %v\n", err)
    }
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

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

$tokens = $client->uploadTokens()->deleteToken('token here');

print($tokens);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const uploadToken = 'to1tcmSFHeYY5KzyhOqVKMKb'; // The unique identifier for the upload token you want to delete. Deleting a token will make it so the token can no longer be used for authentication.

        // void
        const result = await client.uploadTokens.deleteToken(uploadToken);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Delete a video using its video ID
import apivideo
from apivideo.apis import VideosApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

videos_api = VideosApi(client)

title = "Sample AVI Video"

## List videos that have the exact, unique title you wanted to delete
videos = videos_api.list(title=title)

## Get list of videos out of response object or single item depending on whether you filtered
videos = videos['data']

## In this case, let's assume we know there's only one video with the title we filtered for. 
print(videos[0]['video_id'])
        
## Delete the video
response = videos_api.delete(videos[0]['video_id'])
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}


## Conclusion

Delegated upload is a useful method if you want to allow users to upload videos - you can provide them delegated upload tokans that expire. You can also use this method to create one-time video uploader solutions. It's also useful if you want to upload content quickly without worrying about the metadata. Everything is uploaded at once - it's just the video and the title. You can add the metadata later.