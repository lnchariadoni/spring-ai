package com.chari.learning.spring.ai.spring_ai.dvdrental.mapper;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieSummaryDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Movie;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
  private final ActorMapper actorMapper;

  public MovieMapper(@Lazy ActorMapper actorMapper) {
    this.actorMapper = actorMapper;
  }

  public MovieSummaryDTO toMovieSummaryDTO(Movie movie) {
    return MovieSummaryDTO.builder()
        .filmID(movie.getFilmID())
        .title(movie.getTitle())
        .releaseYear(movie.getReleaseYear())
        .description(movie.getDescription())
        .build();
  }

  public MovieDetailDTO toMovieDetailDTO(Movie movie) {
    return MovieDetailDTO.builder()
        .filmID(movie.getFilmID())
        .title(movie.getTitle())
        .releaseYear(movie.getReleaseYear())
        .description(movie.getDescription())
        .actors(movie.getActors().stream().map(actorMapper::toActorSummaryDTO).toList())
        .build();
  }
}
