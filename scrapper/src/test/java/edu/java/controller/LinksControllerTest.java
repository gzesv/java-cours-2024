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
class LinksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String LINKS_CONTROLLER_PATH = "/links";
    private static final String REQUEST_HEADER_NAME = "Tg-Chat-Id";

    @Test
    public void getLinksCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders
            .get(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id))
        .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test
    public void getLinksIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders
            .get(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void postLinksCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders
            .post(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id)
            .content("""
                {
                  "link": ""
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test
    public void postLinksIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders
            .post(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id)
            .content("""
                {
                  "link": ""
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void postLinksIncorrectParameterTest() throws Exception {
        String id = "1L";
        mockMvc.perform(MockMvcRequestBuilders
            .post(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id)
            .content("""
                {
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteLinksCorrectTest() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders
            .delete(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id)
            .content("""
                {
                  "link": ""
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isNotImplemented());
    }

    @Test
    public void deleteLinksIncorrectTest() throws Exception {
        String id = "q";
        mockMvc.perform(MockMvcRequestBuilders
            .delete(LINKS_CONTROLLER_PATH)
            .header(REQUEST_HEADER_NAME, id)
            .content("""
                {
                  "link": ""
                }
                """)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
