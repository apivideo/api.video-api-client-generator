---
title: "Vimeo migration guide"
slug: "vimeo-migration"
meta:
  description: This page gets users started on how to migrate from Vimeo to api.video.
---

# Vimeo migration guide

Planning to migrate api.video from Vimeo? We got you covered!

Using the api.video Import tool, it only takes a couple of clicks to migrate all of your video content from Vimeo to api.video. This simple tool only requires a Vimeo access token, and that you authenticate yourself with your api.video account.

Check out the [Import Tool](https://import.api.video/) to get started.

{% capture content %}

Migrating from Vimeo requires that you have at least a Pro subscription or higher with Vimeo. This limitation is set by Vimeo, and is a requirement for video file access.

{% endcapture %}

{% include "_partials/callout.html" kind: "warning", content: content %}

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## Retrieve your Vimeo access token

To access your content on Vimeo, api.video needs an access token. This access token is used to retrieve the list of your videos, and to access the video source files.

You can generate Vimeo access tokens is in two quick steps:

* create a Vimeo application
* create an access token for this application

### 1. Create a Vimeo application

To create an application, go to your [Vimeo applications](https://developer.vimeo.com/apps), and click on **Create an app**.

![](/_assets/get-started/migration-guide/vimeo-migration-1.png)

### 2. Create an access token

Once your app is ready, Vimeo redirects you to the application settings page. You can generate access tokens on this page.

In the **Authentication** section:

* select **Authenticated (you)**
* check the **Private** and **Video Files** check boxes, and then click on Generate.

![](/_assets/get-started/migration-guide/vimeo-migration-2.png)

The access token that you generate appears on the page. You can use the Token value in the api.video [Import Tool](https://import.api.video/) to authenticate your Vimeo account for the import process:

![](/_assets/get-started/migration-guide/vimeo-migration-3.png)

## Import the videos

Once you have all the credentials, you can start the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select **Vimeo** from the list.

3. Log into your api.video account. This process links your api.video workspace to the Import tool.

4. If you have multiple api.video projects in your workspace, select the project where you want to migrate your content to.

5. Next, click on Sign in to Vimeo and paste in the Vimeo access token that you generated.

6. Next, the tool retrieves the available videos from Vimeo. Select the videos you would like to import.

7. Select the videos you want to migrate, and start the import process.

The process displays the upload status and encoding status for each video.