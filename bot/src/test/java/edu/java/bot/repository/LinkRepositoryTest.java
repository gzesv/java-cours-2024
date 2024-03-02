package edu.java.bot.repository;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinkRepositoryTest {
    private LinkRepository linkRepository;

    @BeforeEach
    void setUp() {
        linkRepository = new LinkRepository();
    }

    @Test
    void addLinkTest() {
        Long id = 123L;
        String link = "link1";

        linkRepository.addLink(id, link);

        assertThat(linkRepository.getLinks(id).getLast()).isEqualTo(link);
    }

    @Test
    void getLinksTest() {
        Long id = 123L;
        String link1 = "link1";
        String link2 = "link2";
        linkRepository.addLink(id, link1);
        linkRepository.addLink(id, link2);

        List<String> links = linkRepository.getLinks(id);

        assertThat(links.toArray().length).isEqualTo(2);
    }

    @Test
    void removeLinkTest() {
        Long id = 123L;
        String link = "link1";
        linkRepository.addLink(id, link);

        linkRepository.removeLink(id, link);

        assertThat(linkRepository.getLinks(id).isEmpty()).isNotNull().isTrue();
    }
}
