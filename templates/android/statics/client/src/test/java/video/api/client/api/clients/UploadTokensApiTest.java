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


/**
 * API tests for UploadTokensApi
 */
public class UploadTokensApiTest extends AbstractApiTest {

    private final UploadTokensApi api = apiClientMock.uploadTokens();


    @Test
    public void deleteTokenRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'uploadToken' when calling deleteToken",
                () -> api.deleteToken(null));
    }


    @Test
    public void getTokenRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'uploadToken' when calling getToken",
                () -> api.getToken(null));
    }


    @Test
    public void createTokenRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'tokenCreationPayload' when calling createToken",
                () -> api.createToken(null));
    }

}
