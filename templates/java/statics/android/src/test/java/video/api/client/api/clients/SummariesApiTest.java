/*
 * api.video Java API client
 * api.video is an API that encodes on the go to facilitate immediate playback, enhancing viewer streaming experiences across multiple devices and platforms. You can stream live or on-demand online videos within minutes.
 *
 * The version of the OpenAPI document: 1
 * Contact: ecosystem@api.video
 *
 * NOTE: This class is auto generated.
 * Do not edit the class manually.
 */

package video.api.client.api.clients;

import video.api.client.api.ApiException;
import video.api.client.api.models.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for SummariesApi
 */
@DisplayName("SummariesApi")
public class SummariesApiTest extends AbstractApiTest {

    private final SummariesApi api = apiClientMock.summaries();

    @Nested
    @DisplayName("create")
    class create {
        private static final String PAYLOADS_PATH = "/payloads/summaries/create/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.create(new SummaryCreationPayload()))
                    .hasMessage("Missing the required parameter 'summaryCreationPayload.videoId' when calling create");

            assertThatThrownBy(() -> api.create(null))
                    .hasMessage("Missing the required parameter 'summaryCreationPayload' when calling create");

            assertThatNoException().isThrownBy(() -> api.create(new SummaryCreationPayload().videoId("123")));
        }

        @Test
        @DisplayName("409 response")
        public void responseWithStatus409Test() throws ApiException {
            answerOnAnyRequest(409, readResourceFile(PAYLOADS_PATH + "responses/409.json"));

            assertThatThrownBy(() -> api.create(new SummaryCreationPayload().videoId("123")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(409))
                    .hasMessage("A summary already exists or is being created on this video.");

        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/summaries/getSummarySource/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.getSummarySource("summary_1CGHWuXjhxmeH4WiZ51234"));

            assertThatThrownBy(() -> api.getSummarySource(null))
                    .hasMessage("Missing the required parameter 'summaryId' when calling getSummarySource");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.getSummarySource("summary_1CGHWuXjhxmeH4WiZ51234"))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");

        }
    }

    @Nested
    @DisplayName("update")
    class update {
        private static final String PAYLOADS_PATH = "/payloads/summaries/update/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException()
                    .isThrownBy(() -> api.update("summary_1CGHWuXjhxmeH4WiZ51234", new SummaryUpdatePayload()));
            assertThatThrownBy(() -> api.update(null, null))
                    .hasMessage("Missing the required parameter 'summaryId' when calling update");
        }

        @Test
        @DisplayName("409 response")
        public void responseWithStatus409Test() throws ApiException {
            answerOnAnyRequest(409, readResourceFile(PAYLOADS_PATH + "responses/409.json"));

            assertThatThrownBy(() -> api.update("summary_1CGHWuXjhxmeH4WiZ51234", new SummaryUpdatePayload()))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(409))
                    .hasMessage("A summary already exists or is being created on this video.");
        }
    }

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/summaries/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.delete("summary_1CGHWuXjhxmeH4WiZ51234"));
            assertThatThrownBy(() -> api.delete(null))
                    .hasMessage("Missing the required parameter 'summaryId' when calling delete");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/summaries/list/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.list());
            assertThatNoException().isThrownBy(() -> api.list());
            //
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<Summary> execute = api.list().execute();

        }
    }

}
