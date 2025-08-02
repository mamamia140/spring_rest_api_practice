package tr.gov.bilgem.restpractice.audit;

import org.junit.jupiter.api.Disabled;
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
import tr.gov.bilgem.restpractice.config.SecurityConfig;
import tr.gov.bilgem.restpractice.config.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AuditController
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
class AuditControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAllAudits() throws Exception {
        mvc.perform(get("/api/audits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Disabled("Disabled for now")
    @Test
    void testGetAllAuditsWithPagination() throws Exception {
        mvc.perform(get("/api/audits")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .param("mode", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Link"));
    }

    @Test
    void testGetAuditByIdNotFound() throws Exception {
        mvc.perform(get("/api/audits/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAuditNotFound() throws Exception {
        String auditJson = """
                {
                    "action": "UPDATE",
                    "details": "Test audit update",
                    "timestamp": "2024-01-01T00:00:00Z"
                }
                """;

        mvc.perform(put("/api/audits/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(auditJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAuditNotFound() throws Exception {
        mvc.perform(delete("/api/audits/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchAudits() throws Exception {
        mvc.perform(get("/api/audits/search")
                .param("q", "test")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .param("mode", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testSearchAuditsWithDateRange() throws Exception {
        mvc.perform(get("/api/audits/search")
                .param("q", "test")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .param("mode", "asc")
                .param("start", "1640995200") // 2022-01-01
                .param("end", "1640995200")   // 2022-01-01
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testSearchAuditsWithSpecificDateRange() throws Exception {
        mvc.perform(get("/api/audits/search")
                .param("q", "CREATE")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "timestamp")
                .param("mode", "desc")
                .param("start", "1640995200") // 2022-01-01
                .param("end", "1704067200")   // 2024-01-01
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
