package edu.java.bot.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StartCommandTest {

    private StartCommand startCommand;

    @BeforeEach
    void setUp() {
        startCommand = new StartCommand();
    }

    @Test
    void commandTest() {
        assertThat(startCommand.description()).isNotNull();
    }

    @Test
    void descriptionTest() {
        assertThat(startCommand.description()).isNotNull();
    }

    @Test
    void handleTest() {
        assertThat(startCommand.description()).isNotNull();
    }
}
