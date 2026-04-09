package com.hubskills.service;

import com.hubskills.exception.ResourceNotFoundException;
import com.hubskills.model.User;
import com.hubskills.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
    }

    @Test
    void getAllUsers_returnsAllUsers() {
        User user2 = new User(2L, "jane@example.com", "password", "Jane", "Doe", "MANAGER");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_existingId_returnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getUserById_nonExistingId_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void getUserByEmail_existingEmail_returnsUser() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getUserByEmail_nonExistingEmail_throwsException() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("unknown@example.com"));
    }

    @Test
    void createUser_savesAndReturnsUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_callsRepository() {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
