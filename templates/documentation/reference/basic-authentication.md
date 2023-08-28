Basic authentication
====================

## Introduction

api.video uses [HTTP Basic Auth](https://swagger.io/docs/specification/authentication/basic-authentication/). The authentication is your API Key as username.

If invalid credentials are provided, a `401 Unauthorized` response code is returned with the corresponding JSON body.

You must use different credentials depending on the [environment](/reference/README.md#environments) you are in. If credentials for the wrong environment are used, a `401 Unauthorized` response code is returned with the corresponding JSON body.


{% capture content %}
**📘 API Key protection**

To protect your credentials from being revealed on the client-side, invoke the api.video calls from your own server-side applications only.

Please make sure to read the security guidance [here](/reference/README.md#security).
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

## Retrieve API keys

You'll be able to find your API key in the dashboard [dashboard.api.video](https://dashboard.api.video/overview).

There are separate API keys for Sandbox and Production [environment](/reference/README.md#environments).

In production, you can also manage multiple API keys from your dashboard, for different applications.

![](/_assets/production-api-key.png)

### Basic Auth Request 🔒

Sample requests with authentication can be found below:

* Production authentication

  ```bash
  curl -u api_key: https://ws.api.video/videos
  ```

* Sandbox authentication

  ```bash
  curl -u api_key: https://sandbox.api.video/videos
  ```

{% capture content %}
**📘 Notes**

* Replace `api_key` with the key you have already copied from [https://dashboard.api.video](https://dashboard.api.video/)
* **⚠️ Important: You need to keep the colon after `api_key`**
* The above API request is equivalent to  
  ```bash
    curl --header 'Authorization: Basic your\_api\_key\_with\_trailing\_colon\_in\_base64' https://ws.api.video/videos
  ```
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}
