---
title: Video Domain Restriction
metadata: 
  description: Secure your videos and live streams by restricting from where the content is being consumed
---
# Domain Referrer

Domain referrer restrictions are a great way to make sure that your videos or live streams are secure and being consumed specifically through your domain or subdomains.

Examples of why you might want to implement domain referrer restrictions:

- Video or live stream consumption strictly through your website
- Making sure your videos or live streams are not shared on other domains

**Note that Domain Referrer Restriction is mostly aimed to assist you with the majority of your website visitors.**

An edge case to consider:

- The feature is only supported for browser users.

If you are looking to secure your videos and live streams in a more advanced way, for example by using a native mobile player or through securing and limiting access granularly, please visit our guide to [Private Videos](/delivery-analytics/video-privacy-access-management.md).

## How it works?

Leveraging the [referer header](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referer), a request to any video asset or live stream asset that you embed in your website, whether it's api.video player or a custom player, will check the header in question and verify that the request is coming from one of your allow-listed domains.

For example, you've requested to allow list: `https://some.domain.com` and the video is hosted in that domain:

![Showing website domain referrer via inspector tool](/_assets/domain-referrer-fig-1.png)

What happens if the same video is embedded in a different website? In this case, the video will return a `403` error, as the referrer header is different from what was allowed:

![Showing website domain referrer via inspector tool](/_assets/domain-referrer-fig-2.png)

## Domain referrer restriction with videos on native mobile players

As mentioned previously, the feature only supports browsers.

If you have a project which is hybrid, for example, videos are being consumed through an app and also in a browser, an extra configuration is needed to **allow an empty referrer**.

Mobile native players do not support referrer headers, therefore you need to allow empty referrers in order for videos or live streams to work on the mobile device on native players.

## Getting Started

To get started with Domain Referrer Restriction, all you have to do is request access from our support team and pass in the domains that you would like to be allow listed.

The following information is needed to get you started:

| Use case                              | Referrer                                        | Example                                                                                                                           |
| ------------------------------------- | ----------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| Browser player                        | strict domain referrer                          | - Domain: `mydomain.com` <br><br> \* Optional: <br>- subdomains: `yyy.mydomain.com`, `zzz.mydomain.com`                           |
| Browser player + native mobile player | strict domain referrer + empty referrer allowed | - Domain: `mydomain.com` <br> - `Allow empty referrer` <br><br>\* Optional:<br>- subdomains: `yyy.mydomain.com`, `zzz.mydomain.com` |
| Native mobile player                  | Not supported                                   | -                                                                                                                                 |

Our team will configure the feature on the backend and let you know as soon as it is ready.

### Request Access

Visit the [Dashboard](https://dashboard.api.video/domains) to get started with setting your allowed domains, or get in touch directly at [hello@api.video](mailto:hello@api.video).

## Implementation

After our team has confirmed that your domain or subdomains have been set, you can implement the videos into your website by using the video or live streams assets.

### Embedding videos or live streams into your website

The implementation is very simple, and you don't have to do any extra steps after our team has registered your domain. All you have to do is embed the videos or live stream into your website and make sure that the request to play these videos is coming from the registered domain. There's a great tutorial that you can follow on [how to implement videos into your website in 5 minutes](/vod/get-started-in-5-minutes).

