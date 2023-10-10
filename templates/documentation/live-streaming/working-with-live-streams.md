---
title: "Working with live streams"
---
Working with live streams
============================================================

When working with live streams, you will want to retrieve details about them, update them or sometimes delete them. This guide walks you through how to manage your live streams.

## API documentation

- [Retrieve a live stream](/reference/api/Live-Streams#retrieve-live-stream)
- [Update a live stream](/reference/api/Live-Streams#update-a-live-stream)
- [Delete a live stream](/reference/api/Live-Streams#delete-a-list-stream)

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

## Create an account

Before you can start streaming, you need to [create an api.video account](https://dashboard.api.video/register). 

Once you are logged in to the Dashboard, select the environment of your choice (sandbox or production) and copy your API key.

## Show live stream (retrieve details for watching)

You can retrieve details about any live stream by sending a request containing the live stream ID. The response will show you the current state of the live stream and provide all the details you need if you want to broadcast using a particular live stream container. 

{% capture samples %}

```curl
curl --request GET \
     --url https://ws.api.video/live-streams/li400mYKSgQ6xs7taUeSaEKr \
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
        
    liveStreamId := "li400mYKSgQ6xs7taUeSaEKr" // string | The unique ID for the live stream you want to watch.

    
    res, err := client.LiveStreams.Get(liveStreamId)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.Get``: %v\n", err)
    }
    // response from `Get`: LiveStream
    fmt.Fprintf(os.Stdout, "Response from `LiveStreams.Get`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\LiveStreamsApi;

$apiKey = 'API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

$livestream = $client->liveStreams()->get("live stream ID here");

print($livestream);
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const liveStreamId = 'li400mYKSgQ6xs7taUeSaEKr'; // The unique ID for the live stream you want to watch.

        // LiveStream
        const result = await client.liveStreams.get(liveStreamId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Get details about a live stream using its ID 
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
live_stream_id = "your live stream ID here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

## Retrieve live stream details
response = live_stream_api.get(live_stream_id)
print(response)
```
{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}

## Show live stream (retrieve details for watching) with your dashboard

If you don't want to retrieve details about a live stream programmatically, you can retrieve live stream details from your dashboard. Do the following:

1. Log in to your dashboard.

2. From the menu on the left, click **Live streams**. The Live streams page opens.

3. If you have a lot of live stream containers, you can filter them by title using the search bar at the top of the Live streams page. Otherwise, click on the live stream container you want information about. 

4. When you click on a container title, you'll get a pop-up with some quick details about the live stream container. If you want full details, click **See details**. This will bring up all information about the live stream container you wanted to review.

   ![Showing the live stream details](/_assets/show-livestream.png)

## Update a live stream

If you want to update details for your live stream, you need the unique ID for your live stream and then the changes you want to make. You're able to update: 

- name - A string representing the name of your live stream.
- public - A boolean representing whether your video is public or not (true for public, false for private).
- playerId - You can associate an api.video player with your live stream by providing the player's ID.

The code sample to update is: 

{% capture samples %}

```curl
curl --request PATCH \
     --url https://ws.api.video/live-streams/li400mYKSgQ6xs7taUeSaEKr \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MTQxNDUuMjE2Mzc2LCJuYmYiOjE2NDI4MTQxNDUuMjE2Mzc2LCJleHAiOjE2NDI4MTc3NDUuMjE2Mzc2LCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.GSDqqMzBxo-wOwl9IVbOnzevm8A6LSyaR5kxCWUdkEneSU0kIdoNfhwmXZBq5QWpVa-0GIT8JR59W6npNO-ayhaXmV3LA6EQpvv0mHd_dAhg3N8T96eC0ps0YIrkmw0_Oe6iRgEDI-wJ9nc6tQWi9ybbMHi1LDBjxW4rbFlq7G59C1QZGabd14QO7uqAUUSNqHC1l42z_m7BTK1AhFiBEXmMcfW7X0VmGcaEUy7NiNda8rmq_nrdvkxgN8KHguXzxMsw_4GE_d0eQwHcZvS1q-FebI6b8AoqpoltFOZvUACCrfXH_D_UPshHuJM3apXbD2dg_zQicc8oWBHVGiobLQ' \
     --header 'Content-Type: application/json' \
     --data '
{
     "name": "My Live Stream Video",
     "public": true,
     "playerId": "pl45KFKdlddgk654dspkze"
}
'
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
        
    liveStreamId := "li400mYKSgQ6xs7taUeSaEKr" // string | The unique ID for the live stream that you want to update information for such as player details.
    liveStreamUpdatePayload := *apivideosdk.NewLiveStreamUpdatePayload() // LiveStreamUpdatePayload | 

    
    res, err := client.LiveStreams.Update(liveStreamId, liveStreamUpdatePayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.Update``: %v\n", err)
    }
    // response from `Update`: LiveStream
    fmt.Fprintf(os.Stdout, "Response from `LiveStreams.Update`: %v\n", res)
}
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const liveStreamId = 'li400mYKSgQ6xs7taUeSaEKr'; // The unique ID for the live stream that you want to update information for such as player details.
        const liveStreamUpdatePayload = {
			name: "My Live Stream Video", // The name you want to use for your live stream.
			_public: true, // Whether your video can be viewed by everyone, or requires authentication to see it. A setting of false will require a unique token for each view.
			playerId: "pl45KFKdlddgk654dspkze", // The unique ID for the player associated with a live stream that you want to update.
		}; 

        // LiveStream
        const result = await client.liveStreams.update(liveStreamId, liveStreamUpdatePayload);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Update information about your live stream. For example you can change whether you have it presented publicly. 
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
live_stream_id = "your live stream id here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

## Add the details you want to update to a dictionary
live_stream_update_payload = {
    "public": True,
    "name": "Bob III"    
}

## Update the live stream
response = live_stream_api.update(live_stream_id, live_stream_update_payload)
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}

## Update a live stream using the dashboard

You can update live stream details from your dashboard if you don't want to retrieve details about a stream programmatically. Do the following:

1. Log in to your dashboard. 

2. From the menu on the left, click **Live streams**. The Live streams screen opens.

3. Choose the live stream container you want to update by clicking on it. A pop-up appears displaying key details about your live stream. 

4. Click **See details** to open a display of complete information about your live stream. 

   ![Showing the live stream details](/_assets/show-livestream.png)

5. You can change the title of your live stream, upload a thumbnail, and associate a player ID on this screen. When you're done, click **Save**.

## Delete a live stream

You can delete a live stream by sending in a DELETE request using the unique ID for the live stream. The code sample looks like this:

{% capture samples %}

```curl
curl --request DELETE \
     --url https://ws.api.video/live-streams/li400mYKSgQ6xs7taUeSaEKr \
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
        
    liveStreamId := "li400mYKSgQ6xs7taUeSaEKr" // string | The unique ID for the live stream that you want to remove.

    
    err := client.LiveStreams.Delete(liveStreamId)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `LiveStreams.Delete``: %v\n", err)
    }
}
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });

        const liveStreamId = 'li400mYKSgQ6xs7taUeSaEKr'; // The unique ID for the live stream that you want to remove.

        // void
        const result = await client.liveStreams.delete(liveStreamId);
        console.log(result);
    } catch (e) {
        console.error(e);
    }
})();
```
```python
## Delete a live stream using its ID
import apivideo
from apivideo.apis import LiveStreamsApi
from apivideo.exceptions import ApiAuthException

api_key = "your api key here"
live_stream = "your live stream ID here"

client = apivideo.AuthenticatedApiClient(api_key)

## If you'd rather use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

live_stream_api = LiveStreamsApi(client)

## Delete the live stream
response = live_stream_api.delete(live_stream)
print(response)
```

{% endcapture %}
{% include "_partials/code-tabs.html" content: samples %}

## Delete a live stream from your dashboard

To delete a live stream from your dashboard, do the following:

1. Log in to your dashboard. 

2. From the menu on the left, click **Live streams**. 

3. Check the check box next to one or more live stream containers you want to delete. Please choose carefully. Once you delete them, they are not retrievable. After checking one or more boxes, a trash can icon appears at the bottom of the screen.

4. Click the **trash can** icon. 

5. You'll see a pop-up asking if you're sure you want to delete because deletion is permanent. If you want to delete the live stream(s) you checked the box for, click **Confirm**.
