package tr.gov.bilgem.restpractice.group;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for GroupController
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
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAllGroups() throws Exception {
        mvc.perform(get("/api/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllGroupsWithPagination() throws Exception {
        mvc.perform(get("/api/groups")
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
    void testGetGroupByIdNotFound() throws Exception {
        mvc.perform(get("/api/groups/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateGroup() throws Exception {
        String groupJson = """
                {
                    "name": "Test Group",
                    "description": "Test Group Description"
                }
                """;

        mvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(groupJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateGroupNotFound() throws Exception {
        String groupJson = """
                {
                    "name": "Test Group",
                    "description": "Test Group Description"
                }
                """;

        mvc.perform(put("/api/groups/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(groupJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteGroupNotFound() throws Exception {
        mvc.perform(delete("/api/groups/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchGroups() throws Exception {
        mvc.perform(get("/api/groups/search")
                .param("q", "test")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .param("mode", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
