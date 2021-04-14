package video.api.client.api.clients;

import video.api.client.ApiVideoClient;
import okhttp3.*;
import org.mockito.invocation.InvocationOnMock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractApiTest {
    private final OkHttpClient httpClient = mock(OkHttpClient.class);
    protected final ApiVideoClient apiClientMock = new ApiVideoClient(httpClient);

    private Call buildAnswer(InvocationOnMock invocationOnMock, Integer statusCode, String body) throws IOException {
        Request request = invocationOnMock.getArgumentAt(0, Request.class);
        Call call = mock(Call.class);

        when(call.execute()).thenReturn(new Response.Builder().request(request).protocol(Protocol.HTTP_2)
                .code(statusCode) // status code
                .message("").body(ResponseBody.create(MediaType.get("application/json; charset=utf-8"), body)).build());

        return call;
    }

    protected String readResourceFile(String path) {
        try {
            URL resource = this.getClass().getResource(path);
            if (resource == null)
                throw new FileNotFoundException(path);
            return new String(java.nio.file.Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void answerOnAnyRequest(Integer statusCode, String body) {
        when(httpClient.newCall(any())).thenAnswer(invocationOnMock -> buildAnswer(invocationOnMock, statusCode, body));
    }

}
