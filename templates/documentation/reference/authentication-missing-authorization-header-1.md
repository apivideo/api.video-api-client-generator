Missing Authorization Header
============================

You did not send the "Authorization" header at all.

## Solution

You must authenticate every time you use the api.video API. Depending on what part of the process you are in, you will need to send in your API key to retrieve a token or create a delegated token. You can then use the token in the header to make further calls.

## Learning resources

### Tutorials

- [Authentication steps](https://api.video/blog/tutorials/authentication-tutorial) \- Walk through how to authenticate and retrieve an access token.
- [When your token expires, hit refresh and protect your API key](https://api.video/blog/tutorials/when-your-token-expires-hit-refresh-and-protect-your-api-key) \- Use refresh tokens to retrieve a new access token when yours expires.

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

- [Go](https://github.com/apivideo/api.video-go-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [Node.js](https://github.com/apivideo/api.video-nodejs-client)
