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

import video.api.client.api.ApiException;
import video.api.client.api.models.*;

import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for AnalyticsApi
 */
@DisplayName("AnalyticsApi")
public class AnalyticsApiTest extends AbstractApiTest {

    private final AnalyticsApi api = apiClientMock.analytics();

    @Nested
    @DisplayName("getAggregatedMetrics")
    class getAggregatedMetrics {
        private static final String PAYLOADS_PATH = "/payloads/analytics/getAggregatedMetrics/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.getAggregatedMetrics("play", "count").execute());
            // String metric, String aggregation, OffsetDateTime from, OffsetDateTime to, String filterBy, Integer
            // currentPage, Integer pageSize
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            AnalyticsAggregatedMetricsResponse res = api.getAggregatedMetrics("play", "count").execute();

            /*
             * sample response: { "context" : { "metric" : "impression", "aggregation" : "count", "timeframe" : { "from"
             * : "2024-05-28T11:15:07+00:00", "to" : "2024-05-29T11:15:07+00:00" } }, "data" : 346.5 }
             */
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400-0.json"));

            assertThatThrownBy(() -> api.getAggregatedMetrics("play", "count").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("An attribute is invalid.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/request-invalid-query-parameter", "title" :
             * "A query parameter is invalid.", "status" : 400, "detail" : "This field was not expected.", "name" :
             * "from:2024-05-20T09:15:05+02:00" }
             */
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.getAggregatedMetrics("play", "count").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("Unrecognized request URL.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/unrecognized-request-url", "title" :
             * "Unrecognized request URL.", "status" : 404 }
             */
        }

        @Test
        @DisplayName("429 response")
        public void responseWithStatus429Test() throws ApiException {
            answerOnAnyRequest(429, readResourceFile(PAYLOADS_PATH + "responses/429.json"));

            assertThatThrownBy(() -> api.getAggregatedMetrics("play", "count").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(429))
                    .hasMessage("Too many requests.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/too-many-requests", "title" :
             * "Too many requests.", "status" : 429 }
             */
        }
    }

    @Nested
    @DisplayName("getMetricsBreakdown")
    class getMetricsBreakdown {
        private static final String PAYLOADS_PATH = "/payloads/analytics/getMetricsBreakdown/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.getMetricsBreakdown("play", "media-id").execute());
            // String metric, String breakdown, OffsetDateTime from, OffsetDateTime to, String filterBy, Integer
            // currentPage, Integer pageSize
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<AnalyticsMetricsBreakdownResponseData> res = api.getMetricsBreakdown("play", "media-id").execute();

            /*
             * sample response: { "context" : { "metric" : "play", "breakdown" : "country", "timeframe" : { "from" :
             * "2024-04-28T07:15:05+00:00", "to" : "2024-05-29T11:25:37+00:00" } }, "data" : [ { "metricValue" : 7,
             * "dimensionValue" : "FR" } ], "pagination" : { "currentPage" : 1, "currentPageItems" : 1, "pageSize" : 25,
             * "pagesTotal" : 1, "itemsTotal" : 1, "links" : [ { "rel" : "self", "uri" :
             * "/data/buckets/play/country?from=2024-04-28T09%3A15%3A05%2B02%3A00&amp;currentPage=1&amp;pageSize=25" },
             * { "rel" : "first", "uri" :
             * "/data/buckets/play/country?from=2024-04-28T09%3A15%3A05%2B02%3A00&amp;currentPage=1&amp;pageSize=25" },
             * { "rel" : "last", "uri" :
             * "/data/buckets/play/country?from=2024-04-28T09%3A15%3A05%2B02%3A00&amp;currentPage=1&amp;pageSize=25" } ]
             * } }
             */
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400-0.json"));

            assertThatThrownBy(() -> api.getMetricsBreakdown("play", "media-id").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("An attribute is invalid.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/request-invalid-query-parameter", "title" :
             * "A query parameter is invalid.", "status" : 400, "detail" : "This field was not expected.", "name" :
             * "from:2024-05-20T09:15:05+02:00" }
             */
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.getMetricsBreakdown("play", "media-id").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("Unrecognized request URL.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/unrecognized-request-url", "title" :
             * "Unrecognized request URL.", "status" : 404 }
             */
        }

        @Test
        @DisplayName("429 response")
        public void responseWithStatus429Test() throws ApiException {
            answerOnAnyRequest(429, readResourceFile(PAYLOADS_PATH + "responses/429.json"));

            assertThatThrownBy(() -> api.getMetricsBreakdown("play", "media-id").execute())
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(429))
                    .hasMessage("Too many requests.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/too-many-requests", "title" :
             * "Too many requests.", "status" : 429 }
             */
        }
    }

    @Nested
    @DisplayName("getMetricsOverTime")
    class getMetricsOverTime {
        private static final String PAYLOADS_PATH = "/payloads/analytics/getMetricsOverTime/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatNoException().isThrownBy(() -> api.getMetricsOverTime("play").execute());
            // String metric, OffsetDateTime from, OffsetDateTime to, OffsetDateTime interval, String filterBy, Integer
            // currentPage, Integer pageSize
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<AnalyticsMetricsOverTimeResponseData> res = api.getMetricsOverTime("play").execute();

            /*
             * sample response: { "context" : { "metric" : "play", "interval" : "hour", "timeframe" : { "from" :
             * "2024-05-28T11:08:39+00:00", "to" : "2024-05-29T11:08:39+00:00" } }, "data" : [ { "emittedAt" :
             * "2024-05-29T07:00:00+00:00", "metricValue" : 2 }, { "emittedAt" : "2024-05-29T08:00:00+00:00",
             * "metricValue" : 1 }, { "emittedAt" : "2024-05-29T09:00:00+00:00", "metricValue" : 1 } ], "pagination" : {
             * "currentPage" : 1, "currentPageItems" : 3, "pageSize" : 25, "pagesTotal" : 1, "itemsTotal" : 3, "links" :
             * [ { "rel" : "self", "uri" : "/data/timeseries/play?currentPage=1&amp;pageSize=25" }, { "rel" : "first",
             * "uri" : "/data/timeseries/play?currentPage=1&amp;pageSize=25" }, { "rel" : "last", "uri" :
             * "/data/timeseries/play?currentPage=1&amp;pageSize=25" } ] } }
             */
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400-0.json"));

            assertThatThrownBy(() -> api.getMetricsOverTime("play").execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("An attribute is invalid.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/request-invalid-query-parameter", "title" :
             * "A query parameter is invalid.", "status" : 400, "detail" : "This field was not expected.", "name" :
             * "from:2024-05-20T09:15:05+02:00" }
             */
        }

        @Test
        @DisplayName("404 response")
        public void responseWithStatus404Test() throws ApiException {
            answerOnAnyRequest(404, readResourceFile(PAYLOADS_PATH + "responses/404.json"));

            assertThatThrownBy(() -> api.getMetricsOverTime("play").execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(404))
                    .hasMessage("Unrecognized request URL.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/unrecognized-request-url", "title" :
             * "Unrecognized request URL.", "status" : 404 }
             */
        }

        @Test
        @DisplayName("429 response")
        public void responseWithStatus429Test() throws ApiException {
            answerOnAnyRequest(429, readResourceFile(PAYLOADS_PATH + "responses/429.json"));

            assertThatThrownBy(() -> api.getMetricsOverTime("play").execute()).isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(429))
                    .hasMessage("Too many requests.");

            /*
             * sample response: { "type" : "https://docs.api.video/reference/too-many-requests", "title" :
             * "Too many requests.", "status" : 429 }
             */
        }
    }

}
