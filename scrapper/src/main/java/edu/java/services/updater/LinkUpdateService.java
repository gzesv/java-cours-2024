package edu.java.services.updater;

import edu.java.dto.Update;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.model.Link;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {

    private final LinkUpdatersHolder updatersHolder;

    private final LinkService linkService;

    private final ChatService chatService;

    public List<LinkUpdateRequest> fetchAllUpdates(long interval) {
        List<Link> links = linkService.findAllOutdatedLinks(interval);
        List<Update> updates = new ArrayList<>();

        links.forEach(link -> {
            String host = URI.create(link.getUrl()).getHost();
            LinkUpdater updater = updatersHolder.getUpdaterByDomain(host);

            Optional<Update> update = updater.fetchUpdate(link);
            update.ifPresent(u -> {
                updates.add(u);
                linkService.setUpdateAndCheckTime(
                    link.getId(),
                    u.updateTime(),
                    OffsetDateTime.now(ZoneId.systemDefault())
                );
            });
        });

        return convertToLinkUpdateRequests(updates);
    }

    private List<LinkUpdateRequest> convertToLinkUpdateRequests(List<Update> updates) {
        List<LinkUpdateRequest> requests = new ArrayList<>();

        updates.forEach((update) -> {
            List<Long> chatIds = chatService.findAllChatsIdsWithLink(update.linkId());
            var linkUpdateRequest =
                new LinkUpdateRequest(update.linkId(), update.url(), update.description(), chatIds);
            requests.add(linkUpdateRequest);
        });

        return requests;
    }
}
