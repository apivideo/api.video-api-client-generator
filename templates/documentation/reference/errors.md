# Authentication Errors

The errors you might get while authenticating against the API

## Error map

| Error message                                | Status | Description                                                                                             | Details |
| -------------------------------------------- | ------ | ------------------------------------------------------------------------------------------------------- | ------- |
| The API key is invalid.                      | `401`  | You've entered an incorrect API key                                                                     |         |
| The access token is invalid or expired.      | `401`  | The access token sent in `"Authorization: Bearer <ACCESS_TOKEN>"` does not exist at all or has expired. |         |
| The upload token is invalid or expired.      | `401`  | The upload token you use does not exist at all or has expired.                                          |         |
| The refresh token is invalid.                | `401`  | The refresh token you sent does not exist at all.                                                       |         |
| The "Authorization" header value is invalid. | `401`  | The "Authorization" header value is invalid for the authentication method you chose.                    |         |
| The "Authorization" header is missing.       | `401`  | You did not send the "Authorization" header at all.                                                     |         |
| The upload token is missing.                 | `401`  | You did not send the upload token at all.                                                               |         |
| Suspended account                            | `403`  | You sent an authenticated request but your account is suspended.                                        |         |
