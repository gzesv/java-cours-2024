package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;

class ScrapperWebClientTest {
    private final static String CHAT_CONTROLLER_PATH = "/tg-chat/1";
    private final static String LINKS_CONTROLLER_PATH = "/links";

    @Test
    public void scrapperWebClientPostChatTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(post(CHAT_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            )
        );
        server.start();
        ScrapperClient client = new ScrapperWebClient(server.baseUrl());

        String actual = client.addChat(1L);

        assertThat(actual).isNull();

        server.stop();
    }

    @Test
    public void scrapperWebClientDeleteChatTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(delete(CHAT_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            )
        );
        server.start();
        ScrapperClient client = new ScrapperWebClient(server.baseUrl());

        String actual = client.deleteChat(1L);

        assertThat(actual).isNull();

        server.stop();
    }

    @Test
    public void scrapperWebClientGetLinksTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(get(LINKS_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                      "links": [
                        {
                          "id": 1,
                          "url": "http://localhost:8080/links"
                        }
                      ],
                      "size": 1
                    }
                    """)
            )
        );

        server.start();
        ScrapperClient client = new ScrapperWebClient(server.baseUrl());
        ListLinksResponse actual = client.getLinks(1L);

        assertThat(actual).isNotNull();

        server.stop();
    }

    @Test
    public void scrapperWebClientTrackLinksTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(post(LINKS_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                       "id": 1,
                       "url": "string"
                     }
                    """)
            )
        );

        server.start();
        ScrapperClient client = new ScrapperWebClient(server.baseUrl());
        LinkResponse actual = client.trackLink(1L, new AddLinkRequest("http://localhost:8080/links"));

        assertThat(actual).isNotNull();

        server.stop();
    }

    @Test
    public void scrapperWebClientUntrackLinksTest() {
        WireMockServer server = new WireMockServer();
        server.stubFor(delete(LINKS_CONTROLLER_PATH)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                       "id": 1,
                       "url": "http://localhost:8080/links"
                     }
                    """)
            )
        );

        server.start();
        ScrapperClient client = new ScrapperWebClient(server.baseUrl());
        LinkResponse actual = client.untrackLink(1L, new RemoveLinkRequest("http://localhost:8080/links"));

        assertThat(actual).isNotNull();

        server.stop();
    }
}
