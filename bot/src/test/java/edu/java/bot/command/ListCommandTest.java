package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static edu.java.bot.Utils.makeMockUpdate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ListCommandTest {
    LinkRepository linkRepository = new LinkRepository();
    private ListCommand listCommand;

    @BeforeEach
    void setUp() {
        listCommand = new ListCommand(linkRepository);
    }

    @Test
    void commandTest() {
        assertThat(listCommand.command()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(listCommand.description()).isNotNull();
    }

    @Test
    void linkListIsEmptyTest() {
        SendMessage sendMessage = listCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Список отслеживаемых ссылок пуст.");
    }

    @Test
    void linkListIsNotEmptyTest() {
        linkRepository.addLink(123L, "/link");

        SendMessage sendMessage = listCommand.handle(makeMockUpdate());

        assertThat(sendMessage.getParameters().get("text"))
            .isEqualTo("Список отслеживаемых ссылок:" + "\n/link");
    }

}
