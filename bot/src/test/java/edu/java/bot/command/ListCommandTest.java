package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {
    LinkRepository linkRepository = new LinkRepository();
    private ListCommand listCommand;

    @BeforeEach
    void setUp() {
        listCommand  = new ListCommand(linkRepository);
    }

    @Test
    void linkListIsEmptyTest() {
        SendMessage sendMessage = listCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Список отслеживаемых ссылок пуст.");
    }

    @Test
    void linkListIsNotEmptyTest() {
        linkRepository.addLink(1L, "/link");

        SendMessage sendMessage = listCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Список отслеживаемых ссылок:" + "\n/link");
    }

    public static Update makeMockUpdate() {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(1L);

        var messageMock = mock(Message.class);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }
}
