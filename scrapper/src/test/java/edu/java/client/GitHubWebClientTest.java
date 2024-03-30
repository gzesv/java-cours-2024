package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.github.GitHubWebClient;
import edu.java.dto.github.RepositoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

class GitHubWebClientTest {
    private static final String repositoryName = "java-cours-2024";

    private static final String userName = "gzesv";

    private WireMockServer wireMockServer;

    private GitHubWebClient gitHubWebClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        gitHubWebClient = new GitHubWebClient(
            "http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void repositoryInfoTest() {
        wireMockServer
            .stubFor(get("/repos/gzesv/java-cours-2024")
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(getResponseBody())));

        RepositoryResponse response = gitHubWebClient.fetchRepository(userName, repositoryName);

        assertThat(response.id())
            .isEqualTo(759342600L);
        assertThat(response.owner().login())
            .isEqualTo(userName);
        assertThat(response.repositoryName())
            .isEqualTo(repositoryName);
    }

    static String getResponseBody() {
        return """
            {
                "id": "759342600",
                "name": "java-cours-2024",
                "owner": {
                    "login": "gzesv",
                    "id": "126079469"
                },
                "created_at": "2024-02-18T10:26:42Z",
                "updated_at": "2024-02-18T10:31:55Z",
                "pushed_at": "2024-02-25T10:35:17Z"
            }
            """;
    }
}
