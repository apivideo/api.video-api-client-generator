package video.api.client.api.clients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import video.api.client.api.ApiException;
import video.api.client.api.models.AccessToken;
import video.api.client.api.models.AuthenticatePayload;
import video.api.client.api.models.RefreshTokenPayload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * API tests for AuthenticationApi
 */
@DisplayName("AuthenticationApi")
public class AuthenticationApiTest extends AbstractApiTest {

    private final AuthenticationApi api = new AuthenticationApi(apiClientMock.getHttpClient());

    @Nested
    @DisplayName("authenticate")
    class authenticate {
        private static final String PAYLOADS_PATH = "/payloads/authentication/authenticate/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.authenticate(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'authenticatePayload' when calling authenticate");

            assertThatThrownBy(() -> api.authenticate(new AuthenticatePayload())).isInstanceOf(ApiException.class)
                    .hasMessage(
                            "Missing the required parameter 'authenticatePayload.apiKey' when calling authenticate");

            assertThatNoException().isThrownBy(() -> api.authenticate(new AuthenticatePayload().apiKey("key")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            AccessToken res = api.authenticate(new AuthenticatePayload().apiKey("key"));

            assertThat(res.getAccessToken()).isEqualTo(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjUyZWM4NWUyMjFkODZjOWI0NDQ5NzBhMjQwMzUyOWQ4MDQyNGQ3ZmJjYjFlYWM2MjVlM2VkMjI2YWRlNTcxMDY2NDUyZDc0NjdhN2E4NjI0In0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiNTJlYzg1ZTIyMWQ4NmM5YjQ0NDk3MGEyNDAzNTI5ZDgwNDI0ZDdmYmNiMWVhYzYyNWUzZWQyMjZhZGU1NzEwNjY0NTJkNzQ2N2E3YTg2MjQiLCJpYXQiOjE1MjUyNzYxNDcsIm5iZiI6MTUyNTI3NjE0NywiZXhwIjoxNTI1Mjc5NzQ3LCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.rUvishDNyJLNlI4W5VmguNecm5KD2uZgPkKJQbaqw-cJbSrVxkSbiKYtk_E3cz3WT7-IS2yFTsYN3uIo5Rbit8_HftweyEp2bdBRI8yjR6oZZ1sNJJXswISN1i2kk4r-aaxu7Xxf_LtsjOMUj_YZsvcc2nqBXPKjHbJCJryx3DDJaIcymOqao7nhQaCCQyrQooAXNTYs4E9fWN1dC_x2O-zok5TuG-xhEW-umwxfSUMWNgSTkz38ACceQ0PCJSgB3jqjDH4MwC7B3ppEPZuK5E6JhKeyRlalswRyYq3UQPnVeMTam7YQHsuTgbehF6WySW8i44o7V_MCe9hjPdp-WA");
            assertThat(res.getRefreshToken()).isEqualTo(
                    "def50200a28d88fb9aaa921be78eeb5604b071101a334899a7d5fc7492cf8ea752962ddc8961fe5c126101d4ecacd980396eb2fd494995b812dffcb98256c4277f790d1f658fc2d2e34f350740544e5232d69d68d34c648271d706c5e7049adac0b1832d0fdf71809715cc7e97fa63f65966deadb501a55ff469b0fd23a637cb6acbe9d9b8594a17f09efc2efeed82984764a0065d5e29c950c7b081a61ba2aaa192be3085c400ee37eac50fa9320ce2cfe8916c8165418d23e9f91b6a5c8515e1d74ee193a5a1ca01954fbff27361c20184240be2359e0afbed0bf1c762cf872450b5e8b5d4704f4fd9583e4470adc98409dd42965709712806bd9019378a72eea0b4912ce684ffd833db5806ab84174f905db2a75380071d004615c944bb8f8c4045cce7234c2be9a2330522cf7f067b8e58f57cffb6edb4b7ef91313e12bcde47e5e76ceee7fa52990132288f345d33ed917ae4fd54b7284f8964d898e97e1ee3bc4157f75d7fee63976e4be66ac1ec32ef74afa533f0eb593523f226cbec57d196ac8962");
            assertThat(res.getExpiresIn()).isEqualTo(3600);
            assertThat(res.getTokenType()).isEqualTo("Bearer");
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.authenticate(new AuthenticatePayload().apiKey("invalid")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("The user credentials were incorrect.");
        }
    }

    @Nested
    @DisplayName("refresh")
    class refresh {
        private static final String PAYLOADS_PATH = "/payloads/authentication/refresh/";

        @Test
        @DisplayName("required parameters")
        public void requiredParametersTest() {
            answerOnAnyRequest(201, "{}");

            assertThatThrownBy(() -> api.refresh(null)).isInstanceOf(ApiException.class)
                    .hasMessage("Missing the required parameter 'refreshTokenPayload' when calling refresh");

            assertThatThrownBy(() -> api.refresh(new RefreshTokenPayload())).isInstanceOf(ApiException.class)
                    .hasMessage(
                            "Missing the required parameter 'refreshTokenPayload.refreshToken' when calling refresh");

            assertThatNoException().isThrownBy(() -> api.refresh(new RefreshTokenPayload().refreshToken("token")));
        }

        @Test
        @DisplayName("200 response")
        public void responseWithStatus200Test() throws ApiException {
            answerOnAnyRequest(200, readResourceFile(PAYLOADS_PATH + "responses/200.json"));

            AccessToken res = api.refresh(new RefreshTokenPayload().refreshToken("token"));

            assertThat(res.getAccessToken()).isEqualTo(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjUyZWM4NWUyMjFkODZjOWI0NDQ5NzBhMjQwMzUyOWQ4MDQyNGQ3ZmJjYjFlYWM2MjVlM2VkMjI2YWRlNTcxMDY2NDUyZDc0NjdhN2E4NjI0In0.eyJhdWQiOiJsaWJjYXN0IiwianRpIjoiNTJlYzg1ZTIyMWQ4NmM5YjQ0NDk3MGEyNDAzNTI5ZDgwNDI0ZDdmYmNiMWVhYzYyNWUzZWQyMjZhZGU1NzEwNjY0NTJkNzQ2N2E3YTg2MjQiLCJpYXQiOjE1MjUyNzYxNDcsIm5iZiI6MTUyNTI3NjE0NywiZXhwIjoxNTI1Mjc5NzQ3LCJzdWIiOiJ1c01vbml0b3IiLCJzY29wZXMiOlsibW9uaXRvci5saWJjYXN0LmNvbSJdLCJjb250ZXh0Ijp7InVzZXIiOiJ1c01vbml0b3IiLCJwcm9qZWN0IjoicHJNb25pdG9yIiwibWVtYmVyIjoibWVNb25pdG9yIn19.rUvishDNyJLNlI4W5VmguNecm5KD2uZgPkKJQbaqw-cJbSrVxkSbiKYtk_E3cz3WT7-IS2yFTsYN3uIo5Rbit8_HftweyEp2bdBRI8yjR6oZZ1sNJJXswISN1i2kk4r-aaxu7Xxf_LtsjOMUj_YZsvcc2nqBXPKjHbJCJryx3DDJaIcymOqao7nhQaCCQyrQooAXNTYs4E9fWN1dC_x2O-zok5TuG-xhEW-umwxfSUMWNgSTkz38ACceQ0PCJSgB3jqjDH4MwC7B3ppEPZuK5E6JhKeyRlalswRyYq3UQPnVeMTam7YQHsuTgbehF6WySW8i44o7V_MCe9hjPdp-WA");
            assertThat(res.getRefreshToken()).isEqualTo(
                    "def50200a28d88fb9aaa921be78eeb5604b071101a334899a7d5fc7492cf8ea752962ddc8961fe5c126101d4ecacd980396eb2fd494995b812dffcb98256c4277f790d1f658fc2d2e34f350740544e5232d69d68d34c648271d706c5e7049adac0b1832d0fdf71809715cc7e97fa63f65966deadb501a55ff469b0fd23a637cb6acbe9d9b8594a17f09efc2efeed82984764a0065d5e29c950c7b081a61ba2aaa192be3085c400ee37eac50fa9320ce2cfe8916c8165418d23e9f91b6a5c8515e1d74ee193a5a1ca01954fbff27361c20184240be2359e0afbed0bf1c762cf872450b5e8b5d4704f4fd9583e4470adc98409dd42965709712806bd9019378a72eea0b4912ce684ffd833db5806ab84174f905db2a75380071d004615c944bb8f8c4045cce7234c2be9a2330522cf7f067b8e58f57cffb6edb4b7ef91313e12bcde47e5e76ceee7fa52990132288f345d33ed917ae4fd54b7284f8964d898e97e1ee3bc4157f75d7fee63976e4be66ac1ec32ef74afa533f0eb593523f226cbec57d196ac8962");
            assertThat(res.getExpiresIn()).isEqualTo(3600);
            assertThat(res.getTokenType()).isEqualTo("Bearer");
        }

        @Test
        @DisplayName("400 response")
        public void responseWithStatus400Test() throws ApiException {
            answerOnAnyRequest(400, readResourceFile(PAYLOADS_PATH + "responses/400.json"));

            assertThatThrownBy(() -> api.refresh(new RefreshTokenPayload().refreshToken("token")))
                    .isInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(400))
                    .hasMessage("The user credentials were incorrect.");
        }
    }
}
