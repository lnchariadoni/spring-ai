package com.chari.learning.spring.ai.spring_ai.dvdrental.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DTO classes to verify proper builder patterns, 
 * getters/setters, equals/hashCode, and basic functionality.
 */
class DTOTest {

  @Test
  void testActorSummaryDTO_builderAndGetters() {
    // Given & When
    ActorSummaryDTO dto = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    // Then
    assertEquals(1L, dto.getActorID());
    assertEquals("Tom", dto.getFirstName());
    assertEquals("Hanks", dto.getLastName());
  }

  @Test
  void testActorSummaryDTO_setters() {
    // Given
    ActorSummaryDTO dto = new ActorSummaryDTO();

    // When
    dto.setActorID(2L);
    dto.setFirstName("Meryl");
    dto.setLastName("Streep");

    // Then
    assertEquals(2L, dto.getActorID());
    assertEquals("Meryl", dto.getFirstName());
    assertEquals("Streep", dto.getLastName());
  }

  @Test
  void testActorSummaryDTO_equalsAndHashCode() {
    // Given
    ActorSummaryDTO dto1 = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    ActorSummaryDTO dto2 = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    ActorSummaryDTO dto3 = ActorSummaryDTO.builder()
        .actorID(2L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    // Then
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1, dto3);
  }

  @Test
  void testMovieSummaryDTO_builderAndGetters() {
    // Given & When
    MovieSummaryDTO dto = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .build();

    // Then
    assertEquals(1L, dto.getFilmID());
    assertEquals("The Matrix", dto.getTitle());
    assertEquals(1999, dto.getReleaseYear());
    assertEquals("A hacker discovers reality", dto.getDescription());
  }

  @Test
  void testMovieSummaryDTO_setters() {
    // Given
    MovieSummaryDTO dto = new MovieSummaryDTO();

    // When
    dto.setFilmID(2L);
    dto.setTitle("Inception");
    dto.setReleaseYear(2010);
    dto.setDescription("Dream within a dream");

    // Then
    assertEquals(2L, dto.getFilmID());
    assertEquals("Inception", dto.getTitle());
    assertEquals(2010, dto.getReleaseYear());
    assertEquals("Dream within a dream", dto.getDescription());
  }

  @Test
  void testMovieSummaryDTO_equalsAndHashCode() {
    // Given
    MovieSummaryDTO dto1 = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .build();

    MovieSummaryDTO dto2 = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .build();

    MovieSummaryDTO dto3 = MovieSummaryDTO.builder()
        .filmID(2L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .build();

    // Then
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1, dto3);
  }

  @Test
  void testActorDetailDTO_withMovies() {
    // Given
    MovieSummaryDTO movie1 = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .build();

    MovieSummaryDTO movie2 = MovieSummaryDTO.builder()
        .filmID(2L)
        .title("John Wick")
        .build();

    // When
    ActorDetailDTO dto = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .movies(Arrays.asList(movie1, movie2))
        .build();

    // Then
    assertEquals(1L, dto.getActorID());
    assertEquals("Keanu", dto.getFirstName());
    assertEquals("Reeves", dto.getLastName());
    assertNotNull(dto.getMovies());
    assertEquals(2, dto.getMovies().size());
    assertEquals("The Matrix", dto.getMovies().get(0).getTitle());
    assertEquals("John Wick", dto.getMovies().get(1).getTitle());
  }

  @Test
  void testActorDetailDTO_withEmptyMoviesList() {
    // When
    ActorDetailDTO dto = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .movies(Collections.emptyList())
        .build();

    // Then
    assertNotNull(dto.getMovies());
    assertTrue(dto.getMovies().isEmpty());
  }

  @Test
  void testMovieDetailDTO_withActors() {
    // Given
    ActorSummaryDTO actor1 = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();

    ActorSummaryDTO actor2 = ActorSummaryDTO.builder()
        .actorID(2L)
        .firstName("Laurence")
        .lastName("Fishburne")
        .build();

    // When
    MovieDetailDTO dto = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .actors(Arrays.asList(actor1, actor2))
        .build();

    // Then
    assertEquals(1L, dto.getFilmID());
    assertEquals("The Matrix", dto.getTitle());
    assertEquals(1999, dto.getReleaseYear());
    assertEquals("A hacker discovers reality", dto.getDescription());
    assertNotNull(dto.getActors());
    assertEquals(2, dto.getActors().size());
    assertEquals("Keanu", dto.getActors().get(0).getFirstName());
    assertEquals("Laurence", dto.getActors().get(1).getFirstName());
  }

  @Test
  void testMovieDetailDTO_withEmptyActorsList() {
    // When
    MovieDetailDTO dto = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .actors(Collections.emptyList())
        .build();

    // Then
    assertNotNull(dto.getActors());
    assertTrue(dto.getActors().isEmpty());
  }

  @Test
  void testActorDetailDTO_equalsAndHashCode() {
    // Given
    MovieSummaryDTO movie = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .build();

    ActorDetailDTO dto1 = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .movies(Arrays.asList(movie))
        .build();

    ActorDetailDTO dto2 = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .movies(Arrays.asList(movie))
        .build();

    // Then
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  void testMovieDetailDTO_equalsAndHashCode() {
    // Given
    ActorSummaryDTO actor = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();

    MovieDetailDTO dto1 = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .actors(Arrays.asList(actor))
        .build();

    MovieDetailDTO dto2 = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A hacker discovers reality")
        .actors(Arrays.asList(actor))
        .build();

    // Then
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  void testAllDTOs_toString_shouldNotThrowException() {
    // Given
    ActorSummaryDTO actorSummary = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    MovieSummaryDTO movieSummary = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A movie")
        .build();

    ActorDetailDTO actorDetail = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .movies(Arrays.asList(movieSummary))
        .build();

    MovieDetailDTO movieDetail = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A movie")
        .actors(Arrays.asList(actorSummary))
        .build();

    // When & Then - should not throw exceptions
    assertNotNull(actorSummary.toString());
    assertNotNull(movieSummary.toString());
    assertNotNull(actorDetail.toString());
    assertNotNull(movieDetail.toString());
    
    assertTrue(actorSummary.toString().contains("Tom"));
    assertTrue(movieSummary.toString().contains("Matrix"));
  }
}
