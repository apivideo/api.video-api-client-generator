---
title: "Invalid Payload"
slug: "invalid-payload-1"
metadata: 
  title: "Invalid Payload"
  description: "The payload you sent is missing OR it cannot be json decoded."
---

Invalid Payload
===============

The payload you sent is missing OR it cannot be json decoded.

## Solution

If the payload is completely missing, then add a payload. If you included a payload and it's not working, then there could be a couple different problems: 

* You may have neglected to send your payload as properly formed JSON. Check that everything is set up correctly, since one misplaced or absent comma can mess up your payload. 
* If you're using one of our API clients, you may have added the parameters incorrectly. Consult the examples and documentation for your API client for further details.

## Learning resources

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

* [Go](https://github.com/apivideo/api.video-go-client)
* [Python](https://github.com/apivideo/api.video-python-client)
* [C#](https://github.com/apivideo/api.video-csharp-client)
* [PHP](https://github.com/apivideo/api.video-php-client)
* [Node.js](https://github.com/apivideo/api.video-nodejs-client)
