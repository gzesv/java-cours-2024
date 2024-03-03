package edu.java.client.bot;

import edu.java.dto.Request.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
