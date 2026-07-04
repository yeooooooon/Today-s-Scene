package com.todaysscene.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "poster_path", length = 255)
    private String posterPath;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private Set<Mood> moods = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("movie")
    private Set<Review> reviews = new HashSet<>();
}
