package com.hubskills.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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

    public UserSkill(User user, Skill skill, Integer currentLevel, Integer targetLevel) {
        this.user = user;
        this.skill = skill;
        this.currentLevel = currentLevel;
        this.targetLevel = targetLevel;
        this.lastUpdated = LocalDateTime.now();
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
        this.lastUpdated = LocalDateTime.now();
    }
}
