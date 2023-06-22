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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.truth.Truth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import video.api.client.api.ApiException;
import video.api.client.api.models.AnalyticsData;
import video.api.client.api.models.Page;

/**
 * API tests for AnalyticsApi
 */
@DisplayName("AnalyticsApi")
public class AnalyticsApiTest extends AbstractApiTest {

    private final AnalyticsApi api = new AnalyticsApi(apiClientMock.getHttpClient());

    @Nested
    @DisplayName("getLiveStreamsPlays")
    class getLiveStreamsPlays {
        private static final String PAYLOADS_PATH = "/payloads/analytics/getLiveStreamsPlays/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), null).execute())
                    .isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'dimension' when calling getLiveStreamsPlays");
            assertThatThrownBy(() -> api.getLiveStreamsPlays(null, "liveStreamId").execute())
                    .isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'from' when calling getLiveStreamsPlays");

            assertThatNoException()
                    .isThrownBy(() -> api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "liveStreamId").execute());
        }

        @Test
        @DisplayName("200 response by liveStreamId")
        public void responseWithStatusByLiveStreamId200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-0.json"));

            Page<AnalyticsData> res = api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "liveStreamId").execute();

            AnalyticsData expected1 = new AnalyticsData().value("li3q7HxhApxRF1c8F8r6VeaI");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("li3q7HxhApxRF1c8F8r6VeaB");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("li3q7HxhApxRF1c8F8r6VeaD");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("200 response by country")
        public void responseWithStatusByCountry200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-1.json"));

            Page<AnalyticsData> res = api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "country").execute();

            AnalyticsData expected1 = new AnalyticsData().value("france");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("united states");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("spain");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("200 response by emittedAt")
        public void responseWithStatusByEmittedAt200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-2.json"));

            Page<AnalyticsData> res = api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "emittedAt").execute();

            AnalyticsData expected1 = new AnalyticsData().value("2023-06-10T10:00:00.000Z");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("2023-06-10T11:00:00.000Z");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("2023-06-10T12:00:00.000Z");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400ForUnknownDimensionTest() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400-2.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "unknownDimension").execute());
            Truth.assertThat(e.getCode()).isEqualTo(400);
            Truth.assertThat(e).hasMessageThat().contains("A query parameter is invalid.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            ApiException e = assertThrows(ApiException.class,
                    () -> api.getLiveStreamsPlays(LocalDate.parse("2023-04-01"), "country").execute());
            Truth.assertThat(e.getCode()).isEqualTo(404);
            Truth.assertThat(e).hasMessageThat().contains("");
        }
    }

    @Nested
    @DisplayName("getVideosPlays")
    class getVideosPlays {
        private static final String PAYLOADS_PATH = "/payloads/analytics/getVideosPlays/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.getVideosPlays(LocalDate.parse("2023-04-01"), null).execute())
                    .isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'dimension' when calling getVideosPlays");
            assertThatThrownBy(() -> api.getVideosPlays(null, "videoId").execute()).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'from' when calling getVideosPlays");

            assertThatNoException()
                    .isThrownBy(() -> api.getVideosPlays(LocalDate.parse("2023-04-01"), "videoId").execute());
        }

        @Test
        @DisplayName("200 response by videoId")
        public void responseWithStatusByVideoId200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-0.json"));

            Page<AnalyticsData> res = api.getVideosPlays(LocalDate.parse("2023-04-01"), "videoId").execute();

            AnalyticsData expected1 = new AnalyticsData().value("vi3q7HxhApxRF1c8F8r6VeaI");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("vi3q7HxhApxRF1c8F8r6VeaF");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("vi3q7HxhApxRF1c8F8r6VeaH");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("200 response by country")
        public void responseWithStatusByCountry200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-1.json"));

            Page<AnalyticsData> res = api.getVideosPlays(LocalDate.parse("2023-04-01"), "country").execute();

            AnalyticsData expected1 = new AnalyticsData().value("france");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("united states");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("spain");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("200 response by emittedAt")
        public void responseWithStatusByEmittedAt200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200-2.json"));

            Page<AnalyticsData> res = api.getVideosPlays(LocalDate.parse("2023-04-01"), "emittedAt").execute();

            AnalyticsData expected1 = new AnalyticsData().value("2023-06-10T10:00:00.000Z");
            expected1.setPlays(100);
            AnalyticsData expected2 = new AnalyticsData().value("2023-06-10T11:00:00.000Z");
            expected2.setPlays(10);
            AnalyticsData expected3 = new AnalyticsData().value("2023-06-10T12:00:00.000Z");
            expected3.setPlays(1);
            assertThat(res.getItems()).containsExactlyInAnyOrder(expected1, expected2, expected3);
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400ForUnknownDimensionTest() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400-2.json"));

            ApiException e = assertThrows(ApiException.class,
                    () -> api.getVideosPlays(LocalDate.parse("2023-04-01"), "unknownDimension").execute());
            Truth.assertThat(e.getCode()).isEqualTo(400);
            Truth.assertThat(e).hasMessageThat().contains("A query parameter is invalid.");
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, "");

            ApiException e = assertThrows(ApiException.class,
                    () -> api.getVideosPlays(LocalDate.parse("2023-04-01"), "country").execute());
            Truth.assertThat(e.getCode()).isEqualTo(404);
            Truth.assertThat(e).hasMessageThat().contains("");
        }
    }

}
