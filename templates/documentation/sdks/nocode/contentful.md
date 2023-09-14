---
title: "Contentful"
slug: "contentful"
excerpt: "The official plugin to facilitate the use of api.video in your Contentful applications."
metadata: 
  title: "Contentful • Plugins • api.video Documentation"
  description: "The official plugin to facilitate the upload, hosting and sharing of your videos in your Contentful applications."
---
Contentful
==========

Using [api.video with Contentful](https://www.contentful.com/marketplace/app/api-video/) is easy!

Log into your Contentful account and add the [api.video app](https://www.contentful.com/marketplace/app/api-video/) by clicking "Apps" and then "Manage apps" in the main navigation bar.

Input your api.video API key first. You'll also notice that a list of your JSON objects is listed. The api.video app should be connected to JSON object types in Contentful. If you don't see any objects listed, that's ok. We'll add a JSON object in the next step.

![](/_assets/apivideoapp.jpg)

## Adding api.video to content models

In this section, we'll add api.video to 2 different content models:

1. A new JSON object
2. Add it to the Component:Video Object


### A new JSON object

In the top menu, select "Content Model," and a new page will open with all the models/components in your app. Choose the "Add content type" button on the top right to begin creating a new type:

1. The video field
First, you will name your model, and then the UI will walk you through adding fields. Click the blue button to add a field and add a JSON Object. You'll be asked to name the field - choose a name that reminds you that it is an api.video object that is being added.

Click the "Create and Configure" button, and under "Appearance," choose api.video.

2. Name field:
 Create a new field, text, Short text, and when you configure the item, click the "Entry title" button. (If you do not add this step, every object you add will be named "Untitled," which is less than ideal.)

### Modifying the Component:video model

Contentful has a built-in video model to which you can add the api.video app. Let's walk through the steps to do that.

In the "Content Model" section, click on the "Component:Video" model. This will give you a page that shows all the fields in the model. It has 2 fields, the title, and a YouTube Video Id by default. We'll modify this so that you can still use a YouTube video Id, but you can also add an api.video object.

#### Add the api.video object

Click the "add field" button on the right. Add a JSON object, and name the field "apivideo" or another term that helps you remember where the videos will be stored. Click the "Create and Configure" button, and then under Appearance, add the api.video app.


#### Edit the YouTube object

The Component:Video model is designed to only work with YouTube, and one of the settings is that the YouTube video Id is required. We're making this model more flexible (YouTube AND api.video), so we need to remove this requirement. Click the settings button, and under "Validation," you'll uncheck the "Required field" checkbox.

## Adding api.video videos to Contentful

The process is very similar - no matter which type of object you created in the last step.

### Using the Content section

In the top menu, choose content, and then pick the model you wish to use (in the screenshots, I've used the Component:Video model.)


The title is 'My Awesome video,' and I'm skipping the YouTube section - it's no longer required! With the api.video app, you can choose a video already uploaded to api.video ("select an existing video") or upload a new video (descriptively named "upload a new video").

![](/_assets/addingavideo.png)

Now publish the content, and you've added the video for playback!

You can also add the content model to a page you are building and follow the same steps to add an api.video to your site.
