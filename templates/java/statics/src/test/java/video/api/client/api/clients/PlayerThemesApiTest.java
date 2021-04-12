package video.api.client.api.clients;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.Page;
import video.api.client.api.models.Player;
import video.api.client.api.models.PlayerCreationPayload;
import video.api.client.api.models.PlayerUpdatePayload;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

            assertThatNoException().isThrownBy(() -> api.delete("pl45d5vFFGrfdsdsd156dGhh"));
            assertThatNoException().isThrownBy(() -> api.delete(null));
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

            assertThatThrownBy(() -> api.delete("pl45d5vFFGrfdsdsd156dGhh")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");

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

            assertThatNoException().isThrownBy(() -> api.deleteLogo("pl14Db6oMJRH6SRVoOwORacK"));
            assertThatNoException().isThrownBy(() -> api.deleteLogo(null));
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

            assertThatThrownBy(() -> api.deleteLogo("pl14Db6oMJRH6SRVoOwORacK")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");

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

            assertThatNoException().isThrownBy(() -> api.list().execute());
            // String sortBy, String sortOrder, Integer currentPage, Integer pageSize
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<Player> res = api.list().execute();

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

            assertThatThrownBy(() -> api.list().execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("This parameter is out of the allowed range of values.");

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

            assertThatThrownBy(() -> api.get(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'playerId' when calling get");

            assertThatNoException().isThrownBy(() -> api.get("pl45d5vFFGrfdsdsd156dGhh"));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Player res = api.get("pl45d5vFFGrfdsdsd156dGhh");

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T11:12:14+00:00");
            assertThat(res.getShapeRadius()).isEqualTo(3);
            assertThat(res.getShapeAspect()).isEqualTo("flat");
            assertThat(res.getShapeBackgroundTop()).isEqualTo("rgba(50, 50, 50, .7)");
            assertThat(res.getShapeBackgroundBottom()).isEqualTo("rgba(50, 50, 50, .8)");
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

            assertThatThrownBy(() -> api.get("pl45d5vFFGrfdsdsd156dGhh")).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.update(null, new PlayerUpdatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'playerId' when calling update");

            assertThatThrownBy(() -> api.update("pl45d5vFFGrfdsdsd156dGhh", null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'playerUpdatePayload' when calling update");

            assertThatNoException().isThrownBy(() -> api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerUpdatePayload()));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Player res = api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerUpdatePayload());

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T11:12:14+00:00");
            assertThat(res.getShapeRadius()).isEqualTo(3);
            assertThat(res.getShapeAspect()).isEqualTo("flat");
            assertThat(res.getShapeBackgroundTop()).isEqualTo("rgba(50, 50, 50, .7)");
            assertThat(res.getShapeBackgroundBottom()).isEqualTo("rgba(50, 50, 50, .8)");
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

            assertThatThrownBy(() -> api.update("pl45d5vFFGrfdsdsd156dGhh", new PlayerUpdatePayload()))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");
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

            assertThatThrownBy(() -> api.create(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'playerCreationPayload' when calling create");

            assertThatNoException().isThrownBy(() -> api.create(new PlayerCreationPayload()));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, readResourceFile(PAYLOADS_PATH + "responses/201.json"));

            Player res = api.create(new PlayerCreationPayload());

            assertThat(res.getPlayerId()).isEqualTo("pl45d5vFFGrfdsdsd156dGhh");
            assertThat(res.getCreatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getUpdatedAt()).isEqualTo("2020-01-13T10:09:17+00:00");
            assertThat(res.getShapeRadius()).isEqualTo(3);
            assertThat(res.getShapeAspect()).isEqualTo("flat");
            assertThat(res.getShapeBackgroundTop()).isEqualTo("rgba(50, 50, 50, .7)");
            assertThat(res.getShapeBackgroundBottom()).isEqualTo("rgba(50, 50, 50, .8)");
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

            assertThatThrownBy(() -> api.uploadLogo(null, new File(""), "en")).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'playerId' when calling uploadLogo");

            assertThatThrownBy(() -> api.uploadLogo("12", null, "en")).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'file' when calling uploadLogo");

            assertThatThrownBy(() -> api.uploadLogo("12", new File(""), null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'link' when calling uploadLogo");

            assertThatNoException()
                    .isThrownBy(() -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"));
        }

        @Test
        @DisplayName("201 response")
        public void responseWithStatus201Test() throws ApiException {
            answerOnAnyRequest(201, "");

            Player res = api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example");
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("Only ['jpg', 'JPG', 'jpeg', 'JPEG', 'png', 'PNG'] extensions are supported.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.uploadLogo("pl14Db6oMJRH6SRVoOwORacK", new File(""), "file_example"))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("The requested resource was not found.");

        }
    }

}
