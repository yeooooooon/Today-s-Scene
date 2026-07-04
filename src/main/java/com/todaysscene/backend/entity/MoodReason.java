package com.todaysscene.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "mood_reasons")
@Getter
@Setter
@NoArgsConstructor
public class MoodReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Integer reasonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mood_id")
    @JsonIgnoreProperties("reasons")
    private Mood mood;

    @Column(name = "reason_text", columnDefinition = "TEXT")
    private String reasonText;
}
