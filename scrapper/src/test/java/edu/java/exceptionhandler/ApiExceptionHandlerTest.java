package edu.java.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.controller.ChatController;
import edu.java.controller.LinkController;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

@WebMvcTest({ChatController.class, LinkController.class})
class ApiExceptionHandlerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatController chatController;

    @MockBean
    private LinkController linkController;

    @Test
    public void chatAlreadyExistsExceptionTest() throws Exception {
        doThrow(ChatAlreadyExistsException.class)
            .when(chatController)
            .deleteChat(-1L);

        var result = mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/tg-chat/{id}", -1L)
        ).andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(ChatAlreadyExistsException.class);
    }

    @Test
    public void chatNotFoundExceptionTest() throws Exception {
        doThrow(ChatNotFoundException.class)
            .when(chatController)
            .deleteChat(1L);

        var result = mockMvc.perform(
                MockMvcRequestBuilders
                    .delete("/tg-chat/{id}", 1L))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    public void linkAlreadyExistsExceptionTest() throws Exception {
        doThrow(LinkAlreadyExistsException.class)
            .when(linkController)
            .addLink(1L, new AddLinkRequest(""));

        var result = mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/links")
                    .header("Tg-Chat-Id", 1L)
                    .content("""
                        {
                            "link": ""
                        }
                        """)
                    .contentType("application/json"))
            .andReturn();

        ApiErrorResponse response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ApiErrorResponse.class
        );

        assertThat(response.code()).isEqualTo("400");
        assertThat(result.getResolvedException()).isInstanceOf(LinkAlreadyExistsException.class);
    }
}
