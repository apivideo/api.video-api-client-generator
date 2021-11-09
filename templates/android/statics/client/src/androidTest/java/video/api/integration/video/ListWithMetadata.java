package video.api.integration.video;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.api.ApiException;
import video.api.client.api.models.Metadata;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.integration.AbstractApiTest;

public class ListWithMetadata extends AbstractApiTest {
    private Video testVideo;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        createVideo();
    }

    public void createVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.testVideo = apiClient.videos()
                .create(new VideoCreationPayload()
                        .metadata(Collections.singletonList(new Metadata("key1", "value1")))
                        .title("[Android-API-Client-tests] list metadata"));
    }

    @Test
    public void listMetadataNotFound() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("key1", "valueNotFound");
        List<Video> videos = apiClient.videos().list(null, null, metadata, null, null, null, null, null, null).getData();

        Assert.assertTrue(videos.isEmpty());
    }

    @Test
    public void listMetadataFound() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        List<Video> videos = apiClient.videos().list(null, null, metadata, null, null, null, null, null, null).getData();

        Assert.assertFalse(videos.isEmpty());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
