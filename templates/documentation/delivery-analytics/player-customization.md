---
title: "Player customization"
slug: "player-customization"
metadata: 
  description: This guide explains the customization options for the api.video player. You can define your brand's color scheme, include your company logo with their player, and define the available player controls for your users. 
---

Player customization
==========================

api.video enables you to customize the api.video player and match your brand's color scheme, as well as include your company logo with the player. 

The three major aspects and importance of video player customization are:

- **Branding:** Customizing the color scheme and adding your company logo to the video player can help reinforce brand recognition and increase brand awareness among viewers.

- **Professionalism:** A customized video player with a consistent color scheme and company logo can help create a professional image for your brand and improve the overall look and feel of the video content.

- **Differentiation:** A customized video player can differentiate your brand from others in the market, as viewers are more likely to remember and associate your brand with the video content they watch, which can lead to increased brand loyalty and engagement.

## How to customize

There are two options to customize your player:

- Use the [customizer](https://dashboard.api.video/players) available in the dashboard

- Use the API to [create and customize a player](/reference/api/Player-Themes#create-a-player)

## Add a custom company logo

You can add a clickable custom company logo to your videos.

## Available customization options

| Parameter Name   | Description                                          | Type    | Default                  |
|------------------|------------------------------------------------------|---------|--------------------------|
| `name`           | The name of the player theme                        | string  |                          |
| `text`           | RGBA color for timer text. ![](https://files.readme.io/fac4eaf-Screenshot_2023-03-13_at_10.49.30.png) | string  | rgba(255, 255, 255, 1)  |
| `link`           | RGBA color for all controls. ![](https://files.readme.io/12127f5-Screenshot_2023-03-13_at_10.53.12.png) | string  | rgba(255, 255, 255, 1)  |
| `linkHover`      | RGBA color for controls when hovering over. ![](https://files.readme.io/e72c530-Screenshot_2023-03-13_at_10.58.17.png) | string  | rgba(255, 255, 255, 1)  |
| `linkActive`     | RGBA color for the play button when hovered. ![](https://files.readme.io/cb636b9-Screenshot_2023-03-13_at_11.02.36.png) | string  | rgba(255, 255, 255, 1)  |
| `trackPlayed`    | RGBA color playback bar: played content. ![](https://files.readme.io/bea91e5-Screenshot_2023-03-13_at_11.07.45.png) | string  | rgba(88, 131, 255, .95) |
| `trackUnplayed`  | RGBA color playback bar: downloaded but unplayed (buffered) content. ![](https://files.readme.io/fabe2f3-Screenshot_2023-03-13_at_11.13.49.png) | string  | rgba(255, 255, 255, .35) |
| `trackBackground`| RGBA color playback bar: background. ![](https://files.readme.io/255d36f-Screenshot_2023-03-13_at_11.16.25.png) | string  | rgba(255, 255, 255, .2)  |
| `backgroundTop`  | RGBA color: top 50% of background. ![](https://files.readme.io/5a5e240-Screenshot_2023-03-13_at_11.20.17.png) | string  | rgba(0, 0, 0, .7)       |
| `backgroundBottom`| RGBA color: bottom 50% of background. ![](https://files.readme.io/554522c-Screenshot_2023-03-13_at_11.23.41.png) | string  | rgba(0, 0, 0, .7)       |
| `backgroundText` | RGBA color for title text. ![](https://files.readme.io/8ef21b7-Screenshot_2023-03-13_at_11.26.03.png) | string  | rgba(255, 255, 255, 1)  |
| `enableControls` | Enabled or disabled player controls.                 | boolean | True                     |
| `enableApi`      | Enable/disable player SDK access.                   | boolean | True                     |
| `forceAutoplay`  | Enable/disable player autoplay.                     | boolean | False                    |
| `hideTitle`      | Hide video title.                                    | boolean | False                    |
| `forceLoop`      | Enable video loop by default.                       | boolean | False                    |
