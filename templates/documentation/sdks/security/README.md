---
title: Security best practices
meta:
    description: Best practices on SDK and API security that will help secure your application and protect your users.
---

# Security Best Practices

Ensuring app security is paramount for developers for several compelling reasons:

* User Trust: A secure app instills trust in users. When people know their data and privacy are protected, they are more likely to use the app and share personal information.

* Reputation: Security breaches can severely damage a developer's reputation. News of vulnerabilities or data leaks can spread rapidly and deter users from using the app.

* Legal and Compliance Issues: Failing to secure user data can lead to legal consequences, especially with the introduction of data protection regulations like GDPR and CCPA. Developers can face lawsuits, fines, or other penalties for non-compliance.

* Financial Impact: Security breaches can be financially devastating. The costs associated with cleaning up a breach, compensating affected users, and lost business can be exorbitant.

* Customer Retention: When users feel safe, they are more likely to stay loyal to the app. A secure app reduces churn and increases customer retention.

* Competitive Advantage: Security can be a significant differentiator. Apps that prioritize security can gain a competitive edge, attracting more users and business partners.

In summary, securing an app is not merely a technical necessity; it's a strategic imperative for developers to protect their users, reputation, and long-term success.

## Securing your application

We highly recommend to avoid storing the API keys in your frontend & mobile application as long as it is possible. The best way to make sure that your API keys are secure is separating the frontend & mobile application with the backend, while making sure that all the API keys and sensitive app information is stored on the backend.

* All the api.video client libraries are using the [Advanced Authentication](https://docs.api.video/reference/disposable-bearer-token-authentication) method. If you are writing your own API client wrapper, be sure to leverage the Advanced Authentication instead of the Basic Authentication concept.

* While serving the [upload token](https://docs.api.video/vod/delegated-upload-tokens) to the frontend, make sure that you maintain the shortest TTL possible for the tokens. In order to make sure that token is not hijacked and abused.

* Maintain a secure channel of communication between the front-end and backend at all times.

* Do not store API keys in files inside your application’s source tree. If you store API keys in files, keep the files outside your application’s source tree to help ensure your keys do not end up in your source code control system. This is particularly important if you use a public source code management system such as GitHub.

* Do not embed API keys directly in code. API keys that are embedded in code can be accidentally exposed to the public. For example, you may forget to remove the keys from code that you share. Instead of embedding your API keys in your applications, store them in environment variables or in files outside of your application’s source tree.

* Review your code before publicly releasing it. Ensure that your code does not contain API keys or any other private information before you make your code publicly available.
Delete unneeded API keys to minimize exposure to attacks.

* Limit one API key pair's usage to a specific system of your platform backend. This limits the scope of each key. If an API key is compromised, you can delete or regenerate the impacted key without needing to update your other API keys.


## Securing the API keys on the application [Not recommended]

{% capture content %}
api.video highly recommend to avoid storing the API keys on the application side at all cost. Even if you take steps to obfuscate your API keys on the application, your application is still prone to security threats
{% endcapture %}
{% include "_partials/callout.html" kind: "warning", content: content %}

Obfuscation transforms the key into a form that isn't immediately readable. However, it's crucial to understand that obfuscation is not foolproof. It merely makes the task of key retrieval more challenging, but not impossible. Determined malicious actors with advanced skills can still de-obfuscate the key. Think of obfuscation as an added layer of security, not a standalone solution.

## General information on securing your mobile application

There are multiple guides around the web on how to secure your frontend application. You can find more recommendations on securing your application on various frameworks and languages below:

* [React Native - Storing sensitive data](https://reactnative.dev/docs/security#storing-sensitive-info)

* [Flutter - Obfuscate code](https://docs.flutter.dev/deployment/obfuscate)

* [Android - App Security](https://developer.android.com/privacy-and-security/security-tips)

* [iOS - App Security](https://developer.apple.com/documentation/security)