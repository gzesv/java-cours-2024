package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static edu.java.bot.Utils.makeMockUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StartCommandTest {

    private StartCommand startCommand;

    @BeforeEach
    void setUp() {
        startCommand = new StartCommand();
    }

    @Test
    void commandTest() {
        assertThat(startCommand.command()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(startCommand.description()).isNotNull();
    }

    @Test
    void handleTest() {
        SendMessage sendMessage = startCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Вы зарегистрированы."
                + " Используйте /help для получения информации о командах.");
    }
}
