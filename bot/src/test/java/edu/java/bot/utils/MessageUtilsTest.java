package edu.java.bot.utils;

import org.junit.jupiter.api.Test;
import static edu.java.bot.utils.MessageUtils.getCommandFromMessage;
import static edu.java.bot.utils.MessageUtils.getLinkFromMessage;
import static edu.java.bot.utils.MessageUtils.messageHasLink;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MessageUtilsTest {
    private static final String MESSAGE = "/track https://ya.ru/";
    private static final String MESSAGE_WITHOUT_LINK = "/track";
    private static final String LINK = "https://ya.ru/";
    private static final String COMMAND = "/track";

    @Test
    void getCommandFromMessageTest() {
        String command = getCommandFromMessage(MESSAGE);

        assertThat(command).isEqualTo(COMMAND);
    }

    @Test
    void getLinkFromMessageTest() {
        String link = getLinkFromMessage(MESSAGE);

        assertThat(link).isEqualTo(LINK);
    }

    @Test
    void messageHasLinkTest() {
        assertThat(messageHasLink(MESSAGE)).isTrue();
    }

    @Test
    void messageHasNoLinkTest() {
        assertThat(messageHasLink(MESSAGE_WITHOUT_LINK)).isFalse();
    }
}
