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
import video.api.client.api.models.VideoThumbnailPickPayload;
import video.api.client.api.models.VideoUpdatePayload;


/**
 * API tests for VideosApi
 */
public class VideosApiTest extends AbstractApiTest {

    private final VideosApi api = apiClientMock.videos();


    @Test
    public void deleteRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling delete",
                () -> api.delete(null));
    }

    @Test
    public void getRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling get",
                () -> api.get(null));
    }


    @Test
    public void getStatusRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling getStatus",
                () -> api.getStatus(null));
    }


    @Test
    public void updateRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling update",
                () -> api.update(null, new VideoUpdatePayload()));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoUpdatePayload' when calling update",
                () -> api.update("1234", null));
    }


    @Test
    public void pickThumbnailRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling pickThumbnail",
                () -> api.pickThumbnail(null, new VideoThumbnailPickPayload()));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoThumbnailPickPayload' when calling pickThumbnail",
                () -> api.pickThumbnail("1234", null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoThumbnailPickPayload.timecode' when calling pickThumbnail",
                () -> api.pickThumbnail("1234", new VideoThumbnailPickPayload()));
    }


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
    public void createRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoCreationPayload' when calling create",
                () -> api.create(null));
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


    @Test
    public void uploadThumbnailRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'videoId' when calling uploadThumbnail",
                () -> api.uploadThumbnail(null, new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling uploadThumbnail",
                () -> api.uploadThumbnail("1234", null));
    }

}
