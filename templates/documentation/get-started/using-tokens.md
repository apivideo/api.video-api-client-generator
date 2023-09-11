---
title: "Using tokens"
slug: "using-tokens"
meta:
  description: This page explains the various options of authentication using access tokens, refresh tokens, delegated upload tokens, private tokens, and session tokens.
---

api.video provides you with various options of authentication that enable you to leverage the API on different levels of your application, whether it's on the frontend or the backend. Moreover, you can provide better security for your backend and videos using delegated upload tokens.

# API access & upload tokens

## Access tokens

This token is created to enhance the backend security and create a short-lived bearer token that will replace your API key for any API request. The Access Token is a disposable bearer token that is being generated from the [/auth/api-key](/reference/api/Advanced-authentication#get-bearer-token) endpoint, by passing the workspace API key. The endpoint will respond with a short-lived bearer token (3600 seconds) that can be refreshed by making a request to the [/auth/refresh](/reference/api/Advanced-authentication#refresh-bearer-token) endpoint.  

For more detailed information, head over to [the article about the Access Token](/reference/disposable-bearer-token-authentication.md)

## Delegated upload tokens

The delegated upload token is created in order to assist you in building features in the front end. The delegated upload token is a one-time token that is valid only with the [/upload](https://docs.api.video/reference/post_upload) endpoint. The idea behind it is that you can serve a one-time token to the front end from the back end that will be used by the front-end to upload a video.

For more details please visit the [Delegated upload tokens page](https://docs.api.video/reference/upload-tokens).

# Video access management tokens

## Private tokens

In order for your user to consume private videos, api.video will generate a one-time private token for the assets to be passed as a query parameter. You can learn more about private videos and private tokens [here](https://docs.api.video/docs/private-videos).

## Session tokens

This token's purpose is to retain a private session for private videos. When you serve private videos to your user, you will have to leverage session tokens to retrain the session and continue consuming other assets that are related to the video. Learn more about session tokens [here](https://docs.api.video/docs/private-video-session-tokens).