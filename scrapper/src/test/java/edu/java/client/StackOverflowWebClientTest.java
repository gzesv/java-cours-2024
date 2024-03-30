package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.stackoverflow.StackOverflowWebClient;
import edu.java.dto.stackoverflow.QuestionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

class StackOverflowWebClientTest {
    private static final Long questionId = 75634833L;

    private WireMockServer wireMockServer;

    private StackOverflowWebClient stackOverflowWebClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stackOverflowWebClient = new StackOverflowWebClient(
            "http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void repositoryInfoTest() {
        wireMockServer
            .stubFor(get("/questions/75634833?site=stackoverflow")
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(getResponseBody())));

        QuestionResponse response = stackOverflowWebClient.fetchQuestion(questionId);

        assertThat(response.items().getFirst().id())
            .isEqualTo(questionId);
    }

    static String getResponseBody() {
        return """
            {
                "items": [
                    {
                        "owner": {
                            "display_name": "LenaHm"
                        },
                        "view_count": 629,
                        "answer_count": 1,
                        "last_activity_date": 1677946516,
                        "question_id": 75634833
                    }
                ]
            }
            """;
    }
}
