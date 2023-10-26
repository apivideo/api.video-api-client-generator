---
title: Wordpress
meta: 
  description: The official plugin to facilitate the use of api.video on your WordPress website through uploading, embedding, listing, and managing your videos.
---

# Wordpress

With the api.video Wordpress plugin you can upload and embed your videos into your Wordpress website effortlessly.

The plugin allows you to:

- list all of your uploaded videos
- upload new videos
- manage existing videos

## Pre-requisting

- WordPress website
- api.video account

## Installation

In order to install the api.video Wordpress plugin, just navigate to the Plugin tab in your Wordpres admin panel and search for "api.video"

`Plugin installation from the plugins directory image`

Click on the "Install Now" button and activate the plugin as soon as the installation is complete.

## Setting the api.video plugin

Once the plugin is installed, you'll need to configure it. 

- Navigate to the api.video dashboard and copy the API key
- Navigate back to the Wordpress admin panel
- You should have a new icon on the nav bar from the left hand side called "api.video", open the menu and select Settings
- Paste your API key in the respective field and save the changes 

## Usage

After you've configured the api.video plugin, you can now start using it. You will noticed several button that are up for you disposal:

- **Library:** Will automatically populate all of the videos in your account (if there are any existing).
- **Add new:** will allow you to upload and add new videos to your library.

### Uploading a video

In order to upload a video, all you have to do is navigate to the `Add new` in the api.video plugin, from there you can either drag and drop the video or select it from your file system.

### Listing your videos

You can find the list of all the videos you have uploaded to your api.video account by clicking on api.video » Library. For each of your videos, you will see the associated thumbnail and its title.

### The list of all your videos

If you want to integrate one of the videos into an article and then modify it, click on the associated thumbnail. You will be redirected to the video editing page.

### Managing videos

You can manage a specific video object and update some of it's properties. The video details include the following parameters:

- Update the video title.
- Update the video description.
- Change video privacy status (Private/public). More information on private videos can be found [here](../../delivery-analytics/video-privacy-access-management.md).
- Enable / disable panoramic video mode.
- Enable / disable the ability of a viewer to download the video.
- Add / remove video tags.
- Delete the video.
- Download the video.

You can access the video management page right after you've uploaded the video or by selecting an entry in the videos list.

## Embedding a video in a post/page

In order to embed the video into your page, you will have to copy the video shortcode first from video management. 

- Select to the video you would like to embed from the Library.
- Copy the video short code. Example: `[api.video video_id=123456789]`.
- Navigate to the Wordpress page you would like to edit and where you would like to embed the video.
- At the place where you want the video to appear, insert the "shortcode" block into your article. One way to do that is to show the block inserter by clicking on the big + at the top left of the page. Then type "shortcode" in the search input and click on the Shortcode element.
- Once the block has been added in your article, you now have to paste the value you copied in the video editing page (it should look like `[api.video video_id=123456789]`).
- Paste the shortcut value into the dedicated input

{% capture content %}

In most cases (but it depends on your WordPress theme), you will get better results if you transform the block to "Columns". To do that, click on the first icon above the block and select Columns.

{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

And that's it! Once your article is saved, you will see the video appear where you inserted it.

Your video is now embedded in your article.
