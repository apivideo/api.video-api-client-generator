---
title: Progressive Upload Errors - Content Range By Part
meta: 
    description: This guide explains the cause and the possible solutions for the Content Range By Part error.
---

# Progressive Upload Errors Content Range By Part

## Error map

| Error message                                                                                                                    | Status | Description                                                                                                                                                                                        | Details |
| :------------------------------------------------------------------------------------------------------------------------------- | :----- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :------ |
| `The "total_parts" value in the "Content-Range" header must be the same than the one you already set for this video.`            | `400`  | The "total_parts" you already set for this video is X.                                                                                                                                             |         |
| `The "part" value in the "Content-Range" header collides with a previous one you already sent for this video.`                   | `400`  | The part number X has already been sent.                                                                                                                                                           |         |
| `The "part" value in the "Content-Range" header is higher than the maximum number of parts allowed for a video.`                 | `400`  | The "part" value in the "Content-Range" header is higher than the maximum number of parts allowed for a video. The maximum number of parts allowed for a video is 10000.                           |         |
| `The "part" value in the "Content-Range" header is higher than the maximum number of parts allowed for a video.`                 | `400`  | The maximum number of parts allowed for a video is X.                                                                                                                                              |         |
| `The "part" value in the "Content-Range" header must be greater than 0.`                                                         | `400`  | The "part" value in the "Content-Range" header must be greater than 0.                                                                                                                             |         |
| `The "total_parts" value in the "Content-Range" header is higher than the maximum number of parts allowed for a video.`          | `400`  | The "total_parts" value in the "Content-Range" header is higher than the maximum number of parts allowed for a video. The maximum number of parts allowed is 10000.                                |         |
| `The "total_parts" value in the "Content-Range" header is lower than or equal to a part number you already sent for this video.` | `400`  | The "total_parts" value in the "Content-Range" header is lower than or equal to a part number you already sent for this video. Check the "detail" key to see the existing conflicting part number. |         |
| `The "total_parts" value in the "Content-Range" header must be greater than 0.`                                                  | `400`  | The "total_parts" value in the "Content-Range" header must be greater than 0.                                                                                                                      |         |
| `he uploaded file is too small.`                                                                                                 | `400`  | The uploaded file is too small. It must be greater than or equal to 5 MiB (5,242,880 bytes), except if it is the last part.                                                                        |         |
