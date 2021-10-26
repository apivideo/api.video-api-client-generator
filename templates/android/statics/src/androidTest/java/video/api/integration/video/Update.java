package video.api.integration.video;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.models.Metadata;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoUpdatePayload;

public class Update {
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
                .create(new VideoCreationPayload().title("[Android-API-Client-tests] upload updates"));
    }

    @Test
    public void addMetadata() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Video updated = apiClient.videos().update(testVideo.getVideoId(),
                new VideoUpdatePayload().addMetadataItem(new Metadata("firstKey", "firstValue"))
                        .addMetadataItem(new Metadata("secondKey", "secondValue")));

        Assert.assertArrayEquals(new Metadata[]{new Metadata("firstKey", "firstValue"),
                new Metadata("secondKey", "secondValue")}, updated.getMetadata().stream().toArray(Metadata[]::new));

        Video updated2 = apiClient.videos().update(testVideo.getVideoId(), new VideoUpdatePayload()
                .metadata(updated.addMetadataItem(new Metadata("thirdKey", "thirdValue")).getMetadata()));

        Assert.assertArrayEquals(new Metadata[]{new Metadata("firstKey", "firstValue"),
                        new Metadata("secondKey", "secondValue"), new Metadata("thirdKey", "thirdValue")},
                updated.getMetadata().stream().toArray(Metadata[]::new));

        Video updated3 = apiClient.videos().update(testVideo.getVideoId(),
                new VideoUpdatePayload().metadata(Collections.emptyList()));

        Assert.assertTrue(updated3.getMetadata().isEmpty());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
