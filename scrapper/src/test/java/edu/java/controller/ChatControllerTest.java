package edu.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.mapper.DefaultObjectMapper;
import edu.java.model.Chat;
import edu.java.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatService chatService;

    @MockBean
    private DefaultObjectMapper mapper;

    @Test
    public void addChatCorrectTest() throws Exception {
        Chat chat = new Chat(1L);
        when(mapper.convertToChat(chat.getId())).thenReturn(chat);
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", chat.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());

        verify(chatService).addChat(chat);
    }

    @Test
    public void addChatWhenChatExistsTest() throws Exception {
        Chat chat = new Chat(1L);
        when(mapper.convertToChat(chat.getId())).thenReturn(chat);
        doThrow(ChatAlreadyExistsException.class)
            .when(chatService).addChat(chat);

        var actual = mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", chat.getId()))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            actual.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        verify(chatService).addChat(chat);
        assertThat(response.code()).isEqualTo("400");
        assertThat(actual.getResolvedException()).isInstanceOf(ChatAlreadyExistsException.class);
    }

    @Test
    public void deleteChatCorrectTest() throws Exception {
        Chat chat = new Chat(1L);
        when(mapper.convertToChat(chat.getId())).thenReturn(chat);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", chat.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());

        verify(chatService).deleteChat(chat);
    }

    @Test
    public void deleteChatWhenChatNotExistsTest() throws Exception {
        Chat chat = new Chat(1L);
        when(mapper.convertToChat(chat.getId())).thenReturn(chat);
        doThrow(ChatNotFoundException.class)
            .when(chatService).deleteChat(chat);

        var actual = mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", chat.getId()))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            actual.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        verify(chatService).deleteChat(chat);
        assertThat(response.code()).isEqualTo("400");
        assertThat(actual.getResolvedException()).isInstanceOf(ChatNotFoundException.class);
    }

}
