---
title: "api.video backup and cold storage"
slug: "backup-and-cold-storage"
meta:
  description: Backup your uploaded videos to your preferred provider.
---

# Backup & Cold Storage

It's important to feel safe and know that the content you've uploaded is backed up. While api.video got you covered by backing up the videos and having a disaster recovery plan, we understand that you might want to take an extra step and have your own backup.

There are also cases where you would only want to use api.video's transcoding power but then host your transcoded videos on a different storage solution.

With this in mind, we've created ways for you to backup your already transcoded videos to your preferred providers.

In this section, you'll be able to find guides on how to backup the videos hosted on api.video to your Azure Storage account, Amazon S3 and other storage providers.


{% include "_partials/dark-light-image.md" dark: "/_assets/vod/backup-and-cold-storage/backup-and-cold-storage-dark.svg", light: "/_assets/vod/backup-and-cold-storage/backup-and-cold-storage-light.svg", alt: "A diagram that shows the process of backing up videos on different hosting solutions" %}

Below, you will find the scripts that are available for your disposale in order to backup your videos from api.video.

## Available Scripts

<div class="hagrid">

{% include "_partials/hagrid-item.md" title: "Amazon", image: "/_assets/get-started/migration-guide/Amazon-S3-Logo.svg", subtitle: "S3",  link: "./amazon-cold-storage-backup.md" %}
{% include "_partials/hagrid-item.md" title: "Google", image: "/_assets/get-started/migration-guide/Google-Storage-Logo.svg", subtitle: "Storage account",  link: "./google-cold-storage-backup.md" %}
{% include "_partials/hagrid-item.md" title: "Azure", image: "/_assets/get-started/migration-guide/Microsoft_Azure.svg", subtitle: "Storage account",  link: "./azure-cold-storage-backup.md" %}

</div>
