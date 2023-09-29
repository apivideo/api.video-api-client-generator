---
title: "Vimeo migration guide"
slug: "vimeo-migration"
meta:
  description: This page gets users started on how to migrate from Vimeo to api.video.
---

# Vimeo migration guide

Planning to migrate api.video from Vimeo? We got you covered!

There are two methods at your disposal that you can use to migrate all of your video content from S3 to api.video.

- Migrate with api.video Import tool
- Migrate using your own script

{% capture content %}

Migrating from Vimeo requires that you have at least a Pro subscription or higher with Vimeo. This limitation is set by Vimeo, and is a requirement for video file access.

{% endcapture %}

{% include "_partials/callout.html" kind: "warning", content: content %}

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## api.video Import Tool

With api.video, you can use our in-house import tool to migrate all of your videos in a short time from your previous hosting and video provider.

This simple tool only requires a Vimeo access token, and that you authenticate yourself with your api.video account.

Check out the [Import Tool](https://import.api.video/) to get started.

### Retrieve your Vimeo access token

To access your content on Vimeo, api.video needs an access token. This access token is used to retrieve the list of your videos, and to access the video source files.

You can generate Vimeo access tokens is in two quick steps:

* create a Vimeo application
* create an access token for this application

#### 1. Create a Vimeo application

To create an application, go to your [Vimeo applications](https://developer.vimeo.com/apps), and click on **Create an app**.

![](/_assets/get-started/migration-guide/vimeo-migration-1.png)

#### 2. Create an access token

Once your app is ready, Vimeo redirects you to the application settings page. You can generate access tokens on this page.

In the **Authentication** section:

* select *Authenticated (you)*
* check the *Private* and *Video Files* check boxes, and then click on Generate.

![](/_assets/get-started/migration-guide/vimeo-migration-2.png)

The access token that you generate appears on the page. You can use the Token value in the api.video [Import Tool](https://import.api.video/) to authenticate your Vimeo account for the import process:

![](/_assets/get-started/migration-guide/vimeo-migration-3.png)

### Importing the videos

Once you have all the credentials, you can start the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select **Vimeo** from the list.

3. Log into your api.video account. This process links your api.video workspace to the Import tool.

4. If you have multiple api.video projects in your workspace, select the project where you want to migrate your content to.

5. Next, click on Sign in to Vimeo and paste in the Vimeo access token that you generated.

6. Next, the tool retrieves the available videos from Vimeo. Select the videos you would like to import.

7. Now you can proceed with the import.

The process will show you the status of each video and the encoding status.

## Importing with a script (WIP!)

Another way to import your videos is to use your own script and leverage one of the api.video client libraries with the provider's client libraries or API (if there are any).

### Setting up

What you'll need to run the script:

- api.video API key
- AWS credentials
- Node.js

### Getting the api.video API key

In order to get your API key. Navigate to the [api.video dashboard](https://dashboard.api.video/videos) (if you are not already signed up, sign up with api.video) and continue to copy your API key from the **Overview page** or the **API Keys page**

### What the script will do?

The below script is designed to run through all of the files in a specific S3 bucket, generate a signed URL, and pass it on to api.video as the source. api.video will then take the URL and ingest the video directly from S3.

Make sure that the script is modified according to your needs.

### Preparation

If you are new to Node.js, here's how you can run this script.

Make sure that you have Node.js installed, if it doesn't exist on your machine, you can install it by following this [guide](https://kinsta.com/blog/how-to-install-node-js/).

Now that you have Node.js installed, we will have to create the path of the script and install the dependencies.

1. Create the folder for the script: 
```shell
$ mkdir s3-apivideo-migration
```

2. Navigate to the folder
```shell
$ cd s3-apivideo-migration
```

3. Initialize `npm` in order to create the package.json file for the module dependacies
```shell
$ npm init
```

4. Install the modules that we will need to run
```shell
$ npm install @aws-sdk/client-s3 --save
$ npm install @aws-sdk/s3-request-presigner --save
$ npm install @api.video/nodejs-client --save
```

5. Now create an index file that you would like the script to exist in:
```shell
$ touch index.js
```

6. Copy over the below script and replace the following parameters with your own: `videBucket`, `region`, `accessKeyId`, `secrectAccessKey`, `apivideoCreds`

```javascript
import { S3Client, ListObjectsV2Command, GetObjectCommand, S3 } from "@aws-sdk/client-s3";
import { getSignedUrl } from "@aws-sdk/s3-request-presigner";
import  ApiVideoClient from '@api.video/nodejs-client';

// your S3 bucket name
const videoBucket = "videos-cold-storage-api-video";
// your S3 bucket region
const region = 'eu-north-1';
// your S3 credentails
const s3creds = {
    accessKeyId: 'XXXXXX', 
    secretAccessKey: 'XXXXXXX',
    }
// api.video api key
const apivideoCreds = 'XXXXXXX';


const apivideoClient = new ApiVideoClient({ apiKey: apivideoCreds });

const client = new S3Client({ region, 
credentials: s3creds
});

new S3({ region, 
    credentials: s3creds
});

const bucketName = { 
    Bucket: videoBucket, 
  };
const listBucketObjectsCommand = new ListObjectsV2Command(bucketName);

try { 
    const listObjects = await client.send(listBucketObjectsCommand);
    if (listObjects?.$metadata?.httpStatusCode === 200 && listObjects?.Contents.length > 0) {
        for (let i in listObjects.Contents) {
            const objectKey = listObjects.Contents[i].Key;
            bucketName.Key = objectKey;
            const objectDetailsCommand = new GetObjectCommand(bucketName);
            const objectDetails = await client.send(objectDetailsCommand);
            if(/video/.test(objectDetails.ContentType)) {
                const signedUrl = await getSignedUrl(client, objectDetailsCommand, { expiresIn: 3600 });
                const videoCreationPayload = {
                    title: objectKey,
                    source: signedUrl
                };
                await apivideoClient.videos.create(videoCreationPayload);
                console.log("upload complete")
            }
        } 
    }
} catch (e) {
    console.error(e);
}
```

7. Save the file and run the script by:
```shell
$ node index.js
```
