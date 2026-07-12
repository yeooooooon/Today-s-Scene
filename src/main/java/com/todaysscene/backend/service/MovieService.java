package com.todaysscene.backend.service;

import com.todaysscene.backend.domain.Movie;
import com.todaysscene.backend.dto.MovieDto;
import com.todaysscene.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieById(Integer id) {
        return movieRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setMovieId(movieDto.getMovieId());
        movie.setTitle(movieDto.getTitle());
        movie.setPosterPath(movieDto.getPosterPath());
        movie.setOverview(movieDto.getOverview());
        
        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    private MovieDto convertToDto(Movie movie) {
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getPosterPath(),
                movie.getOverview()
        );
    }
}
