package edu.java.controller;

import edu.java.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService service;

    @Test
    public void postChatCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postChatIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteChatCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteChatIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
