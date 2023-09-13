---
title: "Bubble.io API calls"
slug: "bubbleio-api-calls"
excerpt: "You can use the api.video Bubble plugin as a bridge between your Bubble application and the api.video API."
---

Bubbleio API Calls
==================

The api.video Bubble plugin contains some API calls definition that will act as a bridge between your Bubble application and the api.video API. Not all the api.video endpoints are used this way but the most important are there. 

## Authentication

In order to use the API, you have to put your api.video private API key in the dedicated input field of the plugin settings.

To do this, go to the "Plugins" tab on the left, select "Api.video" and paste the value of your api key into the "Api key" field:

![](/_assets/bubbleio_2.png)

## Action API calls

Calls of type "action" are intended to trigger an action at api.video (e.g., delete a video). These calls do not return any value. They are performed from a Bubble workflow.

### Delete a video

This API call will delete a video. It has one required parameter: `videoId,` which is the id of the video to delete. 

*Example: delete a video from a workflow*

Let's imagine you'd like to delete a specific video when a user clicks on a button. Here is how to proceed:
- first, create a new button element, go in its properties, and click on "Start/edit workflow":


![](/_assets/bubbleio_9.png)

- in the workflow page, click on "Click here to add an action" and select "authenticate - delete a video" in the "Plugins" tab:


![](/_assets/bubbleio_10.png)

- the properties popup of the action appears, enter the video id you want to delete in the "(path) videoId" field, and you're all done. 

_Note: in a real world application, you'll probably not use a hard-coded video id. You'd rather use a video id stored in your Bubble database. You can easily do it since the videoId value is a dynamic one._

### Delete an upload token

This API call will delete an upload token. It has one required parameter: `uploadToken`, which is the upload token to delete. 

A typical use case of this action would be to delete an upload token once a video has been uploaded using this token. 


## Data API calls

The purpose of "data" calls is to retrieve information from api.video (for example: retrieve the list of all your videos). These calls are not made from a workflow but will be used as a dynamic source for elements of your app (for example, you can use the list of your videos as a source for a "repeating group").


### Generate upload token

This API call will request a new upload token from api.video and return its value. It's useful when you have a video uploader element in your application. 

For example, you can create an upload token for each user of your application. To do this, you have to modify the normal process of creating users a little.

In the workflow action that sign the users up, click on the "Change another field" button:


![](/_assets/bubbleio_11.png)

Then, select "create a new field", and create a field called "uploadtoken", with the type "text":


![](/_assets/bubbleio_12.png)

Click on the link to associate a value to the field and select "Get data from an external API":


![](/_assets/bubbleio_13.png)

Then, select "authenticate - generate upload token" in the API provider field and clear the "TTL" field to have a token that will never expire:



![](/_assets/bubbleio_14.png)

Finally, close the "authenticate - generate upload token" popup and select "'s token" to use the "token" attribute from the API response:

![](/_assets/bubbleio_15.png)

By doing this, you assign a unique upload token to each user that you can use in the uploader element. This can be considered a good practice because if one of your users abuses the token (if he uploads more than you would like), you'll be able to delete this token from api.video without any impact on your other users.

### Get video status

This API call will return the status of a given video. For more details about video statuses, see [here](/reference/api/Videos#retrieve-video-status-and-details).

In response to this call, one value is beneficial: the "playable" value. It indicates whether your video is ready to be played or not yet. For example, you can display a video player only when the video is ready to be played:


![](/_assets/bubbleio_16.png)

### List all videos

This API call will return the list of all the videos you have in your api.video account. You can apply some video filters to retrieve them based on their title or tags. The result is paginated. The API calls have some parameters to manage the pagination: sortBy, sortOrder, currentPage, and pageSize. You'll find more details about this API endpoint [here](/reference/api/Videos#list-all-video-objects).
