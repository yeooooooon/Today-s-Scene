package com.todaysscene.backend.repository;

import com.todaysscene.backend.entity.MoodReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodReasonRepository extends JpaRepository<MoodReason, Integer> {
    List<MoodReason> findByMood_MoodId(String moodId);
}
