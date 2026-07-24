package com.todaysscene.backend.controller;

import com.todaysscene.backend.dto.ReviewDto;
import com.todaysscene.backend.dto.ReviewRequestDto;
import com.todaysscene.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByMovie(@PathVariable Integer movieId) {
        List<ReviewDto> reviews = reviewService.getReviewsByMovie(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewDto> addReview(@PathVariable Integer movieId, @jakarta.validation.Valid @RequestBody ReviewRequestDto reviewRequest) {
        ReviewDto savedReview = reviewService.addReview(movieId, reviewRequest);
        return ResponseEntity.ok(savedReview);
    }

    @DeleteMapping("/{movieId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer movieId, @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
