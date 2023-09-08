---
title: "AWS migration guide"
slug: "aws-migration"

meta:
description: This page gets users started on how to migrate from AWS to api.video.
---

# AWS migration guide

Planning to migrate api.video from Amazon S3? We got you covered!

There are two methods at your disposal that you can use to migrate all of your video content from S3 to api.video.

- Migrate with api.video Import tool
- Migrate using your own script

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## api.video Import Tool

With api.video, you can use our in-house import tool to migrate all of your videos in a short time from the popular hosting and video provider.

The simple tool will require you to input several parameters from the provider you are leaving and from api.video.

Check out the [Import Tool](https://import.api.video/) now.

### How to get the AWS Access Key and Account Secret

To access AWS, you will need to sign up for an AWS account.

Access keys consist of an access key ID and secret access key, which are used to sign programmatic requests that you make to AWS. If you don't have access keys, you can create them by using the [IAM console](https://console.aws.amazon.com/iam/).

We recommend that you use IAM access keys instead of AWS root account access keys. IAM lets you securely control access to AWS services and resources in your AWS account.

{% capture content %}

To create access keys, you must have permission to perform the required IAM actions. For more information, see Granting IAM User Permission to Manage Password Policy and Credentials in the IAM User Guide.

{% endcapture %}

{% include "_partials/callout.html" kind: "info", content: content %}

**To get your access key ID and secret access key:**
- Open the [IAM console](https://console.aws.amazon.com/iam/).
- On the navigation menu, choose Users.
- Choose your IAM user name (not the check box).
- Open the Security credentials tab, and then choose Create access key.
- To see the new access key, choose Show. Your credentials resemble the following:

```

Access key ID: AKIAIOSFODNN7EXAMPLE

Secret access key: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

```

- To download the key pair, choose Download .csv file. Store the .csv file with keys in a secure location.

{% capture content %}

Keep the keys confidential to protect your AWS account, and never email them. Do not share them outside your organization, even if an inquiry appears to come from AWS or Amazon.com. No one who legitimately represents Amazon will ever ask you for your secret key.

{% endcapture %}

{% include "_partials/callout.html" kind: "warning", content: content %}

You can retrieve the secret access key only when you initially create the key pair. Like a password, **you can't retrieve it later.** If you lose it, you must create a new key pair.

### Importing the videos

Once you have all the credentials, you can proceed with the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select Amazon S3 from the list.

3. Autherize with api.video with your account. This process will link your api.video workspace to the Import tool.

4. Select the project that you would like to import the video.

5. Next, click on Sign in to Amazon S3 and fill in the AWS Access Key and AWS Secret Key you got from Amazon S3.

6. Select the S3 bucket you would like to import.

7. Now you can proceed with the import.

The process will show you the status of each video and the encoding status.

## Importing with a script

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
