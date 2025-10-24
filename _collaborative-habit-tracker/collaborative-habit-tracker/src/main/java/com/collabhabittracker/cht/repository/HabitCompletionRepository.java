package com.collabhabittracker.cht.repository;

import com.collabhabittracker.cht.model.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {
    List<HabitCompletion> findByUserId(Long userId);
    List<HabitCompletion> findByHabitId(Long habitId);
    
    @Query("SELECT hc FROM HabitCompletion hc WHERE hc.userId = :userId AND hc.completionDate = :date")
    List<HabitCompletion> findByUserIdAndCompletionDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT COUNT(hc) FROM HabitCompletion hc WHERE hc.userId = :userId AND hc.completionDate = :date")
    Integer countCompletionsByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT COUNT(DISTINCT hc.completionDate) FROM HabitCompletion hc WHERE hc.userId = :userId AND hc.completionDate >= :startDate")
    Integer countActiveDaysSince(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
}