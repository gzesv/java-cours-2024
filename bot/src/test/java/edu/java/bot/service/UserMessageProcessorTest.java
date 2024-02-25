package edu.java.bot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.StartCommand;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserMessageProcessorTest {

    private UserMessageProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new UserMessageProcessor(List.of(new StartCommand()));
    }

    @Test
    void processTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        SendMessage sendMessage = processor.process(update);

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Вы зарегистрированы. "
                + "Используйте /help для получения информации о командах.");

    }

    @Test
    void processNoSuchCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/command");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        SendMessage sendMessage = processor.process(update);

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Такой команды не существует");

    }
}
