package edu.java.exceptionhandler;

import edu.java.controller.ChatController;
import edu.java.controller.LinkController;
import edu.java.dto.request.AddLinkRequest;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ApiExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatController chatController;

    @MockBean
    private LinkController linkController;

    @Test
    public void chatAlreadyExistsExceptionTest() throws Exception {
        Mockito.doThrow(ChatAlreadyExistsException.class)
            .when(chatController)
            .deleteChat(-1L);

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/tg-chat/{id}", -1L)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void chatNotFoundExceptionTest() throws Exception {
        Mockito.doThrow(ChatNotFoundException.class)
            .when(chatController)
            .deleteChat(1L);

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/tg-chat/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void linkAlreadyExistsExceptionTest() throws Exception {
        Mockito.doThrow(LinkAlreadyExistsException.class)
            .when(linkController)
            .addLink(1L, new AddLinkRequest(""));

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/links")
                .header("Tg-Chat-Id", 1L)
                .content("""
                    {
                        "link": ""
                    }
                    """)
                .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
