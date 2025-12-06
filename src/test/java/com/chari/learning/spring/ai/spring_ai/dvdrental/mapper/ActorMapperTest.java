package com.chari.learning.spring.ai.spring_ai.dvdrental.mapper;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorSummaryDTO;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorMapperTest {

  @Mock
  private MovieMapper movieMapper;

  private ActorMapper actorMapper;

  private Actor testActor;
  private Movie testMovie;

  @BeforeEach
  void setUp() {
    actorMapper = new ActorMapper(movieMapper);

    testMovie = Movie.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about reality")
        .build();

    testActor = Actor.builder()
        .actorID(1L)
        .firstName("Keanu")
        .lastName("Reeves")
        .movies(Arrays.asList(testMovie))
        .build();
  }

  @Test
  void testToActorSummaryDTO_shouldMapAllFields() {
    // When
    ActorSummaryDTO result = actorMapper.toActorSummaryDTO(testActor);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getActorID());
    assertEquals("Keanu", result.getFirstName());
    assertEquals("Reeves", result.getLastName());
  }

  @Test
  void testToActorSummaryDTO_withDifferentValues_shouldMapCorrectly() {
    // Given
    Actor actor = Actor.builder()
        .actorID(42L)
        .firstName("Morgan")
        .lastName("Freeman")
        .build();

    // When
    ActorSummaryDTO result = actorMapper.toActorSummaryDTO(actor);

    // Then
    assertEquals(42L, result.getActorID());
    assertEquals("Morgan", result.getFirstName());
    assertEquals("Freeman", result.getLastName());
  }

  @Test
  void testToActorDetailDTO_shouldMapAllFieldsIncludingMovies() {
    // Given
    MovieSummaryDTO movieSummaryDTO = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about reality")
        .build();

    when(movieMapper.toMovieSummaryDTO(any(Movie.class))).thenReturn(movieSummaryDTO);

    // When
    ActorDetailDTO result = actorMapper.toActorDetailDTO(testActor);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getActorID());
    assertEquals("Keanu", result.getFirstName());
    assertEquals("Reeves", result.getLastName());
    assertNotNull(result.getMovies());
    assertEquals(1, result.getMovies().size());
    assertEquals("The Matrix", result.getMovies().get(0).getTitle());
    verify(movieMapper, times(1)).toMovieSummaryDTO(testMovie);
  }

  @Test
  void testToActorDetailDTO_withMultipleMovies_shouldMapAll() {
    // Given
    Movie movie2 = Movie.builder()
        .filmID(2L)
        .title("John Wick")
        .releaseYear(2014)
        .description("An ex-hitman comes out of retirement")
        .build();

    testActor.setMovies(Arrays.asList(testMovie, movie2));

    MovieSummaryDTO movieSummary1 = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .build();

    MovieSummaryDTO movieSummary2 = MovieSummaryDTO.builder()
        .filmID(2L)
        .title("John Wick")
        .build();

    when(movieMapper.toMovieSummaryDTO(testMovie)).thenReturn(movieSummary1);
    when(movieMapper.toMovieSummaryDTO(movie2)).thenReturn(movieSummary2);

    // When
    ActorDetailDTO result = actorMapper.toActorDetailDTO(testActor);

    // Then
    assertNotNull(result.getMovies());
    assertEquals(2, result.getMovies().size());
    assertTrue(result.getMovies().stream().anyMatch(m -> m.getTitle().equals("The Matrix")));
    assertTrue(result.getMovies().stream().anyMatch(m -> m.getTitle().equals("John Wick")));
    verify(movieMapper, times(2)).toMovieSummaryDTO(any(Movie.class));
  }

  @Test
  void testToActorDetailDTO_withEmptyMovieList_shouldHandleGracefully() {
    // Given
    testActor.setMovies(Collections.emptyList());

    // When
    ActorDetailDTO result = actorMapper.toActorDetailDTO(testActor);

    // Then
    assertNotNull(result);
    assertNotNull(result.getMovies());
    assertTrue(result.getMovies().isEmpty());
    verify(movieMapper, never()).toMovieSummaryDTO(any());
  }

  @Test
  void testToActorSummaryDTO_shouldNotCallMovieMapper() {
    // When
    actorMapper.toActorSummaryDTO(testActor);

    // Then - MovieMapper should not be called for summary DTO
    verify(movieMapper, never()).toMovieSummaryDTO(any());
  }

  @Test
  void testMappingConsistency_multipleCalls_shouldProduceSameResults() {
    // Given
    MovieSummaryDTO movieSummaryDTO = MovieSummaryDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .build();
    when(movieMapper.toMovieSummaryDTO(any())).thenReturn(movieSummaryDTO);

    // When
    ActorDetailDTO result1 = actorMapper.toActorDetailDTO(testActor);
    ActorDetailDTO result2 = actorMapper.toActorDetailDTO(testActor);

    // Then
    assertEquals(result1.getActorID(), result2.getActorID());
    assertEquals(result1.getFirstName(), result2.getFirstName());
    assertEquals(result1.getLastName(), result2.getLastName());
    assertEquals(result1.getMovies().size(), result2.getMovies().size());
  }
}
