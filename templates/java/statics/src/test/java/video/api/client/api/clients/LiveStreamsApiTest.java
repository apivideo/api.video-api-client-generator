package video.api.client.api.clients;

import video.api.client.api.ApiException;
import video.api.client.api.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for LiveApi
 */
@DisplayName("LiveStreamsApi")
public class LiveStreamsApiTest extends AbstractApiTest {

    private final LiveStreamsApi api = apiClientMock.liveStreams();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.delete(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamId' when calling delete");

            assertThatNoException().isThrownBy(() -> api.delete("li400mYKSgQ6xs7taUeSaEKr"));
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            api.delete("li400mYKSgQ6xs7taUeSaEKr");
        }
    }

    @Nested
    @DisplayName("deleteThumbnail")
    class deleteThumbnail {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/deleteThumbnail/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.deleteThumbnail(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamId' when calling deleteThumbnail");

            assertThatNoException().isThrownBy(() -> api.deleteThumbnail("li400mYKSgQ6xs7taUeSaEKr"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, "");

            LiveStream res = api.deleteThumbnail("li400mYKSgQ6xs7taUeSaEKr");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.deleteThumbnail("li400mYKSgQ6xs7taUeSaEKr")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/list/";

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

            Page<LiveStream> res = api.list().execute();

            assertThat(res.getCurrentPage()).isEqualTo(1);
            assertThat(res.getPageSize()).isEqualTo(25);
            assertThat(res.getPagesTotal()).isEqualTo(1);
            assertThat(res.getCurrentPageItems()).isEqualTo(19);
            assertThat(res.getLinks()).containsExactlyInAnyOrder(
                    new PaginationLink().rel("self").uri(URI.create("/live-streams?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("first").uri(URI.create("/live-streams?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("last").uri(URI.create("/live-streams?currentPage=1&pageSize=25")));

            assertThat(res.getItems()).containsExactlyInAnyOrder(new LiveStream()
                    .liveStreamId("li400mYKSgQ6xs7taUeSaEKr").streamKey("30087931-229e-42cf-b5f9-e91bcc1f7332")
                    .name("Live Stream From the browser").record(true).broadcasting(false)._public(true)
                    .assets(new LiveStreamAssets().iframe(
                            "<iframe src=\"https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                            .player(URI.create("https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr"))
                            .hls(URI.create("https://live.api.video/li400mYKSgQ6xs7taUeSaEKr.m3u8")).thumbnail(
                                    URI.create("https://cdn.api.video/live/li400mYKSgQ6xs7taUeSaEKr/thumbnail.jpg"))),
                    new LiveStream().liveStreamId("li4pqNqGUkhKfWcBGpZVLRY5")
                            .streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385135").name("Live From New York").record(true)
                            .broadcasting(false)._public(true)
                            .assets(new LiveStreamAssets().iframe(
                                    "<iframe src=\"https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                                    .player(URI.create("https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5"))
                                    .hls(URI.create("https://live.api.video/li4pqNqGUkhKfWcBGpZVLRY5.m3u8"))
                                    .thumbnail(URI.create(
                                            "https://cdn.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5/thumbnail.jpg"))));
        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/get/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.get(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamId' when calling get");

            assertThatNoException().isThrownBy(() -> api.get("li400mYKSgQ6xs7taUeSaEKr"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.get("li400mYKSgQ6xs7taUeSaEKr");

            assertThat(res.getLiveStreamId()).isEqualTo("li400mYKSgQ6xs7taUeSaEKr");
            assertThat(res.getStreamKey()).isEqualTo("30087931-229e-42cf-b5f9-e91bcc1f7332");
            assertThat(res.getName()).isEqualTo("Live Stream From the browser");
            assertThat(res.getRecord()).isEqualTo(true);
            assertThat(res.getBroadcasting()).isEqualTo(false);
            assertThat(res.getAssets()).isEqualTo(new LiveStreamAssets().iframe(
                    "<iframe src=\"https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr"))
                    .hls(URI.create("https://live.api.video/li400mYKSgQ6xs7taUeSaEKr.m3u8"))
                    .thumbnail(URI.create("https://cdn.api.video/live/li400mYKSgQ6xs7taUeSaEKr/thumbnail.jpg")));
        }
    }

    @Nested
    @DisplayName("update")
    class update {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/update/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.update("li400mYKSgQ6xs7taUeSaEKr", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamUpdatePayload' when calling update");

            assertThatThrownBy(() -> api.update(null, new LiveStreamUpdatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamId' when calling update");

            assertThatNoException()
                    .isThrownBy(() -> api.update("li400mYKSgQ6xs7taUeSaEKr", new LiveStreamUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.update("li400mYKSgQ6xs7taUeSaEKr", new LiveStreamUpdatePayload());

            assertThat(res.getLiveStreamId()).isEqualTo("li400mYKSgQ6xs7taUeSaEKr");
            assertThat(res.getStreamKey()).isEqualTo("30087931-229e-42cf-b5f9-e91bcc1f7332");
            assertThat(res.getName()).isEqualTo("Live Stream From the browser");
            assertThat(res.getRecord()).isEqualTo(true);
            assertThat(res.getBroadcasting()).isEqualTo(false);
            assertThat(res.getAssets()).isEqualTo(new LiveStreamAssets().iframe(
                    "<iframe src=\"https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr"))
                    .hls(URI.create("https://live.api.video/li400mYKSgQ6xs7taUeSaEKr.m3u8"))
                    .thumbnail(URI.create("https://cdn.api.video/live/li400mYKSgQ6xs7taUeSaEKr/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, "");

            assertThatThrownBy(() -> api.update("li400mYKSgQ6xs7taUeSaEKr", new LiveStreamUpdatePayload()))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400)).hasMessage("");
        }
    }

    @Nested
    @DisplayName("create")
    class create {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/create/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.create(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamCreatePayload' when calling create");

            assertThatThrownBy(() -> api.create(new LiveStreamCreatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamCreatePayload.name' when calling create");

            assertThatNoException().isThrownBy(() -> api.create(new LiveStreamCreatePayload().name("name")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.create(new LiveStreamCreatePayload().name("name"));

            assertThat(res.getLiveStreamId()).isEqualTo("li4pqNqGUkhKfWcBGpZVLRY5");
            assertThat(res.getStreamKey()).isEqualTo("cc1b4df0-d1c5-4064-a8f9-9f0368385135");
            assertThat(res.getName()).isEqualTo("Live From New York");
            assertThat(res.getRecord()).isEqualTo(true);
            assertThat(res.getBroadcasting()).isEqualTo(false);
            assertThat(res.getAssets()).isEqualTo(new LiveStreamAssets().iframe(
                    "<iframe src=\"https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5"))
                    .hls(URI.create("https://live.api.video/li4pqNqGUkhKfWcBGpZVLRY5.m3u8"))
                    .thumbnail(URI.create("https://cdn.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, "");

            assertThatThrownBy(() -> api.create(new LiveStreamCreatePayload().name("name")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400)).hasMessage("");
        }
    }

    @Nested
    @DisplayName("uploadThumbnail")
    class uploadThumbnail {
        private static final String PAYLOADS_PATH = "/payloads/livestreams/uploadThumbnail/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.uploadThumbnail(null, new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'liveStreamId' when calling uploadThumbnail");

            assertThatThrownBy(() -> api.uploadThumbnail("12", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling uploadThumbnail");

            assertThatNoException().isThrownBy(() -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, "");

            LiveStream res = api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File(""));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("Only [jpeg, jpg, JPG, JPEG] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
        }
    }

}
