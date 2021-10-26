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
import video.api.client.api.models.AuthenticatePayload;
import video.api.client.api.models.RefreshTokenPayload;


/**
 * API tests for AuthenticationApi
 */
public class AuthenticationApiTest extends AbstractApiTest {

    private final AuthenticationApi api = new AuthenticationApi();


    @Test
    public void authenticateRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'authenticatePayload' when calling authenticate",
                () -> api.authenticate(null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'authenticatePayload.apiKey' when calling authenticate",
                () -> api.authenticate(new AuthenticatePayload()));
    }


    @Test
    public void refreshRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'refreshTokenPayload' when calling refresh",
                () -> api.refresh(null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'refreshTokenPayload.refreshToken' when calling refresh",
                () -> api.refresh(new RefreshTokenPayload()));
    }

}
