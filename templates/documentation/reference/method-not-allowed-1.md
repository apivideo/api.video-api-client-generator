---
title: "Method Not Allowed"
slug: "method-not-allowed-1"
metadata: 
  title: "Method not allowed"
  description: "The request made to the endpoint didn't contain a supported HTTP method. For example, a request was made to the `/webhooks` endpoint with the `DELETE` method, which is not supported for the endpoint in question."
---

Method Not Allowed
==================

The request made to the endpoint didn't contain a supported HTTP method. For example, a request was made to the `/webhooks` endpoint with the `DELETE` method, which is not supported for the endpoint in question.

## Solution

This could be many different problems, your best bet is to check the documentation for the endpoint you're using to see what methods are allowed, and make sure you've implemented them correctly. You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

- [Go](https://github.com/apivideo/api.video-go-client)
- [Python](https://github.com/apivideo/api.video-python-client)
- [C#](https://github.com/apivideo/api.video-csharp-client)
- [PHP](https://github.com/apivideo/api.video-php-client)
- [Node.js](https://github.com/apivideo/api.video-nodejs-client)
