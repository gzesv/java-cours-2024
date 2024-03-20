package edu.java.client.bot;

import edu.java.dto.request.LinkUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotWebClient implements BotClient {
    private static final String DEFAULT_BASE_URL = "http://127.0.0.1:8090";
    private static final String UPDATES_ENDPOINT = "/updates";
    private final WebClient webClient;

    public BotWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public BotWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public String sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        return webClient.post()
            .uri(UPDATES_ENDPOINT)
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
