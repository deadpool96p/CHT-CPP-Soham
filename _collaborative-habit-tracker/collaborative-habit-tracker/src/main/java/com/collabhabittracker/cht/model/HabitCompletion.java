package com.collabhabittracker.cht.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "habit_completions")
public class HabitCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "habit_id", nullable = false)
    private Long habitId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "completion_date")
    private LocalDate completionDate;
    
    private String notes;
    
    // Constructors
    public HabitCompletion() {
        this.completedAt = LocalDateTime.now();
        this.completionDate = LocalDate.now();
    }
    
    public HabitCompletion(Long habitId, Long userId) {
        this();
        this.habitId = habitId;
        this.userId = userId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getHabitId() {
        return habitId;
    }
    
    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public LocalDate getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}