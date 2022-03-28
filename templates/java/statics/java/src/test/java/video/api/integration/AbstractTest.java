package video.api.integration;

import video.api.client.ApiVideoClient;
import video.api.client.api.ApiClient;

import java.util.Optional;

public abstract class AbstractTest {
    ApiVideoClient apiClient;

    AbstractTest() {
        String basePath = Optional.ofNullable(System.getenv().get("INTEGRATION_TESTS_BASE_PATH")).orElse("https://ws.api.video");
        this.apiClient = new ApiVideoClient(new ApiClient(System.getenv().get("INTEGRATION_TESTS_API_KEY"), basePath));
        this.apiClient.setApplicationName("client-integration-tests");
    }
}
