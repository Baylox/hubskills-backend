package com.hubskills.service;

import com.hubskills.model.Skill;
import com.hubskills.repository.SkillRepository;
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
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
    }

    @Test
    void getAllSkills_returnsAllSkills() {
        Skill skill2 = new Skill(2L, "React", "Frontend", "Bibliothèque JavaScript");
        when(skillRepository.findAll()).thenReturn(Arrays.asList(skill, skill2));

        List<Skill> result = skillService.getAllSkills();

        assertEquals(2, result.size());
        verify(skillRepository).findAll();
    }

    @Test
    void getSkillById_existingId_returnsSkill() {
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getName());
    }

    @Test
    void getSkillById_nonExistingId_returnsNull() {
        when(skillRepository.findById(99L)).thenReturn(Optional.empty());

        Skill result = skillService.getSkillById(99L);

        assertNull(result);
    }

    @Test
    void createSkill_savesAndReturnsSkill() {
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill result = skillService.createSkill(skill);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(skillRepository).save(skill);
    }

    @Test
    void deleteSkill_callsRepository() {
        skillService.deleteSkill(1L);

        verify(skillRepository).deleteById(1L);
    }
}
