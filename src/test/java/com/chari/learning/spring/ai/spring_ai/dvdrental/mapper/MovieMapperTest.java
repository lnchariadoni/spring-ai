package com.chari.learning.spring.ai.spring_ai.dvdrental.mapper;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorSummaryDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieSummaryDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Actor;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieMapperTest {

  @Mock
  private ActorMapper actorMapper;

  private MovieMapper movieMapper;

  private Movie testMovie;
  private Actor testActor;

  @BeforeEach
  void setUp() {
    movieMapper = new MovieMapper(actorMapper);

    testActor = Actor.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();

    testMovie = Movie.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about the true nature of reality")
        .actors(Arrays.asList(testActor))
        .build();
  }

  @Test
  void testToMovieSummaryDTO_shouldMapAllFields() {
    // When
    MovieSummaryDTO result = movieMapper.toMovieSummaryDTO(testMovie);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getFilmID());
    assertEquals("The Matrix", result.getTitle());
    assertEquals(1999, result.getReleaseYear());
    assertEquals("A computer hacker learns about the true nature of reality", result.getDescription());
  }

  @Test
  void testToMovieSummaryDTO_withDifferentValues_shouldMapCorrectly() {
    // Given
    Movie movie = Movie.builder()
        .filmID(42L)
        .title("Inception")
        .releaseYear(2010)
        .description("A thief who steals corporate secrets")
        .build();

    // When
    MovieSummaryDTO result = movieMapper.toMovieSummaryDTO(movie);

    // Then
    assertEquals(42L, result.getFilmID());
    assertEquals("Inception", result.getTitle());
    assertEquals(2010, result.getReleaseYear());
    assertEquals("A thief who steals corporate secrets", result.getDescription());
  }

  @Test
  void testToMovieDetailDTO_shouldMapAllFieldsIncludingActors() {
    // Given
    ActorSummaryDTO actorSummaryDTO = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();

    when(actorMapper.toActorSummaryDTO(any(Actor.class))).thenReturn(actorSummaryDTO);

    // When
    MovieDetailDTO result = movieMapper.toMovieDetailDTO(testMovie);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getFilmID());
    assertEquals("The Matrix", result.getTitle());
    assertEquals(1999, result.getReleaseYear());
    assertEquals("A computer hacker learns about the true nature of reality", result.getDescription());
    assertNotNull(result.getActors());
    assertEquals(1, result.getActors().size());
    assertEquals("Keanu", result.getActors().get(0).getFirstName());
    assertEquals("Reeves", result.getActors().get(0).getLastName());
    verify(actorMapper, times(1)).toActorSummaryDTO(testActor);
  }

  @Test
  void testToMovieDetailDTO_withMultipleActors_shouldMapAll() {
    // Given
    Actor actor2 = Actor.builder()
        .actorID(2L)
        .firstName("Laurence")
        .lastName("Fishburne")
        .build();

    testMovie.setActors(Arrays.asList(testActor, actor2));

    ActorSummaryDTO actorSummary1 = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();

    ActorSummaryDTO actorSummary2 = ActorSummaryDTO.builder()
        .actorID(2L)
        .firstName("Laurence")
        .lastName("Fishburne")
        .build();

    when(actorMapper.toActorSummaryDTO(testActor)).thenReturn(actorSummary1);
    when(actorMapper.toActorSummaryDTO(actor2)).thenReturn(actorSummary2);

    // When
    MovieDetailDTO result = movieMapper.toMovieDetailDTO(testMovie);

    // Then
    assertNotNull(result.getActors());
    assertEquals(2, result.getActors().size());
    assertTrue(result.getActors().stream().anyMatch(a -> a.getFirstName().equals("Keanu")));
    assertTrue(result.getActors().stream().anyMatch(a -> a.getFirstName().equals("Laurence")));
    verify(actorMapper, times(2)).toActorSummaryDTO(any(Actor.class));
  }

  @Test
  void testToMovieDetailDTO_withEmptyActorList_shouldHandleGracefully() {
    // Given
    testMovie.setActors(Collections.emptyList());

    // When
    MovieDetailDTO result = movieMapper.toMovieDetailDTO(testMovie);

    // Then
    assertNotNull(result);
    assertNotNull(result.getActors());
    assertTrue(result.getActors().isEmpty());
    verify(actorMapper, never()).toActorSummaryDTO(any());
  }

  @Test
  void testToMovieSummaryDTO_shouldNotCallActorMapper() {
    // When
    movieMapper.toMovieSummaryDTO(testMovie);

    // Then - ActorMapper should not be called for summary DTO
    verify(actorMapper, never()).toActorSummaryDTO(any());
  }

  @Test
  void testToMovieSummaryDTO_withNullReleaseYear_shouldHandleGracefully() {
    // Given
    testMovie.setReleaseYear(null);

    // When
    MovieSummaryDTO result = movieMapper.toMovieSummaryDTO(testMovie);

    // Then
    assertNotNull(result);
    assertNull(result.getReleaseYear());
  }

  @Test
  void testMappingConsistency_multipleCalls_shouldProduceSameResults() {
    // Given
    ActorSummaryDTO actorSummaryDTO = ActorSummaryDTO.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .build();
    when(actorMapper.toActorSummaryDTO(any())).thenReturn(actorSummaryDTO);

    // When
    MovieDetailDTO result1 = movieMapper.toMovieDetailDTO(testMovie);
    MovieDetailDTO result2 = movieMapper.toMovieDetailDTO(testMovie);

    // Then
    assertEquals(result1.getFilmID(), result2.getFilmID());
    assertEquals(result1.getTitle(), result2.getTitle());
    assertEquals(result1.getReleaseYear(), result2.getReleaseYear());
    assertEquals(result1.getDescription(), result2.getDescription());
    assertEquals(result1.getActors().size(), result2.getActors().size());
  }
}
