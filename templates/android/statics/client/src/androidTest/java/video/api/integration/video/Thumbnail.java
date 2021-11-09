package video.api.integration.video;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import video.api.client.api.ApiException;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoThumbnailPickPayload;
import video.api.integration.AbstractApiTest;
import video.api.integration.utils.Utils;

public class Thumbnail extends AbstractApiTest {
    private Video testVideo;

    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException, ApiException {
        createVideo();
    }

    public void createVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        this.testVideo = apiClient.videos()
                .create(new VideoCreationPayload().title("[Android-API-Client-tests] thumbnail"));
    }

    @Test
    public void uploadThumbnail() throws ApiException, InterruptedException, ExecutionException, TimeoutException, IOException {
        File jpgFile = Utils.getFileFromAsset("cat.jpg");

        Video video = apiClient.videos().uploadThumbnail(testVideo.getVideoId(), jpgFile);

        Assert.assertNotNull(video.getAssets());
        Assert.assertNotNull(video.getAssets().getThumbnail());
    }

    @Test
    public void pickThumbnail() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        Video video = apiClient.videos().pickThumbnail(testVideo.getVideoId(),
                new VideoThumbnailPickPayload().timecode("00:00:02"));

        Assert.assertNotNull(video.getAssets());
        Assert.assertNotNull(video.getAssets().getThumbnail());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
