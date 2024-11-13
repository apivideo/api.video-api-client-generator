package video.api.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.Webhook;
import video.api.client.api.models.WebhooksCreationPayload;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests of api.webhooks() methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_KEY", matches = ".+")
public class WebhooksTest extends AbstractTest {

    private Webhook webhook;
    
    @Test
    @Order(1)
    @DisplayName("create a webhook")
    public void createWebhook() throws ApiException {
        this.webhook = apiClient.webhooks()
                .create(new WebhooksCreationPayload()
                        .events(Collections.singletonList(WebhooksCreationPayload.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED))
                        .url("https://webhooks.test-java-api-client.fr"));

        assertThat(webhook.getWebhookId()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("list webhooks")
    public void listWebhooks() throws ApiException {
        Page<Webhook> webhooks = apiClient.webhooks().list().execute();

        List<Webhook> filteredWebhooks = webhooks.getItems().stream()
                .filter(webhook -> webhook.getWebhookId().equals(this.webhook.getWebhookId()))
                .collect(Collectors.toList());

        assertThat(filteredWebhooks).hasSize(1);
        assertThat(filteredWebhooks.get(0).getWebhookId()).isEqualTo(this.webhook.getWebhookId());
        assertThat(filteredWebhooks.get(0).getEvents()).hasSize(1);
        assertThat(filteredWebhooks.get(0).getUrl()).isEqualTo("https://webhooks.test-java-api-client.fr");
    }

    @Test
    @Order(3)
    @DisplayName("get webhook")
    public void getWebhook() throws ApiException {
        Webhook webhook = apiClient.webhooks().get(this.webhook.getWebhookId());

        assertThat(webhook.getWebhookId()).isEqualTo(this.webhook.getWebhookId());
        assertThat(webhook.getEvents()).hasSize(1);
        assertThat(webhook.getUrl()).isEqualTo("https://webhooks.test-java-api-client.fr");
    }

    @AfterAll
    public void deleteWebhook() throws ApiException {
        apiClient.webhooks().delete(this.webhook.getWebhookId());
    }

}
