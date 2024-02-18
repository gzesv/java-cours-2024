package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.bot.utils.MessageUtils.getCommandFromMessage;

@Service
@Data
public class UserMessageProcessor {
    private final List<? extends Command> commands;

    private static final String NO_SUCH_COMMAND_EXISTS = "Такой команды не существует";

    @Autowired
    public UserMessageProcessor(List<? extends Command> commands) {
        this.commands = commands;
    }

    public SendMessage process(Update update) {
        String userCommand = getCommandFromMessage(update.message().text());

        for (Command command : commands) {
            if (command.command().equals(userCommand)) {
                return command.handle(update);
            }
        }

        return new SendMessage(update.message().chat().id(), NO_SUCH_COMMAND_EXISTS);
    }
}
