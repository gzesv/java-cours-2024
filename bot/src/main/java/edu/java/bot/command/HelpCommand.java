package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String COMMAND = "/help";
    public static final String DESCRIPTION = "Вывести список команд.";
    private static final String MESSAGE = "Список команд:\n";
    private final List<Command> commands;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
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
        StringBuilder botMessage = new StringBuilder();
        botMessage.append(MESSAGE);
        for (Command command : commands) {
            botMessage.append(command.command())
                .append(" - ")
                .append(command.description())
                .append("\n");
        }

        return new SendMessage(update.message().chat().id(), botMessage.toString());
    }
}
