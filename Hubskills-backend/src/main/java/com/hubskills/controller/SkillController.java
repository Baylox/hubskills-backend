package com.hubskills.controller;

import com.hubskills.model.Skill;
import com.hubskills.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // GET /api/skills - Récupérer toutes les skills
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    // GET /api/skills/{id} - Récupérer une skill par ID
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id);
        if (skill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skill);
    }

    // POST /api/skills - Créer une nouvelle skill
    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill createdSkill = skillService.createSkill(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }

    // PUT /api/skills/{id} - Modifier une skill
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skillDetails) {
        Skill skill = skillService.getSkillById(id);
        if (skill == null) {
            return ResponseEntity.notFound().build();
        }

        // Mettre à jour les champs
        skill = new Skill(id, skillDetails.getName(), skillDetails.getCategory(), skillDetails.getDescription());
        Skill updatedSkill = skillService.createSkill(skill);

        return ResponseEntity.ok(updatedSkill);
    }

    // DELETE /api/skills/{id} - Supprimer une skill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id);
        if (skill == null) {
            return ResponseEntity.notFound().build();
        }

        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
