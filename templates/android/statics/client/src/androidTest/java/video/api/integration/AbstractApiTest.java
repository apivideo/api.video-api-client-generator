package video.api.integration;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assume;

import video.api.client.ApiVideoClient;
import video.api.client.api.models.Environment;

public class AbstractApiTest {
    protected ApiVideoClient apiClient;

    public AbstractApiTest() {
        Assume.assumeNotNull(InstrumentationRegistry.getArguments().getString("INTEGRATION_TESTS_API_TOKEN"));
        apiClient = new ApiVideoClient(InstrumentationRegistry.getArguments().getString("INTEGRATION_TESTS_API_TOKEN"),
                Environment.SANDBOX);
    }
}
