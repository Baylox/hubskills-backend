package com.hubskills.service;

import com.hubskills.exception.ResourceNotFoundException;
import com.hubskills.model.UserSkill;
import com.hubskills.model.User;
import com.hubskills.model.Skill;
import com.hubskills.repository.UserSkillRepository;
import com.hubskills.repository.UserRepository;
import com.hubskills.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillService(UserSkillRepository userSkillRepository,
                            UserRepository userRepository,
                            SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public List<UserSkill> getSkillsByUser(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public List<UserSkill> getUsersBySkill(Long skillId) {
        return userSkillRepository.findBySkillId(skillId);
    }

    public UserSkill getUserSkill(Long userId, Long skillId) {
        return userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found for user " + userId + " and skill " + skillId));
    }

    public UserSkill addSkillToUser(Long userId, Long skillId, Integer currentLevel, Integer targetLevel) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + skillId));

        if (userSkillRepository.findByUserIdAndSkillId(userId, skillId).isPresent()) {
            throw new IllegalStateException("User " + userId + " already has skill " + skillId);
        }

        return userSkillRepository.save(new UserSkill(user, skill, currentLevel, targetLevel));
    }

    public UserSkill updateUserSkillLevel(Long userId, Long skillId, Integer newLevel) {
        UserSkill userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found for user " + userId + " and skill " + skillId));
        userSkill.setCurrentLevel(newLevel);
        return userSkillRepository.save(userSkill);
    }

    public void removeSkillFromUser(Long userId, Long skillId) {
        UserSkill userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found for user " + userId + " and skill " + skillId));
        userSkillRepository.delete(userSkill);
    }
}
