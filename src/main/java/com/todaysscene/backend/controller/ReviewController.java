package com.todaysscene.backend.controller;

import com.todaysscene.backend.domain.Movie;
import com.todaysscene.backend.domain.Review;
import com.todaysscene.backend.repository.MovieRepository;
import com.todaysscene.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovie_MovieIdOrderByCreatedAtDesc(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<?> addReview(@PathVariable Integer movieId, @RequestBody Review reviewRequest) {
        return movieRepository.findById(movieId).map(movie -> {
            reviewRequest.setMovie(movie);
            Review savedReview = reviewRepository.save(reviewRequest);
            return ResponseEntity.ok(savedReview);
        }).orElseGet(() -> {
            Movie newMovie = new Movie();
            newMovie.setMovieId(movieId);
            movieRepository.save(newMovie);

            reviewRequest.setMovie(newMovie);
            Review savedReview = reviewRepository.save(reviewRequest);
            return ResponseEntity.ok(savedReview);
        });
    }
}
