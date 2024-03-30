package edu.java.mapper;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.model.Chat;
import edu.java.model.Link;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultObjectMapper {

    public ListLinksResponse mapToListLinksResponse(List<Link> links) {
        List<LinkResponse> linkResponses = mapToListWithLinkResponses(links);
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    public List<LinkResponse> mapToListWithLinkResponses(List<Link> links) {
        return links.stream().map(link -> new LinkResponse(link.getId(), URI.create(link.getUrl()))).toList();
    }

    public LinkResponse linkToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), URI.create(link.getUrl()));
    }

    public Link convertToLink(AddLinkRequest request) {
        return new Link(request.link());
    }

    public Link convertToLink(RemoveLinkRequest request) {
        return new Link(request.link());
    }

    public Chat convertToChat(long id) {
        return new Chat(id);
    }

}
