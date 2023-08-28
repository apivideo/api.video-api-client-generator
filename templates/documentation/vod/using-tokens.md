api.video provides you with various options of authentication which allows you to leverage the API on a different level of your application, whether it's on the front end or backend. Moreover, you can provide better security for your backend and videos.

# Upload & API Access Tokens

## Access Token

This token is created to enhance the backend security and create a short-lived bearer token that will replace your API key for any API request. The Access Token is a disposable bearer token that is being generated from the [/auth/api-key](https://docs.api.video/reference/post_auth-api-key) endpoint, by passing the workspace API key. The endpoint will respond with a short-lived bearer token (3600 seconds) that can be refreshed by making a request to the [/auth/refresh](https://docs.api.video/reference/post_auth-refresh) endpoint.  

For more detailed information, head over to [the article about the Access Token](https://docs.api.video/reference/disposable-bearer-token-authentication)

## Delegated Upload Token

The delegated upload token is created in order to assist you in building features in the front end. The delegated upload token is a one-time token that is valid only with the [/upload](https://docs.api.video/reference/post_upload) endpoint. The idea behind it is that you can serve a one-time token to the front end from the back end that will be used by the front-end to upload a video.

For more details please visit the [Delegated upload tokens page](https://docs.api.video/reference/upload-tokens).

# Video Access Management Tokens

## Private Token

In order for your user to consume private videos, api.video will generate a one-time private token for the assets to be passed as a query parameter. You can learn more about private videos and private tokens [here](https://docs.api.video/docs/private-videos).

## Session Token

This token's purpose is to retain a private session for private videos. When you serve private videos to your user, you will have to leverage session tokens to retrain the session and continue consuming other assets that are related to the video. Learn more about session tokens [here](https://docs.api.video/docs/private-video-session-tokens).