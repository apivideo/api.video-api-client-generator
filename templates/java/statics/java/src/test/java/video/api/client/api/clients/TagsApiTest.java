package video.api.client.api.clients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.ListTagsResponseData;
import video.api.client.api.models.Page;
import video.api.client.api.models.PaginationLink;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * API tests for TagsApi
 */
@DisplayName("TagsApi")
public class TagsApiTest extends AbstractApiTest {
    private final TagsApi api = apiClientMock.tags();

    @Nested
    @DisplayName("list")
    class listTags {
        private static final String PAYLOADS_PATH = "/payloads/tags/list/";


        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            Page<ListTagsResponseData> res = api.list().execute();

            assertThat(res.getCurrentPage()).isEqualTo(1);
            assertThat(res.getPageSize()).isEqualTo(25);
            assertThat(res.getPagesTotal()).isEqualTo(1);
            assertThat(res.getCurrentPageItems()).isEqualTo(2);
            assertThat(res.getLinks()).containsExactlyInAnyOrder(
                    new PaginationLink().rel("self").uri(URI.create("/tags?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("first").uri(URI.create("/tags?currentPage=1&pageSize=25")),
                    new PaginationLink().rel("last").uri(URI.create("/tags?currentPage=1&pageSize=25")));

            assertThat(res.getItems()).containsExactlyInAnyOrder(
                    new ListTagsResponseData().value("maths").videoCount(33),
                    new ListTagsResponseData().value("tutorials").videoCount(10)
            );
        }
    }

}
