package com.hubskills.service;

import com.hubskills.model.UserSkill;
import com.hubskills.model.User;
import com.hubskills.model.Skill;
import com.hubskills.repository.UserSkillRepository;
import com.hubskills.repository.UserRepository;
import com.hubskills.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    // Récupérer toutes les skills d'un user
    public List<UserSkill> getSkillsByUser(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    // Récupérer tous les users qui ont une skill
    public List<UserSkill> getUsersBySkill(Long skillId) {
        return userSkillRepository.findBySkillId(skillId);
    }

    // Récupérer une relation spécifique user-skill
    public Optional<UserSkill> getUserSkill(Long userId, Long skillId) {
        return userSkillRepository.findByUserIdAndSkillId(userId, skillId);
    }

    // Créer une relation user-skill
    public UserSkill addSkillToUser(Long userId, Long skillId, Integer currentLevel, Integer targetLevel) {
        User user = userRepository.findById(userId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (user == null || skill == null) {
            return null; // User ou Skill n'existe pas
        }

        // Vérifier si la relation n'existe pas déjà
        Optional<UserSkill> existing = userSkillRepository.findByUserIdAndSkillId(userId, skillId);
        if (existing.isPresent()) {
            return null; // Relation déjà existe
        }

        UserSkill userSkill = new UserSkill(user, skill, currentLevel, targetLevel);
        return userSkillRepository.save(userSkill);
    }

    // Modifier le niveau d'une skill pour un user
    public UserSkill updateUserSkillLevel(Long userId, Long skillId, Integer newLevel) {
        Optional<UserSkill> userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        if (userSkill.isEmpty()) {
            return null; // Relation n'existe pas
        }

        UserSkill updated = userSkill.get();
        updated.setCurrentLevel(newLevel);
        return userSkillRepository.save(updated);
    }

    // Supprimer une skill d'un user
    public boolean removeSkillFromUser(Long userId, Long skillId) {
        Optional<UserSkill> userSkill = userSkillRepository.findByUserIdAndSkillId(userId, skillId);

        if (userSkill.isEmpty()) {
            return false; // Relation n'existe pas
        }

        userSkillRepository.delete(userSkill.get());
        return true;
    }

    // Récupérer une UserSkill par ID
    public UserSkill getUserSkillById(Long userSkillId) {
        return userSkillRepository.findById(userSkillId).orElse(null);
    }
}
