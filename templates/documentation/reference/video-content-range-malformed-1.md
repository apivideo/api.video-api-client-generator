---
title: "Range Malformed"
slug: "video-content-range-malformed-1"
---

Video Content Range Malformed
=============================

The value of the "Content-Range" header is malformed, the expected format is "bytes <from_byte>-<to_byte>/<total_bytes>" or "part <part>/<total_parts>".

## Solution

You'll need to figure out what you're doing wrong with your header set up. One easy way to solve this is to try out progressive uploads or use one of our clients! The client handles the header for bytes for you and for progressive uploads you don't have to worry about the bytes for your video.  If you are off by even one byte, the header will be wrong and your upload won't work.

## Learning resources

### Tutorials

* [Video upload (large videos)](https://api.video/blog/tutorials/video-upload-tutorial-large-videos)
* [Upload a big video file using Python](https://api.video/blog/tutorials/upload-a-big-video-file-using-python)
* [Delegated uploads for videos large and small](https://api.video/blog/tutorials/delegated-uploads-for-videos-large-and-small-python)
* [Uploading large files with JavaScript: File.slice() to the rescue!](https://api.video/blog/tutorials/uploading-large-files-with-javascript)
* [Tutorial to upload large video file](https://api.video/blog/tutorials/video-upload-tutorial-large-videos)

### Tools

You can cut down on mistakes by using one of our clients. We offer clients for our API in these languages:

* [Go](https://github.com/apivideo/api.video-go-client)
* [Python](https://github.com/apivideo/api.video-python-client)
* [C#](https://github.com/apivideo/api.video-csharp-client)
* [PHP](https://github.com/apivideo/api.video-php-client)
* [Node.js](https://github.com/apivideo/api.video-nodejs-client)
