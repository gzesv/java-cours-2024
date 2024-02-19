package edu.java.bot.command;

import edu.java.bot.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UntrackCommandTest {
    LinkRepository linkRepository = new LinkRepository();
    private UntrackCommand untrackCommand;

    @BeforeEach
    void setUp() {
        untrackCommand = new UntrackCommand(linkRepository);
    }

    @Test
    void commandTest() {
        assertThat(untrackCommand.description()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(untrackCommand.description()).isNotNull();
    }
}
