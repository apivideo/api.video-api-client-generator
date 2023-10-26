---
title: Progressive Upload Errors - General
meta: 
    description: This guide explains the cause and the possible solutions for the General Progressive Upload error.
---

# Progressive Upload Errors General

## Error map

| Error message                                                                                                                                                  | Status | Description                                                                                                                                                  | Details |
| :------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- | :------ |
| `This "Content-Range" upload has been started with "part", you cannot continue it with "bytes".`                                                               | `400`  | This "Content-Range" upload has been started with "part", you cannot continue it with "bytes".                                                               |         |
| `This "Content-Range" upload has been started with "bytes", you cannot continue it with "part"`                                                                | `400`  | This "Content-Range" upload has been started with "bytes", you cannot continue it with "part".                                                               |         |
| `The value of the "Content-Range" header is malformed, the expected format is "bytes \<from_byte>-\<to_byte>/\<total_bytes>" or "part <part>/\<total_parts>".` | `400`  | The value of the "Content-Range" header is malformed, the expected format is "bytes \<from_byte>-\<to_byte>/\<total_bytes>" or "part <part>/\<total_parts>". |         |
| `This video source has already been initiated with a "Content-Range" upload.`                                                                                  | `400`  |                                                                                                                                                              |         |
