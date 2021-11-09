package video.api.integration.video;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.api.ApiException;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.integration.AbstractApiTest;

public class ListWithTags extends AbstractApiTest {
    private Video testVideo;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        createVideo();
    }

    public void createVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.testVideo = apiClient.videos()
                .create(new VideoCreationPayload()
                        .tags(Arrays.asList("tag1", "tag2"))
                        .title("[Android-API-Client-tests] list metadata"));
    }

    @Test
    public void listTagNotFound() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        List<Video> videos = apiClient.videos().list(null, Collections.singletonList("valueNotFound"), null, null, null, null, null, null, null).getData();

        Assert.assertTrue(videos.isEmpty());
    }

    @Test
    public void listTagFound() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        List<Video> videos = apiClient.videos().list(null, Arrays.asList("tag1", "tag2"), null, null, null, null, null, null, null).getData();

        Assert.assertFalse(videos.isEmpty());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
