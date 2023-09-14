# Live stream best practices

## Starting a live stream

- When a stream is starting it might take up to 30 seconds for the playback to start delivering.
- Before starting the stream, it is important to have an intro (static picture, countdown and etc) to the beginning of the stream that will allow users to connect before the stream starts.
- In order to prevent the live stream from getting stuck (buffering indefinitely), please ensure that you are following the recommended [settings](#recommended-setting-for-ingestion).
- Make sure to verify that the connection speed is adequate and stable before the stream is starting.

## Ending the live stream

Make sure to use an outro (static picture, music and etc.) at the end of the stream for 30 seconds. This is a good way for users to indicate that the stream has ended.

When reaching the end of the playback, the player buffers until the live stream is deleted, which could take from 10 seconds to 5 minutes and could cause the live stream not to end properly even if you have stopped the broadcast.

## Connectivity loss reconnection

Reconnection is handled by api.video, however, an edge case might occur (very slim chance), which will result in the inability to reconnect to the stream with good quality. In this case, we recommend creating a manual stream reconnection, where the streamer will create a new streaming key while the consumers will have to refresh their player instance.

## Reading stream history

While the stream is live, the user can watch the stream for up to 6 hours in the past.

As soon as the stream ends, we will store the stream for another 4-5 minutes subsequently ending the stream and disposing of the cached video.

## Recommended setting for ingestion

Video codec: H.264  
Audio codec: AAC/MP3  
Bitrate encoding: CBR  
Keyframe Interval: 2 second

| Quality  | Framerate | Video bitrate              | Audio sample rate | Audio Bitrate |
| -------- | --------- | -------------------------- | ----------------- | ------------- |
| 240p     | 25-30 fps | 300-700 Kbps               | 44,1 kHz          | 64 Kbps       |
| 360p     | 25-30 fps | 400-1000 Kbps              | 44,1 kHz          | 128 Kbps      |
| 480p     | 25-30 fps | 500-2000 Kbps              | 44,1 kHz          | 128 Kbps      |
| 720p     | (30 fps)  | 25-30 fps 1500-4000 Kbps   | 44,1 kHz          | 128 Kbps      |
| 720p     | (60 fps)  | 60 fps 2250-6000 Kbps      | 44,1 kHz          | 128 Kbps      |
| 1080p    | (30 fps)  | 25-30 fps 3000-6000 Kbps   | 44,1 kHz          | 128 Kbps      |
| 1080p    | (60 fps)  | 60 fps 4500-9000 Kbps      | 44,1 kHz          | 128 Kbps      |
| 2160(4K) | (30 fps)  | 25-30 fps 13000-34000 Kbps | 44,1 kHz          | 192 Kbps      |
| 2160(4K) | (60 fps)  | 60 fps 20000-51000 Kbps    | 44,1 kHz          | 192 Kbps      |
