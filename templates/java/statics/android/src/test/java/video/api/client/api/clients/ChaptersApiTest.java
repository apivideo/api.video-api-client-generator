package video.api.client.api.clients;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import video.api.client.api.ApiException;
import video.api.client.api.models.Chapter;
import video.api.client.api.models.Page;
import video.api.client.api.models.PaginationLink;

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

            ApiException e = assertThrows(ApiException.class, () -> api.delete(null, "en"));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling delete");

            e = assertThrows(ApiException.class, () -> api.delete("12", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'language' when calling delete");

            assertDoesNotThrow(() -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
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

            ApiException e = assertThrows(ApiException.class, () -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("");
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

            ApiException e = assertThrows(ApiException.class, () -> api.list(null).execute());
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling list");

            assertDoesNotThrow(() -> api.list("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute());
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
            assertThat(res.getLinks()).containsExactlyElementsIn(Arrays.asList(
                    new PaginationLink().rel("self")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("first")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("last")
                            .uri(URI.create("/videos/vi3N6cDinStg3oBbN79GklWS/chapters?currentPage=1&pageSize=25"))));

            assertThat(res.getItems()).hasSize(2);

            assertThat(res.getItems()).containsExactlyElementsIn(Arrays.asList(
                    new Chapter().src("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/en.vtt")
                            .uri("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/en").language("en"),
                    new Chapter().src("https://cdn.api.video/vod/vi3N6cDinStg3oBbN79GklWS/chapters/fr.vtt")
                            .uri("/videos/vi3N6cDinStg3oBbN79GklWS/chapters/fr").language("fr")));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            ApiException e = assertThrows(ApiException.class, () -> api.list("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute());
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("");
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

            ApiException e = assertThrows(ApiException.class, () -> api.get(null, "en"));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling get");

            e = assertThrows(ApiException.class, () -> api.get("12", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'language' when calling get");

            assertDoesNotThrow(() -> api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
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

            ApiException e = assertThrows(ApiException.class, () -> api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("");
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

            ApiException e = assertThrows(ApiException.class, () -> api.upload(null, "en", new File("")));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling upload");

            e = assertThrows(ApiException.class, () -> api.upload("12", null, new File("")));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'language' when calling upload");

            e = assertThrows(ApiException.class, () -> api.upload("12", "en", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'file' when calling upload");

            assertDoesNotThrow(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")));
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

            ApiException e = assertThrows(ApiException.class, () -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            ApiException e = assertThrows(ApiException.class, () -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", "en", new File("")));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("");
        }
    }
}
