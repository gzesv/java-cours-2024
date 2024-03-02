package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackCommandTest {

    LinkRepository linkRepository = new LinkRepository();
    private TrackCommand trackCommand;

    @BeforeEach
    void setUp() {
        trackCommand = new TrackCommand(linkRepository);
    }

    @Test
    void commandTest() {
        assertThat(trackCommand.command()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(trackCommand.description()).isNotNull();
    }

    @Test
    void handleCommandWithoutLinkTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        SendMessage sendMessage = trackCommand.handle(update);

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Сообщение не содержит ссылку.");
    }

    @Test
    void handleCommandWithLinkTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track https://github.com");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        SendMessage sendMessage = trackCommand.handle(update);

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Cсылка добавлена в список отслеживаемых ссылок.");
    }
}
