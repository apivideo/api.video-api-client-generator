---
title: "Video Ingestion Errors"
slug: "video-upload-errors"
excerpt: "The errors you might get while authenticating against the API"
---

Video Upload Errors
===================

## Error map

| Error message                                                                                  | Status | Description                                                                                                             | Details |
| :--------------------------------------------------------------------------------------------- | :----- | :---------------------------------------------------------------------------------------------------------------------- | :------ |
| `The uploaded file is empty.`                                                                  | `400`  | You have uploaded an empty file (0 bytes).                                                                              |         |
| `The uploaded file is invalid.`                                                                | `400`  | You have uploaded a file that is not valid for the endpoint you are using. Check the "detail" key for more information. |         |
| `There are more than one uploaded file in the request.`                                        | `400`  | You tried to upload multiple files at once.                                                                             |         |
| `There is no uploaded file in the request.`                                                    | `400`  | You are trying to send a file to our servers but you forgot to fill it in the body of the request.                      |         |
| `The uploaded file is too large.`                                                              | `400`  | If your video is larger than 200 MiB, check [Progressively upload large video]                                          |         |
| `This video source has already been copied from an existing video when the video was created.` | `400`  | This video source has already been copied from an existing video when the video was created.                            |         |
| `This video source has already been downloaded when the video was created.`                    | `400`  |                                                                                                                         |         |
| `This video source has already been uploaded.`                                                 | `400`  |                                                                                                                         |         |
