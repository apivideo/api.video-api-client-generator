/*
 * api.video Java API client
 * api.video is an API that encodes on the go to facilitate immediate playback, enhancing viewer streaming experiences across multiple devices and platforms. You can stream live or on-demand online videos within minutes.
 *
 * The version of the OpenAPI document: 1
 * Contact: ecosystem-team@api.video
 *
 * NOTE: This class is auto generated.
 * Do not edit the class manually.
 */

package video.api.client.api.clients;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.threeten.bp.OffsetDateTime.parse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import video.api.client.api.ApiException;
import video.api.client.api.models.LiveStreamSession;
import video.api.client.api.models.LiveStreamSessionClient;
import video.api.client.api.models.LiveStreamSessionDevice;
import video.api.client.api.models.LiveStreamSessionLocation;
import video.api.client.api.models.LiveStreamSessionReferrer;
import video.api.client.api.models.LiveStreamSessionSession;
import video.api.client.api.models.Page;
import video.api.client.api.models.PlayerSessionEvent;
import video.api.client.api.models.VideoSession;
import video.api.client.api.models.VideoSessionClient;
import video.api.client.api.models.VideoSessionDevice;
import video.api.client.api.models.VideoSessionLocation;
import video.api.client.api.models.VideoSessionOs;
import video.api.client.api.models.VideoSessionReferrer;
import video.api.client.api.models.VideoSessionSession;


/**
 * API tests for RawStatisticsApi
 */
@DisplayName("RawStatisticsApi")
public class RawStatisticsApiTest extends AbstractApiTest {

    private final RawStatisticsApi api = apiClientMock.rawStatistics();

    @Nested
    @DisplayName("listLiveStreamSessions")
    class listLiveStreamSessions {
        private static final String PAYLOADS_PATH = "/payloads/rawstatistics/listLiveStreamSessions/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.listLiveStreamSessions(null).execute());
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'liveStreamId' when calling listLiveStreamSessions");

            assertDoesNotThrow(() -> api.listLiveStreamSessions("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute());
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<LiveStreamSession> page = api.listLiveStreamSessions("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute();

            assertThat(page.getCurrentPage()).isEqualTo(1);
            assertThat(page.getPageSize()).isEqualTo(25);
            assertThat(page.getPagesTotal()).isEqualTo(1);
            assertThat(page.getCurrentPageItems()).isEqualTo(1);

            assertThat(page.getItems()).containsExactlyElementsIn(Collections.singletonList(new LiveStreamSession()
                    .session(new LiveStreamSessionSession().sessionId("ps4zRWVOv2If2vzKJLMr3jQo")
                            .loadedAt(parse("2018-09-11T13:04:37.89Z"))
                            .endedAt(parse("2018-09-11T14:47:22.186Z")))
                    .location(new LiveStreamSessionLocation().country("France").city("Paris"))
                    .referrer(new LiveStreamSessionReferrer().url("unknown").medium("unknown").source("unknown")
                            .searchTerm("unknown"))
                    .device(new LiveStreamSessionDevice().type("desktop").vendor("unknown").model("unknown"))
                    .os(new VideoSessionOs().name("unknown").shortname("unknown").version("unknown"))
                    .client(new LiveStreamSessionClient().type("browser").name("Firefox").version("61.0"))))
                    .inOrder();
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.listLiveStreamSessions("vi4k0jvEUuaTdRAEjQ4Jfrgz").execute());
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("listSessionEvents")
    class listSessionEvents {
        private static final String PAYLOADS_PATH = "/payloads/rawstatistics/listSessionEvents/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.listSessionEvents(null).execute());
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'sessionId' when calling listSessionEvents");

            assertDoesNotThrow(() -> api.listSessionEvents("psEmFwGQUAXR2lFHj5nDOpy").execute());
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<PlayerSessionEvent> page = api.listSessionEvents("psEmFwGQUAXR2lFHj5nDOpy").execute();

            assertThat(page.getCurrentPage()).isEqualTo(1);
            assertThat(page.getCurrentPageItems()).isEqualTo(25);
            assertThat(page.getPageSize()).isEqualTo(25);
            assertThat(page.getPagesTotal()).isEqualTo(2);
            assertThat(page.getItemsTotal()).isEqualTo(30);

            assertThat(page.getItems()).containsExactlyElementsIn(Arrays.asList(
                    new PlayerSessionEvent().type("ready").emittedAt(parse("2020-09-15T09:47:42+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("play").emittedAt(parse("2020-09-15T21:35:57+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-15T21:36:05+00:00"))
                            .at(7),
                    new PlayerSessionEvent().type("resume").emittedAt(parse("2020-09-15T21:36:19+00:00"))
                            .at(21),
                    new PlayerSessionEvent().type("seek.forward")
                            .emittedAt(parse("2020-09-15T21:36:19+00:00")).from(7).to(21),
                    new PlayerSessionEvent().type("end").emittedAt(parse("2020-09-15T21:36:28+00:00"))
                            .at(30),
                    new PlayerSessionEvent().type("play").emittedAt(parse("2020-09-15T21:36:29+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("seek.backward")
                            .emittedAt(parse("2020-09-15T21:36:29+00:00")).from(30).to(0),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-15T21:36:29+00:00"))
                            .at(21),
                    new PlayerSessionEvent().type("resume").emittedAt(parse("2020-09-15T21:36:30+00:00"))
                            .at(21),
                    new PlayerSessionEvent().type("seek.forward")
                            .emittedAt(parse("2020-09-15T21:36:30+00:00")).from(0).to(21),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-15T21:36:33+00:00"))
                            .at(20),
                    new PlayerSessionEvent().type("resume").emittedAt(parse("2020-09-15T21:36:33+00:00"))
                            .at(20),
                    new PlayerSessionEvent().type("seek.backward")
                            .emittedAt(parse("2020-09-15T21:36:33+00:00")).from(24).to(20),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-15T21:36:39+00:00"))
                            .at(17),
                    new PlayerSessionEvent().type("resume").emittedAt(parse("2020-09-15T21:36:39+00:00"))
                            .at(17),
                    new PlayerSessionEvent().type("seek.forward")
                            .emittedAt(parse("2020-09-15T21:36:39+00:00")).from(17).to(17),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-15T21:36:41+00:00"))
                            .at(19),
                    new PlayerSessionEvent().type("ready").emittedAt(parse("2020-09-17T09:20:47+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("ready").emittedAt(parse("2020-09-17T09:41:01+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("ready").emittedAt(parse("2020-09-17T09:41:08+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("play").emittedAt(parse("2020-09-17T09:41:10+00:00"))
                            .at(0),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-17T09:41:12+00:00"))
                            .at(1),
                    new PlayerSessionEvent().type("resume").emittedAt(parse("2020-09-17T09:41:13+00:00"))
                            .at(1),
                    new PlayerSessionEvent().type("pause").emittedAt(parse("2020-09-17T09:41:15+00:00"))
                            .at(3))).inOrder();
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.listSessionEvents("psEmFwGQUAXR2lFHj5nDOpy").execute());
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

    @Nested
    @DisplayName("listVideoSessions")
    class listVideoSessions {
        private static final String PAYLOADS_PATH = "/payloads/rawstatistics/listVideoSessions/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            ApiException e = assertThrows(ApiException.class, () -> api.listVideoSessions(null).execute());
            assertThat(e).hasMessageThat().contains("Missing the required parameter 'videoId' when calling listVideoSessions");

            assertDoesNotThrow(() -> api.listVideoSessions("vi4k0jvEUuaTdRAEjQ4Prklg").execute());
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<VideoSession> page = api.listVideoSessions("vi4k0jvEUuaTdRAEjQ4Prklg").execute();

            assertThat(page.getCurrentPage()).isEqualTo(1);
            assertThat(page.getPageSize()).isEqualTo(25);
            assertThat(page.getPagesTotal()).isEqualTo(1);
            assertThat(page.getCurrentPageItems()).isEqualTo(1);

            assertThat(page.getItems()).containsExactlyElementsIn(Arrays.asList(new VideoSession()
                    .session(new VideoSessionSession().sessionId("psEmFwGQUAXR2lFHj5nDOpy")
                            .loadedAt(parse("2019-06-24T11:45:01.109Z"))
                            .endedAt(parse("2019-06-24T11:49:19.243Z")))
                    .location(new VideoSessionLocation().country("France").city("Paris"))
                    .referrer(new VideoSessionReferrer().url("https://api.video").medium("organic")
                            .source("https://google.com").searchTerm("video encoding hosting and delivery"))
                    .device(new VideoSessionDevice().type("desktop").vendor("Dell").model("unknown"))
                    .os(new VideoSessionOs().name("Microsoft Windows").shortname("W10").version("Windows10"))
                    .client(new VideoSessionClient().type("browser").name("Firefox").version("67.0"))))
                    .inOrder();
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            ApiException e = assertThrows(ApiException.class, () -> api.listVideoSessions("vi4k0jvEUuaTdRAEjQ4Prklg").execute());
            assertThat(e.getCode()).isEqualTo(404);
            assertThat(e).hasMessageThat().contains("The requested resource was not found.");
        }
    }

}
