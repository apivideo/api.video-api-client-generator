---
title: "Create and manage tokens for delegated uploads"
slug: "create-and-manage-tokens-for-delegated-uploads"
hidden: false
metadata: 
  description: "To complete a delegated upload, you need to create a token. You can create as many tokens as you like. If you don't include a TTL (time-to-live) for a token, then it lasts until you choose to delete it. In this guide, we will walk through how to create a token and then how to list tokens, retrieve token details, and delete a token you no longer need."
createdAt: "2022-01-22T01:11:35.697Z"
updatedAt: "2023-05-05T17:37:04.539Z"
---
Create And Manage Tokens For Delegated Uploads
==============================================

To complete a delegated upload, you need to create a token. You can create as many tokens as you like. If you don't include a TTL (time-to-live) for a token, then it lasts until you choose to delete it. In this guide, we will walk through how to create a token and then how to list tokens, retrieve token details, and delete a token you no longer need. 

{% capture content %}
📘 **NOTE**

If you want to learn about regular or progressive uploads, please see the [Upload a video - regular, progressive, and bytes](/vod/upload-a-video-regular-upload) guide.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Associated API reference documentation

- [Upload Tokens](/reference/api/Upload-Tokens) 

## Choose an api.video client

The clients offered by api.video include:

- [Go](https://github.com/apivideo/api.video-go-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [JavaScript ](https://github.com/apivideo/api.video-nodejs-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)

## Installation

To install your selected client, do the following: 

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

## Retrieve your API key

You'll need your API key to get started. You can sign up for one here: [Get your api.video API key!](https://dashboard.api.video/register). Then do the following: 

1. Log in to the api.video dashboard. 
2. From the list of choices on the left, make sure you are on **API Keys** 
3. You will always be able to choose to use your Sandbox API key. If you want to use the Production API key instead, enter your credit card information. 
4. Grab the key you want, and you're ready to get started! 

## Generate a token for delegated upload

Use this code sample to generate a token for use with a delegated upload. You can include a TTL (time-to-live) if you like. The token will expire after exceeding the set TTL. If you don't send in a TTL, your token will last until you choose to delete it.

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



## List all tokens you created

If a token is compromised, or you want to see how many tokens you have, you will need to retrieve a list of them programmatically. Here is the code sample for that:

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



## Show details about a specific token

Retrieve information about a specific token. To do this, you send a request containing the token ID for the token you need details about.

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



## Delete a token

If you create a token that's compromised, you may want to remove it. Or, you might want to clean up how many tokens you have in general. All you need to do to delete a token is send a request containing the token ID for the token you want to remove.

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



## Conclusion

You need tokens for delegated uploads. This type of upload is great if you want to allow users to upload something themselves - they can with a token that expires. It's also useful if you want to upload content quickly without worrying about the metadata. Everything is uploaded at once - it's just the video and title. You can add the metadata later.
