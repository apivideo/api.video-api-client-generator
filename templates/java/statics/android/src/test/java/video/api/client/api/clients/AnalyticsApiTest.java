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
import org.threeten.bp.LocalDate;

import video.api.client.api.ApiException;
import video.api.client.api.models.AnalyticsData;
import video.api.client.api.models.Page;

/**
 * API tests for AnalyticsApi
 */
@DisplayName("AnalyticsApi")
public class AnalyticsApiTest extends AbstractApiTest {

    private final AnalyticsApi api = apiClientMock.analytics();


}
