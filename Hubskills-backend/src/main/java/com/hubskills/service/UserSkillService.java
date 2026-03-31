package com.hubskills.service;

import com.hubskills.model.UserSkill;
import com.hubskills.model.User;
import com.hubskills.model.Skill;
import com.hubskills.repository.UserSkillRepository;
import com.hubskills.repository.UserRepository;
import com.hubskills.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<UserSkill> getUserSkill(Long userId, Long skillId) {
        return userSkillRepository.findByUserIdAndSkillId(userId, skillId);
    }

    public UserSkill addSkillToUser(Long userId, Long skillId, Integer currentLevel, Integer targetLevel) {
        User user = userRepository.findById(userId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (user == null || skill == null) {
            return null;
        }

        Optional<UserSkill> existing = userSkillRepository.findByUserIdAndSkillId(userId, skillId);
        if (existing.isPresent()) {
            return null;
        }

        UserSkill userSkill = new UserSkill(user, skill, currentLevel, targetLevel);
        return userSkillRepository.save(userSkill);
    }

    public UserSkill updateUserSkillLevel(Long userId, Long skillId, Integer newLevel) {
        Optional<UserSkill> userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        if (userSkill.isEmpty()) {
            return null;
        }

        UserSkill updated = userSkill.get();
        updated.setCurrentLevel(newLevel);
        return userSkillRepository.save(updated);
    }

    public boolean removeSkillFromUser(Long userId, Long skillId) {
        Optional<UserSkill> userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        if (userSkill.isEmpty()) {
            return false;
        }

        userSkillRepository.delete(userSkill.get());
        return true;
    }

    public UserSkill getUserSkillById(Long userSkillId) {
        return userSkillRepository.findById(userSkillId).orElse(null);
    }
}
