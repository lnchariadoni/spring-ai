package com.chari.learning.spring.ai.spring_ai.dvdrental.mapper;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorSummaryDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Actor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActorMapper {
  private final MovieMapper movieMapper;

  public ActorMapper(@Lazy MovieMapper movieMapper) {
    this.movieMapper = movieMapper;
  }

  public ActorSummaryDTO toActorSummaryDTO(Actor actor) {
    return ActorSummaryDTO.builder()
        .actorID(actor.getActorID())
        .firstName(actor.getFirstName())
        .lastName(actor.getLastName())
        .build();
  }

  public ActorDetailDTO toActorDetailDTO(Actor actor) {
    return ActorDetailDTO.builder()
        .actorID(actor.getActorID())
        .firstName(actor.getFirstName())
        .lastName(actor.getLastName())
        .movies(actor.getMovies().stream().map(movieMapper::toMovieSummaryDTO).toList())
        .build();
  }
}
