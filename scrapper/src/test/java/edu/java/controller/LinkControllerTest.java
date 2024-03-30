package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exception.ChatNotFoundException;
import edu.java.mapper.DefaultObjectMapper;
import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.services.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(LinkController.class)
class LinkControllerTest {

    private static final String LINKS_CONTROLLER_PATH = "/links";

    private static final String REQUEST_HEADER_NAME = "Tg-Chat-Id";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LinkService linkService;

    @MockBean
    private DefaultObjectMapper mapper;

    @Test
    public void getLinksCorrectTest() throws Exception {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(linkService.getLinks(chat.getId())).thenReturn(List.of(link));
        when(mapper.mapToListLinksResponse(List.of(link))).thenCallRealMethod();
        when(mapper.mapToListWithLinkResponses(List.of(link))).thenCallRealMethod();
        ListLinksResponse expected = new ListLinksResponse(List.of(
            new LinkResponse(1L, new URI("https://github.com"))), 1
        );

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId()))
            .andReturn();

        ListLinksResponse actual = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ListLinksResponse.class
        );

        verify(linkService).getLinks(chat.getId());
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getLinksWhenChatNotExistsTest() throws Exception {
        Chat chat = new Chat(1L);
        doThrow(ChatNotFoundException.class)
            .when(linkService).getLinks(chat.getId());

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId()))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        verify(linkService).getLinks(chat.getId());
        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    public void postLinkCorrectTest() throws Exception {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Link linkAfterConvert = new Link("https://github.com");
        when(linkService.add(chat.getId(), linkAfterConvert)).thenReturn(link);
        when(mapper.linkToLinkResponse(link)).thenCallRealMethod();
        when(mapper.convertToLink(new AddLinkRequest("https://github.com"))).thenReturn(linkAfterConvert);
        LinkResponse expected = new LinkResponse(1L, new URI("https://github.com"));

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId())
                .content("""
                    {
                      "link": "https://github.com"
                    }
                    """)
                .contentType("application/json"))
            .andReturn();

        LinkResponse actual = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            LinkResponse.class
        );

        verify(linkService).add(chat.getId(), linkAfterConvert);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void postLinkWhenChatNotExistsTest() throws Exception {
        Chat chat = new Chat(1L);
        Link linkAfterConvert = new Link("https://github.com");
        when(mapper.convertToLink(new AddLinkRequest("https://github.com"))).thenReturn(linkAfterConvert);
        doThrow(ChatNotFoundException.class)
            .when(linkService).add(chat.getId(), linkAfterConvert);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId())
                .content("""
                    {
                      "link": "https://github.com"
                    }
                    """)
                .contentType("application/json"))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        verify(linkService).add(chat.getId(), linkAfterConvert);
        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    public void deleteLinksCorrectTest() throws Exception {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Link linkAfterConvert = new Link("https://github.com");
        when(linkService.remove(chat.getId(), linkAfterConvert)).thenReturn(link);
        when(mapper.linkToLinkResponse(link)).thenCallRealMethod();
        when(mapper.convertToLink(new RemoveLinkRequest("https://github.com"))).thenReturn(linkAfterConvert);
        LinkResponse expected = new LinkResponse(1L, new URI("https://github.com"));

        var result = mockMvc.perform(MockMvcRequestBuilders
                .delete(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId())
                .content("""
                    {
                      "link": "https://github.com"
                    }
                    """)
                .contentType("application/json"))
            .andReturn();

        LinkResponse actual = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            LinkResponse.class
        );

        verify(linkService).remove(chat.getId(), linkAfterConvert);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteLinkWhenChatNotExistsTest() throws Exception {
        Chat chat = new Chat(1L);
        Link linkAfterConvert = new Link("https://github.com");
        when(mapper.convertToLink(new RemoveLinkRequest("https://github.com"))).thenReturn(linkAfterConvert);
        doThrow(ChatNotFoundException.class)
            .when(linkService).remove(chat.getId(), linkAfterConvert);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .delete(LINKS_CONTROLLER_PATH)
                .header(REQUEST_HEADER_NAME, chat.getId())
                .content("""
                    {
                      "link": "https://github.com"
                    }
                    """)
                .contentType("application/json"))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        verify(linkService).remove(chat.getId(), linkAfterConvert);
        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(ChatNotFoundException.class);
    }
}
