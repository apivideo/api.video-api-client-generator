---
title: "AWS migration guide"
slug: "aws-migration"
meta:
  description: This page gets users started on how to migrate from AWS to api.video.
---

# AWS migration guide

Planning to migrate api.video from Amazon S3? We got you covered! 

There are two methods for your disposal that you can use in order to migrate all of your video content from S3 to api.video.

## api.video Import Tool

With api.video, you can use our in-house import tool in order to migrate all of your video in a short time from the popular hosting and video provider.

The simple tool will require you to input serveral parameters from provider you are leaving and from api.video. 

Check out the [Import Tool](https://import.api.video/) now.




### How to get the AWS Access Key and Account Secret

To access AWS, you will need to sign up for an AWS account.

Access keys consist of an access key ID and secret access key, which are used to sign programmatic requests that you make to AWS. If you don't have access keys, you can create them by using the IAM console at https://console.aws.amazon.com/iam/. We recommend that you use IAM access keys instead of AWS root account access keys. IAM lets you securely control access to AWS services and resources in your AWS account.

Note
To create access keys, you must have permissions to perform the required IAM actions. For more information, see Granting IAM User Permission to Manage Password Policy and Credentials in the IAM User Guide.

To get your access key ID and secret access key
Open the IAM console at https://console.aws.amazon.com/iam/.

On the navigation menu, choose Users.

Choose your IAM user name (not the check box).

Open the Security credentials tab, and then choose Create access key.

To see the new access key, choose Show. Your credentials resemble the following:

Access key ID: AKIAIOSFODNN7EXAMPLE

Secret access key: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

To download the key pair, choose Download .csv file. Store the .csv file with keys in a secure location.

Important
Keep the keys confidential to protect your AWS account, and never email them. Do not share them outside your organization, even if an inquiry appears to come from AWS or Amazon.com. No one who legitimately represents Amazon will ever ask you for your secret key.

You can retrieve the secret access key only when you initially create the key pair. Like a password, you can't retrieve it later. If you lose it, you must create a new key pair.