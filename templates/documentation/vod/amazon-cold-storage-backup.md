---
title: "Backup your videos to Amazon S3"
slug: "amazon-cold-storage-backup"
meta:
  description: Backup videos uploaded to api.video to Amazon Storage account with a simple script. 
---
# Video backup to Amazon S3

At [api.video](https://api.video/) we understand that sometimes, you want to make sure that your videos are safe and secure. Although api.video has a backup and disaster recovery plan, it’s up to you to decide if you want an extra backup resource or if you would like to use api.video just to transcode your videos but eventually store them on a different resource.

That’s why, we’ve created this guide so you can store the videos you’ve already transcoded with [api.video](https://api.video/) on a file storage resource.

## How it’s done?

This guide will explain how you can store your transcoded videos on Amazon S3 Account. In short, all you need to do is run a small script that will copy the videos from [api.video](https://api.video/) to Amazon S3. The videos will be kept on api.video as well, however, if you wish to delete them you can do so by leveraging the `DELETE /videos` endpoint, more information can be found [here](https://docs.api.video/vod/delete-a-video).  

## Preparation

**What we will need to run the script?**

1. **[api.video](https://api.video/) API key**, you can find the information on how to retrieve the <a href="https://docs.api.video/reference/authentication-guide#retrieve-your-apivideo-api-key" target="_blank">API key in the Retrieve your api.video API key guide</a>
2. <a href="https://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide/getting-started-nodejs.html#getting-started-nodejs-credentials" target="_blank">Amazon S3 credentials</a>
3. Copy the Amazon S3 region from the console
4. api.video <a href="https://github.com/apivideo/backup-cold-storage" target="_blank">Cold Storage script</a>,
5. **Node.js** and **npm**, you can find the installation instructions <a href="https://docs.npmjs.com/downloading-and-installing-node-js-and-npm" target="_blank">here</a>
6. **Typescript**, you can find the installation instructions <a href="https://www.npmjs.com/package/typescript" target="_blank">here</a>

### Getting the Amazon S3 credentials

You can find the guide of getting the Amazon S3 Access Key id and Secret Key [here](https://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide/getting-started-nodejs.html#getting-started-nodejs-credentials)

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

Edit the `.env` file and replace the following with the keys you've received from AWS and [api.video](https://api.video/). 

```bash
# possible providers: google, aws, azure
PROVIDER = "aws"

# api.video API key
APIVIDEO_API_KEY = "api_video_api_key"

# the name of the bucket on Google Amazon S3 or the container on Azure Storage
SPACE_NAME = "your_bucket_name"

# Amazon S3 credentials
AWS_ACCESS_KEY_ID = "amazon_access_key_id"
AWS_SECRET_ACCESS_KEY = "amazon_secret_key"
AWS_REGION = "s3_bucket_region"
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