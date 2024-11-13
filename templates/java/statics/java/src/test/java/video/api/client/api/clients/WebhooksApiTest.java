/*
 * api.video Java API client
 * api.video is an API that encodes on the go to facilitate immediate playback, enhancing viewer streaming experiences across multiple devices and platforms. You can stream live or on-demand online videos within minutes.
 *
 * The version of the OpenAPI document: 1
 * Contact: ecosystem-team@api.video
 *
 * NOTE: This class is auto generated.
 * Do not edit the class manually.
 */

package video.api.client.api.clients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.Webhook;
import video.api.client.api.models.WebhooksCreationPayload;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for WebhooksApi
 */
@DisplayName("WebhooksApi")
public class WebhooksApiTest extends AbstractApiTest {

    private final WebhooksApi api = apiClientMock.webhooks();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/webhooks/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.delete(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'webhookId' when calling delete");

            assertThatNoException().isThrownBy(() -> api.delete("webhookId_example"));
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            assertThatNoException().isThrownBy(() -> api.delete("webhookId_example"));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.delete("webhookId_example")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/webhooks/get/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.get(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'webhookId' when calling get");

            assertThatNoException().isThrownBy(() -> api.get("webhookId_example"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Webhook res = api.get("webhookId_example");

            assertThat(res.getWebhookId()).isEqualTo("webhook_XXXXXXXXXXXXXXX");
            assertThat(res.getCreatedAt()).isEqualTo("2021-01-08T14:12:18.000+00:00");
            assertThat(res.getEvents()).containsExactlyInAnyOrder(Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED);
            assertThat(res.getUrl()).isEqualTo("http://clientnotificationserver.com/notif?myquery=query");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/webhooks/list/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.list().execute());
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<Webhook> page = api.list().execute();

            assertThat(page.getCurrentPage()).isEqualTo(1);
            assertThat(page.getPageSize()).isEqualTo(25);
            assertThat(page.getPagesTotal()).isEqualTo(1);
            assertThat(page.getCurrentPageItems()).isEqualTo(2);

            assertThat(page.getItems()).hasSize(2);

            assertThat(page.getItems()).containsExactlyInAnyOrder(
                    new Webhook().webhookId("webhook_XXXXXXXXXXXXXXX")
                            .createdAt(OffsetDateTime.parse("2021-01-08T14:12:18.000+00:00"))
                            .addEventsItem(Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED)
                            .url("http://clientnotificationserver.com/notif?myquery=query")
                            .signatureSecret("sig_sec_Abcd12348RLP7VPLi7nYVh"),
                    new Webhook().webhookId("webhook_XXXXXXXXXYYYYYY")
                            .createdAt(OffsetDateTime.parse("2021-01-12T12:12:12.000+00:00"))
                            .addEventsItem(Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED)
                            .url("http://clientnotificationserver.com/notif?myquery=query2")
                            .signatureSecret("sig_sec_Abcd12358RLP7VPLi7nYVy"));
        }
    }

    @Nested
    @DisplayName("create")
    class create {
        private static final String PAYLOADS_PATH = "/payloads/webhooks/create/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.create(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'webhooksCreationPayload' when calling create");

            assertThatThrownBy(() -> api.create(new WebhooksCreationPayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'webhooksCreationPayload.url' when calling create");

            assertThatNoException().isThrownBy(() -> api.create(new WebhooksCreationPayload().url("url")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            api.create(new WebhooksCreationPayload().url("url"));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.create(new WebhooksCreationPayload().url("url")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400)).satisfies(e -> {
                        ApiException apiException = (ApiException) e;
                        assertThat(apiException.getProblems()).containsExactlyInAnyOrder(
                                new ApiException.ApiProblem("https://docs.api.video/reference/attribute-required",
                                        "This attribute is required.", "events"),
                                new ApiException.ApiProblem("https://docs.api.video/reference/attribute-required",
                                        "This attribute is required.", "url"),
                                new ApiException.ApiProblem("https://docs.api.video/reference/invalid-attribute",
                                        "This attribute must be an array.", "events"));
                    }).hasMessage("This attribute is required.");
        }
    }

}
