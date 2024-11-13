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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.threeten.bp.OffsetDateTime;


import java.util.Arrays;

import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.Webhook;
import video.api.client.api.models.WebhooksCreationPayload;

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

            ApiException e = assertThrows(ApiException.class, () -> api.delete(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'webhookId' when calling delete");

            assertDoesNotThrow(() -> api.delete("webhookId_example"));
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            assertDoesNotThrow(() -> api.delete("webhookId_example"));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.delete("webhookId_example"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
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

            ApiException e = assertThrows(ApiException.class, () -> api.get(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'webhookId' when calling get");

            assertDoesNotThrow(() -> api.get("webhookId_example"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Webhook res = api.get("webhookId_example");

            assertThat(res.getWebhookId()).isEqualTo("webhook_XXXXXXXXXXXXXXX");
            assertThat(res.getCreatedAt().toString()).isEqualTo("2021-01-08T14:12:18Z");
            assertThat(res.getEvents()).containsExactlyElementsIn(Arrays.asList((Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED)));
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

            assertDoesNotThrow(() -> api.list().execute());
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

            assertThat(page.getItems()).containsExactlyElementsIn(Arrays.asList(
                    new Webhook().webhookId("webhook_XXXXXXXXXXXXXXX")
                            .createdAt(OffsetDateTime.parse("2021-01-08T14:12:18.000+00:00"))
                            .addEventsItem(Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED)
                            .url("http://clientnotificationserver.com/notif?myquery=query")
                            .signatureSecret("sig_sec_Abcd12348RLP7VPLi7nYVh"),
                    new Webhook().webhookId("webhook_XXXXXXXXXYYYYYY")
                            .createdAt(OffsetDateTime.parse("2021-01-12T12:12:12.000+00:00"))
                            .addEventsItem(Webhook.EventsEnum.VIDEO_ENCODING_QUALITY_COMPLETED)
                            .url("http://clientnotificationserver.com/notif?myquery=query2")
                            .signatureSecret("sig_sec_Abcd12358RLP7VPLi7nYVy")))
                    .inOrder();

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

            ApiException e = assertThrows(ApiException.class, () -> api.create(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'webhooksCreationPayload' when calling create");

            e = assertThrows(ApiException.class, () -> api.create(new WebhooksCreationPayload()));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'webhooksCreationPayload.url' when calling create");

            assertDoesNotThrow(() -> api.create(new WebhooksCreationPayload().url("url")));
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

            ApiException e = assertThrows(ApiException.class, () -> api.create(new WebhooksCreationPayload().url("url")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e.getProblems()).containsExactlyElementsIn(Arrays.asList(
                        new ApiException.ApiProblem("https://docs.api.video/reference/attribute-required",
                                "This attribute is required.", "events"),
                        new ApiException.ApiProblem("https://docs.api.video/reference/attribute-required",
                                "This attribute is required.", "url"),
                        new ApiException.ApiProblem("https://docs.api.video/reference/invalid-attribute",
                                "This attribute must be an array.", "events")))
                    .inOrder();
            assertThat(e).hasMessageThat().contains("This attribute is required.");
        }
    }

}
