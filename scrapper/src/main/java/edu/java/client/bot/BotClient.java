package edu.java.client.bot;

import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.LinkUpdateResponse;

public interface BotClient {
    LinkUpdateResponse sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
