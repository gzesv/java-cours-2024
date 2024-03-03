package edu.java.bot.client;

import edu.java.bot.dto.Request.AddLinkRequest;
import edu.java.bot.dto.Request.RemoveLinkRequest;
import edu.java.bot.dto.Response.LinkResponse;
import edu.java.bot.dto.Response.ListLinksResponse;

public interface ScrapperClient {
    ListLinksResponse getLinks(Long tgChatId);

    LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    void addChat(Long id);

    void deleteChat(Long id);
}
