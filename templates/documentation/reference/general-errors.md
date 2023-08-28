---
title: "General Errors"
slug: "general-errors"
excerpt: "The errors you might get while authenticating against the API"
metadata: 
  title: "General Erros"
  description: "The list of possible errors that are generated from the API"
---
General Errors
==============

## Error map

| Error message                                      | Status | Description                                                                                                           | Details |
| :------------------------------------------------- | :----- | :-------------------------------------------------------------------------------------------------------------------- | :------ |
| `Invalid attributes.`                              | `400`  | You sent in a parameter that doesn't exist, isn't correct for the endpoint you're using, or has a bad value.          |         |
| `The payload is invalid.`                          | `400`  | The payload you sent is missing OR it cannot be json decoded.                                                         |         |
| `Your account has reached the webhook urls limit.` | `400`  | You have too many webhooks.                                                                                           |         |
| `The requested resource could not be found.`       | `404`  | You requested a resource (video, live stream, etc.) that doesn't exist at all or that doesn't belong to your project. |         |
| `Unrecognized request URL.`                        | `404`  | You send a request to an endpoint that doesn't exist at all, for example GET /foobar.                                 |         |
| `Method is not allowed for this route.`            | `405`  | The endpoint exists but not for this HTTP method, for example you DELETE /webhooks instead of GET.                    |         |
| `Server error`                                     | `500`  | An error occurred on our server, we are working to fix it.                                                            |         |
