---
title: "Contentful"
metadata: 
  title: "Contentful • Plugins • api.video Documentation"
  description: "The official plugin to facilitate the upload, hosting, and sharing of your videos in your Contentful applications."
---
# Contentful

Contentful empowers creative teams to visually craft captivating content experiences - without writing a single line of code, and now you can easily add and deliver videos from your Contentful page with few simple steps using api.video.

## Getting started with Contentful

If you need help with getting started with Contentful, you can follow the [Contentful getting started guide](https://www.contentful.com/help/contentful-101/).

## api.video Contentful plugin features

* Upload videos–up to 4k–directly in the Contentful UI; they’ll be quickly encoded and ready for streaming in seconds.
* Search and select previously uploaded videos from your api.video library.
* Update video titles, descriptions, caption & chapter files, and even tags and metadata in the Contentful UI.
* Embed videos with our responsive & fully customizable HTML5 player.

## Installing the api.video plugin

Log into your Contentful account and add the [api.video app](https://www.contentful.com/marketplace/app/api-video/) by clicking Apps -> Marketplace

![Entering the Contentful Marketplace](/_assets/nocode/contentful/contentful-marketplace.png)

Select the Featured tab and search for the api.video plugin in the search bar

![Searching for the api.video plugin on the Contentful Marketplace](/_assets/nocode/contentful/contentful-search.png)

Once you've found the api.video plugin, click on the icon. You will get a new page where you can install the plugin. Click on Install and authorize the access for api.video

![Authorizing access to api.video](/_assets/nocode/contentful/contentful-auth.png)

Enter your [api.video API key](https://docs.api.video/reference/authentication-guide#retrieve-your-apivideo-api-key) an click Install

![Entering your api.video API key](/_assets/nocode/contentful/contentful-apikey.png)

Congratulations! you have installed the api.video plugin successfully!

## Usage

After a successful installation, you cannow add videos to your content. All you have to do is create a JSON object with the api.video `video appearance` type.

### Adding api.video to the Content Model

1. Create or edit an existing Content Model
2. Add a new field with a JSON object type
![Selecting the JSON object](/_assets/nocode/contentful/contentful-select-object.png)
3. Give the new JSON object a name and api key 
![Naming the JSON field](/_assets/nocode/contentful/contentful-field-name.png)
4. Click on Add and Configure
5. On the configuration page, scroll down to Appearance and select api.video and save the settings
![Selecting the field appearance](/_assets/nocode/contentful/contentful-appearance.png)
6. Now that you have a video JSON object, you can create or edit an entry in the Content and add a video.

### Adding videos to your content

1. In the Content menu, click on add an entry or edit an previous entry (to which you've added the JSON object)
2. Select the Content type that you want to use (the one you've added the JSON object to)
3. Now you will see that you can either upload a video or select from existing videos that you've uploaded to api.video
![Adding an entry with video content](/_assets/nocode/contentful/contentful-new-entry.png)
4. Once you've selected the video you want to add, you will see the video appearing in the content editor
![Showing the video preview for the selected video](/_assets/nocode/contentful/contentful-selected-video.png)
