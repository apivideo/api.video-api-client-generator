package video.api.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import video.api.client.api.ApiException;
import video.api.client.api.models.Webhook;
import video.api.client.api.models.WebhooksCreationPayload;

public class WebhooksTest extends AbstractApiTest {
    private Webhook webhook;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        createWebhook();
    }

    private void createWebhook() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.webhook = apiClient.webhooks()
                .create(new WebhooksCreationPayload()
                        .events(Collections.singletonList("video.encoding.quality.completed"))
                        .url("https://webhooks.test-android-api-client.com"));

        Assert.assertNotNull(this.webhook.getWebhookId());
    }

    @Test
    public void listWebhooks() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        List<Webhook> webhooks = apiClient.webhooks().list(null, null, null).getData();

        List<Webhook> filteredWebhooks = webhooks.stream()
                .filter(it -> it.getWebhookId().equals(this.webhook.getWebhookId()))
                .collect(Collectors.toList());

        Assert.assertEquals(1, filteredWebhooks.size());
        Assert.assertEquals(this.webhook.getWebhookId(), filteredWebhooks.get(0).getWebhookId());
        Assert.assertEquals(1, filteredWebhooks.get(0).getEvents().size());
        Assert.assertEquals("https://webhooks.test-android-api-client.com", filteredWebhooks.get(0).getUrl());
    }

    @Test
    public void getWebhook() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Webhook webhook = apiClient.webhooks().get(this.webhook.getWebhookId());

        Assert.assertEquals(this.webhook.getWebhookId(), webhook.getWebhookId());
        Assert.assertEquals(1, this.webhook.getEvents().size());
        Assert.assertEquals("https://webhooks.test-android-api-client.com", webhook.getUrl());
    }

    @After
    public void deleteWebhook() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.webhook != null) {
            apiClient.webhooks().delete(this.webhook.getWebhookId());
        }
    }
}
