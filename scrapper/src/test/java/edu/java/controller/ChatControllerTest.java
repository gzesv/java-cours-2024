package edu.java.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postChatCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isNotImplemented());
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
            .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test
    public void deleteChatIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", id))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
