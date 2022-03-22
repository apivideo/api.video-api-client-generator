package video.api.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.models.Chapter;
import video.api.client.api.models.Environment;
import video.api.client.api.models.Page;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests of api.videos() methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_TOKEN", matches = ".+")
public class ChaptersTest {

    ApiVideoClient apiClient;

    public ChaptersTest() {
        this.apiClient = new ApiVideoClient(System.getenv().get("INTEGRATION_TESTS_API_TOKEN"), Environment.SANDBOX);
    }

    @Nested
    @DisplayName("upload by chunk")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class UploadCHapters {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Java-SDK-tests] chapters")._public(false));
        }

        @Test
        public void uploadVideo() throws ApiException {
            File chapters = new File(this.getClass().getResource("/assets/chapters.vtt").getFile());

            apiClient.chapters().upload(this.testVideo.getVideoId(), "fr", chapters);
            apiClient.chapters().upload(this.testVideo.getVideoId(), "ch", chapters);
            apiClient.chapters().upload(this.testVideo.getVideoId(), "en", chapters);
            apiClient.chapters().upload(this.testVideo.getVideoId(), "de", chapters);
            apiClient.chapters().upload(this.testVideo.getVideoId(), "es", chapters);

            Page<Chapter> chaptersList = apiClient.chapters().list(this.testVideo.getVideoId()).execute();
            assertThat(chaptersList.getItems().size()).isEqualTo(5);
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

}
