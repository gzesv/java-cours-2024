package edu.java.bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postUpdatesCorrectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/updates")
            .content("""
                {
                    "id": "1",
                    "url": "url",
                    "description": "description",
                    "tgChatIds": [
                        0
                    ]
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test
    public void postUpdatesIncorrectTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/updates")
            .content("""
                {
                    "id": "",
                    "description": "",
                    "tgChatIds": [
                        0
                    ]
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
