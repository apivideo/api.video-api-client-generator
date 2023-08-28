Invalid Upload Token
====================

The upload token you use does not exist at all or has expired.

## Solution

tauri://localhost#generate-an-upload-token

You create upload tokens with the [delegated upload](/reference/api/Upload-Tokens#generate-an-upload-token) endpoint. If you don't specify how long they last, they last until you delete them. If you are having a problem with your token, you may have the token represented incorrectly or you may have created a token that expired. To fetch a new one, you'll need an authentication token, or a refresh token which you can use to retrieve a new authentication token.

## Learning resources

### Tutorials

* [Delegated uploads](https://api.video/blog/tutorials/delegated-uploads)
* [Delegated uploads for videos large and small](https://api.video/blog/tutorials/delegated-uploads-for-videos-large-and-small-python)
* [Integrate delegated upload to ingest videos from your users](https://docs.api.video/reference#videos-delegated-upload)

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

* [Go](https://github.com/apivideo/api.video-go-client)
* [Python](https://github.com/apivideo/api.video-python-client)
* [C#](https://github.com/apivideo/api.video-csharp-client)
* [PHP](https://github.com/apivideo/api.video-php-client)
* [Node.js](https://github.com/apivideo/api.video-nodejs-client)
