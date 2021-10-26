package video.api.integration.video;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoStatus;

public class VideoStatusTest {
    private Video testVideo;
    private ApiVideoClient apiClient;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        Assume.assumeNotNull(InstrumentationRegistry.getArguments().getString("INTEGRATION_TESTS_API_TOKEN"));
        apiClient = new ApiVideoClient(InstrumentationRegistry.getArguments().getString("INTEGRATION_TESTS_API_TOKEN"),
                ApiVideoClient.Environment.SANDBOX);

        createVideo();
    }

    public void createVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.testVideo = apiClient.videos()
                .create(new VideoCreationPayload().title("[Android-API-Client-tests] videoStatus"));
    }

    @Test
    public void getVideoStatus() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        VideoStatus videoStatus = apiClient.videos().getStatus(testVideo.getVideoId());

        Assert.assertNull(videoStatus.getIngest());
        Assert.assertNotNull(videoStatus.getEncoding());
        Assert.assertFalse(videoStatus.getEncoding().getPlayable());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
