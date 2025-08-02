package tr.gov.bilgem.restpractice.device;

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
import tr.gov.bilgem.restpractice.config.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for DeviceController
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
class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAllDevices() throws Exception {
        mvc.perform(get("/api/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllDevicesWithPagination() throws Exception {
        mvc.perform(get("/api/devices")
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
    void testGetDeviceByIdNotFound() throws Exception {
        mvc.perform(get("/api/devices/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Disabled("Disabled for now")
    @Test
    void testCreateDevice() throws Exception {
        String deviceJson = """
                "name": "createdDeviceasdas",
                    "serialNo": "131251233",
                    "ipAddress": "192.168.0.33",
                    "location":
                    {
                        "id":5,
                        "city":"Mersin",
                        "county":"Yenisehir",
                        "country":"Turkey",
                        "longitude":"39.310594180426705",
                        "altitude":"119.9018585224797"
                    },
                    "group":
                    {
                        "id": 1,
                        "name": "group-0",
                        "description": "Illum placeat similique maxime asperiores."
                    },
                    "owner":
                    {
                        "id":2,
                        "username":"operator",
                        "email":"shaunna.okeefe@hotmail.com",
                        "password":"7k86d12g",
                        "role":"OPERATOR"
                    },
                    "accessible": true
                """;

        mvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(deviceJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Disabled("Disabled for now")
    @Test
    void testUpdateDeviceNotFound() throws Exception {
        String deviceJson = """
                "name": "createdDevicesa",
                    "serialNo": "13125213",
                    "ipAddress": "192.168.0.120",
                    "location":
                    {
                        "id":5,
                        "city":"Mersin",
                        "county":"Yenisehir",
                        "country":"Turkey",
                        "longitude":"39.310594180426705",
                        "altitude":"119.9018585224797"
                    },
                    "group":
                    {
                        "id": 1,
                        "name": "group-0",
                        "description": "Illum placeat similique maxime asperiores."
                    },
                    "owner":
                    {
                        "id":2,
                        "username":"operator",
                        "email":"shaunna.okeefe@hotmail.com",
                        "password":"7k86d12g",
                        "role":"OPERATOR"
                    },
                    "accessible": true
                """;

        mvc.perform(put("/api/devices/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(deviceJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDeviceNotFound() throws Exception {
        mvc.perform(delete("/api/devices/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchDevices() throws Exception {
        mvc.perform(get("/api/devices/search")
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
    void testSearchDevicesByStatus() throws Exception {
        mvc.perform(get("/api/devices/search")
                .param("q", "ACTIVE")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name")
                .param("mode", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testSearchDevicesByType() throws Exception {
        mvc.perform(get("/api/devices/search")
                .param("q", "ACTUATOR")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name")
                .param("mode", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

