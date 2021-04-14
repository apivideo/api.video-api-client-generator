package video.api.client.api.clients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.Chapter;
import video.api.client.api.models.Page;
import video.api.client.api.models.PaginationLink;

import java.io.File;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for ChaptersApi
 */
@DisplayName("ChaptersApi")
public class ChaptersApiTest extends AbstractApiTest {

    private final ChaptersApi api = apiClientMock.chapters();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/chapters/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.delete(null, "en")).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling delete");

            assertThatThrownBy(() -> api.delete("12", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'language' when calling delete");

            assertThatNoException().isThrownBy(() -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            assertThatThrownBy(() -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404)).hasMessage("");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/chapters/list/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.list(null).execute()).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling list");

            assertThatNoException().isThrownBy(() -> api.list("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute());
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<Chapter> res = api.list("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute();

            assertThat(res.getCurrentPage()).isEqualTo(1);
            assertThat(res.getPageSize()).isEqualTo(25);
            assertThat(res.getPagesTotal()).isEqualTo(1);
            assertThat(res.getCurrentPageItems()).isEqualTo(2);
            assertThat(res.getLinks()).containsExactlyInAnyOrder(
                    new PaginationLink().rel("self")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("first")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("last")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25")));

            assertThat(res.getItems()).hasSize(2);

            assertThat(res.getItems()).containsExactlyInAnyOrder(
                    new Chapter().src("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/en.vtt")
                            .uri("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/en").language("en"),
                    new Chapter().src("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/fr.vtt")
                            .uri("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/fr").language("fr"));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            assertThatThrownBy(() -> api.list("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404)).hasMessage("");
        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/chapters/get/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.get(null, "en")).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling get");

            assertThatThrownBy(() -> api.get("12", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'language' when calling get");

            assertThatNoException().isThrownBy(() -> api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Chapter res = api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en");

            assertThat(res.getUri()).isEqualTo("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/fr");
            assertThat(res.getSrc()).isEqualTo("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/fr.vtt");
            assertThat(res.getLanguage()).isEqualTo("fr");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            assertThatThrownBy(() -> api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404)).hasMessage("");
        }
    }

    @Nested
    @DisplayName("upload")
    class upload {
        private static final String PAYLOADS_PATH = "/payloads/chapters/upload/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.upload(null, "en", new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling upload");

            assertThatThrownBy(() -> api.upload("12", null, new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'language' when calling upload");

            assertThatThrownBy(() -> api.upload("12", "en", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling upload");

            assertThatNoException().isThrownBy(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Chapter res = api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File(""));

            assertThat(res.getUri()).isEqualTo("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/fr");
            assertThat(res.getSrc()).isEqualTo("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/fr.vtt");
            assertThat(res.getLanguage()).isEqualTo("fr");
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, "");

            assertThatThrownBy(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400)).hasMessage("");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            assertThatThrownBy(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404)).hasMessage("");
        }
    }
}
