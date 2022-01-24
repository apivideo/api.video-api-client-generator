package video.api.client.api;

import okhttp3.RequestBody;
import okio.Buffer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import video.api.client.api.models.VideoUpdatePayload;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("VideosDelegatedUploadApi")
public class ApiClientTest {

    private String requestBodyToString(ApiClient apiClient, Object payload) throws IOException, ApiException {
        RequestBody requestBody = apiClient.serialize(payload, "application/json");
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.toString();
    }

    @Test
    @DisplayName("required parameters")
    public void requiredParametersTest() throws ApiException, IOException {
        ApiClient apiClient = new ApiClient("path");

        VideoUpdatePayload videoUpdatePayload = new VideoUpdatePayload()
                .title("title")
                .playerId(null);

        assertThat(requestBodyToString(apiClient, videoUpdatePayload)).isEqualTo("[text={\"playerId\":null,\"title\":\"title\"}]");

        videoUpdatePayload.unsetPlayerId();

        assertThat(requestBodyToString(apiClient, videoUpdatePayload)).isEqualTo("[text={\"title\":\"title\"}]");
    }

    @Test
    public void applicationNameVerification() {
        ApiClient apiClient = new ApiClient("path");
        assertThatThrownBy(() -> apiClient.setApplicationName("bad application name"));
        assertThatThrownBy(() -> apiClient.setApplicationName("012345678901234567890123456789012345678901234567890"));
        apiClient.setApplicationName("great-application-name/2.0.0");
    }

}
