package edu.java.bot.controller;

import edu.java.bot.dto.request.LinkUpdateRequest;
import edu.java.bot.service.UpdateService;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.verify;

@WebMvcTest(UpdateController.class)
class UpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateService updateService;

    @MockBean
    private Counter updatesCounter;

    @Test
    public void postUpdatesCorrectTest() throws Exception {
        LinkUpdateRequest request = new LinkUpdateRequest(1L, "url", "description", List.of(0L));
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
            .andExpect(MockMvcResultMatchers.status().isOk());

        verify(updateService).processUpdate(request);
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
