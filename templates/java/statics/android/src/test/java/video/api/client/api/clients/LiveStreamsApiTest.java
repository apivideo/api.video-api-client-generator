package video.api.client.api.clients;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.threeten.bp.OffsetDateTime;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import video.api.client.api.ApiException;
import video.api.client.api.models.LiveStream;
import video.api.client.api.models.LiveStreamAssets;
import video.api.client.api.models.LiveStreamCreationPayload;
import video.api.client.api.models.LiveStreamUpdatePayload;
import video.api.client.api.models.Page;
import video.api.client.api.models.PaginationLink;
import video.api.client.api.models.RestreamsResponseObject;

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

            ApiException e = assertThrows(ApiException.class, () -> api.delete(null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamId' when calling delete");

            assertDoesNotThrow(() -> api.delete("li400mYKSgQ6xs7taUeSaEKr"));
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

            ApiException e = assertThrows(ApiException.class, () -> api.deleteThumbnail(null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamId' when calling deleteThumbnail");

            assertDoesNotThrow(() -> api.deleteThumbnail("li400mYKSgQ6xs7taUeSaEKr"));
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

            ApiException e = assertThrows(ApiException.class, () -> api.deleteThumbnail("li400mYKSgQ6xs7taUeSaEKr"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
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

            assertDoesNotThrow(() -> api.list().execute());
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
            assertThat(res.getLinks()).containsExactlyElementsIn(Arrays.asList(
                    new PaginationLink().rel("self").uri(URI.create("/live-streams?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("first").uri(URI.create("/live-streams?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("last").uri(URI.create("/live-streams?currentPage=1&pageSize=25"))))
                    .inOrder();

            assertThat(res.getItems()).containsExactlyElementsIn(Arrays.asList(new LiveStream()
                    .updatedAt(OffsetDateTime.parse("2020-03-09T13:19:43Z"))
                    .createdAt(OffsetDateTime.parse("2020-01-31T10:17:47Z")).liveStreamId("li400mYKSgQ6xs7taUeSaEKr")
                    .streamKey("30087931-229e-42cf-b5f9-e91bcc1f7332").name("Live Stream From the browser")
                    .broadcasting(false)._public(true)
                    .assets(new LiveStreamAssets().iframe(
                            "<iframe src=\"https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                            .player(URI.create("https://embed.api.video/live/li400mYKSgQ6xs7taUeSaEKr"))
                            .hls(URI.create("https://live.api.video/li400mYKSgQ6xs7taUeSaEKr.m3u8"))
                            .thumbnail(URI.create("https://cdn.api.video/live/li400mYKSgQ6xs7taUeSaEKr/thumbnail.jpg")))
                    .restreams(Arrays.asList(
                            new RestreamsResponseObject().name("YouTube")
                                    .serverUrl("rtmp://youtube.broadcast.example.com")
                                    .streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385188"),
                            new RestreamsResponseObject().name("Twitch")
                                    .serverUrl("rtmp://twitch.broadcast.example.com")
                                    .streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385188"))),
                    new LiveStream().updatedAt(OffsetDateTime.parse("2020-07-29T10:45:35Z"))
                            .createdAt(OffsetDateTime.parse("2020-07-29T10:45:35Z"))
                            .liveStreamId("li4pqNqGUkhKfWcBGpZVLRY5").streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385135")
                            .name("Live From New York").broadcasting(false)._public(true)
                            .assets(new LiveStreamAssets().iframe(
                                    "<iframe src=\"https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                                    .player(URI.create("https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5"))
                                    .hls(URI.create("https://live.api.video/li4pqNqGUkhKfWcBGpZVLRY5.m3u8"))
                                    .thumbnail(URI.create(
                                            "https://cdn.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5/thumbnail.jpg")))
                            .restreams(Arrays.asList(
                                    new RestreamsResponseObject().name("YouTube")
                                            .serverUrl("rtmp://youtube.broadcast.example.com")
                                            .streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385135"),
                                    new RestreamsResponseObject().name("Twitch")
                                            .serverUrl("rtmp://twitch.broadcast.example.com")
                                            .streamKey("cc1b4df0-d1c5-4064-a8f9-9f0368385135")))))
                    .inOrder();
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

            ApiException e = assertThrows(ApiException.class, () -> api.get(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'liveStreamId' when calling get");

            assertDoesNotThrow(() -> api.get("li400mYKSgQ6xs7taUeSaEKr"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.get("li4pqNqGUkhKfWcBGpZVLRY5");

            assertThat(res.getLiveStreamId()).isEqualTo("li4pqNqGUkhKfWcBGpZVLRY5");
            assertThat(res.getStreamKey()).isEqualTo("cc1b4df0-d1c5-4064-a8f9-9f0368385135");
            assertThat(res.getName()).isEqualTo("Live From New York");
            assertThat(res.getBroadcasting()).isEqualTo(false);
            assertThat(res.getAssets()).isEqualTo(new LiveStreamAssets().iframe(
                    "<iframe src=\"https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5"))
                    .hls(URI.create("https://live.api.video/li4pqNqGUkhKfWcBGpZVLRY5.m3u8"))
                    .thumbnail(URI.create("https://cdn.api.video/live/li4pqNqGUkhKfWcBGpZVLRY5/thumbnail.jpg")));
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

            ApiException e = assertThrows(ApiException.class, () -> api.update("li400mYKSgQ6xs7taUeSaEKr", null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamUpdatePayload' when calling update");
            e = assertThrows(ApiException.class, () -> api.update(null, new LiveStreamUpdatePayload()));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamId' when calling update");

            assertDoesNotThrow(() -> api.update("li400mYKSgQ6xs7taUeSaEKr", new LiveStreamUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.update("li4pqNqGUkhKfWcBGpZVLRY5", new LiveStreamUpdatePayload());

            assertThat(res.getLiveStreamId()).isEqualTo("li4pqNqGUkhKfWcBGpZVLRY5");
            assertThat(res.getStreamKey()).isEqualTo("cc1b4df0-d1c5-4064-a8f9-9f0368385135");
            assertThat(res.getName()).isEqualTo("Live From New York");
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

            ApiException e = assertThrows(ApiException.class,
                    () -> api.update("li400mYKSgQ6xs7taUeSaEKr", new LiveStreamUpdatePayload()));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("");
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

            ApiException e = assertThrows(ApiException.class, () -> api.create(null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamCreationPayload' when calling create");

            e = assertThrows(ApiException.class, () -> api.create(new LiveStreamCreationPayload()));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamCreationPayload.name' when calling create");

            assertDoesNotThrow(() -> api.create(new LiveStreamCreationPayload().name("name")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            LiveStream res = api.create(new LiveStreamCreationPayload().name("name"));

            assertThat(res.getLiveStreamId()).isEqualTo("li4pqNqGUkhKfWcBGpZVLRY5");
            assertThat(res.getStreamKey()).isEqualTo("cc1b4df0-d1c5-4064-a8f9-9f0368385135");
            assertThat(res.getName()).isEqualTo("Live From New York");
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

            ApiException e = assertThrows(ApiException.class,
                    () -> api.create(new LiveStreamCreationPayload().name("name")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("");
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

            ApiException e = assertThrows(ApiException.class, () -> api.uploadThumbnail(null, new File("")));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'liveStreamId' when calling uploadThumbnail");

            e = assertThrows(ApiException.class, () -> api.uploadThumbnail("12", null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'file' when calling uploadThumbnail");

            assertDoesNotThrow(() -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")));
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

            ApiException e = assertThrows(ApiException.class,
                    () -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("Only [jpeg, jpg, JPG, JPEG] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.uploadThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

}
