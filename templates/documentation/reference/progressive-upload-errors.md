---
title: "Progressive Upload Errors - Content Range By Bytes"
slug: "progressive-upload-errors"
hidden: false
createdAt: "2023-03-13T11:34:40.562Z"
updatedAt: "2023-05-11T06:45:25.883Z"
---

Progressive Upload Errors - Content Range By Bytes
==================================================

## Error map

| Error message                                                                                                                                 | Status | Description                                                                                                                                                                                               | Details |
| :-------------------------------------------------------------------------------------------------------------------------------------------- | :----- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :------ |
| `The "total_bytes" value in the "Content-Range" header must be the same than the one you already set for this video.`                         | `400`  | The "total_bytes" you already set for this video is X                                                                                                                                                     |         |
| `The "from_byte" value in the "Content-Range" header is higher than the "to_byte" value.`                                                     | `400`  | The "from_byte" value in the "Content-Range" header is higher than the "to_byte" value.                                                                                                                   |         |
| `The size of the file you sent is different than the range length you set in the "Content-Range" header.`                                     | `400`  | The size of the file you sent is X bytes but the range length you set is Y.                                                                                                                               |         |
| `The range in the "Content-Range" header overlaps with a previous one you already sent for this video.`                                       | `400`  | You send a chunk that you've already sent, for example 2 requests in a row with Content-Range: "bytes 1000-2000/10000". Check the "detail" key for more information.                                      |         |
| `The range in the "Content-Range" header is too small.`                                                                                       | `400`  | The range in the "Content-Range" header is too small. It must be greater than or equal to 5,242,880 (5 MiB), except if it is the last chunk.                                                              |         |
| `The "to_byte" value in the "Content-Range" header is higher than the maximum upper bound allowed for a video.`                               | `400`  | The "to_byte" value in the "Content-Range" header is higher than the maximum upper bound allowed for a video. The maximum upper bound allowed for a video is 52,428,799,999.                              |         |
| `The "to_byte" value in the "Content-Range" header is higher than the "total_bytes" value you set for this video.`                            | `400`  | The "to_byte" value in the "Content-Range" header is higher than the "total_bytes" value you set for this video. Check the "detail" key to see the total you already set.                                 |         |
| `The "total_bytes" value in the "Content-Range" header is higher than the maximum allowed limit.`                                             | `400`  | The "total_bytes" value in the "Content-Range" header is higher than the maximum allowed limit. The maximum allowed limit is 52,428,800,000.                                                              |         |
| `The "total_bytes" value in the "Content-Range" header is lower than or equal to the upper bound of a range you already sent for this video.` | `400`  | The "total_bytes" value in the "Content-Range" header is lower than or equal to the upper bound of a range you already sent for this video. Check the "detail" key to see the existing conflicting range. |         |
| `The "total_bytes" value in the "Content-Range" header must be greater than 0.`                                                               | `400`  |                                                                                                                                                                                                           |         |