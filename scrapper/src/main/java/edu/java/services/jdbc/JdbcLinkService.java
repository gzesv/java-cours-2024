package edu.java.services.jdbc;

import edu.java.domain.ChatToLinkRepository;
import edu.java.domain.LinkRepository;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.model.Link;
import edu.java.services.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final ChatToLinkRepository chatToLinkRepository;
    private final JdbcChatService chatService;

    @Override
    @Transactional
    public List<Link> getLinks(long chatId) {
        if (!chatService.isChatExists(chatId)) {
            throw new ChatNotFoundException();
        }

        return linkRepository.findAll(chatId);
    }

    @Override
    @Transactional
    public Link add(long chatId, Link link) {
        if (!chatService.isChatExists(chatId)) {
            throw new ChatNotFoundException();
        }

        Optional<Link> linkByUrl = linkRepository.findLinkByUrl(link.getUrl());

        if (linkByUrl.isEmpty()) {
            Link linkDb = linkRepository.add(link);
            chatToLinkRepository.add(chatId, linkDb.getId());
            return linkDb;
        }

        chatToLinkRepository.add(chatId, linkByUrl.get().getId());
        return linkByUrl.get();
    }

    @Override
    @Transactional
    public Link remove(long chatId, Link link) {
        if (!chatService.isChatExists(chatId)) {
            throw new ChatNotFoundException();
        }

        Optional<Link> linkByUrl = linkRepository.findLinkByUrl(link.getUrl());

        if (linkByUrl.isEmpty()) {
            throw new LinkNotFoundException();
        }

        long linkId = linkByUrl.get().getId();

        chatToLinkRepository.remove(chatId, linkId);

        if (isLinkTracked(linkId)) {
            linkRepository.remove(linkId);
        }

        return linkByUrl.get();
    }

    @Override
    public List<Link> findAllOutdatedLinks(long interval) {
        return linkRepository.findAllOutdatedLinks(interval);
    }

    @Override
    public void setUpdateAndCheckTime(long linkId, OffsetDateTime lastUpdateTime, OffsetDateTime lastCheckTime) {
        linkRepository.setUpdateAndCheckTime(linkId, lastUpdateTime, lastCheckTime);
    }

    private boolean isLinkTracked(long id) {
        return chatToLinkRepository.linkTrackers(id).isEmpty();
    }
}
