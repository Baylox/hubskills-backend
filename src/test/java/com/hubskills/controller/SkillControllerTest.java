package com.hubskills.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubskills.model.Skill;
import com.hubskills.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.hubskills.exception.ResourceNotFoundException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSkills_returnsOk() throws Exception {
        Skill skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
        when(skillService.getAllSkills()).thenReturn(Arrays.asList(skill));

        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Java"));
    }

    @Test
    void getSkillById_existingId_returnsOk() throws Exception {
        Skill skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
        when(skillService.getSkillById(1L)).thenReturn(skill);

        mockMvc.perform(get("/api/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getSkillById_nonExistingId_returnsNotFound() throws Exception {
        when(skillService.getSkillById(99L)).thenThrow(new ResourceNotFoundException("Skill not found: 99"));

        mockMvc.perform(get("/api/skills/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSkill_validData_returnsCreated() throws Exception {
        Skill skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
        when(skillService.createSkill(any(Skill.class))).thenReturn(skill);

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void createSkill_blankName_returnsBadRequest() throws Exception {
        Skill skill = new Skill(null, "", "Backend", "Description");

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteSkill_existingId_returnsNoContent() throws Exception {
        Skill skill = new Skill(1L, "Java", "Backend", "Langage de programmation");
        when(skillService.getSkillById(1L)).thenReturn(skill);

        mockMvc.perform(delete("/api/skills/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSkill_nonExistingId_returnsNotFound() throws Exception {
        when(skillService.getSkillById(99L)).thenThrow(new ResourceNotFoundException("Skill not found: 99"));

        mockMvc.perform(delete("/api/skills/99"))
                .andExpect(status().isNotFound());
    }
}
