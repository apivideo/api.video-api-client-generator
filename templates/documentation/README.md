---
hide_navigation: false
hide_side_table_of_contents: true
meta: 
    description: Cloud-based video hosting and live streaming platform with analytics. Mobile and web SDKs for VOD, live streaming, and player for NodeJS, Javascript, Typescript, Python, Go, PHP, C#, iOS Swift, Android Kotlin.
---

<div class="section-header">

### New to api.video?

## Get started [here](/get-started/start-building.md)!

<hr>

</div>

Welcome to the developer documentation of api.video! Our platform empowers developers and businesses to seamlessly integrate video functionality into their applications and services. Whether you're looking to offer video on demand, live streaming, or leverage our player and analytics features, the api.video API is designed to simplify the process.

Check out the products:

<div class="product-cards">

{% capture vod-description %}

api.video's hosting service enables users to upload and store videos, which can then be easily delivered and shared across various devices and platforms.


{% endcapture %}

{% capture live-stream-description %}

api.video provides you with the possibility to embed live streaming into your application or project seamlessly.


{% endcapture %}

{% capture delivery-analytics-description %}

api.video lets you customize a large part of the delivery, whether it's the player's branding or adding captions, chapters, and watermarks.

<br>

{% endcapture %}

{% include "_partials/product-card.md" product: "Video", subheading: "on demand", description: vod-description, link: "/vod/README.md" %}
{% include "_partials/product-card.md" product: "Live", subheading: "streaming", description: live-stream-description, link: "/live-streaming/README.md" %}
{% include "_partials/product-card.md" product: "Delivery", subheading: "& analytics", description: delivery-analytics-description, link: "/delivery-analytics/README.md" %}

</div>


<div class="section-header">

<hr/>

## Quick links

</div>

<div class="quick-links">
{% capture get-started-links %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/setup-checklist.svg", label: "Start building with api.video", link: "/get-started/start-building" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/vod-quickstart.svg", label: "VOD quickstart", link: "/vod/get-started-in-5-minutes" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/livestream-quickstart.svg", label: "Live streaming quickstart", link: "/live-streaming/create-a-live-stream" %}
{% endcapture %}

{% capture libraries-sdks %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/github.svg", label: "API clients", link: "/sdks/api-clients" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/videos-sdks.svg", label: "Video libraries & SDKs", link: "/sdks/vod" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/livestream-sdks.svg", label: "Live streaming libraries & SDKs", link: "/sdks/livestream" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/player-sdks.svg", label: "Delivery & analytics SDKs", link: "/sdks/player" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/no-code.svg", label: "No-code solutions", link: "/sdks/nocode" %}
{% endcapture %}

{% capture understand-api-video %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/setup-checklist.svg", label: "Help Center and FAQs", link: "https://help.api.video/en/" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/js.svg", label: "API reference", link: "/reference" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/js.svg", label: "Blog", link: "https://api.video/blog/" %}
{% include "_partials/quick-link.md" icon: "/_assets/icons/js.svg", label: "Changelog", link: "https://api.video/changelog/" %}
{% endcapture %}


{% include "_partials/quick-links-container.md" name: "getting-started", title: "Get Started", content: get-started-links %}
{% include "_partials/quick-links-container.md" name: "sdks", title: "Libraries & SDKs", content: libraries-sdks %}
{% include "_partials/quick-links-container.md" name: "understand", title: "Understand api.video", content: understand-api-video %}

</div>


<div class="section-header"> 

<hr/>

## Need help?

</div>

<div class="support-cards">
{% include "_partials/support-card.md" label: "Browse Help Center", icon: "/_assets/icons/js.svg", link: "https://help.api.video/en/" %}
{% include "_partials/support-card.md" label: "Ask the Community", icon: "/_assets/icons/js.svg", link: "https://community.api.video/" %}
</div> 
