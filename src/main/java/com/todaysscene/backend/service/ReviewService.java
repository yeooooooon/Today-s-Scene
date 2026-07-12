package com.todaysscene.backend.service;

import com.todaysscene.backend.domain.Movie;
import com.todaysscene.backend.domain.Review;
import com.todaysscene.backend.dto.ReviewDto;
import com.todaysscene.backend.dto.ReviewRequestDto;
import com.todaysscene.backend.repository.MovieRepository;
import com.todaysscene.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public List<ReviewDto> getReviewsByMovie(Integer movieId) {
        return reviewRepository.findByMovie_MovieIdOrderByCreatedAtDesc(movieId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto addReview(Integer movieId, ReviewRequestDto request) {
        Movie movie = movieRepository.findById(movieId).orElseGet(() -> {
            Movie newMovie = new Movie();
            newMovie.setMovieId(movieId);
            return movieRepository.save(newMovie);
        });

        Review review = new Review();
        review.setMovie(movie);
        review.setAuthorName(request.getAuthorName());
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        return convertToDto(savedReview);
    }

    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getReviewId(),
                review.getAuthorName(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt(),
                review.getMovie().getMovieId()
        );
    }
}
