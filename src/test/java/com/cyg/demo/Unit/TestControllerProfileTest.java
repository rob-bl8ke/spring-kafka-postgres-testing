package com.cyg.demo.Unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.cyg.demo.TestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@ActiveProfiles("not-local") // Use a profile other than 'local'
public class TestControllerProfileTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testControllerNotAvailableInNonLocalProfile() throws Exception {
        mockMvc.perform(post("/api/kafka/publish")
                .param("message", "testMessage"))
                .andExpect(status().isNotFound()); // Expect 404 Not Found
    }
}