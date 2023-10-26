---
title: Strapi
meta:
  description: With the api.video Strapi plugin you can upload and embed your videos into your Strapi website effortlessly.
---

# Strapi

With the api.video Strapi plugin you can upload and embed your videos into your Strapi website effortlessly.

## Features

- Upload videos using a file to api.video inside of Strapi
- Manage assets with the plugin's asset grid and pagination capabilities
- Search for assets using title
- Preview content using our player (powered by the api.video-player-react package)
- Delete assets which result in the api.video Asset also being deleted

## Installation

### Via Strapi Marketplace

Just navigate to [Strapi marketplace](https://market.strapi.io/plugins/@api.video-strapi-uploader-plugin) and click on `Install Now`.

### Via command line

Use yarn to install this plugin within your Strapi project (recommended)

```
yarn add @api.video/strapi-uploader-plugin@latest
```

After successful installation you've to re-build your Strapi instance:

```
yarn build
yarn develop
```

or just run Strapi in the development mode with `--watch-admin` option:

```
yarn develop --watch-admin
```

The api-video-uploader plugin should appear in the Plugins section of Strapi sidebar after you run app again.

## Requirements

- api.video account
- Strapi installed

### Minimum environment requirements

- Node.js >=14.19.1 <=18.x.x
- NPM >=6.x.x
- In our minimum support we're following official Node.js releases timelines.

### Supported Strapi versions:

- Strapi v4.5.1 (recently tested)
- Strapi v4.x
- This plugin is designed for Strapi v4 and is not working with v3.x.

We recommend always using the latest version of Strapi to start your new projects.

## Configuration

- Navigate to your api.video dashboard and copy over yout API key.

- On your Strapi admin page, navigate to Settings and click on "General" under the API.VIDEO UPLOADER tab.

- Paste your Api Key and click Save.

{% capture content %}

To resolve `Content Security Policy` directive issue and to visualize the thumbnail and the video, we need to configure external ressource like embed.api.videoand cdn.api.video/vod/. Modify the middlewares.js file like below:

```
// config/middlewares.js
module.exports = ({ env }) => [
    'strapi::errors',
    {
        name: 'strapi::security',
        config: {
            contentSecurityPolicy: {
                useDefaults: true,
                directives: {
                    'connect-src': ["'self'", 'https:'],
                    'img-src': ["'self'", 'data:', 'blob:', 'embed.api.video', 'cdn.api.video/vod/'],
                    'frame-src': ["'self'", 'data:', 'blob:', 'embed.api.video'],
                    upgradeInsecureRequests: null,
                },
            },
        },
    },
    'strapi::cors',
    'strapi::poweredBy',
    'strapi::logger',
    'strapi::query',
    'strapi::body',
    'strapi::favicon',
    'strapi::public',
]
```
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

### Permissions Configurations

- **Strapi Community Edition**: any user with the role of super administrator can configure and use the plugin.

- **Strapi Enterprise Edition**: can be fine tuned according to the role of users. This is done in the Roles view in the Administration Panel.

### Metdata

By default we send the value Strapias Upload source to keep track. This value is set by default and can't be changed.

### Contributing

Contributions, issues and feature requests are welcome!

If you encounter an error or have questions, please feel free to file inquiries on the Issues page for @api.video/strapi-uploader-plugin.

