---
title: "Bearer Token Authentication"
metadata: 
  description: Learn about api.video's enhanced security authentication method, which uses a disposable bearer token that has a short time to live and has to be refreshed every 3600 seconds.
---

Bearer Token Authentication
=================================================

We provide an enhanced security authentication method, which uses a disposable bearer token that has a short time to live and has to be refreshed every 3600 seconds.

## How it works?

{% include "_partials/dark-light-image.md" dark: "/_assets/reference/disposable-bearer-auth/disposable-bearer-token-dark.png", light: "/_assets/reference/disposable-bearer-auth/disposable-bearer-token-light.png" %}

_Fig. 1_

We provide two additional endpoints that will allow you to [obtain](/reference/api/Advanced-authentication#get-bearer-token) and [refresh](/reference/api/Advanced-authentication#refresh-bearer-token) the bearer token. The API endpoint list below will return an `access_token` and a `refresh_token`.

The `access_token` is a [JWT](https://jwt.io/) encoded token that will come as a replacement for the API key, however, it's only valid for 3600 seconds.

The access token is used as a Bearer token in the request header, for example:

```bash
curl --location 'https://ws.api.video/videos' \
  --header 'Authorization: Bearer eyJ0eXAiO[...]TWXWhy_g'
```

### Obtain the bearer token

```bash
curl --request POST \
     --url https://ws.api.video/auth/api-key \
     --header 'accept: application/json' \
     --header 'content-type: application/json' \
     --data '{"apiKey":"api_key"}'
```

### Refresh the bearer token

```bash
curl --request POST \
     --url https://ws.api.video/auth/refresh \
     --header 'accept: application/json' \
     --header 'content-type: application/json' \
     --data '{"refreshToken": "jwt_bearer_token"}'
```

### Example response

```json
{
    "token_type": "Bearer",
    "expires_in": 3600,
    "access_token": "eyJ0eXAiO[...]TWXWhy_g",
    "refresh_token": "eyJ0eXAiO[...]AACCC_n"
}
```

## Implementation

There are a few ways to implement authentication through a temporary bearer token. One possible way (demonstrated in Fig. 1). However, you can also handle 401 response from the API as an indicator for refreshing the access token.

### Cron job implementation

1. Obtain the bearer token
2. Setup a cron job that will run on your backend for 3600 seconds and trigger the refresh token and pass it through to the service that makes the API requests
3. Use the bearer token as authentication in the request header to any endpoint

### 401 response implementation

1. Obtain the bearer token
2. Create a logic that will refresh the bearer token if getting 401 error from the API (for safety reasons it's good to create a retry logic for the main API request which will backoff after several requests).
3. Use the refreshed access token as bearer
