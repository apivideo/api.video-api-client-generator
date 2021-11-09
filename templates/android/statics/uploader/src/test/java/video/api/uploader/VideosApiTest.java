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


package video.api.uploader;

import org.junit.Test;

import java.io.File;

import video.api.uploader.api.ApiException;


/**
 * API tests for VideosApi
 */
public class VideosApiTest {

    private final VideosApi api = new VideosApi();


    @Test
    public void uploadWithUploadTokenRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'token' when calling uploadWithUploadToken",
                () -> api.uploadWithUploadToken(null, new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling uploadWithUploadToken",
                () -> api.uploadWithUploadToken("1234", null));
    }


    @Test
    public void uploadRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling upload",
                () -> api.upload(null, new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling upload",
                () -> api.upload("1234", null));
    }

}
