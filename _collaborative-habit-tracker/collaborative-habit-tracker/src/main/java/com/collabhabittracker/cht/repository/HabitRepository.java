package com.collabhabittracker.cht.repository;

import com.collabhabittracker.cht.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserId(Long userId);
    List<Habit> findByUserIdAndActiveTrue(Long userId);
    
    @Query("SELECT COUNT(h) FROM Habit h WHERE h.userId = :userId AND h.active = true")
    Integer countActiveHabitsByUser(@Param("userId") Long userId);
    
    @Query("SELECT h FROM Habit h WHERE h.userId = :userId AND h.active = true ORDER BY h.createdAt DESC")
    List<Habit> findActiveHabitsByUser(@Param("userId") Long userId);
}