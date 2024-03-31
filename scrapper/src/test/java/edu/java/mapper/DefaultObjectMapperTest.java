package edu.java.mapper;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultObjectMapperTest {

    private final DefaultObjectMapper mapper = new DefaultObjectMapper();

    @Test
    void mapToListLinksResponseTest() {
        List<Link> list = List.of(
            new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now()),
            new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now())
        );

        ListLinksResponse listLinksResponse = mapper.mapToListLinksResponse(list);

        assertThat(listLinksResponse.size()).isEqualTo(2);
    }

    @Test
    void mapToListWithLinkResponses() {
        List<Link> list = List.of(
            new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now()),
            new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now())
        );

        List<LinkResponse> linkResponses = mapper.mapToListWithLinkResponses(list);

        assertThat(linkResponses.size()).isEqualTo(2);
    }

    @Test
    void linkToLinkResponse() {
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());

        LinkResponse response = mapper.linkToLinkResponse(link);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.url()).isEqualTo(URI.create(link.getUrl()));
    }

    @Test
    void convertToLink() {
        AddLinkRequest request = new AddLinkRequest("https://github.com");

        Link link = mapper.convertToLink(request);

        assertThat(request.link()).isEqualTo(link.getUrl());
    }

    @Test
    void testConvertToLink() {
        RemoveLinkRequest request = new RemoveLinkRequest("https://github.com");

        Link link = mapper.convertToLink(request);

        assertThat(request.link()).isEqualTo(link.getUrl());
    }
}
