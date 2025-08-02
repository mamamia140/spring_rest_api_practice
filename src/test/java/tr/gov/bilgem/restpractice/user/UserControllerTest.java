package tr.gov.bilgem.restpractice.user;

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
import tr.gov.bilgem.restpractice.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for UserController
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
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllUsersWithPagination() throws Exception {
        mvc.perform(get("/api/users")
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
    void testGetUserById() throws Exception {
        // First create a user
        User user = new User("testuser", "test@example.com", "password123", User.Role.OPERATOR);

        String userJson = objectMapper.writeValueAsString(user);

        // Create user
        String location = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // Extract ID from location
        String id = location.substring(location.lastIndexOf("/") + 1);

        // Get user by ID
        mvc.perform(get("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        mvc.perform(get("/api/users/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User("newuser", "newuser@example.com", "password123", User.Role.OPERATOR);

        String userJson = objectMapper.writeValueAsString(user);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateUser() throws Exception {
        // First create a user
        User user = new User("originaluser", "original@example.com", "password123", User.Role.OPERATOR);

        String userJson = objectMapper.writeValueAsString(user);

        String location = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        String id = location.substring(location.lastIndexOf("/") + 1);

        // Update user
        User updatedUser = new User("updateduser", "updated@example.com", "password123", User.Role.ADMIN);

        String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

        mvc.perform(put("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        User user = new User("testuser", "test@example.com", "password123", User.Role.OPERATOR);

        String userJson = objectMapper.writeValueAsString(user);

        mvc.perform(put("/api/users/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        // First create a user
        User user = new User("deleteuser", "delete@example.com", "password123", User.Role.OPERATOR);

        String userJson = objectMapper.writeValueAsString(user);

        String location = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        String id = location.substring(location.lastIndexOf("/") + 1);

        // Delete user
        mvc.perform(delete("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        mvc.perform(delete("/api/users/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchUsers() throws Exception {
        mvc.perform(get("/api/users/search")
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
