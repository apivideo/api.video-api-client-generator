---
title: Video management basics
meta:
  description: This page serves as a foundational guide to the different operations using api.video's solutions for video on demand (VOD). These are list videos, show and update video details, add thumbnail to videos, and delete videos.
---

# Video management basics

This page serves as a foundational guide to the different operations you can use in api.video's solutions for video on demand (VOD). 

Before jumping into the different operations, follow these steps to set up your work environment:

### Choose an API client

The clients offered by api.video include:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)


### Install

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

Check out the [API clients library](/sdks/api-clients.md) for detailed instructions for all libraries & SDKs.

### Retrieve your API key

You'll need your API key to get started. You can sign up to the [api.video dashboard](https://dashboard.api.video/register) for free. Then do the following: 

1. Log in to the api.video dashboard. 
2. From the list of choices on the left, make sure you are on **API Keys** 
3. Choose your Sandbox API key. If you want to use the Production API key instead, you will need to enter your credit card information. 
4. Grab the key you want, and you're ready to get started!

## Next steps

Check out the different operations for basic video management:

- [List videos](/vod/list-videos-2.md)
- [Show video details](/vod/show-video-details.md)
- [Update video details](/vod/update-video-details.md)
- [Add a thumbnail to your video](/vod/add-a-thumbnail-to-your-video.md)
- [Delete a video](/vod/delete-a-video.md)