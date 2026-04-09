package com.hubskills.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubskills.model.User;
import com.hubskills.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import com.hubskills.exception.ResourceNotFoundException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_returnsOk() throws Exception {
        User user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void getUserById_existingId_returnsOk() throws Exception {
        User user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getUserById_nonExistingId_returnsNotFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new ResourceNotFoundException("User not found: 99"));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByEmail_existingEmail_returnsOk() throws Exception {
        User user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        when(userService.getUserByEmail("john@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/users/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUser_validData_returnsCreated() throws Exception {
        User user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUser_invalidEmail_returnsBadRequest() throws Exception {
        User user = new User(null, "not-an-email", "password", "John", "Doe", "EMPLOYEE");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_invalidRole_returnsBadRequest() throws Exception {
        User user = new User(null, "john@example.com", "password", "John", "Doe", "INVALID_ROLE");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_blankFirstName_returnsBadRequest() throws Exception {
        User user = new User(null, "john@example.com", "password", "", "Doe", "EMPLOYEE");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser_existingId_returnsNoContent() throws Exception {
        User user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_nonExistingId_returnsNotFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new ResourceNotFoundException("User not found: 99"));

        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound());
    }
}
