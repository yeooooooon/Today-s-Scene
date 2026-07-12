package com.todaysscene.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private String authorName;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private Integer movieId;
}
