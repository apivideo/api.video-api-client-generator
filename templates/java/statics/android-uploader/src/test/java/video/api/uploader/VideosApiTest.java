package video.api.uploader;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import video.api.uploader.api.ApiException;
import video.api.uploader.api.models.Metadata;
import video.api.uploader.api.models.Video;
import video.api.uploader.api.models.VideoAssets;
import video.api.uploader.api.models.VideoSource;

/**
 * API tests for VideosApi
 */
@DisplayName("VideosApi")
public class VideosApiTest extends AbstractApiTest {

    private final VideosApi api = new VideosApi(apiClientMock);

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
            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598Z");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4k0jvEUuaTdRAEjQ4Jfrgz/source"));
            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/1080/source.mp4")));
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

            assertThat(res.getPublishedAt()).isEqualTo("4665-07-14T23:36:18.598+00:00");
            assertThat(res.getSource()).isEqualTo(new VideoSource().uri("/videos/vi4blUQJFrYWbaG44NChkH27/source"));

            assertThat(res.getAssets()).isEqualTo(new VideoAssets()
                    .hls(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/hls/manifest.m3u8"))
                    .iframe("<iframe src=\"https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                    .player(URI.create("https://embed.api.video/vod/vi4blUQJFrYWbaG44NChkH27"))
                    .thumbnail(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/thumbnail.jpg"))
                    .mp4(URI.create("https://cdn.api.video/vod/vi4blUQJFrYWbaG44NChkH27/mp4/1080/source.mp4")));

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

}
