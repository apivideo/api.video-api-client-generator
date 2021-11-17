package video.api.client.api.clients;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.PlayerTheme;
import video.api.client.api.models.PlayerThemeCreationPayload;
import video.api.client.api.models.PlayerThemeUpdatePayload;

/**
 * API tests for PlayersApi
 */
@DisplayName("PlayerThemeApi")
@Disabled
public class PlayerThemesApiTest extends AbstractApiTest {

    private final PlayerThemesApi api = apiClientMock.playerThemes();

    @Nested
    @DisplayName("delete")
    class delete {
        private static final String PAYLOADS_PATH = "/payloads/players/delete/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertDoesNotThrow(() -> api.delete("pl45d5vFFGrfdsdsd156dGhh"));
            assertDoesNotThrow(() -> api.delete(null));
            // String playerId
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            api.delete("pl45d5vFFGrfdsdsd156dGhh");

            /*
             * sample response:
             *
             */
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.delete("pl45d5vFFGrfdsdsd156dGhh"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");

            /*
             * sample response: { "type" : "https://docs.api.video/docs/resourcenot_found", "title" :
             * "The requested resource was not found.", "name" : "playerId", "status" : 404 }
             */
        }
    }

    @Nested
    @DisplayName("deleteLogo")
    class deleteLogo {
        private static final String PAYLOADS_PATH = "/payloads/players/deleteLogo/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertDoesNotThrow(() -> api.deleteLogo("pl14Db6oMJRH6SRVoOwORacK"));
            assertDoesNotThrow(() -> api.deleteLogo(null));
            // String playerId
        }

        @Test
        @DisplayName("204 response")
        public void responseWithStatus204Test() throws ApiException {
            answerOnAnyRequest(204, "");

            api.deleteLogo("pl14Db6oMJRH6SRVoOwORacK");

            /*
             * sample response:
             *
             */
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.deleteLogo("pl14Db6oMJRH6SRVoOwORacK"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");

            /*
             * sample response: { "type" : "https://docs.api.video/docs/resourcenot_found", "title" :
             * "The requested resource was not found.", "name" : "playerId", "status" : 404 }
             */
        }
    }

    @Nested
    @DisplayName("list")
    class list {
        private static final String PAYLOADS_PATH = "/payloads/players/list/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertDoesNotThrow(() -> api.list().execute());
            // String sortBy, String sortOrder, Integer currentPage, Integer pageSize
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<PlayerTheme> res = api.list().execute();

            /*
             * sample response: { "data" : [ { "playerId" : "pl4fgtjy4tjyKDK545DRdfg", "createdAt" :
             * "2020-01-13T10:09:17+00:00", "updatedAt" : "2020-01-13T10:09:17+00:00", "shapeMargin" : 10, "shapeRadius"
             * : 3, "shapeAspect" : "flat", "shapeBackgroundTop" : "rgba(50, 50, 50, .7)", "shapeBackgroundBottom" :
             * "rgba(50, 50, 50, .8)", "text" : "rgba(255, 255, 255, .95)", "link" : "rgba(255, 0, 0, .95)", "linkHover"
             * : "rgba(255, 255, 255, .75)", "linkActive" : "rgba(255, 0, 0, .75)", "trackPlayed" :
             * "rgba(255, 255, 255, .95)", "trackUnplayed" : "rgba(255, 255, 255, .1)", "trackBackground" :
             * "rgba(0, 0, 0, 0)", "backgroundTop" : "rgba(72, 4, 45, 1)", "backgroundBottom" : "rgba(94, 95, 89, 1)",
             * "backgroundText" : "rgba(255, 255, 255, .95)", "enableApi" : false, "enableControls" : false,
             * "forceAutoplay" : false, "hideTitle" : false, "forceLoop" : false }, { "playerId" :
             * "pl54fgtjy4tjyKDK45DRdfg", "createdAt" : "2020-01-13T10:09:17+00:00", "updatedAt" :
             * "2020-01-13T10:09:17+00:00", "shapeMargin" : 10, "shapeRadius" : 3, "shapeAspect" : "flat",
             * "shapeBackgroundTop" : "rgba(50, 50, 50, .7)", "shapeBackgroundBottom" : "rgba(50, 50, 50, .8)", "text" :
             * "rgba(255, 255, 255, .95)", "link" : "rgba(255, 0, 0, .95)", "linkHover" : "rgba(255, 255, 255, .75)",
             * "linkActive" : "rgba(255, 0, 0, .75)", "trackPlayed" : "rgba(255, 255, 255, .95)", "trackUnplayed" :
             * "rgba(255, 255, 255, .1)", "trackBackground" : "rgba(0, 0, 0, 0)", "backgroundTop" :
             * "rgba(72, 4, 45, 1)", "backgroundBottom" : "rgba(94, 95, 89, 1)", "backgroundText" :
             * "rgba(255, 255, 255, .95)", "enableApi" : true, "enableControls" : true, "forceAutoplay" : true,
             * "hideTitle" : false, "forceLoop" : false } ], "pagination" : { "currentPage" : 1, "pageSize" : 25,
             * "pagesTotal" : 1, "itemsTotal" : 4, "currentPageItems" : 4, "links" : [ { "rel" : "self", "uri" :
             * "https://ws.api.video/players?currentPage&#x3D;1" }, { "rel" : "first", "uri" :
             * "https://ws.api.video/players?currentPage&#x3D;1" }, { "rel" : "last", "uri" :
             * "https://ws.api.video/players?currentPage&#x3D;1" } ] } }
             */
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.list().execute());
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("This parameter is out of the allowed range of values.");

            /*
             * sample response: { "title" : "This parameter is out of the allowed range of values.", "name" : "page",
             * "status" : 400, "range" : { "min" : 1 }, "problems" : [ { "title" :
             * "This parameter is out of the allowed range of values.", "name" : "page", "range" : { "min" : 1 } }, {
             * "title" : "This parameter is out of the allowed range of values.", "name" : "pageSize", "range" : { "min"
             * : 10, "max" : 100 } } ] }
             */
        }
    }

    @Nested
    @DisplayName("get")
    class get {
        private static final String PAYLOADS_PATH = "/payloads/players/get/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.get(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'playerId' when calling get");

            assertDoesNotThrow(() -> api.get("pl45d5vFFGrfdsdsd156dGhh"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            PlayerTheme res = api.get("pl45d5vFFGrfdsdsd156dGhh");

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T11:12:14+00:00");
            assertThat(res.getText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getLink()).isEqualTo("rgba(255, 0, 0, .95)");
            assertThat(res.getLinkHover()).isEqualTo("rgba(255, 255, 255, .75)");
            assertThat(res.getLinkActive()).isEqualTo("rgba(255, 0, 0, .75)");
            assertThat(res.getTrackPlayed()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getTrackUnplayed()).isEqualTo("rgba(255, 255, 255, .1)");
            assertThat(res.getTrackBackground()).isEqualTo("rgba(0, 0, 0, 0)");
            assertThat(res.getBackgroundTop()).isEqualTo("rgba(72, 4, 45, 1)");
            assertThat(res.getBackgroundBottom()).isEqualTo("rgba(94, 95, 89, 1)");
            assertThat(res.getBackgroundText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getEnableApi()).isEqualTo(false);
            assertThat(res.getEnableControls()).isEqualTo(false);
            assertThat(res.getForceAutoplay()).isEqualTo(false);
            assertThat(res.getHideTitle()).isEqualTo(false);
            assertThat(res.getForceLoop()).isEqualTo(false);
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.get("pl45d5vFFGrfdsdsd156dGhh"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("update")
    class update {
        private static final String PAYLOADS_PATH = "/payloads/players/update/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.update(null, new PlayerThemeUpdatePayload()));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'playerId' when calling update");

            e = assertThrows(ApiException.class, () -> api.update("pl45d5vFFGrfdsdsd156dGhh", null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'playerUpdatePayload' when calling update");

            assertDoesNotThrow(() -> api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerThemeUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            PlayerTheme res = api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerThemeUpdatePayload());

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T11:12:14+00:00");
            assertThat(res.getText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getLink()).isEqualTo("rgba(255, 0, 0, .95)");
            assertThat(res.getLinkHover()).isEqualTo("rgba(255, 255, 255, .75)");
            assertThat(res.getLinkActive()).isEqualTo("rgba(255, 0, 0, .75)");
            assertThat(res.getTrackPlayed()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getTrackUnplayed()).isEqualTo("rgba(255, 255, 255, .1)");
            assertThat(res.getTrackBackground()).isEqualTo("rgba(0, 0, 0, 0)");
            assertThat(res.getBackgroundTop()).isEqualTo("rgba(72, 4, 45, 1)");
            assertThat(res.getBackgroundBottom()).isEqualTo("rgba(94, 95, 89, 1)");
            assertThat(res.getBackgroundText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getEnableApi()).isEqualTo(false);
            assertThat(res.getEnableControls()).isEqualTo(false);
            assertThat(res.getForceAutoplay()).isEqualTo(false);
            assertThat(res.getHideTitle()).isEqualTo(false);
            assertThat(res.getForceLoop()).isEqualTo(false);
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerThemeUpdatePayload()));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("create")
    class create {
        private static final String PAYLOADS_PATH = "/payloads/players/create/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.create(null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'playerCreationPayload' when calling create");

            assertDoesNotThrow(() -> api.create(new PlayerThemeCreationPayload()));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            PlayerTheme res = api.create(new PlayerThemeCreationPayload());

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getLink()).isEqualTo("rgba(255, 0, 0, .95)");
            assertThat(res.getLinkHover()).isEqualTo("rgba(255, 255, 255, .75)");
            assertThat(res.getLinkActive()).isEqualTo("rgba(255, 0, 0, .75)");
            assertThat(res.getTrackPlayed()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getTrackUnplayed()).isEqualTo("rgba(255, 255, 255, .1)");
            assertThat(res.getTrackBackground()).isEqualTo("rgba(0, 0, 0, 0)");
            assertThat(res.getBackgroundTop()).isEqualTo("rgba(72, 4, 45, 1)");
            assertThat(res.getBackgroundBottom()).isEqualTo("rgba(94, 95, 89, 1)");
            assertThat(res.getBackgroundText()).isEqualTo("rgba(255, 255, 255, .95)");
            assertThat(res.getEnableApi()).isEqualTo(false);
            assertThat(res.getEnableControls()).isEqualTo(false);
            assertThat(res.getForceAutoplay()).isEqualTo(false);
            assertThat(res.getHideTitle()).isEqualTo(false);
            assertThat(res.getForceLoop()).isEqualTo(false);
        }
    }

    @Nested
    @DisplayName("uploadLogo")
    class uploadLogo {
        private static final String PAYLOADS_PATH = "/payloads/players/uploadLogo/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.uploadLogo(null, new File(""), "en"));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'playerId' when calling uploadLogo");

            e = assertThrows(ApiException.class, () -> api.uploadLogo("12", null, "en"));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'file' when calling uploadLogo");

            e = assertThrows(ApiException.class, () -> api.uploadLogo("12", new File(""), null));
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'link' when calling uploadLogo");

            assertDoesNotThrow(() -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, "");

            PlayerTheme res = api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example");
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"));
            assertThat(e.getCode()).isEqualTo(400);
            assertThat(e).hasMessageThat().contains("Only ['jpg', 'JPG', 'jpeg', 'JPEG', 'png', 'PNG'] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"));
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");

        }
    }

}
