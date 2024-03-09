package edu.java.bot.client;

import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperWebClient implements ScrapperClient {
    private static final String LINK_ENDPOINT = "/links";
    private static final String TG_CHAT_ENDPOINT = "/tg-chat/{id}";
    private static final String DEFAULT_BASE_URL = "http://127.0.0.1:8080";
    private static final String TG_CHAT_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;

    public ScrapperWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public ScrapperWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public ListLinksResponse getLinks(Long tgChatId) {
        return webClient.get()
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public String addChat(Long id) {
        return webClient.post()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String deleteChat(Long id) {
        return webClient.delete()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
