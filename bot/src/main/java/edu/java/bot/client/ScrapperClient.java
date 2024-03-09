package edu.java.bot.client;

import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;

public interface ScrapperClient {
    ListLinksResponse getLinks(Long tgChatId);

    LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    String addChat(Long id);

    String deleteChat(Long id);
}
