---
title: "Google Cloud Storage migration guide"
slug: "gcs-migration"
meta:
  description: This page gets users started on how to migrate from Google Cloud Storage to api.video.
---

# Google Cloud Storage migration guide

Planning to migrate api.video from Google Cloud Storage? We got you covered!

Using the api.video Import tool, it only takes a couple of clicks to migrate all of your video content from Google Cloud Storage to api.video. This simple tool only requires read access to your Google Cloud Storage bucket, and that you authenticate yourself with your api.video account.

Check out the [Import Tool](https://import.api.video/) to get started.

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## Granting read access to your GCS bucket

To access your content on Google Cloud Storage, api.video needs read access. This means that you need to grant the Import Tool's external service account read access to GCS objects.

You can grant this access in two quick steps:

* select the project and bucket that you want to migrate
* grant read access to the Import Tool

### 1. Select your project and bucket

* Go to the [Google Cloud Console](https://console.cloud.google.com/), log in to your account and select the project you want to use.
* Navigate to Cloud Storage > Buckets.

![Showing the Buckets menu in Google Cloud Storage](/_assets/get-started/migration-guide/gcs-migration-1.png)

* Select the bucket that you want to migrate.

![Showing the list of buckets in Google Cloud Storage](/_assets/get-started/migration-guide/gcs-migration-2.png)

* Click on the "Permissions" tab and click on "Grant access". This enables you to add a new member to the bucket.

![Showing the Grant access button in Google Cloud Storage](/_assets/get-started/migration-guide/gcs-migration-3.png)

### 2. Grant read access to the Import Tool

Once you have navigated to the bucket that you want to migrate, you need to add the Import Tool as a principal and assign Storage Object Viewer role:

![Showing the Grant access dialog box Google Cloud Storage](/_assets/get-started/migration-guide/gcs-migration-3.png)

* In the "Grant access" dialog box, enter `storage-service-account@video-import-tool.iam.gserviceaccount.com` in the "New principals" input field. 
* In the "Role" dropdown menu, select "Storage Object Viewer".
* Save your changes.

The Import Tool's service account now has read access to the Google Cloud Storage bucket you selected. You can now import videos from this bucket.

## Import the videos

Once you have granted all necessary access, you can start the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select **Google Cloud Storage** from the list.

3. Log into your api.video account. This process links your api.video workspace to the Import tool.

4. If you have multiple api.video projects in your workspace, select the project where you want to migrate your content to.

5. Next, enter the name of the Google Cloud Storage bucket that you want to migrate.

6. Next, the tool retrieves the available videos from Google Cloud Storage. Select the videos you would like to import.

7. Select the videos you want to migrate, and start the import process.

The process displays the upload status and encoding status for each video.