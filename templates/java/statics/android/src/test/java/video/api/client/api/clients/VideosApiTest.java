package video.api.client.api.clients;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;

import video.api.client.api.ApiException;
import video.api.client.api.models.BytesRange;
import video.api.client.api.models.Metadata;
import video.api.client.api.models.Page;
import video.api.client.api.models.Quality;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoAssets;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoSource;
import video.api.client.api.models.VideoStatus;
import video.api.client.api.models.VideoStatusIngest;
import video.api.client.api.models.VideoThumbnailPickPayload;
import video.api.client.api.models.VideoUpdatePayload;

/**
 * API tests for VideosApi
 */
@DisplayName("VideosApi")
public class VideosApiTest extends AbstractApiTest {

    private final VideosApi api = apiClientMock.videos();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/videos/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.delete(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling delete");

            assertDoesNotThrow(() -> api.delete("vi4blUQJFrYWbaG44NChkH27"));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.delete("vi4blUQJFrYWbaG44NChkH27"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/videos/get/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.get(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling get");

            assertDoesNotThrow(() -> api.get("vi4blUQJFrYWbaG44NChkH27"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.get("videoId_example");

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.get("videoId_example"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("uploadWithUploadToken")
    class uploadWithUploadToken {
        private static final String PAYLOADS_PATH = "/payloads/videos/uploadWithUploadToken/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.uploadWithUploadToken(null, new File("")));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'token' when calling uploadWithUploadToken");

            e = assertThrows(ApiException.class, () -> api.uploadWithUploadToken("to1tcmSFHeYY5KzyhOqVKMKb", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'file' when calling upload");

            assertDoesNotThrow(() -> api.uploadWithUploadToken("to1tcmSFHeYY5KzyhOqVKMKb", new File("")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Video res = api.uploadWithUploadToken("to1tcmSFHeYY5KzyhOqVKMKb", new File(""));

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getTags()).containsExactlyElementsIn(Arrays.asList("maths", "string theory", "video"))
                    .inOrder();
            assertThat(res.getMetadata())
                    .containsExactlyElementsIn(
                            Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")))
                    .inOrder();
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));
            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }
    }

    @Nested
    @DisplayName("getStatus")
    class getStatus {
        private static final String PAYLOADS_PATH = "/payloads/videos/getStatus/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.getStatus(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling getStatus");

            assertDoesNotThrow(() -> api.getStatus("vi4blUQJFrYWbaG44NChkH27"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            VideoStatus res = api.getStatus("vi4blUQJFrYWbaG44NChkH27");

            assertThat(res.getIngest().getStatus()).isEqualTo(VideoStatusIngest.StatusEnum.UPLOADED);
            assertThat(res.getIngest().getFilesize()).isEqualTo(273579401);
            assertThat(res.getIngest().getReceivedBytes())
                    .isEqualTo(Arrays.asList(new BytesRange().from(0).to(134217727).total(273579401),
                            new BytesRange().from(134217728).to(268435455).total(273579401),
                            new BytesRange().from(268435456).to(273579400).total(273579401)));
            assertThat(res.getEncoding().getPlayable()).isTrue();
            assertThat(res.getEncoding().getQualities()).isEqualTo(
                    Arrays.asList(new Quality().quality(Quality.QualityEnum._360P).status(Quality.StatusEnum.ENCODED),
                            new Quality().quality(Quality.QualityEnum._480P).status(Quality.StatusEnum.ENCODED),
                            new Quality().quality(Quality.QualityEnum._720P).status(Quality.StatusEnum.ENCODED),
                            new Quality().quality(Quality.QualityEnum._1080P).status(Quality.StatusEnum.ENCODING),
                            new Quality().quality(Quality.QualityEnum._2160P).status(Quality.StatusEnum.WAITING)));

            assertThat(res.getEncoding().getMetadata().getWidth()).isEqualTo(424);
            assertThat(res.getEncoding().getMetadata().getHeight()).isEqualTo(240);
            assertThat(res.getEncoding().getMetadata().getBitrate()).isEqualTo(BigDecimal.valueOf(411.218));
            assertThat(res.getEncoding().getMetadata().getDuration()).isEqualTo(4176);
            assertThat(res.getEncoding().getMetadata().getFramerate()).isEqualTo(24);
            assertThat(res.getEncoding().getMetadata().getSamplerate()).isEqualTo(48000);
            assertThat(res.getEncoding().getMetadata().getVideoCodec()).isEqualTo("h264");
            assertThat(res.getEncoding().getMetadata().getAudioCodec()).isEqualTo("aac");
            assertThat(res.getEncoding().getMetadata().getAspectRatio()).isEqualTo("16/9");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.getStatus("vi4blUQJFrYWbaG44NChkH27"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/videos/list/";

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<Video> page = api.list().execute();

            assertThat(page.getCurrentPage()).isEqualTo(1);
            assertThat(page.getPageSize()).isEqualTo(25);
            assertThat(page.getPagesTotal()).isEqualTo(1);
            assertThat(page.getCurrentPageItems()).isEqualTo(11);

            assertThat(page.getItems()).hasSize(3);

            Video res1 = page.getItems().get(0);

            assertThat(res1.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res1.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res1.getTitle()).isEqualTo("Maths video");
            assertThat(res1.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res1.getPublic()).isEqualTo(false);
            assertThat(res1.getPanoramic()).isEqualTo(false);
            assertThat(res1.getMp4Support()).isEqualTo(true);
            assertThat(res1.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res1.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res1.getSource())
                    .isEqualTo(new VideoSource().uri("/videos/c188ed58-3403-46a2-b91b-44603d10b2c9/source"));

            assertThat(res1.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));

        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.list().execute());
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("This parameter is out of the allowed range of values.");
        }
    }

    @Nested
    @DisplayName("update")
    class update {
        private static final String PAYLOADS_PATH = "/payloads/videos/update/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.update("1234", null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoUpdatePayload' when calling update");

            e = assertThrows(ApiException.class, () -> api.update(null, new VideoUpdatePayload()));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling update");

            assertDoesNotThrow(() -> api.update("vi4blUQJFrYWbaG44NChkH27", new VideoUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.update("vi4blUQJFrYWbaG44NChkH27", new VideoUpdatePayload());

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.update("vi4blUQJFrYWbaG44NChkH27", new VideoUpdatePayload()));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("This attribute must be a ISO-8601 date.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.update("vi4blUQJFrYWbaG44NChkH27", new VideoUpdatePayload()));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("pickThumbnail")
    class pickThumbnail {
        private static final String PAYLOADS_PATH = "/payloads/videos/pickThumbnail/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class,
                    () -> api.pickThumbnail(null, new VideoThumbnailPickPayload().timecode("10:10:10")));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoId' when calling pickThumbnail");

            e = assertThrows(ApiException.class, () -> api.pickThumbnail("1234", null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoThumbnailPickPayload' when calling pickThumbnail");

            e = assertThrows(ApiException.class, () -> api.pickThumbnail("1234", new VideoThumbnailPickPayload()));
            assertThat(e).hasMessageThat().contains(
                    "Missing the required parameter 'videoThumbnailPickPayload.timecode' when calling pickThumbnail");

            assertDoesNotThrow(() -> api.pickThumbnail("vi4blUQJFrYWbaG44NChkH27",
                    new VideoThumbnailPickPayload().timecode("10:10:10")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.pickThumbnail("vi4blUQJFrYWbaG44NChkH27",
                    new VideoThumbnailPickPayload().timecode("00:00:00"));

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.pickThumbnail("vi4blUQJFrYWbaG44NChkH27",
                    new VideoThumbnailPickPayload().timecode("00:00:00")));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("create")
    class create {
        private static final String PAYLOADS_PATH = "/payloads/videos/create/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.create(null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoCreationPayload' when calling create");

            e = assertThrows(ApiException.class, () -> api.create(new VideoCreationPayload()));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoCreationPayload.title' when calling create");

            assertDoesNotThrow(() -> api.create(new VideoCreationPayload().title("title")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Video res = api.create(new VideoCreationPayload().title("title"));

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.create(new VideoCreationPayload().title("title")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("This attribute is required.");
        }
    }

    @Nested
    @DisplayName("upload")
    class upload {
        private static final String PAYLOADS_PATH = "/payloads/videos/upload/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.upload(null, new File("")));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling upload");

            e = assertThrows(ApiException.class, () -> api.upload("1234", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'file' when calling upload");

            assertDoesNotThrow(() -> api.upload("videoId_example", new File("")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Video res = api.upload("vi4blUQJFrYWbaG44NChkH27", new File(""));

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory.");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));

        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.upload("vi4blUQJFrYWbaG44NChkH27", new File("")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("The source of the video is already uploaded.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.upload("vi4blUQJFrYWbaG44NChkH27", new File("")));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("uploadThumbnail")
    class uploadThumbnail {
        private static final String PAYLOADS_PATH = "/payloads/videos/uploadThumbnail/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.uploadThumbnail(null, new File("")));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'videoId' when calling uploadThumbnail");

            e = assertThrows(ApiException.class, () -> api.uploadThumbnail("1234", null));
            assertThat(e).hasMessageThat()
                    .contains("Missing the required parameter 'file' when calling uploadThumbnail");

            assertDoesNotThrow(() -> api.uploadThumbnail("videoId_example", new File("")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.uploadThumbnail("videoId_example", new File(""));

            assertThat(res.getVideoId()).isEqualTo("vi4blUQJFrYWbaG44NChkH27");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/source.mp4")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.uploadThumbnail("videoId_example", new File("")));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("Only [jpeg, jpg, JPG, JPEG] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.uploadThumbnail("videoId_example", new File("")));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");

        }
    }

}
