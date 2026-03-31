package com.hubskills.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Min(1)
    @Max(4)
    @Column(nullable = false)
    private Integer currentLevel;

    @NotNull
    @Min(1)
    @Max(4)
    @Column(nullable = false)
    private Integer targetLevel;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public UserSkill() {
    }

    public UserSkill(User user, Skill skill, Integer currentLevel, Integer targetLevel) {
        this.user = user;
        this.skill = skill;
        this.currentLevel = currentLevel;
        this.targetLevel = targetLevel;
        this.lastUpdated = LocalDateTime.now();
    }

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

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
        this.lastUpdated = LocalDateTime.now();
    }

    public void setTargetLevel(Integer targetLevel) {
        this.targetLevel = targetLevel;
    }
}
