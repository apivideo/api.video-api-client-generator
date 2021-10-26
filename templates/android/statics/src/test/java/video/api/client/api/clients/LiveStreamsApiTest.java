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
import video.api.client.api.models.LiveStreamCreationPayload;
import video.api.client.api.models.LiveStreamUpdatePayload;


/**
 * API tests for LiveStreamsApi
 */
public class LiveStreamsApiTest extends AbstractApiTest {

    private final LiveStreamsApi api = apiClientMock.liveStreams();


    @Test
    public void deleteRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamId' when calling delete",
                () -> api.delete(null));
    }


    @Test
    public void deleteThumbnailRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamId' when calling deleteThumbnail",
                () -> api.deleteThumbnail(null));
    }


    @Test
    public void getRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamId' when calling get",
                () -> api.get(null));
    }


    @Test
    public void updateRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamId' when calling update",
                () -> api.update(null, new LiveStreamUpdatePayload()));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamUpdatePayload' when calling update",
                () -> api.update("1234", null));
    }


    @Test
    public void createRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamCreationPayload' when calling create",
                () -> api.create(null));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamCreationPayload.name' when calling create",
                () -> api.create(new LiveStreamCreationPayload()));
    }


    @Test
    public void uploadThumbnailRequiredParametersTest() {
        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'liveStreamId' when calling uploadThumbnail",
                () -> api.uploadThumbnail(null, new File("test")));

        Utils.assertThrows(ApiException.class,
                "Missing the required parameter 'file' when calling uploadThumbnail",
                () -> api.uploadThumbnail("1234", null));
    }

}
