package video.api.integration.video;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.api.ApiException;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoStatus;
import video.api.integration.AbstractApiTest;

public class VideoStatusTest extends AbstractApiTest {
    private Video testVideo;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
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
