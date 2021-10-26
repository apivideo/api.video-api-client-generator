package video.api.integration.video;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.integration.utils.Utils;

public class Upload {
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
                .create(new VideoCreationPayload().title("[Android-API-Client-tests] upload without chunk"));
    }

    @Test
    public void uploadVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException, IOException {
        File mp4File = Utils.getFileFromAsset("558k.mp4");

        long fileSize = mp4File.length();

        AtomicLong totalUploadedAtomic = new AtomicLong(0);
        AtomicLong totalBytesAtomic = new AtomicLong(0);
        AtomicLong chunkCountAtomic = new AtomicLong(0);
        HashSet<Integer> seenChunkNums = new HashSet<>();

        apiClient.videos().upload(testVideo.getVideoId(), mp4File,
                (bytesWritten, totalBytes, chunkCount, chunkNum) -> {
                    totalUploadedAtomic.set(bytesWritten);
                    totalBytesAtomic.set(totalBytes);
                    chunkCountAtomic.set(chunkCount);
                    seenChunkNums.add(chunkNum);
                });

        Assert.assertEquals(fileSize, totalBytesAtomic.get());
        Assert.assertEquals(fileSize, totalUploadedAtomic.get());
        Assert.assertEquals(1, chunkCountAtomic.get());
        Assert.assertEquals(1, seenChunkNums.size());
    }

    @After
    public void deleteVideo() throws ApiException, InterruptedException, ExecutionException, TimeoutException {
        if (this.testVideo != null) {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
