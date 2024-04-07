package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot implements UpdatesListener, AutoCloseable {
    private final UserMessageProcessor userMessageProcessor;
    private final ApplicationConfig applicationConfig;
    private TelegramBot bot;

    @Autowired
    public Bot(UserMessageProcessor userMessageProcessor, ApplicationConfig applicationConfig) {
        this.userMessageProcessor = userMessageProcessor;
        this.applicationConfig = applicationConfig;
    }

    @PostConstruct()
    public void start() {
        bot = new TelegramBot(applicationConfig.telegramToken());
        bot.setUpdatesListener(this);

        SetMyCommands setMyCommands =
            new SetMyCommands(
                userMessageProcessor.getCommands()
                    .stream()
                    .map(Command::toApiCommand)
                    .toArray(BotCommand[]::new)
            );
        execute(setMyCommands);
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream().map(userMessageProcessor::process)
                .forEach(this::execute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
