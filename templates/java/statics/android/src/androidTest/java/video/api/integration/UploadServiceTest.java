package video.api.integration;

import static com.google.common.truth.Truth.assertThat;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ServiceTestRule;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiException;
import video.api.client.api.clients.VideosApi;
import video.api.client.api.models.Environment;
import video.api.client.api.models.TokenCreationPayload;
import video.api.client.api.models.UploadToken;
import video.api.client.api.models.Video;
import video.api.client.api.models.VideoCreationPayload;
import video.api.client.api.services.UploadService;
import video.api.client.api.services.UploadServiceListener;
import video.api.integration.services.UploadServiceWithoutNotifications;
import video.api.integration.utils.Utils;

@DisplayName("Integration tests of UploadService methods")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "INTEGRATION_TESTS_API_KEY", matches = ".+")
public class UploadServiceTest {
    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();
    final ApiVideoClient apiClient;
    private final UploadService uploadService;

    public UploadServiceTest() throws TimeoutException, IOException {
        Intent serviceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        UploadServiceWithoutNotifications.class);

        serviceIntent.putExtra(UploadService.API_KEY_KEY, Utils.getApiKey());
        serviceIntent.putExtra(UploadService.BASE_PATH_KEY, Environment.SANDBOX.basePath);
        serviceIntent.putExtra(UploadService.APP_NAME_KEY, "client-integration-tests");
        serviceIntent.putExtra(UploadService.APP_VERSION_KEY, "0");

        IBinder binder = serviceRule.bindService(serviceIntent);

        this.uploadService =
                ((UploadService.UploadServiceBinder) binder).getService();

        this.apiClient = new ApiVideoClient(Utils.getApiKey(), Environment.SANDBOX);
        this.apiClient.setApplicationName("client-integration-tests", "0");
    }

    @Nested
    @DisplayName("upload with video id")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Upload {
        private Video testVideo;
        private UploadServiceListener listener;

        @BeforeAll
        public void createVideo() throws ApiException {
            this.testVideo = apiClient.videos()
                    .create(new VideoCreationPayload().title("[Android-SDK-tests] upload without chunk"));
        }

        @Test
        public void uploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            AtomicLong progressAtomic = new AtomicLong(0);
            CountDownLatch successLatch = new CountDownLatch(1);

            listener = new UploadServiceListener() {
                @Override
                public void onLastUpload() {
                }

                @Override
                public void onUploadComplete(@NonNull String id, @NonNull Video video) {
                    successLatch.countDown();
                }

                @Override
                public void onUploadCancelled(@NonNull String id) {
                }

                @Override
                public void onUploadError(@NonNull String id, @NonNull Exception e) {
                }

                @Override
                public void onUploadProgress(@NonNull String id, int progress) {
                    progressAtomic.set(progress);
                }

                @Override
                public void onUploadStarted(@NonNull String id) {
                }
            };

            uploadService.addListener(listener);
            uploadService.upload(testVideo.getVideoId(), mp4File);

            successLatch.await(60, TimeUnit.SECONDS);
            assertThat(progressAtomic.get()).isEqualTo(100);
            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(uploadService.getNumOfRemaining()).isEqualTo(0);
            assertThat(uploadService.getTotalNumOfUploads()).isEqualTo(1);
        }

        @Test
        public void uploadVideoWithError() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch successLatch = new CountDownLatch(0);
            CountDownLatch errorLatch = new CountDownLatch(1);

            listener = new UploadServiceListener() {
                @Override
                public void onLastUpload() {
                }

                @Override
                public void onUploadComplete(@NonNull String id, @NonNull Video video) {
                    successLatch.countDown();
                }

                @Override
                public void onUploadCancelled(@NonNull String id) {
                }

                @Override
                public void onUploadError(@NonNull String id, @NonNull Exception e) {
                    errorLatch.countDown();
                }

                @Override
                public void onUploadProgress(@NonNull String id, int progress) {
                }

                @Override
                public void onUploadStarted(@NonNull String id) {
                }
            };

            uploadService.addListener(listener);
            uploadService.upload("WRONG VIDEO ID", mp4File);

            errorLatch.await(60, TimeUnit.SECONDS);
            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(errorLatch.getCount()).isEqualTo(0);
            assertThat(uploadService.getNumOfError()).isEqualTo(1);
            assertThat(uploadService.getNumOfRemaining()).isEqualTo(0);
            assertThat(uploadService.getTotalNumOfUploads()).isEqualTo(1);
        }


        @Test
        public void cancelUploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            CountDownLatch uploadStartedLatch = new CountDownLatch(1);
            CountDownLatch cancelLatch = new CountDownLatch(1);
            CountDownLatch successLatch = new CountDownLatch(0);

            listener = new UploadServiceListener() {
                @Override
                public void onLastUpload() {
                }

                @Override
                public void onUploadComplete(@NonNull String id, @NonNull Video video) {
                    successLatch.countDown();
                }

                @Override
                public void onUploadCancelled(@NonNull String id) {
                    cancelLatch.countDown();
                }

                @Override
                public void onUploadError(@NonNull String id, @NonNull Exception e) {
                }

                @Override
                public void onUploadProgress(@NonNull String id, int progress) {
                }

                @Override
                public void onUploadStarted(@NonNull String id) {
                    uploadStartedLatch.countDown();
                }
            };

            uploadService.addListener(listener);
            String id = uploadService.upload(testVideo.getVideoId(), mp4File);

            uploadStartedLatch.await(2, TimeUnit.SECONDS); // Wait till upload start
            uploadService.cancel(id);
            cancelLatch.await(60, TimeUnit.SECONDS);

            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(cancelLatch.getCount()).isEqualTo(0);
            assertThat(uploadService.getNumOfCancelled()).isEqualTo(1);
            assertThat(uploadService.getNumOfRemaining()).isEqualTo(0);
            assertThat(uploadService.getTotalNumOfUploads()).isEqualTo(1);
        }

        @AfterEach
        public void clearService() {
            uploadService.removeListener(listener);
            uploadService.cancelAll();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
        }
    }


    @Nested
    @DisplayName("upload with video id")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class UploadWithUploadToken {
        private UploadToken testUploadToken;
        private UploadServiceListener listener;

        @BeforeAll
        public void createToken() throws ApiException {
            this.testUploadToken = apiClient.uploadTokens().createToken(new TokenCreationPayload());
        }

        @Test
        public void uploadVideo() throws IOException, InterruptedException {
            File mp4File = Utils.getFileFromAsset("558k.mp4");

            AtomicLong progressAtomic = new AtomicLong(0);
            CountDownLatch successLatch = new CountDownLatch(1);

            listener = new UploadServiceListener() {
                @Override
                public void onLastUpload() {
                }

                @Override
                public void onUploadComplete(@NonNull String id, @NonNull Video video) {
                    successLatch.countDown();
                }

                @Override
                public void onUploadCancelled(@NonNull String id) {
                }

                @Override
                public void onUploadError(@NonNull String id, @NonNull Exception e) {
                }

                @Override
                public void onUploadProgress(@NonNull String id, int progress) {
                    progressAtomic.set(progress);
                }

                @Override
                public void onUploadStarted(@NonNull String id) {
                }
            };

            uploadService.addListener(listener);
            uploadService.uploadWithUploadToken(testUploadToken.getToken(), mp4File);

            successLatch.await(60, TimeUnit.SECONDS);
            assertThat(progressAtomic.get()).isEqualTo(100);
            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(uploadService.getNumOfRemaining()).isEqualTo(0);
            assertThat(uploadService.getTotalNumOfUploads()).isEqualTo(1);
        }

        @AfterEach
        public void clearService() {
            uploadService.removeListener(listener);
            uploadService.cancelAll();
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
        private UploadServiceListener listener;

        @BeforeAll
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
            UploadService.ProgressiveUploadSession session = uploadService.createProgressiveUploadSession(uploadProgressiveSession);

            listener = new UploadServiceListener() {
                @Override
                public void onLastUpload() {
                }

                @Override
                public void onUploadComplete(@NonNull String id, @NonNull Video video) {
                    successLatch.countDown();
                }

                @Override
                public void onUploadCancelled(@NonNull String id) {
                }

                @Override
                public void onUploadError(@NonNull String id, @NonNull Exception e) {
                }

                @Override
                public void onUploadProgress(@NonNull String id, int progress) {
                }

                @Override
                public void onUploadStarted(@NonNull String id) {
                }
            };

            uploadService.addListener(listener);

            session.uploadPart(part1);
            session.uploadPart(part2);
            session.uploadLastPart(part3);

            successLatch.await(180, TimeUnit.SECONDS);

            assertThat(successLatch.getCount()).isEqualTo(0);
            assertThat(uploadService.getNumOfRemaining()).isEqualTo(0);
            assertThat(uploadService.getTotalNumOfUploads()).isEqualTo(3);
        }

        @AfterEach
        public void clearService() {
            uploadService.removeListener(listener);
            uploadService.cancelAll();
        }

        @AfterAll
        public void deleteVideo() throws ApiException {
            apiClient.videos().delete(testVideo.getVideoId());
            uploadService.cancelAll();
        }
    }
}
