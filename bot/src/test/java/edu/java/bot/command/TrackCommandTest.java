package edu.java.bot.command;

import edu.java.bot.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TrackCommandTest {

    LinkRepository linkRepository = new LinkRepository();
    private TrackCommand trackCommand;

    @BeforeEach
    void setUp() {
        trackCommand = new TrackCommand(linkRepository);
    }

    @Test
    void commandTest() {
        assertThat(trackCommand.description()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(trackCommand.description()).isNotNull();
    }
}
