---
title: Analytics & data
meta: 
  description: Learn how you can get data insights for your videos and live streams using api.video's Analytics solution.
---

# Analytics & data

api.video enables you to collect play event data about your videos and live streams.

You can:

- get the number of plays for a specific video for a period like the current day, current week or the last 30 days
- get the number of plays by country
- get the number of plays for specific devices, browser agents, and operating systems.

Use this data to understand your audience, or integrate the data directly into your own application to display it to your viewers.

Visit the **[Analytics](https://dashboard.api.video/analytics)** page on the Dashboard to get started and see your data visualized!

## How it works

api.video's solution uses playback data to analyze and segment your viewers. 

- Play events are generated when your viewer is engaging in a video or live stream session.
- When using api.video's web player, or a custom-built player with the [**Video.js analytics plugin**](https://github.com/apivideo/api.video-videojs-analytics) integrated, play events are generated immediately. Event updates are sent every 10 seconds, even if the user leaves the page within 10 seconds.
- When using any other player, play events are generated immediately. Event updates are only sent if the user stays on the page for at least 10 seconds.
- Video re-plays using the dedicated re-play button in the player do not generate play events.
- If a user is viewing your content via a browser, refreshes the tab, and plays the content again, a new play event is generated.
- api.video retains play event data for 30 days.

After play events are collected, there is a short delay while the API processes the data. api.video recommends that you retrieve Analytics periodically, for example once every minute. This enables you to keep track of live stream engagement or manage user-generated content without delay. 

### Requirements

{% capture content %}
The Analytics feature is available using api.video's video player. Check out the [Video Player SDK](/sdks/player/apivideo-player-sdk) for details about the implementation.

When using third-party players, you need to implement the [Video.js](/sdks/player/apivideo-videojs-analytics) or [Hls.js](/sdks/player/apivideo-hlsjs-analytics) analytics plugins, or the analytics modules for [Android](/sdks/player/apivideo-android-player-analytics) or [Swift](/sdks/player/apivideo-swift-player-analytics). These enable you to collect and report play event data to api.video, so you can retrieve analytics from the API.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}


## Usage

Api.video offers 2 dedicated API endpoints for analytics:

| Endpoint                                                                                             | Use case                                       |
| :--------------------------------------------------------------------------------------------------- | :--------------------------------------------- |
| [`/analytics/videos/plays`](https://docs.api.video/reference/api/Analytics#get-play-events-for-video)             | Get play event count for VOD (Video on demand) |
| [`/analytics/live-streams/plays`](https://docs.api.video/reference/api/Analytics#get-play-events-for-live-stream) | Get play event count for live streams          |

{% capture content %}
**Testing**

You can test the Analytics endpoints **in api.video's sandbox environment**. Check out [Environments](/reference/README.md#environments) for more details. The sandbox environment returns data within the last 1-day time period.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

### Request

The endpoints accept `GET` requests. You must provide 2 **required** query parameters in your requests: `from` and `dimension`. You can also provide 2 **optional** query parameters: `to`, and `filter`.

For example:

```shell
curl --request GET \
     --url https://ws.api.video/analytics/videos/plays?dimension=country&from=2023-06-01 \
     --header 'Accept: application/json' \
     --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDI4MDUyNzEuOTEyODMsIm5iZiI6MTY0MjgwNTI3MS45MTI4MywiZXhwIjoxNjQyODA4ODcxLjkxMjgzLCJwcm9qZWN0SWQiOiJwclJ6SUpKQTdCTHNxSGpTNDVLVnBCMSJ9.jTiB29R_sg5dqCDBU8wrnz7GRJsCzfVeLVTX-XSctS024B9OmGsuY139s2ua1HzrT63sqkBB1QshrjZbkDLVxSrs0-gt-FaM2bgvCC0lqK1HzEUL4vN2OqPPuM8R2pruj0UdGVaifGqmyfehKcHxuNr0ijGmGIMwSXkabECbXCxm7LraRCgmlobHepuXcUPeUKzKxN5LwPSO1onD684S0FtUUYbVMq9Ik7V8UznbpOjmFaknIZowKKlCkTmgKcyLSq7IaPJd7UuDJVXJDiC49oImEInrjx1xuFbyoBz_wkZlwcgk9GjksTeSz4xzBLcyzVgCwGP2hs8_BtdslXXOrA' \
```

{% capture content %}
api.video retains play event data for 30 days. If you select a time period that is outside the 30 day retention period, the API returns a `400` error.
{% endcapture %}
{% include "_partials/callout.html" kind: "info", content: content %}

| Parameter  | Type  | Required  | Details  |
|---|---|---|---|
| `from`  | `date`  | `true`  | The start date for the time period of the analytics you would like to request.<br><br>- The API returns analytics data **including** the day you set in from.<br>- A valid date value is only **within the last 30 days**.<br>- The value you provide must follow the `YYYY-MM-DD` format.  |
| `dimension`  | `string`  | `true`  | This parameter enables you to define a single property that you want analytics for. You can select only one property in your request. See the [table below for all dimension options](#dimension) |
| `to`  | `date`  | `false`  | Use this optional query parameter to set the end date for the time period that you want analytics for.<br>  <br>- The API returns analytics data **excluding** the day you set in `to`.<br>- If you do not specify a `to` date, the API returns analytics data starting from the `from` date up until today, and **excluding** today.<br>- A valid date value is only **within the last 30 days**.<br>- The value you provide must follow the `YYYY-MM-DD` format.  |
| `filter`  | `key-value pair`  | `false`  | Use this parameter to filter your results to a specific video or live stream in a project that you want analytics for.<br><br>You must use the `videoId:` or `liveStreamId:` prefix when specifying an identifier for a video or a live stream.<br><br>Note that the **Get play events for videos** endpoint only accepts video `ID` as a filter, and the **Get play events for live streams** endpoint only accepts live stream `ID` as a filter.  |

#### Dimension

This parameter enables you to define a single property that you want analytics for. You can select only **one property** in your request.

| Property          | Returns analytics based on ...                                                                                                                                          |
| :---------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `videoId`         | the public video identifiers.                                                                                                                                           |
| `liveStreamId`    | the public live stream identifiers.                                                                                                                                     |
| `emittedAt`       | the UTC times of the play events.                                                                                                                                       |
| `country`         | the country where the play event originates from. The list of supported country names are based on the [GeoNames public database](https://www.geonames.org/countries/). |
| `deviceType`      | the type of device used during the play event. Possible response values are: `computer`, `phone`, `tablet`, `tv`, `console`, `wearable`, `unknown`.                     |
| `operatingSystem` | the operating system used during the play event. Response values include `windows`, `mac osx`, `android`, `ios`, `linux`.                                               |
| `browser`         | the browser used during the play event. Response values include `chrome`, `firefox`, `edge`, `opera`.                                                                   |

### Response

Based on your request the Analytics API returns paginated play event data in an array of objects. Each object contains these analytics fields:

- `value`: shows a value for the property you have specified for `dimension` in your request. For example, if you requested `dimension=browser`, each `value` field in the response returns a type of browser that was used during the play events.
- `plays`: shows the number of play events for one specific `value`.

#### Structure

```json
{
    "data": [
        {
            "value": "<selected dimension>",
            "plays": 00
        },
        {
            "value": "<selected dimension>",
            "plays": 00
        }...
    ],
    "pagination": {
        "currentPage": 1,
        "currentPageItems": 3,
        "pageSize": 25,
        "pagesTotal": 1,
        "itemsTotal": 3,
        "links": [
            {
                "rel": "self",
                "uri": "/analytics/videos/plays?dimension=<selected dimension>&currentPage=1&pageSize=25"
            },
            {
                "rel": "first",
                "uri": "/analytics/videos/plays?dimension=<selected dimension>&currentPage=1&pageSize=25"
            },
            {
                "rel": "last",
                "uri": "/analytics/videos/plays?dimension=<selected dimension>&currentPage=1&pageSize=25"
            }
        ]
    }
}
```

| Property | Type               | Details                                                                                                                                                                     |
| :------- | :----------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `data`   | `array of objects` | The array of objects that contains the actual data response from the API                                                                                                    |
| `value`  | `string`           | The value of the dimension that you've selected in the request. You can find the possible [dimensions in the table above](#dimension). |
| `plays`  | `int`              | The number of play events that was returned for the dimension in question                                                                                                   |

#### Examples

You can find sample responses for some common analytics parameters here:

<details><summary><b>By <code>videoId</code></b></summary>

This response gives you a detailed breakdown for the number of play events for each video from the date you specified until today. You can use this data to understand which videos are the most popular.

The query string used in this example is `https://sandbox.api.video/analytics/videos/plays?`**`from=2023-06-01`**`&`**`dimension=videoId`**

This example uses the sandbox environment's `{base_URL}`. Check out the [**API environments**](/reference/README.md#environments) for more details.

```json
{
    "data": [
        {
            "value": "vi3q7HxhApxRF1c8F8r6VeaI",
            "plays": 100
        },
        {
            "value": "vi3q7HxhApxRF1c8F8r6VeaF",
            "plays": 10
        },
        {
            "value": "vi3q7HxhApxRF1c8F8r6VeaH",
            "plays": 1
        }
    ],
    "pagination": {
        "currentPage": 1,
        "currentPageItems": 3,
        "pageSize": 25,
        "pagesTotal": 1,
        "itemsTotal": 3,
        "links": [
            {
                "rel": "self",
                "uri": "/analytics/videos/plays?dimension=videoId&currentPage=1&pageSize=25"
            },
            {
                "rel": "first",
                "uri": "/analytics/videos/plays?dimension=videoId&currentPage=1&pageSize=25"
            },
            {
                "rel": "last",
                "uri": "/analytics/videos/plays?dimension=videoId&currentPage=1&pageSize=25"
            }
        ]
    }
}
```

</details>

<details>
<summary><b>By <code>country</code></b></summary>

This response gives you a detailed, paginated breakdown for the number of play events by country in a period of 4 days. You can use this data to understand how play events are spread out geographically, and which country has the most play events.

The query string used in this example is `https://sandbox.api.video/analytics/videos/plays?`**`from=2023-06-01`**`&`**`to=2023-06-04`**`&`**`dimension=country`**`&`**`pageSize=2`**

This example uses the sandbox environment's `{base_URL}`. Check out the [API environments](/reference/README.md#environments) for more details.

```json
{
    "data": [
        {
            "value": "france",
            "plays": 100
        },
        {
            "value": "united states",
            "plays": 10
        },
        {
            "value": "spain",
            "plays": 1
        }
    ],
    "pagination": {
        "currentPage": 1,
        "currentPageItems": 2,
        "pageSize": 2,
        "pagesTotal": 2,
        "itemsTotal": 3,
        "links": [
            {
                "rel": "self",
                "uri": "/analytics/videos/plays?dimension=country&currentPage=1&pageSize=2"
            },
            {
                "rel": "first",
                "uri": "/analytics/videos/plays?dimension=country&currentPage=1&pageSize=2"
            },
            {
                "rel": "next",
                "uri": "/analytics/videos/plays?dimension=country&currentPage=2&pageSize=1"
            },
            {
                "rel": "last",
                "uri": "/analytics/videos/plays?dimension=country&currentPage=2&pageSize=1"
            }
        ]
    }
}
```

</details>

<br>

## Best practices

To ensure that your Analytics implementation runs smoothly, make sure that you:

- use the appropriate endpoints: `/videos` for video analytics, and `/live-streams` for live stream analytics.
- set `from` and `to` in your requests keeping in mind that data is only retained for 30 days.
- only select **one** property for `dimension` in your request.
- use the `videoId:` or `liveStreamId:` prefix appropriately when using the `filter` parameter.

## What's next

Check out the **[API reference](/reference/api/Analytics)** for sample responses and errors, and more details about using the Analytics endpoint.

If you have any questions, check out the **[Help Center](https://help.api.video/en/articles/7983849-analytics)** article about Analytics.