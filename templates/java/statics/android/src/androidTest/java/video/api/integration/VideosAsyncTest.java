package video.api.integration;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiCallback;
import video.api.client.api.ApiException;
import video.api.client.api.models.Environment;
import video.api.client.api.models.Metadata;
import video.api.client.api.models.Page;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.models.VideoStatus;
import video.api.client.api.models.VideoThumbnailPickPayload;
import video.api.client.api.models.VideoUpdatePayload;
import video.api.integration.utils.Utils;

@DisplayName("Integration tests of api.videos() methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_KEY", matches = ".+")
public class VideosAsyncTest {

    final ApiVideoClient apiClient;

    public VideosAsyncTest() throws IOException {
        this.apiClient = new ApiVideoClient(Utils.getApiKey(), Environment.SANDBOX);
        this.apiClient.setApplicationName("client-integration-tests", "0");
    }

    @Nested
    @DisplayName("update")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Update {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Android-SDK-tests] video updates"));
        }

        @Test
        public void addMetadata() throws ApiException, ExecutionException, InterruptedException, TimeoutException {

            CompletableFuture<Video> updatedFuture = new CompletableFuture<Video>();
            apiClient.videos().updateAsync(testVideo.getVideoId(),
                    new VideoUpdatePayload().addMetadataItem(new Metadata("firstKey", "firstValue"))
                            .addMetadataItem(new Metadata("secondKey", "secondValue")), new ApiCallback<Video>() {
                        @Override
                        public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                            updatedFuture.completeExceptionally(e);
                        }

                        @Override
                        public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                            updatedFuture.complete(result);
                        }

                        @Override
                        public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                            // Nothing
                        }

                        @Override
                        public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                            // Nothing
                        }
                    });

            Video updated = updatedFuture.get(10, TimeUnit.SECONDS);
            assertThat(updated.getMetadata()).containsExactlyElementsIn(Arrays.asList(new Metadata("firstKey", "firstValue"),
                    new Metadata("secondKey", "secondValue")));

            CompletableFuture<Video> updatedFuture2 = new CompletableFuture<Video>();
            apiClient.videos().updateAsync(testVideo.getVideoId(), new VideoUpdatePayload()
                    .metadata(updated.addMetadataItem(new Metadata("thirdKey", "thirdValue")).getMetadata()), new ApiCallback<Video>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    updatedFuture2.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                    updatedFuture2.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    // Nothing
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    // Nothing
                }
            });
            Video updated2 = updatedFuture2.get(10, TimeUnit.SECONDS);

            assertThat(updated2.getMetadata()).containsExactlyElementsIn(Arrays.asList(new Metadata("firstKey", "firstValue"),
                    new Metadata("secondKey", "secondValue"), new Metadata("thirdKey", "thirdValue")));

            CompletableFuture<Video> updatedFuture3 = new CompletableFuture<Video>();
            apiClient.videos().updateAsync(testVideo.getVideoId(),
                    new VideoUpdatePayload().metadata(Collections.emptyList()), new ApiCallback<Video>() {
                        @Override
                        public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                            updatedFuture3.completeExceptionally(e);
                        }

                        @Override
                        public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                            updatedFuture3.complete(result);
                        }

                        @Override
                        public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                            // Nothing
                        }

                        @Override
                        public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                            // Nothing
                        }
                    });
            Video updated3 = updatedFuture3.get(10, TimeUnit.SECONDS);

            assertThat(updated3.getMetadata()).isEmpty();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

    @Nested
    @DisplayName("get")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Get {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos().create(new VideoCreationPayload().title("[Android-SDK-tests] get"));
        }

        @Test
        public void get() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            CompletableFuture<Video> videoFuture = new CompletableFuture<Video>();
            apiClient.videos().getAsync(testVideo.getVideoId(), new ApiCallback<Video>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    videoFuture.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                    videoFuture.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    // Nothing
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    // Nothing
                }
            });

            Video video = videoFuture.get(10, TimeUnit.SECONDS);
            assertThat(video.getVideoId()).isEqualTo(testVideo.getVideoId());
            assertThat(video.getTitle()).isEqualTo(testVideo.getTitle());
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

    @Nested
    @DisplayName("list with metadata")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ListWithMetadata {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload()
                            .metadata(Collections.singletonList(new Metadata("key1", "value1")))
                            .title("[Android-SDK-tests] list metadatas"));
        }

        @Test
        public void listMetadataNotFound() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("key1", "valueNotFound");
            CompletableFuture<Page<Video>> futurePage = new CompletableFuture<Page<Video>>();
            apiClient.videos().list().metadata(metadata).executeAsync(new ApiCallback<Page<Video>>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Page<Video> result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            Page<Video> page = futurePage.get(10, TimeUnit.SECONDS);

            assertThat(page.getItemsTotal()).isEqualTo(0);
        }

        @Test
        public void listMetadataFound() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("key1", "value1");
            CompletableFuture<Page<Video>> futurePage = new CompletableFuture<Page<Video>>();
            apiClient.videos().list().metadata(metadata).executeAsync(new ApiCallback<Page<Video>>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Page<Video> result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            Page<Video> page = futurePage.get(10, TimeUnit.SECONDS);

            assertThat(page.getItemsTotal()).isGreaterThan(0);
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

    @Nested
    @DisplayName("list with tags")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ListWithTags {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos().create(
                    new VideoCreationPayload().tags(Arrays.asList("tag1", "tag2")).title("[Android-SDK-tests] list tags"));
        }

        @Test
        public void listTagNotFound() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            CompletableFuture<Page<Video>> futurePage = new CompletableFuture<Page<Video>>();
            apiClient.videos().list().tags(Collections.singletonList("valueNotFound")).executeAsync(new ApiCallback<Page<Video>>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Page<Video> result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            Page<Video> page = futurePage.get(10, TimeUnit.SECONDS);

            assertThat(page.getItemsTotal()).isEqualTo(0);
        }

        @Test
        public void listTagFound() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            CompletableFuture<Page<Video>> futurePage = new CompletableFuture<Page<Video>>();
            apiClient.videos().list().tags(Arrays.asList("tag1", "tag2")).executeAsync(new ApiCallback<Page<Video>>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Page<Video> result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futurePage.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            Page<Video> page = futurePage.get(10, TimeUnit.SECONDS);

            assertThat(page.getItemsTotal()).isGreaterThan(0);
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

    @Nested
    @DisplayName("thumbnail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Thumbnail {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos().create(new VideoCreationPayload().title("[Android-SDK-tests] thumbnail"));
        }

        @Test
        public void uploadThumbnail() throws ApiException, IOException, ExecutionException, InterruptedException, TimeoutException {
            File jpgFile = Utils.getFileFromAsset("cat.jpg");

            CompletableFuture<Video> futureVideo = new CompletableFuture<Video>();
            apiClient.videos().uploadThumbnailAsync(testVideo.getVideoId(), jpgFile, new ApiCallback<Video>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futureVideo.completeExceptionally(e);
                }

                @Override
                public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futureVideo.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            Video video = futureVideo.get(10, TimeUnit.SECONDS);

            assertThat(video.getAssets()).isNotNull();
            assertThat(video.getAssets().getThumbnail()).isNotNull();
        }

        @Test
        public void pickThumbnail() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            CompletableFuture<Video> futureVideo = new CompletableFuture<Video>();
            apiClient.videos().pickThumbnailAsync(testVideo.getVideoId(),
                    new VideoThumbnailPickPayload().timecode("00:00:02"), new ApiCallback<Video>() {
                        @Override
                        public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                            futureVideo.completeExceptionally(e);
                        }

                        @Override
                        public void onSuccess(Video result, int statusCode, Map<String, List<String>> responseHeaders) {
                            futureVideo.complete(result);
                        }

                        @Override
                        public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                        }

                        @Override
                        public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                        }
                    });
            Video video = futureVideo.get(10, TimeUnit.SECONDS);

            assertThat(video.getAssets()).isNotNull();
            assertThat(video.getAssets().getThumbnail()).isNotNull();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }

    @Nested
    @DisplayName("video status")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class VideoStatusTest {
        private Video testVideo;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Android-SDK-tests] videoStatus"));
        }

        @Test
        public void getVideoStatus() throws ApiException, ExecutionException, InterruptedException, TimeoutException {
            CompletableFuture<VideoStatus> futureVideoStatus = new CompletableFuture<VideoStatus>();
            apiClient.videos().getStatusAsync(testVideo.getVideoId(), new ApiCallback<VideoStatus>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    futureVideoStatus.completeExceptionally(e);
                }

                @Override
                public void onSuccess(VideoStatus result, int statusCode, Map<String, List<String>> responseHeaders) {
                    futureVideoStatus.complete(result);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                }
            });
            VideoStatus videoStatus = futureVideoStatus.get(10, TimeUnit.SECONDS);

            assertThat(videoStatus.getIngest()).isNull();
            assertThat(videoStatus.getEncoding()).isNotNull();
            assertThat(videoStatus.getEncoding().getPlayable()).isFalse();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
