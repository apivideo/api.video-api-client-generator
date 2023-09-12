---
title: "Azure migration guide"
slug: "azure-migration"
meta:
  description: This page gets users started on how to migrate from Azure to api.video.
---

# Azure Media Services migration guide

Planning to migrate api.video from Azure Media Services? We got you covered!

There are two methods at your disposal that you can use to migrate all of your video content from Azure to api.video.

- Migrate with api.video Import tool
- Migrate using your own script

## What's the cost? 

We understand that when you want to move to a different provider, it takes effort and development resources. You also want to make sure that it is cost-efficient, especially if you are moving to api.video to save costs.

api.video gives you the ability to **migrate for free** and avoid paying anything for encoding!

## api.video Import Tool

With api.video, you can use our in-house import tool to migrate all of your videos in a short time from the popular hosting and video provider.

The simple tool will require you to input several parameters from the provider you are leaving and from api.video.

Check out the [Import Tool](https://import.api.video/) now.

### Obtaining the SAS URL

To enable the Video Import Tool access to the videos in your Azure Blob Storage containers, you must provide it with the necessary Shared Access Signature (SAS) URL.

1. Navigate to your storage account and click on "Shared Access Signature":

![](/_assets/get-started/azure-migration/azure-4.png)

2. Enable the required parameters:

![](/_assets/get-started/azure-migration/azure-storage-doc-11.png)

3. Ensure that you generate the Shared Access Signature. Once you have the links, copy the Blob Service SAS URL.

![](/_assets/get-started/azure-migration/azure-6.png)

4. Finally, paste the URL into the "SAS URL" field of the Import Tool.

### Importing the videos

Once you have all the pre-requisites you can proceed with the migration.

1. Navigate to the api.video [Import Tool](https://import.api.video/).

2. Select Azure Blob Service from the list

3. Autherize with api.video with your account. This process will link your api.video workspace to the Import tool.

4. Select the project that you would like to import the video.

5. Next, click on Sign In to Azure.

6. Pass in the SAS URL that you've obtained in the previous step

7. Mention the container you would like to import or leave it blank to import all containers.

8. Now you can proceed with the import.

The process will show you the status of each video and the encoding status.

## Importing with a script

Another way to import your videos is to use your own script and leverage one of the api.video client libraries with the provider's client libraries or API (if there are any).

### Setting up

What you'll need to run the script:

- api.video API key
- Azure SAS URL
- Node.js

### Getting the api.video API key

In order to get your API key. Navigate to the [api.video dashboard](https://dashboard.api.video/videos) (if you are not already signed up, sign up with api.video) and continue to copy your API key from the **Overview page** or the **API Keys page**

### What the script will do?

1. The script will check for all assets in your Azure Media Service Instance
2. It will get the Storage containers and list the blobs for these storage containers
3. Because of the way Azure is encoding their videos, the script will take the file with the highest resolution (in order not to duplicate the files)
4. Once it gets the list, it will create a video object with the name same as the blob and set the source to the Azure storage URL
5. The file will then be uploaded and encoded with api.video

Make sure that the script is modified according to your needs.

### Usage

If you are new to Node.js, here's how you can run this script.

Make sure that you have Node.js installed, if it doesn't exist on your machine, you can install it by following this [guide](https://kinsta.com/blog/how-to-install-node-js/).

Now that you have Node.js installed, we will have to create the path of the script and install the dependencies.

1. Clone the script from Github, by running the following in your command line:
```shell
$ git clone https://github.com/apivideo/azure-media-services-api-video-migration
```

2. Navigate to the folder you've just cloned
```shell
$ cd azure-media-services-api-video-migration
```

3. Import the modules that you need to run the script:
```shell
$ npm install @api.video/nodejs-client // api.video node.js client, in order to create the video object, upload and encode the video
$ npm install @azure/identity // access Azure services
$ npm install @azure/arm-mediaservices // client to access Azure media services
```

4. You'll need to make sure that you replace all the credentials in the `.env` file, which are represented below, with the credentials you'll get from Azure in the steps following this one, and replace the API key you got from api.video:

```javascript

let mediaServicesClient: AzureMediaServices;
    
    const subscriptionId: string = process.env.AZURE_SUBSCRIPTION_ID as string;
    const resourceGroup: string = process.env.AZURE_RESOURCE_GROUP as string;
    const accountName: string = process.env.AZURE_MEDIA_SERVICES_ACCOUNT_NAME as string;
    
    const credential = new DefaultAzureCredential();
    const apivideoClient = new ApiVideoClient({ apiKey: process.env.APIVIDEO_API_KEY });
    
    let remoteSasUrl: string = process.env.REMOTESTORAGEACCOUNTSAS as string;
```
![](/_assets/get-started/azure-migration/azure-1.png)
{% capture content %}
Note that in the previous step, you will also need to update the .env file and grab the parameters from Azure. In order to do that, navigate to your Azure Media Service, and select the directory that you would like to migrate.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}


5. Select API keys and copy over the parameters presented in the `.ENV` pane (you can select either User Authentication or Service principal authentication)

![](/_assets/get-started/azure-migration/azure-2.png)

![](/_assets/get-started/azure-migration/azure-3.png)

6. To get access to the storage, navigate to the Azure Storage Account → Shared access signature:

![](/_assets/get-started/azure-migration/azure-4.png)

7. Allow the following:

![](/_assets/get-started/azure-migration/azure-5.png)

![](/_assets/get-started/azure-migration/azure-6.png)

Make sure that you generate the Shared access signature and once you have the links, copy the Blob service SAS URL link to the `.env` file. The parameter you are looking for is `REMOTESTORAGEACCOUNTSAS`

8. Add the api.video API key to the .env file by navigating to the [api.video dashboard](https://dashboard.api.video/overview) and copy the API key value

![](/_assets/get-started/azure-migration/azure-7.png)

9. Now, `run npm install` in the terminal in order to install all of the node modules
10. Run `npm run build`
11. Run `npm run start` in order to start the migration script
