package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final Bot bot;

    public void processUpdate(LinkUpdateRequest updateRequest) {
        updateRequest.tgChatIds().forEach((telegramId) ->
            bot.execute(new SendMessage(telegramId, "Доступно обновление по ссылке:\n%s\n\n%s"
                .formatted(updateRequest.url(), updateRequest.description()))));
    }
}
