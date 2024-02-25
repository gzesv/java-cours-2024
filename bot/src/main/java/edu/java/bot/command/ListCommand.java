package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Показать список отслеживаемых ссылок.";
    private static final String MESSAGE = "Список отслеживаемых ссылок:";
    private static final String LINK_LIST_EMPTY = "Список отслеживаемых ссылок пуст.";
    private final LinkRepository linkRepository;

    @Autowired
    public ListCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        List<String> links = linkRepository.getLinks(chatId);
        if (links.isEmpty()) {
            return new SendMessage(chatId, LINK_LIST_EMPTY);
        }

        StringBuilder message = new StringBuilder();
        message.append(MESSAGE);
        for (var link : links) {
            message.append("\n").append(link);
        }

        return new SendMessage(chatId, message.toString());
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
