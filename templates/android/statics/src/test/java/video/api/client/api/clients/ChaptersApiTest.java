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


/**
 * API tests for ChaptersApi
 */
public class ChaptersApiTest extends AbstractApiTest {

    private final ChaptersApi api = apiClientMock.chapters();


    @Test
    public void deleteRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling delete",
                () -> api.delete(null, "en"));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'language' when calling delete",
                () -> api.delete("1234", null));
    }


    @Test
    public void getRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling get",
                () -> api.get(null, "en"));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'language' when calling get",
                () -> api.get("1234", null));
    }


    @Test
    public void uploadRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling upload",
                () -> api.upload(null, "en", new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'language' when calling upload",
                () -> api.upload("1234", null, new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling upload",
                () -> api.upload("1234", "en", null));
    }

}
