package edu.java.client.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.dto.request.LinkUpdateRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;

class BotWebClientTest {

    private static final String UPDATE_CONTROLLER_PATH = "/updates";

    @Test
    public void botWebClientTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(post(UPDATE_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            )
        );
        server.start();

        BotClient client = new BotWebClient("http://localhost:" + server.port());
        String actual = client.sendUpdate(new LinkUpdateRequest(
            1L,
            "",
            "",
            List.of()
        ));

        assertThat(actual).isNull();

        server.stop();
    }
}
