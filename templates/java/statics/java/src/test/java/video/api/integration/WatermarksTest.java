package video.api.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoWatermark;
import video.api.client.api.models.Watermark;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests of api.watermarks() methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_TOKEN", matches = ".+")
public class WatermarksTest extends AbstractTest {
    
    private Watermark testWatermark;

    @Test
    @Order(1)
    public void createWatermark() throws ApiException {
        File jpgFile = new File(this.getClass().getResource("/assets/cat.jpg").getFile());
        this.testWatermark = apiClient.watermarks().upload(jpgFile);
    }

    @Test
    @Order(2)
    public void listWatermarks() throws ApiException {
        Page<Watermark> watermarks = apiClient.watermarks().list().execute();
        assertThat(watermarks.getItems()).anyMatch(w -> w.getWatermarkId().equals(this.testWatermark.getWatermarkId()));
    }

    @Test
    @Order(3)
    public void createVideoWithWatermark() throws ApiException {

        Video video = this.apiClient.videos().create(new VideoCreationPayload()
                .title("[java] video with watermark")
                .watermark(new VideoWatermark()
                        .bottom("10px")
                        .id(this.testWatermark.getWatermarkId())));

        assertThat(video.getVideoId()).isNotBlank();

        this.apiClient.videos().delete(video.getVideoId());
    }

    @Test
    @Order(4)
    public void deleteWatermark() throws ApiException {
        apiClient.watermarks().delete(testWatermark.getWatermarkId());
    }

}
