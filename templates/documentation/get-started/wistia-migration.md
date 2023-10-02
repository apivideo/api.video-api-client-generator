---
title: "Wistia migration guide"
slug: "wistia-migration"
meta:
  description: This page gets users started on how to migrate from Wistia to api.video.
---

# Wistia migration guide

Planning to migrate api.video from Wistia? We got you covered!

Using the api.video Import tool, it only takes a couple of clicks to migrate all of your video content from Wistia to api.video. This simple tool only requires a Wistia access token, and that you authenticate yourself with your api.video account.

Check out the [Import Tool](https://import.api.video/) to get started.

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## Create a Wistia access token

To access your content on Wistia, api.video needs an access token. This access token is used to read video and project data in your Wistia account.

You can create a Wistia access token in just a few clicks:

1. **Create new token**

Navigate to your Wistia account, and click on "Account" > "Settings" > "API Access". Then click on "New Token":

![](/_assets/get-started/migration-guide/wistia-1.png)

2. **Set token details**

Give a name to your token, for example "Import tool token", and check the **Read all project and video data** permission. Then click on "Create token":

![](/_assets/get-started/migration-guide/wistia-2.png)

3. **Copy the token**

The access token that you generate appears on the page. You can use the Password value in the api.video [Import Tool](https://import.api.video/) to authenticate your Wistia account for the import process:

![](/_assets/get-started/migration-guide/wistia-3.png)

## Import the videos

Once you have all the credentials, you can start the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select **Wistia** from the list.

3. Log into your api.video account. This process links your api.video workspace to the Import tool.

4. If you have multiple api.video projects in your workspace, select the project where you want to migrate your content to.

5. Next, click on Sign in to Wistia and paste in the Wistia access token that you created.

6. Next, the tool retrieves the available videos from Wistia. Select the videos you would like to import.

7. Select the videos you want to migrate, and start the import process.

The process displays the upload status and encoding status for each video.