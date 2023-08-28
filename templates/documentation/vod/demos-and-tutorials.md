---
title: "Tutorials and Demos"
slug: "demos-and-tutorials"
excerpt: "api.video's tutorials and demos landing page. You can see a list of content available by endpoint that will help you get started building your video project."
metadata: 
  description: "api.video's tutorials and demos landing page. You can see a list of content available by endpoint that will help you get started building your video project."
---

Demos And Tutorials
===================

A great way to get started with api.video is by checking out our demos and tutorials. See a use case in action for proof of concept, or grab the code and use it for your project. If you're looking for details about a smaller task, try our cookbook tutorials - these are short and sweet walkthroughs for key tasks you'll need to complete various projects with API Video.

## Cookbook Tutorials

Cookbook tutorials are short and sweet descriptions of how to complete common tasks you'll need for your sample application. Think of them like legos you can use to build whatever you want. We've organized them according to the corresponding API endpoint you'd use to complete the task. 

### Authentication Recipes

* [Authentication Steps](https://api.video/blog/tutorials/authentication-tutorial) - Walkthrough the steps for authentication. Every call made to the API.video API requires an access token.

### Video Recipes

* [Video Upload](https://api.video/blog/tutorials/video-upload-tutorial) - Upload a video to api.video
* [Video Upload (Large Videos)](https://api.video/blog/tutorials) - If you upload a large video, you must break it into smaller chunks to ensure delivery. The minimum chunk is 5MB - and we recommend 100MB. This tutorial shows you how to upload your video in pieces.
* [Uploading Large Files with JavaScript](https://api.video/blog/tutorials/uploading-large-files-with-javascript) - A tutorial in JavaScript showing you how to upload large files by breaking your file into chunks for upload.
* [Upload a Video: Discord](https://api.video/blog/tutorials/upload-a-video-discord) - Learn how to upload a video to api.video, then send it to a Discord channel. 
* [Upload a Video with the api.video API Using a Public URL (Python)](https://api.video/blog/tutorials/upload-a-video-with-the-api-video-api-using-a-public-url-python) - Move a video from a public URL to api.video using Python.
* [Upload a Video From Your Computer with the api.video API (Python)](https://api.video/blog/tutorials/upload-a-video-from-your-computer-with-the-api-video-api-python) - Upload a video that's under 128MB.
* [Upload a Big Video File Using Python](https://api.video/blog/tutorials/upload-a-big-video-file-using-python) - Upload a video file over 128MB. With this method, you don't use delegated upload tokens. Code sample is in Python.
* [Delegated Uploads](https://api.video/blog/tutorials/delegated-uploads) - Allow users to upload videos to API video without using your private API key. Instead, give the uploader an upload token.
* [Delegated Uploads for Videos Large and Small (Python)](https://api.video/blog/tutorials/delegated-uploads-for-videos-large-and-small-python) - Use delegated uploads to upload a big or a small video using Python. Code samples for each type of upload are provided.
* [Video Tagging Best Practices](https://api.video/blog/tutorials/video-tagging-best-practices) - Add tags to your videos to simplify searches in your video collection.
* [Adding an "animated GIF" with api.video](https://api.video/blog/tutorials/adding-an-animated-gif-with-api-video) - Create a video that looks like an animated GIF, but without the performance drawbacks a GIF has.
* [Creating Private Videos](https://api.video/blog/tutorials/tutorial-private-videos) - Learn how to create a private video that can only be viewed with single-use tokens.
* [Private Video Upload with a Public Token](https://api.video/blog/tutorials/private-video-upload-with-a-public-token) - Learn how to create a single-use, public token that allows for upload without using your API key. The token can be used to create private, non-downloadable videos.
* [Video Deletion](https://api.video/blog/tutorials/video-deletion) - Delete a video from your account.
* [Listing, Retrieving, and Deleting Delegated Tokens (Python)](https://api.video/blog/tutorials/listing-retrieving-and-deleting-delegated-tokens) - List, retrieve, and delete tokens you created for making uploads using Python.

### Live Stream Recipes

* [Create a Live Stream](https://api.video/blog/tutorials/live-stream-tutorial) - Learn how to live stream your content. 
* [Watching a Live Stream](https://api.video/blog/tutorials/watching-a-livestream) - Create a simple webpage you can use to host your live stream using node.js.
* [Video Streaming with a Raspberry Pi](https://api.video/blog/tutorials/video-streaming-with-a-raspberry-pi) - Stream a video using a Raspberry Pi.
* [Live Stream with OBS and api.video](https://api.video/blog/no-code/live-stream-with-obs-api-video-and-python) - Create a live stream using OBS and api.video. Optionally, display it on a web page using Python. You can also cut n' paste the iframe information or use the player link if you don't want to use the Python code.
* [Live Stream to the Browser with FFMPEG CLI and Python](https://api.video/blog/tutorials/live-stream-to-the-browser-with-ffmpeg-cli-and-python) - Learn how to use FFmpeg at the command line with Python to set up a live stream.
* [Live Stream from Your Android Phone](https://api.video/blog/tutorials/livestream-from-your-android-phone) - Set up a live stream from your Android phone!

### Captions Recipes

* [Adding Captions](https://api.video/blog/tutorials/adding-captions) - Captions allow hearing-impaired users to read the words being said in your video. It also improves watch times and can improve your SEO.
* [Auto Caption a Video](https://api.video/blog/tutorials/auto-caption-a-video) - Automatically add video captions to your videos by combining api.video with the Authot transcription APIs.

### Chapter Recipes
* [Adding Chapters to Your Videos](https://api.video/blog/tutorials/adding-chapters-to-your-videos) - Add chapters to your video to make it easier for a viewer to find the section they want to view. 

### Player Recipes

* [Creating Video Playlists](https://api.video/blog/tutorials/creating-video-playlists) - Use the JavaScript Player SDK to create a video playlist. This is also available as a demo at a.video - [watch.a.video/playlist](https://watch.a.video/playlist).
* [Video Player Customisation](https://api.video/blog/tutorials/tutorial-video-player-customisation) - api.video's player is fully customisable. Learn what you can do to brand our video player.
* [Inserting Custom Headers](https://api.video/blog/tutorials/inserting-custom-headers) - If you want to create a private video, it has a URL token that can be used only once. To authenticate the token, you need to insert a custom header in your player. With api.video's player, this is handled for you, but if you want to use your player, you'll need to learn how to set up the header. Find out how here!
* [Using api.video with 3rd Party Video Players](https://api.video/blog/tutorials/using-api-video-with-external-video-players) - If you want to use a different player than the one offered by api.video, this tutorial will show you how.

### Raw Statistics Recipes

* [Analytics with api.video](https://api.video/blog/tutorials/analytics-with-api-video) - Retrieve and transfer all of the view data for your videos into a database, and learn about the viewers of your videos.
* [Dynamic Metadata](https://api.video/blog/tutorials/dynamic-metadata) - This tutorial shows you how to set variables in video metadata that you can use to segment your audience and track usage based on different audience metrics.
* [Resume a Video](https://api.video/blog/tutorials/resume-a-video) - Use session data to resume videos in the spot where your user left off.
* [Video Analytics: A Primer](https://api.video/blog/tutorials/video-analytics-a-primer) - This tutorial shows you how to track how many people watch your video, where they watch from, how much of a video they watch, whether they rewind or skip ahead, and more.

## Demos

We have two great demo sites for you to check out:

### [a.video](https://a.video/)
For complete, running demos you can use as-is or tweak as needed, check out api.video. We offer numerous sample applications to give you ideas about what our product can do. You can go to GitHub for each project to go through the code, and if there are any concepts you need help with, check out our [Cookbook Tutorials](#cookbooktutorials).

* [Upload a Video](https://a.video/works/upload-a-video) - Video upload from the browser using the delegated token and the file slice API - allows for easy upload of even the largest videos - right from the browser!
* [Upload a Video: To Discord](https://a.video/works/upload-a-video-to-discord) - Connecting the video upload app to the Discord API gives an easy video bot for Discord.
* [Moderate a Video](https://a.video/works/video-moderation) - Video Moderation uses AI to determine if the video's content is inappropriate for your users. When a user uploads a video, it enters a moderation queue and is categorised automatically.
* [Livestream a Video](https://a.video/works/livestream-a-video) - Use this demo app to try livestreaming with api.video right from your browser. Replace the RTMP URL with your personal livestream link, and you'll be using your account.
* [Watch a Video Broadcast](https://a.video/works/watch-a-video-broadcast) - api.video's livestreaming lets you watch a video from anywhere. This page will display the stream (if live) or show the thumbnail if the livestream is not currently broadcasting.
* [Watch a Video Playlist](https://a.video/works/a-video-playlist) - There are many reasons you might like to queue several videos to play one after another. In this application, we use the api.video player and a simple JavaScript array of videoIDs to create a player that plays one video after another.
* [Resume a Video](https://a.video/works/resume-a-video) - When you're interrupted in the middle of a long video - it's great when the service "remembers" where you left off. Learn how to implement this with your api.video account.
* [Sharing a Video: Sending a Video via Live Stream](https://api.video/blog/tutorials/sharing-a-video-sending-video-on-demand-via-livestream) - Watch a video with a friend and chat about it while it plays.
* [Understanding Video Parameters: ffprobe](https://api.video/blog/tutorials/ffprobe) - Learn how to use ffprobe (part of the FFmpeg package) to find out more about a video's parameters. 

###[zap.a.video](https://a.video/zaps/) 
Zapier is a tool that lets you combine your favorite applications in no or low-code solutions. If you don't have a lot of coding experience but you have a great idea, you might be able to set it up with Zapier. Grab a Zapier account, and check out what we have! We've put together a few combinations you can demo. 

* [Send SMS with Twilio on api.Video Upload](https://a.video/zaps/send-sms-on-video-upload) - When a video is uploaded to api.video, send an SMS with Twilio.
* [Upload New Videos from Amazon S3 to api.Video](https://a.video/zaps/upload-new-videos-from-amazon-s3-to-api-video) - When a video is added to an S3 instance, submit it to api.video.
* [Send Slack Messages for New Videos in api.video](https://a.video/zaps/send-slack-messages-for-new-videos-in-api-video) - When a new video is created at api.video, send a message in Slack.
* [Load New VideoAsk Video Response to api.video](https://a.video/zaps/upload-new-videoask-video-responses-to-api-video) - Do you want to share your VideoAsk responses more broadly? Use this VideoAsk-api.video integration to automatically upload new VideoAsk video responses into your api.video account. Easily reuse your video responses outside your VideoAsk account, on social media, your website, and beyond.
* [Upload New Videos from Dropbox to api.video](https://a.video/zaps/upload-new-videos-from-dropbox-to-api-video) - Looking for an easy way to send the new videos from your Dropbox account to your api.video account? Use this integration to have all your new videos from your Dropbox folder automatically uploaded in your api.video account. With no further action needed, find your videos uploaded and encoded, ready to be shared!
* [Send Emails Through Gmail for New Videos in api.video](https://a.video/zaps/send-emails-through-gmail-for-new-videos-in-api-video) - Looking to easily notify your audience by email when you have a new video on your api.video account? This api.video-Gmail integration will trigger automatically every-time a new video is uploaded on your api.video account. Customize your email and spread the news about your new videos quickly!
