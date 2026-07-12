package com.todaysscene.backend.service;

import com.todaysscene.backend.domain.Mood;
import com.todaysscene.backend.dto.MoodDto;
import com.todaysscene.backend.dto.MoodReasonDto;
import com.todaysscene.backend.dto.MovieDto;
import com.todaysscene.backend.repository.MoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoodService {

    private final MoodRepository moodRepository;

    public List<MoodDto> getAllMoods() {
        return moodRepository.findAll().stream()
                .map(this::convertToMoodDto)
                .collect(Collectors.toList());
    }

    public MoodDto getMoodById(String id) {
        Mood mood = moodRepository.findById(id).orElse(null);
        return mood != null ? convertToMoodDto(mood) : null;
    }

    public List<MovieDto> getMoviesByMood(String id) {
        Mood mood = moodRepository.findById(id).orElse(null);
        if (mood == null) return null;
        return mood.getMovies().stream()
                .map(movie -> new MovieDto(movie.getMovieId(), movie.getTitle(), movie.getPosterPath(), movie.getOverview()))
                .collect(Collectors.toList());
    }

    private MoodDto convertToMoodDto(Mood mood) {
        List<MovieDto> movieDtos = mood.getMovies().stream()
                .map(movie -> new MovieDto(movie.getMovieId(), movie.getTitle(), movie.getPosterPath(), movie.getOverview()))
                .collect(Collectors.toList());
        
        List<MoodReasonDto> reasonDtos = mood.getReasons().stream()
                .map(reason -> new MoodReasonDto(reason.getReasonId(), reason.getReasonText()))
                .collect(Collectors.toList());

        return new MoodDto(mood.getMoodId(), movieDtos, reasonDtos);
    }
}
