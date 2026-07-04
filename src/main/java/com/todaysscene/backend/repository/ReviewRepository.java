package com.todaysscene.backend.repository;

import com.todaysscene.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie_MovieIdOrderByCreatedAtDesc(Integer movieId);
}
