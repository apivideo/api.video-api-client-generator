package video.api.client.api.clients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

            assertThatThrownBy(() -> api.delete(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling delete");

            assertThatNoException().isThrownBy(() -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz"));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.delete("vi4k0jvEUuaTdRAEjQ4Jfrgz")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.get(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling get");

            assertThatNoException().isThrownBy(() -> api.get("vi4k0jvEUuaTdRAEjQ4Jfrgz"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.get("videoId_example");

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("2019-12-16T08:25:51+00:00");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.get("videoId_example")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.uploadWithUploadToken(null, new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'token' when calling uploadWithUploadToken");

            assertThatThrownBy(() -> api.uploadWithUploadToken("to1tcmSFHeYY5KzyhOqVKMKb", null))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling upload");

            assertThatNoException()
                    .isThrownBy(() -> api.uploadWithUploadToken("to1tcmSFHeYY5KzyhOqVKMKb", new File("")));
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
            assertThat(res.getTags()).containsExactlyInAnyOrder("maths", "string theory", "video");
            assertThat(res.getMetadata()).containsExactlyInAnyOrder(new Metadata("Author", "John Doe"),
                    new Metadata("Format", "Tutorial"));
            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));
            assertThat(res.getAssets()).isEqualTo(new VideoAssets().iframe(
                    "<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
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

            assertThatThrownBy(() -> api.getStatus(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling getStatus");

            assertThatNoException().isThrownBy(() -> api.getStatus("vi4k0jvEUuaTdRAEjQ4Jfrgz"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Videostatus res = api.getStatus("vi4k0jvEUuaTdRAEjQ4Jfrgz");

            assertThat(res.getIngest().getStatus()).isEqualTo(VideostatusIngest.StatusEnum.UPLOADED);
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

            assertThatThrownBy(() -> api.getStatus("vi4k0jvEUuaTdRAEjQ4Jfrgz")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThat(res1.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Prklg");
            assertThat(res1.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res1.getTitle()).isEqualTo("Maths video");
            assertThat(res1.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res1.getPublic()).isEqualTo(false);
            assertThat(res1.getPanoramic()).isEqualTo(false);
            assertThat(res1.getMp4Support()).isEqualTo(true);
            assertThat(res1.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res1.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res1.getPublishedAt()).isEqualTo("2019-12-16T08:25:51+00:00");
            assertThat(res1.getSource())
                    .isEqualTo(new VideoSource().uri("/videos/c188ed58-3403-46a2-b91b-44603d10b2c9/source"));

            assertThat(res1.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/c188ed58-3403-46a2-b91b-44603d10b2c9?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/c188ed58-3403-46a2-b91b-44603d10b2c9?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));

        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.list().execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("This parameter is out of the allowed range of values.");
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

            assertThatThrownBy(() -> api.update("1234", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoUpdatePayload' when calling update");

            assertThatThrownBy(() -> api.update(null, new VideoUpdatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling update");

            assertThatNoException().isThrownBy(() -> api.update("vi4k0jvEUuaTdRAEjQ4Jfrgz", new VideoUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.update("vi4k0jvEUuaTdRAEjQ4Jfrgz", new VideoUpdatePayload());

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("2019-12-16T08:25:51+00:00");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.update("vi4k0jvEUuaTdRAEjQ4Jfrgz", new VideoUpdatePayload()))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("This attribute must be a ISO-8601 date.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.update("vi4k0jvEUuaTdRAEjQ4Jfrgz", new VideoUpdatePayload()))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.pickThumbnail(null, new VideoThumbnailPickPayload().timecode("10:10:10")))
                    .isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling pickThumbnail");

            assertThatThrownBy(() -> api.pickThumbnail("1234", null)).isInstanceOf(ApiException.class).hasMessage(
                    "Missing the required parameter 'videoThumbnailPickPayload' when calling pickThumbnail");

            assertThatThrownBy(() -> api.pickThumbnail("1234", new VideoThumbnailPickPayload()))
                    .isInstanceOf(ApiException.class).hasMessage(
                            "Missing the required parameter 'videoThumbnailPickPayload.timecode' when calling pickThumbnail");

            assertThatNoException().isThrownBy(() -> api.pickThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz",
                    new VideoThumbnailPickPayload().timecode("10:10:10")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.pickThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz",
                    new VideoThumbnailPickPayload().timecode("00:00:00"));

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.pickThumbnail("vi4k0jvEUuaTdRAEjQ4Jfrgz",
                    new VideoThumbnailPickPayload().timecode("00:00:00"))).isInstanceOf(ApiException.class)
                            .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                            .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.create(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoCreatePayload' when calling create");

            assertThatThrownBy(() -> api.create(new VideoCreatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoCreatePayload.title' when calling create");

            assertThatNoException().isThrownBy(() -> api.create(new VideoCreatePayload().title("title")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Video res = api.create(new VideoCreatePayload().title("title"));

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.create(new VideoCreatePayload().title("title")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("This attribute is required.");
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

            assertThatThrownBy(() -> api.upload(null, new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling upload");

            assertThatThrownBy(() -> api.upload("1234", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling upload");

            assertThatNoException().isThrownBy(() -> api.upload("videoId_example", new File("")));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Video res = api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File(""));

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory.");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("The source of the video is already uploaded.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.upload("vi4k0jvEUuaTdRAEjQ4Jfrgz", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.uploadThumbnail(null, new File(""))).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'videoId' when calling uploadThumbnail");

            assertThatThrownBy(() -> api.uploadThumbnail("1234", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling uploadThumbnail");

            assertThatNoException().isThrownBy(() -> api.uploadThumbnail("videoId_example", new File("")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Video res = api.uploadThumbnail("videoId_example", new File(""));

            assertThat(res.getVideoId()).isEqualTo("vi4k0jvEUuaTdRAEjQ4Jfrgz");
            assertThat(res.getPlayerId()).isEqualTo("pl45KFKdlddgk654dspkze");
            assertThat(res.getTitle()).isEqualTo("Maths video");
            assertThat(res.getDescription()).isEqualTo("An amazing video explaining the string theory");
            assertThat(res.getPublic()).isEqualTo(false);
            assertThat(res.getPanoramic()).isEqualTo(false);
            assertThat(res.getMp4Support()).isEqualTo(true);
            assertThat(res.getTags()).isEqualTo(Arrays.asList("maths", "string theory", "video"));
            assertThat(res.getMetadata())
                    .isEqualTo(Arrays.asList(new Metadata("Author", "John Doe"), new Metadata("Format", "Tutorial")));

            assertThat(res.getPublishedAt()).isEqualTo("2020-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"//embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .mp4(URI.create(
                            "https://cdn.api.video/vod/vi4k0jvEUuaTdRAEjQ4Jfrgz/token/8fd70443-d9f0-45d2-b01c-12c8cfc707c9/mp4/720/source.mp4"))
                    .player(URI.create(
                            "https://embed.api.video/vi4k0jvEUuaTdRAEjQ4Jfrgz?token=831a9bd9-9f50-464c-a369-8e9d914371ae"))
                    .thumbnail(URI.create(
                            "https://cdn.api.video/stream/831a9bd9-9f50-464c-a369-8e9d914371ae/thumbnail.jpg")));
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.uploadThumbnail("videoId_example", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("Only [jpeg, jpg, JPG, JPEG] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.uploadThumbnail("videoId_example", new File("")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");

        }
    }

}
