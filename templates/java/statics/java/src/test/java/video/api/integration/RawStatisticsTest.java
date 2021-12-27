package video.api.integration;

import okhttp3.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import video.api.client.api.ApiException;
import video.api.client.api.models.Metadata;
import video.api.client.api.models.Page;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoSession;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests of api.videos() methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_TOKEN", matches = ".+")
public class RawStatisticsTest extends AbstractTest {

    @Nested
    @DisplayName("list video sessions")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Disabled
    class ListVideoSessions {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Java-SDK-tests] list video sessions")
                            .metadata(Collections.singletonList(new Metadata("user", "__user__"))));
        }

        @Test
        @Order(1)
        public void uploadVideo() throws ApiException {
            File mp4File = new File(this.getClass().getResource("/assets/558k.mp4").getFile());
            apiClient.videos().upload(testVideo.getVideoId(), mp4File);
        }

        @Test
        @Order(2)
        public void logSession() throws IOException {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'"); // Quoted "Z" to indicate UTC, no
            // timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            String json = "{\"emitted_at\": \"" + nowAsISO + "\",\n"
                    + "                   \"session\": {\"loaded_at\": \"" + nowAsISO + "\", \"referrer\": \"\",\n"
                    + "                               \"metadata\": [{\"user\": \"python_test\"}], \"video_id\": \""
                    + this.testVideo.getVideoId() + "\"},\n"
                    + "                   \"events\": [{\"type\": \"ready\", \"emitted_at\": \"" + nowAsISO
                    + "\", \"at\": 0}]}";

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder().url("https://collector.api.video/vod?t=1623232157262").post(body)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().build();

            Call call = client.newCall(request);
            Response response = call.execute();
        }

        @Test
        @Order(3)
        public void retrieveSessionWithMetadata() throws ApiException, InterruptedException {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("user", "python_test");
            Thread.sleep(5000);
            Page<VideoSession> sessions = apiClient.rawStatistics().listVideoSessions(this.testVideo.getVideoId())
                    .metadata(metadata).execute();

            assertThat(sessions.getItems()).hasSize(1);
            assertThat(sessions.getItems().get(0).getSession().getMetadata()).hasSize(1);
            assertThat(sessions.getItems().get(0).getSession().getMetadata().get(0).getKey()).isEqualTo("user");
            assertThat(sessions.getItems().get(0).getSession().getMetadata().get(0).getValue())
                    .isEqualTo("python_test");
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

}
