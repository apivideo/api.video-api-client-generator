# Invalid Access Token

The access token sent in `"Authorization: Bearer <ACCESS_TOKEN>"` does not exist at all or has expired.

## Solution

Fetch a new token using the [Authenticate](/reference/api/Advanced-authentication#get-bearer-token) endpoint, or if you have a refresh token, you can use the [Refresh token](/reference/api/Advanced-authentication#get-bearer-token) endpoint to get a new token.

## Learning resources

### Tutorials

- [Authentication steps](https://api.video/blog/tutorials/authentication-tutorial/) \- Walk through how to authenticate and retrieve an access token.
- [When your token expires, hit refresh and protect your API key](https://api.video/blog/tutorials/when-your-token-expires-hit-refresh-and-protect-your-api-key/) \- Use refresh tokens to retrieve a new access token when yours expires.

### Tools

You can sometimes avoid authentication problems by using a client. We offer clients for our API in these languages:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)
