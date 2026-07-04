package com.todaysscene.backend.controller;

import com.todaysscene.backend.domain.Mood;
import com.todaysscene.backend.domain.Movie;
import com.todaysscene.backend.repository.MoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moods")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow requests from frontend
public class MoodController {

    private final MoodRepository moodRepository;

    @GetMapping
    public List<Mood> getAllMoods() {
        return moodRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mood> getMoodById(@PathVariable String id) {
        return moodRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Movie>> getMoviesByMood(@PathVariable String id) {
        return moodRepository.findById(id)
                .map(mood -> ResponseEntity.ok(List.copyOf(mood.getMovies())))
                .orElse(ResponseEntity.notFound().build());
    }
}
