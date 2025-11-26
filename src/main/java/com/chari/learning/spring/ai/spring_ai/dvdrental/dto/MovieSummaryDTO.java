package com.chari.learning.spring.ai.spring_ai.dvdrental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieSummaryDTO {
  private long filmID;
  private String title;
  private Integer releaseYear;
  private String description;
}
