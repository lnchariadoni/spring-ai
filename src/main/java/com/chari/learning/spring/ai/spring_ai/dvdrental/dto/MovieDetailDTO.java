package com.chari.learning.spring.ai.spring_ai.dvdrental.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDetailDTO {
  private long filmID;
  private String title;
  private Integer releaseYear;
  private String description;
  private List<ActorSummaryDTO> actors;
}
