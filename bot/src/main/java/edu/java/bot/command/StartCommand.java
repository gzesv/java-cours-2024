package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    public static final String COMMAND = "/start";
    public static final String DESCRIPTION = "Зарегистрировать пользователя";
    public static final String MESSAGE = "Вы зарегистрированы."
        + " Используйте /help для получения информации о командах.";

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
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }
}
