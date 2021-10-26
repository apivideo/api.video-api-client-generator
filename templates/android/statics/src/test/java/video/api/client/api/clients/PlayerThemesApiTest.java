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

import java.io.File;

import video.api.client.api.ApiException;
import video.api.client.api.models.PlayerThemeUpdatePayload;


/**
 * API tests for PlayerThemesApi
 */
public class PlayerThemesApiTest extends AbstractApiTest {

    private final PlayerThemesApi api = apiClientMock.playerThemes();


    @Test
    public void deleteRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerId' when calling delete",
                () -> api.delete(null));
    }


    @Test
    public void deleteLogoRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerId' when calling deleteLogo",
                () -> api.deleteLogo(null));
    }


    @Test
    public void getRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerId' when calling get",
                () -> api.get(null));
    }


    @Test
    public void updateRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerId' when calling update",
                () -> api.update(null, new PlayerThemeUpdatePayload()));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerThemeUpdatePayload' when calling update",
                () -> api.update("1234", null));
    }


    @Test
    public void createRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerThemeCreationPayload' when calling create",
                () -> api.create(null));
    }


    @Test
    public void uploadLogoRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'playerId' when calling uploadLogo",
                () -> api.uploadLogo(null, new File("test"), null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling uploadLogo",
                () -> api.uploadLogo("1234", null, null));
    }

}
