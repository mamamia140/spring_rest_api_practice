package tr.gov.bilgem.restpractice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tr.gov.bilgem.restpractice.Main;
import tr.gov.bilgem.restpractice.config.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for MetricsController
 *
 * @author Serdar Serpen
 * @date Nov 8, 2023
 * @since 1.0.0
 */
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class MetricsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetMetricsWhenEnabled() throws Exception {
        mvc.perform(get("/api/metrics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetMetricsWithDifferentContentType() throws Exception {
        mvc.perform(get("/api/metrics")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetMetricsWithoutContentType() throws Exception {
        mvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
