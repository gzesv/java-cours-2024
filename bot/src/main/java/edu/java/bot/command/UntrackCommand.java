package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.utils.MessageUtils.getLinkFromMessage;
import static edu.java.bot.utils.MessageUtils.messageHasLink;

@Component
public class UntrackCommand implements Command {
    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Прекратить отслеживание ссылки.";
    private static final String MESSAGE = "Cсылка больше не отслеживается.";
    private static final String ERROR_MESSAGE = "Сообщение не содержит ссылку.";
    private final LinkRepository linkRepository;

    @Autowired
    public UntrackCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        if (!messageHasLink(update.message().text())) {
            return new SendMessage(chatId, ERROR_MESSAGE);
        }

        String link = getLinkFromMessage(update.message().text());
        linkRepository.removeLink(chatId, link);

        return new SendMessage(chatId, MESSAGE);
    }
}
