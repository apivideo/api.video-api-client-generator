---
title: "Authentication and refresh tokens"
---
Authentication and refresh tokens
=================================

With api.video, every call to the API requires authentication. We use Bearer authentication for everything, so on your first request, you send your API key in an authorization header and get back an access token and a refresh token. The access token lasts for one hour. The refresh token lasts until you make a new call to the API to get an access token, or you send in your API key again to get an access token and refresh token. 

Bearer authentication is simple to set up and use; however, we encourage you to use one of our clients if possible. api.video clients handle authentication for you, including renewing your token as needed. This guide will show you how to quickly install the client of your choice and provide the code snippet you'll need for authentication. 

{% capture content %}
This guide shows the corresponding cURL commands for each part of the tutorial where appropriate.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Associated API reference documentation

- [Authenticate](/reference/api/Advanced-authentication#get-bearer-token)
- [Refresh token](/reference/api/Advanced-authentication#refresh-bearer-token)

## Resources

We offer blog content on this topic: 

- [Authentication steps](https://api.video/blog/tutorials/authentication-tutorial) - A walkthrough for authentication using cURL.
- [You shall not pass: The benefits of token based authentication](https://api.video/blog/video-trends/you-shall-not-pass-the-benefits-of-token-based-authentication) - A discussion about what tokens are and what kinds of authentication api.video uses.

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
{% include "_partials/code-tabs.md" samples: samples %}

## Retrieve your API key

You'll need your API key to get started. You can sign up for one here: [Get your api.video API key!](https://dashboard.api.video/register). Then do the following: 

1. Log in to the api.video dashboard. 
2. From the list of choices on the left, make sure you are on **API Keys** 
3. You will always be able to choose to use your Sandbox API key. If you want to use the Production API key instead, select a plan and enter your credit card information. 
4. Grab the key you want, and you're ready to get started! 

![](/_assets/retrieve-api-key.png)

## Authenticate

For each client, you will set up a client and provide it with your API key. The client then takes care of everything else for you. It will keep track of your access token and refresh token. When the access token expires after an hour, it will renew when needed.  

{% capture samples %}
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
        
    authenticatePayload := *apivideosdk.NewAuthenticatePayload("ApiKey_example") // AuthenticatePayload | 

    
    res, err := client.Authentication.Authenticate(authenticatePayload)

    if err != nil {
        fmt.Fprintf(os.Stderr, "Error when calling `Authentication.Authenticate``: %v\n", err)
    }
    // response from `Authenticate`: AccessToken
    fmt.Fprintf(os.Stdout, "Response from `Authentication.Authenticate`: %v\n", res)
}
```
```php
<?php
require __DIR__ .'/vendor/autoload.php';

use Symfony\Component\HttpClient\Psr18Client;
use ApiVideo\Client\Client;
use ApiVideo\Client\Model\VideoCreationPayload;

$apiKey = 'your API key here';
$apiVideoEndpoint = 'https://ws.api.video';

$httpClient = new \Symfony\Component\HttpClient\Psr18Client();
$client = new ApiVideo\Client\Client(
    $apiVideoEndpoint,
    $apiKey,
    $httpClient
);

/* Your refresh token is handled for you */
```
```javascript
const ApiVideoClient = require('@api.video/nodejs-client');

(async () => {
    try {
        const client = new ApiVideoClient({ apiKey: "YOUR_API_TOKEN" });
		}
})();
```
```python
## Use your api.video api key to retrieve an access token for use with the api.video
## API. Access tokens are good for one hour.

import apivideo

api_key = "your api key here"
client = apivideo.AuthenticatedApiClient(api_key)

## If you prefer to use the sandbox environment:
## client = apivideo.AuthenticatedApiClient(api_key, production=False)

client.connect()

## You can view the object like this. Use this for reference and testing only.

print(client.__dict__)
```
```csharp
using System.Collections.Generic;
using System.Diagnostics;
using System;
using System.IO;
using ApiVideo.Api;
using ApiVideo.Client;
using ApiVideo.Model;

namespace Example
{
    public class Example
    {
        public static void Main()
        {
            var apiKey = "YOUR_API_KEY";

            var apiVideoClient = new ApiVideoClient(apiKey);
            // if you rather like to use the sandbox environment:
            // var apiVideoClient = new ApiVideoClient(apiKey, ApiVideo.Client.Environment.SANDBOX);
        }
    }
}
```
```curl
curl -X POST \
https://sandbox.api.video/auth/api-key \
-H 'Content-Type: application/json' \
-d '{"apiKey": "your API key here"}'
```
{% endcapture %}
{% include "_partials/code-tabs.md" samples: samples %}

## Conclusion

Authentication is required for every call to api.video. It's made easy with clients, so we recommend choosing a client to work with.
