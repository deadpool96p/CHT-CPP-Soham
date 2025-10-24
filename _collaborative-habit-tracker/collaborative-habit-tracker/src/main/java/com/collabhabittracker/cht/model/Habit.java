package com.collabhabittracker.cht.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private String frequency; // daily, weekly, monthly
    
    private String category;
    
    @Column(name = "preferred_time")
    private LocalTime preferredTime;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    private Boolean active = true;
    
    private Integer streak = 0;
    
    @Column(name = "goal_target")
    private Integer goalTarget;
    
    @Column(name = "goal_unit")
    private String goalUnit;
    
    // Constructors
    public Habit() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Habit(String name, String description, String frequency, Long userId) {
        this();
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.userId = userId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalTime getPreferredTime() {
        return preferredTime;
    }
    
    public void setPreferredTime(LocalTime preferredTime) {
        this.preferredTime = preferredTime;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Integer getStreak() {
        return streak;
    }
    
    public void setStreak(Integer streak) {
        this.streak = streak;
    }
    
    public Integer getGoalTarget() {
        return goalTarget;
    }
    
    public void setGoalTarget(Integer goalTarget) {
        this.goalTarget = goalTarget;
    }
    
    public String getGoalUnit() {
        return goalUnit;
    }
    
    public void setGoalUnit(String goalUnit) {
        this.goalUnit = goalUnit;
    }
}