package com.todaysscene.backend.controller;

import com.todaysscene.backend.dto.MoodDto;
import com.todaysscene.backend.dto.MovieDto;
import com.todaysscene.backend.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moods")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MoodController {

    private final MoodService moodService;

    @GetMapping
    public List<MoodDto> getAllMoods() {
        return moodService.getAllMoods();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoodDto> getMoodById(@PathVariable String id) {
        MoodDto mood = moodService.getMoodById(id);
        return mood != null ? ResponseEntity.ok(mood) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDto>> getMoviesByMood(@PathVariable String id) {
        List<MovieDto> movies = moodService.getMoviesByMood(id);
        return movies != null ? ResponseEntity.ok(movies) : ResponseEntity.notFound().build();
    }
}
