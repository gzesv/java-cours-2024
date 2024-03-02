package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static edu.java.bot.Utils.makeMockUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HelpCommandTest {
    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        helpCommand = new HelpCommand(List.of(new StartCommand()));
    }

    @Test
    void commandTest() {
        assertThat(helpCommand.command()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(helpCommand.description()).isNotNull();
    }

    @Test
    void handleTest() {
        SendMessage sendMessage = helpCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Список команд:\n" +
                "/start - Зарегистрировать пользователя\n");
    }
}
