package tr.gov.bilgem.restpractice.location;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Integration tests for LocationController
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
class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllLocations() throws Exception {
        mvc.perform(get("/api/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllLocationsWithPagination() throws Exception {
        mvc.perform(get("/api/locations")
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
    void testGetLocationByIdNotFound() throws Exception {
        mvc.perform(get("/api/locations/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateLocation() throws Exception {
        // Create a simple JSON object for location
        String locationJson = """
                {
                    "city": "Test City",
                    "county": "Test County", 
                    "country": "Test Country",
                    "longitude": 29.0322,
                    "altitude": 100.0
                }
                """;

        mvc.perform(post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateLocationNotFound() throws Exception {
        String locationJson = """
                {
                    "city": "Test City",
                    "county": "Test County",
                    "country": "Test Country",
                    "longitude": 29.0322,
                    "altitude": 100.0
                }
                """;

        mvc.perform(put("/api/locations/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteLocationNotFound() throws Exception {
        mvc.perform(delete("/api/locations/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchLocations() throws Exception {
        mvc.perform(get("/api/locations/search")
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
