package com.hubskills.repository;

import com.hubskills.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    // Trouver toutes les skills d'un user
    List<UserSkill> findByUserId(Long userId);

    // Trouver tous les users qui ont une skill
    List<UserSkill> findBySkillId(Long skillId);

    // Trouver la relation entre un user et une skill spécifique
    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);
}
