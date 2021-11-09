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
import video.api.integration.AbstractApiTest;

public class Get extends AbstractApiTest {
    private Video testVideo;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        createVideo();
    }

    public void createVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.testVideo = apiClient.videos()
                .create(new VideoCreationPayload().title("[Android-API-Client-tests] get"));
    }

    @Test
    public void get() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Video video = apiClient.videos().get(testVideo.getVideoId());

        Assert.assertEquals(testVideo.getVideoId(), video.getVideoId());
        Assert.assertEquals(testVideo.getTitle(), video.getTitle());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
