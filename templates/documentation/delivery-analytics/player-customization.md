---
title: api.video Player customization
metadata: 
  description: Take control of your brand and player behavior by implementing the api.video player customization features.
---

# Player customization

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
| `text`           | RGBA color for timer text. ![Setting the timer text color](/_assets/delivery-analytics/player-customization/fac4eaf-Screenshot_2023-03-13_at_10.49.30.png) | string  | rgba(255, 255, 255, 1)  |
| `link`           | RGBA color for all controls. ![Setting the player control color](/_assets/delivery-analytics/player-customization/12127f5-Screenshot_2023-03-13_at_10.53.12.png) | string  | rgba(255, 255, 255, 1)  |
| `linkHover`      | RGBA color for controls when hovering over. ![Setting the player control hover color](/_assets/delivery-analytics/player-customization/e72c530-Screenshot_2023-03-13_at_10.58.17.png) | string  | rgba(255, 255, 255, 1)  |
| `linkActive`     | RGBA color for the play button when hovered. ![Setting the play button hover color](/_assets/delivery-analytics/player-customization/cb636b9-Screenshot_2023-03-13_at_11.02.36.png) | string  | rgba(255, 255, 255, 1)  |
| `trackPlayed`    | RGBA color playback bar: played content. ![Setting the playback bar color for already played section](/_assets/delivery-analytics/player-customization/bea91e5-Screenshot_2023-03-13_at_11.07.45.png) | string  | rgba(88, 131, 255, .95) |
| `trackUnplayed`  | RGBA color playback bar: downloaded but unplayed (buffered) content. ![Setting the playback bar color for unplayed, buffered section](/_assets/delivery-analytics/player-customization/fabe2f3-Screenshot_2023-03-13_at_11.13.49.png) | string  | rgba(255, 255, 255, .35) |
| `trackBackground`| RGBA color playback bar: background. ![Setting the playback bar background color](/_assets/delivery-analytics/player-customization/255d36f-Screenshot_2023-03-13_at_11.16.25.png) | string  | rgba(255, 255, 255, .2)  |
| `backgroundTop`  | RGBA color: top 50% of background. ![Setting the player's top 50% background color](/_assets/delivery-analytics/player-customization/5a5e240-Screenshot_2023-03-13_at_11.20.17.png) | string  | rgba(0, 0, 0, .7)       |
| `backgroundBottom`| RGBA color: bottom 50% of background. ![Setting the player's lower 50% background color](/_assets/delivery-analytics/player-customization/554522c-Screenshot_2023-03-13_at_11.23.41.png) | string  | rgba(0, 0, 0, .7)       |
| `backgroundText` | RGBA color for title text. ![Setting the title text color](/_assets/delivery-analytics/player-customization/8ef21b7-Screenshot_2023-03-13_at_11.26.03.png) | string  | rgba(255, 255, 255, 1)  |
| `enableControls` | Enabled or disabled player controls.                 | boolean | True                     |
| `enableApi`      | Enable/disable player SDK access.                   | boolean | True                     |
| `forceAutoplay`  | Enable/disable player autoplay.                     | boolean | False                    |
| `hideTitle`      | Hide video title.                                    | boolean | False                    |
| `forceLoop`      | Enable video loop by default.                       | boolean | False                    |
