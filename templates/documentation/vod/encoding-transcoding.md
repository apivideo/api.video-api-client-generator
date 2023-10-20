---
title: Encoding and transcoding
metadata:
  description: This guide explains what video encoding and transcoding means and how api.video enables you to provide your users high quality videos via encoding and transcoding.
---

## Encoding

The term encoding is used to describe the process of converting content into a different form. In the context of video - encoding technically only happens when the video is being recorded, and taken from the camera's raw format and saved into a [codec](https://api.video/what-is/codec/). Any further manipulations of the video file (say changing the format, or changing compression values) is transcoding. But, in practice, these two terms are used interchangeably to describe the process of converting a video file from one format to another.

### Why encode a video?
Video files are inherently large, so using a codec to compress the video ensures that the file will be much smaller. As codecs get better at compression, we are able to transmit higher quality video for the same amount of data. For that reason, there is a lot of research into new codecs.

There are other considerations to undertake when choosing how to encode your video. The [H264](https://api.video/what-is/h-264/), while older and not the best compression, does support the highest browser support - making it a common encoding choice. Newer codecs like [H265](https://api.video/what-is/h-265/), VP9, and AVIF have better compression, but suffer slightly in the realm of browser support for playback.

### Encoding settings
When using a tool like FFMPEG, you can set the video quality. This determines how much compression there will be/how good the video will look. Higher compression leads to smaller files, but also higher [loss](https://api.video/what-is/lossy-compression/). FFMPEG uses the Constant Rate Factor for encoding quality (values of 0 are lossless, 51 is high compression.) The default value for CRF is 23, and generally creates a video that offers a high compression/quality ratio:

```
ffmpeg -i input -c:v libx264 -crf 23 output.mp4
```

### Encoding at api.video

Every video uploaded to api.video is encoded/transcoded into an H264 MP4 and an HLS video stream with up to 6 different sizes (240p, 360p, 480p, 720p, 1080p, 2160p). This ensures that video playback can occur on any device (the MP4), and along with our video player (or any other video player that supports HLS), your video can stream to the web quickly and efficiently.

## Transcoding

Transcoding is the process of taking already encoded (or transcoded) content, decompressing it and then using different codecs to alter or recompress it. It's labor intensive because you are taking every part of your video and audio and recompressing it. During this process, depending on what codecs you use, you may lose data.

### What types of transcoding are there?

There are three types of transcoding you will encounter:

- Lossy-to-lossy - This is where you take a codec that is lossy and transcode to another format that uses a lossy codec. You will lose lots of data during this process, and transcoding will lower the quality of your video.
- Lossless-to-lossy - This is where you take a codec that preserves every detail of a video and transfer it to one that loses data. The trade off results in something useful like faster decompression for playback, or a smaller file.
- Lossy-to-lossless - You cannot regain lost quality, so when transcoding in this situation, you retain all the data that was in the lossy compression without further degradation of the file.

### What are the goals of transcoding?
The goals of transcoding are dependent upon your use case. The main reason to transcode video is because you want to use it in a new scenario with a codec that's optimized for that new scenario. For example, say you want to edit a video. You would probably want a codec that's good quality, but which lets you easily move backwards and forwards through the video so you can quickly find the parts of the video you want to clip and edit. A great example of a codec for this purpose would be ProRes. This codec doesn't work for playback with a lot of devices though. So after you're done editing, you would want to transcode to another codec that works for playback.

Other scenarios might be something like wanting to store a video for later use. When you store a video, if it's for a project, you might want to store the highest quality possible. Then when you get the video out later to edit again or use in a project, it's got as much information available as possible. Or, maybe you're storing a video just for reference purposes and you don't have a lot of space. Then you might want a codec that compresses efficiently but the video quality is lower. That would be enough for reference later.

### Transcoding and api.video

api.video transcodes your video to HLS for fast, high quality playback across the internet. If you're interested in learning more about transcoding, check out our article [Encoding and Transcoding - What's the Difference?](https://api.video/blog/video-trends/what-is-the-difference-between-encoding-and-transcoding/).