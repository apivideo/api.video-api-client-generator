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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.Watermark;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for WatermarksApi
 */
@DisplayName("WatermarksApi")
public class WatermarksApiTest extends AbstractApiTest {

    private final WatermarksApi api = apiClientMock.watermarks();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/watermarks/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.delete("watermark_1BWr2L5MTQwxGkuxKjzh6i"));
            assertThatThrownBy(() -> api.delete(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'watermarkId' when calling delete");
            // String watermarkId
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.delete("watermark_1BWr2L5MTQwxGkuxKjzh6i")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/watermarks/list/";

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

            api.list().sortBy("createdAt").sortOrder("asc").execute();
        }
    }

    @Nested
    @DisplayName("upload")
    class upload {
        private static final String PAYLOADS_PATH = "/payloads/watermarks/upload/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.upload(new File("")));

            assertThatThrownBy(() -> api.upload(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling upload");
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Watermark res = api.upload(new File("/path/to/file"));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.upload(new File("/path/to/file"))).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("Only [jpeg, jpg, JPG, JPEG, png, PNG] extensions are supported.");
        }
    }

}
