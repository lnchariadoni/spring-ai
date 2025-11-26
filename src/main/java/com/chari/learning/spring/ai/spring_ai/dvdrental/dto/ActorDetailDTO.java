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
public class ActorDetailDTO {
    private long actorID;
    private String firstName;
    private String lastName;
    private List<MovieSummaryDTO> movies;
}
