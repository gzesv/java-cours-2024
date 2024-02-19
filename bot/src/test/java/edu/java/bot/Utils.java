package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Utils {
    public static Update makeMockUpdate() {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(123L);

        var messageMock = mock(Message.class);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }
}
