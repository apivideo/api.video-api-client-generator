---
title: List Live Streams
meta: 
    description: This guide explains how you can list live streams via API at at api.video.
---

# List Live Streams

Sometimes you will want a complete list of all your live stream containers. Sometimes you will want a list to choose a specific live stream container. This guide walks you through how to retrieve this information.

## API documentation

- [List all live streams](/reference/api/Live-Streams#list-all-live-streams)

## Choose an api.video client

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
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
{% include "_partials/code-tabs.md" samples: samples %}

## Create an account

Before you can start streaming, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

## List all live streams

You can list all live streams you have to see what's available or send a request that filters your live streams. This code sample shows you how to list all live streams:

{% capture samples %}
```curl
curl --request GET \
     --url https://ws.api.video/live-streams \
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
    res, err := client.LiveStreams.List()

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.List``: %v\n", err)
    }
    // response from `List`: LiveStreamListResponse
    fmt.Fprintf(os.Stdout, "Response from `LiveStreams.List`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\LiveStreamsApi;

$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$livestream = $client->liveStreams()->list();

print($livestream);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        // LiveStreamListResponse
        const result = await client.liveStreams.list()
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## List all live streams 
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

## List all live stream details
response = live_stream_api.list()
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## List a subset of live streams

You can send in a request to filter based on the different parameters for a live stream. You can filter on: 

- `streamKey` - A string representing the stream key that allows you to stream videos to a specific live stream container
- `name` - A string representing the name of your live stream 
- `sortBy` - A string indicating how you want the results sorted, options include createdAt, publishedAt, name. createdAt - the time a livestream was created using the specified streamKey. publishedAt - the time a livestream was published using the specified streamKey. name - the name of the livestream. If you choose one of the time-based options, the time is presented in ISO-8601 format.
- `sortOrder` - A string indicating the order in which to sort everything. Allowed: asc, desc. Ascending for date and time means that earlier values precede later ones. Descending means that latter values precede earlier ones. The title is 0-9 and A-Z ascending and Z-A, 9-0 descending.
- `currentPage` - Choose the search results page to return—minimum value: 1.
- `pageSize` - Choose the number of results per page. Allowed values: 1-100, default is 25.

{% capture samples %}
```curl
curl --request GET \
     --url 'https://ws.api.video/live-streams?name=My%20video&currentPage=1&pageSize=25' \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MjQzMTkuMDk2NjY1LCJuYmYiOjE2NDI4MjQzMTkuMDk2NjY1LCJleHAiOjE2NDI4Mjc5MTkuMDk2NjY1LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.rfchf3btbMTzSukcwhUS0u4fNY4Q3g1JpoMeIz_Dls1ADmqDdKw7yBOE893C7cagb0lpuvUJvhuhgusLStsJ4nqzTveDeM2oPBQBNJjzwaJZNrImTPD4mif7Tzgxvn1_jQJA5L4gQhjd7frCIJW1yAwywrtiDPbxiWNp8fVl7r_QILjZZfslxy-kblPrHJ20Zix9VURqkGIORY5G_457nHSV9Atks1sUlt49E8b_g3jORja3MnznXBS0-0dksz2K62-QMe1_dk78V9JwbLeydqcr15M1jDLA3H6qFGI7GTsTDdZ5jKLhg5OR6yeSHFysqr3kOteTqAGdp3JuTrpZIA'
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
    req := apivideosdk.LiveStreamsApiListRequest{}
    
    req.StreamKey("30087931-229e-42cf-b5f9-e91bcc1f7332") // string | The unique stream key that allows you to stream videos.
    req.Name("My Video") // string | You can filter live streams by their name or a part of their name.
    req.SortBy("createdAt") // string | Allowed: createdAt, publishedAt, name. createdAt - the time a livestream was created using the specified streamKey. publishedAt - the time a livestream was published using the specified streamKey. name - the name of the livestream. If you choose one of the time based options, the time is presented in ISO-8601 format.
    req.SortOrder("desc") // string | Allowed: asc, desc. Ascending for date and time means that earlier values precede later ones. Descending means that later values preced earlier ones. For title, it is 0-9 and A-Z ascending and Z-A, 9-0 descending.
    req.CurrentPage(int32(2)) // int32 | Choose the number of search results to return per page. Minimum value: 1 (default to 1)
    req.PageSize(int32(30)) // int32 | Results per page. Allowed values 1-100, default is 25. (default to 25)

    res, err := client.LiveStreams.List(req)
    

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.List``: %v\n", err)
    }
    // response from `List`: LiveStreamListResponse
    fmt.Fprintf(os.Stdout, "Response from `LiveStreams.List`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\LiveStreamsApi;

$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$livestream = $client->liveStreams()->list(['name' => 'My live stream']);

print($livestream);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const streamKey = '30087931-229e-42cf-b5f9-e91bcc1f7332'; // The unique stream key that allows you to stream videos.
        const name = 'My Video'; // You can filter live streams by their name or a part of their name.
        const sortBy = 'createdAt'; // Allowed: createdAt, publishedAt, name. createdAt - the time a livestream was created using the specified streamKey. publishedAt - the time a livestream was published using the specified streamKey. name - the name of the livestream. If you choose one of the time based options, the time is presented in ISO-8601 format.
        const sortOrder = 'desc'; // Allowed: asc, desc. Ascending for date and time means that earlier values precede later ones. Descending means that later values preced earlier ones. For title, it is 0-9 and A-Z ascending and Z-A, 9-0 descending.
        const currentPage = '2'; // Choose the number of search results to return per page. Minimum value: 1
        const pageSize = '30'; // Results per page. Allowed values 1-100, default is 25.

        // LiveStreamListResponse
        const result = await client.liveStreams.list({ streamKey, name, sortBy, sortOrder, currentPage, pageSize })
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## List all live streams 
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

## List all live stream details
title = 'My Video Title'
response = live_stream_api.list(title=title)
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## List live streams using your dashboard

You can also view all your live stream containers from the dashboard. Do the following:

1. Log in to your dashboard.

2. From the menu on the left, click **Live streams**. 

3. On the Live streams screen, you can see all your live streams and filter them by title.

## Conclusion

Listing live streams is useful when you're trying to find a particular live stream for your video project.
