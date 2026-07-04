package com.todaysscene.backend.repository;

import com.todaysscene.backend.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends JpaRepository<Mood, String> {
}
