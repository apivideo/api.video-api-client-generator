---
title: "Bubble.io player element"
slug: "bubbleio-player-element"
excerpt: "Bubble is a no-code tool that lets you build SaaS platforms, marketplaces and CRMs without code. You use the player element to set up video players on your site."
metadata: 
  description: "Bubble is a no-code tool that lets you build SaaS platforms, marketplaces and CRMs without code. You use the player element to set up video players on your site."
---
Bubble.io Player Element
========================

## Basic usage

To add a video player to a page of your site, click on the "api.video player" item of the Visual elements list.
![Imported image](/_assets/Capture%20d’écran%202021-10-05%20à%2015.49.25.png)
Then, draw the player on your page using your mouse, like you do we any other Visual component. Once done, double-click on the player to open the window for editing its properties. 

You should see something like that:

![Imported image](/_assets/Capture%20d’écran%202021-10-05%20à%2015.54.35.png)
As you can see, there is a list of specific attributes that you can use to customize your player:


|   Option name | Mandatory             | Type    | Description                                                                                                  |
| ------------: | --------------------- | ------- | ------------------------------------------------------------------------------------------------------------ |
|            videoid | **yes**               | string  | the id of the video (VoD or live) to play. This value is dynamic. That means that you can get them from your database, or from another element (a list of videos, for instance)                                                                                         |
|          live | no (default: false)   | boolean | indicate that the video is a live one                                                                        |
|      autoplay | no (default: false)   | boolean | start playing the video as soon as it is loaded                                                              |
|         muted | no (default: false)   | boolean | the video is muted                                                                                           |
|  hideControls | no (default: false)   | boolean | the controls are hidden                                                                                      |
|     hideTitle | no (default: false)   | boolean | the video title is hidden                                                                                    |
| showSubtitles | no (default: false)   | boolean | the video subtitles are shown by default                                                                     |
|          loop | no (default: false)   | boolean | once the video is finished, it automatically starts again                                                     |

Once you've entered a videoId, you can click on "preview" to see your player in action. 
![Imported image](/_assets/Capture%20d’écran%202021-10-05%20à%2016.02.57.png)

## Events

The video player element will trigger events during playback, so you will be able to perform some conditional actions like displaying a custom replay button when playback ends, or incrementing a views counter when playback starts.

Here is the list of all events triggered by the player:

|       Event name | Description                                                   |
| ---------------: | ------------------------------------------------------------  |
|            ended | The playback as reached the end of the video                |
|            error | An error occurred                                              |
|        firstplay | The video started to play for the first time                  |
|       mouseenter | The user's mouse entered the player area                      |
|       mouseleave | The user's mouse left the player area                       |
|            pause | The video has been paused                                     |
|             play | The video started to play (for the first time or after having been paused)  |
|            ready | The player is ready to play                                   |
|           resize | The video size has changed                                    |
|          seeking | The player is seeking                                         |

To perform an action when an event occurs, go to the workflow tab, create a new workflow, and as a trigger condition, select "Elements -> A api.video player ..." : 

![Imported image](/_assets/Capture%20d’écran%202021-10-06%20à%2015.08.15.png)

## Exposed state

The player element has a single exposed state. This contains the "timeupdate" value, which corresponds to the time of the playback progress in seconds. This value is sent several times per second and allows to follow precisely the progression of the playback.

## Actions

The player exposes several actions that can be triggered from a workflow. That makes it fully controllable. 

Here is the list of available actions:

|       Action name | Description                                                   |
| ---------------: | ------------------------------------------------------------  |
| play | Start playing the video |
| pause | Pause the video playback |
| mute | Mute the video |
| unmute | Unmute the video |
| hideControls | Hide the player controls |
| showControls | Show the player controls |
| hideSubtitles | Hide the player subtitles |
| showSubtitles | Show the player subtitles |
