package com.todaysscene.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "moods")
@Getter
@Setter
@NoArgsConstructor
public class Mood {

    @Id
    @Column(name = "mood_id", length = 50)
    private String moodId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mood_movies", joinColumns = @JoinColumn(name = "mood_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    @JsonIgnoreProperties("moods")
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "mood", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("mood")
    private Set<MoodReason> reasons = new HashSet<>();
}
