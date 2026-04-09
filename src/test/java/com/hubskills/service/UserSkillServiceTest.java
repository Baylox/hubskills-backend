package com.hubskills.service;

import com.hubskills.exception.ResourceNotFoundException;
import com.hubskills.model.Skill;
import com.hubskills.model.User;
import com.hubskills.model.UserSkill;
import com.hubskills.repository.SkillRepository;
import com.hubskills.repository.UserRepository;
import com.hubskills.repository.UserSkillRepository;
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
class UserSkillServiceTest {

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private UserSkillService userSkillService;

    private User user;
    private Skill skill;
    private UserSkill userSkill;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john@example.com", "password", "John", "Doe", "EMPLOYEE");
        skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
        userSkill = new UserSkill(user, skill, 2, 4);
    }

    @Test
    void getSkillsByUser_returnsSkills() {
        when(userSkillRepository.findByUserId(1L)).thenReturn(Arrays.asList(userSkill));

        List<UserSkill> result = userSkillService.getSkillsByUser(1L);

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getSkill().getName());
    }

    @Test
    void getUsersBySkill_returnsUsers() {
        when(userSkillRepository.findBySkillId(1L)).thenReturn(Arrays.asList(userSkill));

        List<UserSkill> result = userSkillService.getUsersBySkill(1L);

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getUser().getFirstName());
    }

    @Test
    void getUserSkill_existing_returnsUserSkill() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.of(userSkill));

        UserSkill result = userSkillService.getUserSkill(1L, 1L);

        assertNotNull(result);
        assertEquals(2, result.getCurrentLevel());
    }

    @Test
    void getUserSkill_nonExisting_throwsException() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSkillService.getUserSkill(1L, 99L));
    }

    @Test
    void addSkillToUser_validData_createsUserSkill() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.empty());
        when(userSkillRepository.save(any(UserSkill.class))).thenReturn(userSkill);

        UserSkill result = userSkillService.addSkillToUser(1L, 1L, 2, 4);

        assertNotNull(result);
        verify(userSkillRepository).save(any(UserSkill.class));
    }

    @Test
    void addSkillToUser_userNotFound_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSkillService.addSkillToUser(99L, 1L, 2, 4));
    }

    @Test
    void addSkillToUser_alreadyExists_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.of(userSkill));

        assertThrows(IllegalStateException.class, () -> userSkillService.addSkillToUser(1L, 1L, 2, 4));
    }

    @Test
    void updateUserSkillLevel_existing_updatesLevel() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.of(userSkill));
        when(userSkillRepository.save(any(UserSkill.class))).thenReturn(userSkill);

        UserSkill result = userSkillService.updateUserSkillLevel(1L, 1L, 3);

        assertNotNull(result);
        verify(userSkillRepository).save(any(UserSkill.class));
    }

    @Test
    void updateUserSkillLevel_nonExisting_throwsException() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSkillService.updateUserSkillLevel(1L, 99L, 3));
    }

    @Test
    void removeSkillFromUser_existing_deletesUserSkill() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 1L)).thenReturn(Optional.of(userSkill));

        userSkillService.removeSkillFromUser(1L, 1L);

        verify(userSkillRepository).delete(userSkill);
    }

    @Test
    void removeSkillFromUser_nonExisting_throwsException() {
        when(userSkillRepository.findByUserIdAndSkillId(1L, 99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userSkillService.removeSkillFromUser(1L, 99L));
    }
}
