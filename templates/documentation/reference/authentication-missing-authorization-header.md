---
title: Missing Authorization Header
meta: 
    description: This guide explains the cause and the possible solutions for the Missing Authorization Header error.
---

# Missing Authorization Header

You did not send the "Authorization" header at all.

## Solution

You must authenticate every time you use the api.video API. Depending on what part of the process you are in, you will need to send in your API key to retrieve a token or create a delegated token. You can then use the token in the header to make further calls.

## Learning resources

### Tutorials

- [Authentication steps](https://api.video/blog/tutorials/authentication-tutorial/) \- Walk through how to authenticate and retrieve an access token.
- [When your token expires, hit refresh and protect your API key](https://api.video/blog/tutorials/when-your-token-expires-hit-refresh-and-protect-your-api-key/) \- Use refresh tokens to retrieve a new access token when yours expires.

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)
