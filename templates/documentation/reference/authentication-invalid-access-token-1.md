# Invalid Access Token

The access token sent in `"Authorization: Bearer <ACCESS_TOKEN>"` does not exist at all or has expired.

## Solution

Fetch a new token using the [Authenticate](/reference/api/Advanced-authentication#get-bearer-token) endpoint, or if you have a refresh token, you can use the [Refresh token](/reference/api/Advanced-authentication#get-bearer-token) endpoint to get a new token.

## Learning resources

### Tutorials

- [Authentication steps](https://api.video/blog/tutorials/authentication-tutorial) \- Walk through how to authenticate and retrieve an access token.
- [When your token expires, hit refresh and protect your API key](https://api.video/blog/tutorials/when-your-token-expires-hit-refresh-and-protect-your-api-key) \- Use refresh tokens to retrieve a new access token when yours expires.

### Tools

You can sometimes avoid authentication problems by using a client. We offer clients for our API in these languages:

- [Go](https://github.com/apivideo/api.video-go-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [Node.js](https://github.com/apivideo/api.video-nodejs-client)
