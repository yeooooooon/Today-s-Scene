package com.todaysscene.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoodDto {
    private String moodId;
    private List<MovieDto> movies;
    private List<MoodReasonDto> reasons;
}
