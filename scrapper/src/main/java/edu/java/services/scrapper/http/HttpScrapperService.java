package edu.java.services.scrapper.http;

import edu.java.client.bot.BotClient;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.services.scrapper.ScrapperService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HttpScrapperService implements ScrapperService {

    private final BotClient botClient;

    @Override
    public void send(List<LinkUpdateRequest> requests) {
        requests.forEach(botClient::sendUpdate);
    }
}
