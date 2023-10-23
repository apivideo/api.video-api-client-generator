---
title: Backup your videos to Google Storage account
meta:
  description: Backup videos uploaded to api.video to Google Storage account with a simple script.
---

# Video backup to Google Storage account

At [api.video](https://api.video/) we understand that sometimes, you want to make sure that your videos are safe and secure. Although api.video has a backup and disaster recovery plan, it’s up to you to decide if you want an extra backup resource or if you would like to use api.video just to transcode your videos but eventually store them on a different resource.

That’s why, we’ve created this guide so you can store the videos you’ve already transcoded with [api.video](https://api.video/) on a file storage resource.

## How it’s done?

This guide will explain how you can store your transcoded videos on Google Storage account. In short, all you need to do is run a small script that will copy the videos from [api.video](https://api.video/) to Google Storage account. The videos will be kept on api.video as well, however, if you wish to delete them you can do so by leveraging the `DELETE /videos` endpoint, more information can be found [here](https://docs.api.video/vod/delete-a-video).  

## Preparation

**What we will need to run the script?**

1. **[api.video](https://api.video/) API key**, you can find the information on how to retrieve the <a href="https://docs.api.video/reference/authentication-guide#retrieve-your-apivideo-api-key" target="_blank">API key in the Retrieve your api.video API key guide</a>
2. Preparing to the backup to Google account, you can follow the below steps. You will need to <a href="https://support.google.com/a/answer/7378726?sjid=1556077145065480779-EU" target="_blank">create a service account for the Storage account</a>
4. api.video <a href="https://github.com/apivideo/backup-cold-storage" target="_blank">Cold Storage script</a>,
5. **Node.js** and **npm**, you can find the installation instructions <a href="https://docs.npmjs.com/downloading-and-installing-node-js-and-npm" target="_blank">here</a>
6. **Typescript**, you can find the installation instructions <a href="https://www.npmjs.com/package/typescript" target="_blank">here</a>

### Setting up service account and getting the credentials

- On Google Cloud Platform, navigate to the menu and select `APIs and services`
- Select `Enabled APIs and services`
![](/_assets/vod/backup-and-cold-storage//gcp-1-api-services.png)
- Click on `+ ENABLE APIS AND SERVICES`
![](/_assets/vod/backup-and-cold-storage/gcp-2-enable-api.png)
- Search for `Storage` and select `Storage Cloud`
![](/_assets/vod/backup-and-cold-storage/gcp-3-cloud-storage.png)
- If the API is not enabled, click on `Enable API`, and `Manage`
- In the `API services details` click on `+ Create credentials`
- Select `Service account`
![](/_assets/vod/backup-and-cold-storage/gcp-4-create-service-account.png)
- Fill out the details in the `Create Service Account` screen, click on `Create and Continue`
![](/_assets/vod/backup-and-cold-storage/gcp-5-add-service-account.png)
- In the optional pane `Grant this service account access to the project` grant the Storage Admin role and click continue
![](/_assets/vod/backup-and-cold-storage/gcp-6-add-roles.png)
- Add the user to the service account and click `Done`
![](/_assets/vod/backup-and-cold-storage/gcp-7-grant-user-access.png)
- On the `API/Service details` click on the Service account that was just created, under Service Accounts
![](/_assets/vod/backup-and-cold-storage/gcp-8-navigate-to-service-account.png)
- In the Service Account screen, navigate to the `Keys`tab
![](/_assets/vod/backup-and-cold-storage/gcp-9-navigate-to-keys-add-key.png)
- Click on `Add Key` and `Create new key`, a pop-up will appear, select `JSON` and then `Create`.
![](/_assets/vod/backup-and-cold-storage/gcp-10-export-json.png)
- This action will trigger a download of a JSON file that will storage on your machine/
![](/_assets/vod/backup-and-cold-storage/gcp-11-json-confirmation.png)
- Copy the content of the JSON file
![](/_assets/vod/backup-and-cold-storage/gcp-12-open-json.png)
- Copy the content into the `.env` in the `GCP_KEY` parameter. Note that the parameter should be a string, so encapsulate the JSON into `''`
![](/_assets/vod/backup-and-cold-storage/gcp-14-env-file.png)

## Getting Started

After you’ve got all the keys and installed node.js, npm and typescript, you can proceed with cloning the script from GitHub.

### Cloning the Cold Storage script

1. In the command line enter the following

```bash
$ git clone https://github.com/apivideo/backup-cold-storage
```

1. Once the script is cloned, you can navigate the script directory

```bash
$ cd back-cold-storage
```

### Setting up the script

Once you are in the script directory, install the dependencies

```bash
$ npm install
```

After the dependencies are installed, we will need to enter the credentials we have copied in the preparation phase.

Edit the `.env` file and replace the following with the keys you've received from Google and [api.video](https://api.video/). 

```bash
# possible providers: google, aws, azure
PROVIDER = "google"

# api.video API key
APIVIDEO_API_KEY = "apivideo_api_key"

# the name of the bucket on Google Amazon S3 or the container on Azure Storage
SPACE_NAME = "google_bucket_name"

# Google credentails
GCP_KEY = '{ google json key
}'
```

Don’t forget to save the file. 

## Running the backup

Once you've got all the keys in place in the `.env` file, it's time to run the script. As we are running the script on TypeScript, we will need to build it first, hence, the first command you need to run in the script folder is:

```bash
$ npm run build
```

After the script was built, it’s time to run it:

```bash
$ npm run backup
```