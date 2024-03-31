package edu.java.services.jpa;

import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.model.Link;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.services.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaChatService chatService;

    private final JpaLinkRepository linkRepository;

    @Override
    @Transactional
    public List<Link> getLinks(long chatId) {
        if (chatService.isChatNotExists(chatId)) {
            throw new ChatNotFoundException();
        }

        return linkRepository.findAllByChatsId(chatId);
    }

    @Override
    @Transactional
    public Link add(long chatId, Link link) {
        if (chatService.isChatNotExists(chatId)) {
            throw new ChatNotFoundException();
        }

        Link linkByUrl = linkRepository.findByUrl(link.getUrl());

        if (linkByUrl == null) {
            linkByUrl = linkRepository.save(link);
        }

        linkRepository.saveChatToLink(chatId, linkByUrl.getId());

        return linkByUrl;
    }

    @Override
    @Transactional
    public Link remove(long chatId, Link link) {
        if (chatService.isChatNotExists(chatId)) {
            throw new ChatNotFoundException();
        }

        Link linkByUrl = linkRepository.findByUrl(link.getUrl());

        if (linkByUrl == null) {
            throw new LinkNotFoundException();
        }

        linkRepository.deleteChatToLink(chatId, linkByUrl.getId());

        if (isLinkNotTracked(linkByUrl.getId())) {
            linkRepository.delete(linkByUrl);
        }

        return linkByUrl;
    }

    @Override
    public List<Link> findAllOutdatedLinks(long interval) {
        return linkRepository.findAllOutdatedLinks(interval);
    }

    @Override
    @Transactional
    public void setUpdateAndCheckTime(long linkId, OffsetDateTime lastUpdateTime, OffsetDateTime lastCheckTime) {
        Link link = linkRepository.findById(linkId)
            .orElseThrow(() -> new LinkNotFoundException());

        link.setUpdateAt(lastUpdateTime);
        link.setUpdateAt(lastUpdateTime);

        linkRepository.save(link);
    }

    private boolean isLinkNotTracked(long id) {
        return linkRepository.findAllChatIdsWithLink(id).isEmpty();
    }
}
