---
title: Invalid Payload
meta: 
    description: This guide explains the cause and the possible solutions for the Invalid Payload error.
---

# Invalid Payload

The payload you sent is missing OR it cannot be json decoded.

## Solution

If the payload is completely missing, then add a payload. If you included a payload and it's not working, then there could be a couple different problems: 

* You may have neglected to send your payload as properly formed JSON. Check that everything is set up correctly, since one misplaced or absent comma can mess up your payload. 
* If you're using one of our API clients, you may have added the parameters incorrectly. Consult the examples and documentation for your API client for further details.

## Learning resources

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

- [NodeJS](../sdks/api-clients/apivideo-nodejs-client.md)
- [Python](../sdks/api-clients/apivideo-python-client.md)
- [PHP](../sdks/api-clients/apivideo-php-client.md)
- [Go](../sdks/api-clients/apivideo-go-client.md)
- [C#](../sdks/api-clients/apivideo-csharp-client.md)
- [Java](../sdks/api-clients/apivideo-java-client.md)
- [Swift](../sdks/api-clients/apivideo-swift5-client.md)
- [Android](../sdks/api-clients/apivideo-android-client.md)

