package com.hubskills.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_skills")
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private Integer currentLevel; // 1=Débutant, 2=Intermédiaire, 3=Avancé, 4=Expert

    @Column(nullable = false)
    private Integer targetLevel; // Niveau visé

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructeur vide
    public UserSkill() {
    }

    // Constructeur avec paramètres
    public UserSkill(User user, Skill skill, Integer currentLevel, Integer targetLevel) {
        this.user = user;
        this.skill = skill;
        this.currentLevel = currentLevel;
        this.targetLevel = targetLevel;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Skill getSkill() {
        return skill;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public Integer getTargetLevel() {
        return targetLevel;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    // Setters
    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
        this.lastUpdated = LocalDateTime.now();
    }

    public void setTargetLevel(Integer targetLevel) {
        this.targetLevel = targetLevel;
    }
}
