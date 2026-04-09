package com.hubskills.controller;

import com.hubskills.model.UserSkill;
import com.hubskills.service.UserSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-skills")
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkill>> getSkillsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userSkillService.getSkillsByUser(userId));
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<UserSkill>> getUsersBySkill(@PathVariable Long skillId) {
        return ResponseEntity.ok(userSkillService.getUsersBySkill(skillId));
    }

    @GetMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<UserSkill> getUserSkill(@PathVariable Long userId, @PathVariable Long skillId) {
        Optional<UserSkill> userSkill = userSkillService.getUserSkill(userId, skillId);
        return userSkill.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSkill> addSkillToUser(
            @RequestParam Long userId,
            @RequestParam Long skillId,
            @RequestParam Integer currentLevel,
            @RequestParam Integer targetLevel) {
        UserSkill created = userSkillService.addSkillToUser(userId, skillId, currentLevel, targetLevel);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<UserSkill> updateLevel(
            @PathVariable Long userId,
            @PathVariable Long skillId,
            @RequestParam Integer level) {
        UserSkill updated = userSkillService.updateUserSkillLevel(userId, skillId, level);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<Void> removeSkillFromUser(@PathVariable Long userId, @PathVariable Long skillId) {
        boolean removed = userSkillService.removeSkillFromUser(userId, skillId);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
