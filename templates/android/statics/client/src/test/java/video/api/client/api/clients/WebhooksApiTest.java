/*
 * api.video Java API client
 * api.video is an API that encodes on the go to facilitate immediate playback, enhancing viewer streaming experiences across multiple devices and platforms. You can stream live or on-demand online videos within minutes.
 *
 * The version of the OpenAPI document: 1
 * Contact: ecosystem@api.video
 *
 * NOTE: This class is auto generated.
 * Do not edit the class manually.
 */


package video.api.client.api.clients;

import org.junit.Test;

import video.api.client.api.ApiException;
import video.api.client.api.models.WebhooksCreationPayload;


/**
 * API tests for WebhooksApi
 */
public class WebhooksApiTest extends AbstractApiTest {

    private final WebhooksApi api = apiClientMock.webhooks();


    @Test
    public void deleteRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'webhookId' when calling delete",
                () -> api.delete(null));
    }


    @Test
    public void getRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'webhookId' when calling get",
                () -> api.get(null));
    }

    @Test
    public void createRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'webhooksCreationPayload' when calling create",
                () -> api.create(null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'webhooksCreationPayload.url' when calling create",
                () -> api.create(new WebhooksCreationPayload()));
    }

}
