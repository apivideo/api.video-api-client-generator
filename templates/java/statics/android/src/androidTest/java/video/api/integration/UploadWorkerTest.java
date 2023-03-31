package video.api.integration;

import static com.google.common.truth.Truth.assertThat;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.clients.VideosApi;
import video.api.client.api.models.Environment;
import video.api.client.api.models.TokenCreationPayload;
import video.api.client.api.models.UploadToken;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.work.OperationWithRequest;
import video.api.client.api.work.UploadWorkerHelper;
import video.api.client.api.work.stores.VideosApiStore;
import video.api.integration.utils.Utils;

@DisplayName("Integration tests of WorkManager methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_KEY", matches = ".+")
public class UploadWorkerTest {
    final ApiVideoClient apiClient;

    final WorkManager workManager;

    public UploadWorkerTest() throws IOException {
        this.apiClient = new ApiVideoClient(Utils.getApiKey(), Environment.SANDBOX);
        this.apiClient.setApplicationName("client-integration-tests", "0");

        this.workManager = WorkManager.getInstance(ApplicationProvider.getApplicationContext());

        VideosApiStore.initialize(apiClient.videos());
    }

    @Nested
    @DisplayName("upload with video id")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Upload {
        private Video testVideo;

        @BeforeEach
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Android-SDK-tests] upload without chunk"));
        }

        @Test
        public void uploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch successLatch = new CountDownLatch(1);

            OperationWithRequest operationWithRequest = UploadWorkerHelper.upload(workManager, testVideo.getVideoId(), mp4File, Collections.emptyList());
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                    workManager.getWorkInfoByIdLiveData(operationWithRequest.getRequest().getId()).observeForever(workInfo -> {
                        if (workInfo != null) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                successLatch.countDown();
                            }
                        }
                    }));


            successLatch.await(60, TimeUnit.SECONDS);
            assertThat(successLatch.getCount()).isEqualTo(0);
        }

        @Test
        public void uploadVideoWithError() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch successLatch = new CountDownLatch(0);
            CountDownLatch errorLatch = new CountDownLatch(1);

            OperationWithRequest operationWithRequest = UploadWorkerHelper.upload(workManager, "WRONG VIDEO ID", mp4File, Collections.emptyList());
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                    workManager.getWorkInfoByIdLiveData(operationWithRequest.getRequest().getId()).observeForever(workInfo -> {
                        if (workInfo != null) {
                            if (workInfo.getState() == WorkInfo.State.FAILED) {
                                errorLatch.countDown();
                            }
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                successLatch.countDown();
                            }
                        }
                    }));

            errorLatch.await(60, TimeUnit.SECONDS);
            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(errorLatch.getCount()).isEqualTo(0);
        }


        @Test
        public void cancelUploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch uploadStartedLatch = new CountDownLatch(1);
            CountDownLatch cancelLatch = new CountDownLatch(1);
            CountDownLatch successLatch = new CountDownLatch(0);

            OperationWithRequest operationWithRequest = UploadWorkerHelper.upload(workManager, testVideo.getVideoId(), mp4File, Collections.emptyList());
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                    workManager.getWorkInfoByIdLiveData(operationWithRequest.getRequest().getId()).observeForever(workInfo -> {
                        if (workInfo != null) {
                            if (workInfo.getState() == WorkInfo.State.RUNNING) {
                                uploadStartedLatch.countDown();
                            }
                            if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                                cancelLatch.countDown();
                            }
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                successLatch.countDown();
                            }
                        }
                    }));

            uploadStartedLatch.await(2, TimeUnit.SECONDS); // Wait till upload start
            workManager.cancelWorkById(operationWithRequest.getRequest().getId());
            cancelLatch.await(60, TimeUnit.SECONDS);

            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(cancelLatch.getCount()).isEqualTo(0);
        }

        @AfterEach
        public void clear() throws ApiException {
            workManager.cancelAllWork();
            workManager.pruneWork();
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }


    @Nested
    @DisplayName("upload with video id")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class UploadWithUploadToken {
        private UploadToken testUploadToken;

        @BeforeAll
        public void createToken() throws ApiException {
            this.testUploadToken = apiClient.uploadTokens().createToken(new TokenCreationPayload());
        }

        @Test
        public void uploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch successLatch = new CountDownLatch(1);

            OperationWithRequest operationWithRequest = UploadWorkerHelper.uploadWithUploadToken(workManager, testUploadToken.getToken(), mp4File, null, Collections.emptyList());
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                    workManager.getWorkInfoByIdLiveData(operationWithRequest.getRequest().getId()).observeForever(workInfo -> {
                        if (workInfo != null) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                successLatch.countDown();
                            }
                        }
                    }));

            successLatch.await(60, TimeUnit.SECONDS);
            assertThat(successLatch.getCount()).isEqualTo(0);
        }

        @AfterEach
        public void clear() {
            workManager.cancelAllWork();
            workManager.pruneWork();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.uploadTokens().deleteToken(testUploadToken.getToken());
        }
    }

    @Nested
    @DisplayName("progressive upload")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ProgressiveUpload {
        private Video testVideo;

        @BeforeEach
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Android-SDK-tests] progressive upload")._public(false));
        }

        @Test
        public void uploadVideo() throws IOException, InterruptedException {
            File part1 = Utils.getFileFromAsset("10m.mp4.part.a");
            File part2 = Utils.getFileFromAsset("10m.mp4.part.b");
            File part3 = Utils.getFileFromAsset("10m.mp4.part.c");

            CountDownLatch successLatch = new CountDownLatch(3);

            VideosApi.UploadProgressiveSession uploadProgressiveSession = apiClient.videos().createUploadProgressiveSession(this.testVideo.getVideoId());
            OperationWithRequest operationWithRequest1 = UploadWorkerHelper.uploadPart(workManager, uploadProgressiveSession, part1, false, null, Collections.emptyList());
            OperationWithRequest operationWithRequest2 = UploadWorkerHelper.uploadPart(workManager, uploadProgressiveSession, part2, false, null, Collections.emptyList());
            OperationWithRequest operationWithRequest3 = UploadWorkerHelper.uploadPart(workManager, uploadProgressiveSession, part3, true, null, Collections.emptyList());

            InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
                workManager.getWorkInfoByIdLiveData(operationWithRequest1.getRequest().getId()).observeForever(workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            successLatch.countDown();
                        }
                    }
                });
                workManager.getWorkInfoByIdLiveData(operationWithRequest2.getRequest().getId()).observeForever(workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            successLatch.countDown();
                        }
                    }
                });
                workManager.getWorkInfoByIdLiveData(operationWithRequest3.getRequest().getId()).observeForever(workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            successLatch.countDown();
                        }
                    }
                });
            });

            successLatch.await(180, TimeUnit.SECONDS);

            assertThat(successLatch.getCount()).isEqualTo(0);
        }

        @AfterEach
        public void clear() throws ApiException {
            workManager.cancelAllWork();
            workManager.pruneWork();
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }
}
