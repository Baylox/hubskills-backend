package com.hubskills.controller;

import com.hubskills.model.UserSkill;
import com.hubskills.service.UserSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(userSkillService.getUserSkill(userId, skillId));
    }

    @PostMapping
    public ResponseEntity<UserSkill> addSkillToUser(
            @RequestParam Long userId,
            @RequestParam Long skillId,
            @RequestParam Integer currentLevel,
            @RequestParam Integer targetLevel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userSkillService.addSkillToUser(userId, skillId, currentLevel, targetLevel));
    }

    @PatchMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<UserSkill> updateLevel(
            @PathVariable Long userId,
            @PathVariable Long skillId,
            @RequestParam Integer level) {
        return ResponseEntity.ok(userSkillService.updateUserSkillLevel(userId, skillId, level));
    }

    @DeleteMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<Void> removeSkillFromUser(@PathVariable Long userId, @PathVariable Long skillId) {
        userSkillService.removeSkillFromUser(userId, skillId);
        return ResponseEntity.noContent().build();
    }
}
