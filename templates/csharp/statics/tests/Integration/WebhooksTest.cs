using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ApiVideo.Client;
using ApiVideo.Model;

namespace VideoApiTests.Integration
{
    [TestClass]
    public class WebhooksTest
    {
        private ApiVideoClient apiClient;
        private Webhook webhook;

        [TestInitialize]
        public void init()
        {
            apiClient = new ApiVideoClient(System.Environment.GetEnvironmentVariable("API_KEY"));

        }

        [TestMethod]
        public void createAndListWebhook()
        {
            webhook = apiClient.Webhooks()
                    .create(new WebhooksCreationPayload() { url = "https://webhooks.test-java-api-client", events = new List<string> { "video.encoding.quality.completed", } });
            

            Page<Webhook> list = apiClient.Webhooks().list().execute();

            //TODO
            this.apiClient.Webhooks().delete(this.webhook.webhookid);
        }


    }
}
