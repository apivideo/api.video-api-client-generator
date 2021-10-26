package video.api.client.api.clients;

import video.api.client.ApiVideoClient;

public abstract class AbstractApiTest {
    protected final ApiVideoClient apiClientMock = new ApiVideoClient("apiKey");
}
