package com.chari.learning.spring.ai.spring_ai.dvdrental.service;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.mapper.MovieMapper;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Movie;
import com.chari.learning.spring.ai.spring_ai.dvdrental.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

  @Mock
  private MovieRepository movieRepository;

  @Mock
  private MovieMapper movieMapper;

  @InjectMocks
  private MovieService movieService;

  private Movie testMovie;
  private MovieDetailDTO testMovieDTO;

  @BeforeEach
  void setUp() {
    testMovie = Movie.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about the true nature of reality")
        .build();

    testMovieDTO = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about the true nature of reality")
        .build();
  }

  @Test
  void testGetMovieById_withValidId_shouldReturnMovie() {
    // Given
    when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
    when(movieMapper.toMovieDetailDTO(testMovie)).thenReturn(testMovieDTO);

    // When
    MovieDetailDTO result = movieService.getMovieById(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getFilmID());
    assertEquals("The Matrix", result.getTitle());
    assertEquals(1999, result.getReleaseYear());
    verify(movieRepository, times(1)).findById(1L);
    verify(movieMapper, times(1)).toMovieDetailDTO(testMovie);
  }

  @Test
  void testGetMovieById_withInvalidId_shouldThrowException() {
    // Given
    when(movieRepository.findById(999L)).thenReturn(Optional.empty());

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      movieService.getMovieById(999L);
    });

    assertTrue(exception.getMessage().contains("Movie not found for id: 999"));
    verify(movieRepository, times(1)).findById(999L);
    verify(movieMapper, never()).toMovieDetailDTO(any());
  }

  @Test
  void testGetAllMovies_shouldReturnAllMovies() {
    // Given
    List<Movie> movies = Arrays.asList(testMovie);
    when(movieRepository.findAll()).thenReturn(movies);
    when(movieMapper.toMovieDetailDTO(any(Movie.class))).thenReturn(testMovieDTO);

    // When
    List<MovieDetailDTO> result = movieService.getAllMovies();

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("The Matrix", result.get(0).getTitle());
    verify(movieRepository, times(1)).findAll();
    verify(movieMapper, times(1)).toMovieDetailDTO(testMovie);
  }

  @Test
  void testGetAllMovies_withEmptyRepository_shouldReturnEmptyList() {
    // Given
    when(movieRepository.findAll()).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieService.getAllMovies();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(movieRepository, times(1)).findAll();
  }

  @Test
  void testSearchMoviesByTitle_withMatchingTitle_shouldReturnMovies() {
    // Given
    String searchTerm = "Matrix";
    List<Movie> movies = Arrays.asList(testMovie);
    when(movieRepository.findByTitleContainingIgnoreCase(searchTerm)).thenReturn(movies);
    when(movieMapper.toMovieDetailDTO(any(Movie.class))).thenReturn(testMovieDTO);

    // When
    List<MovieDetailDTO> result = movieService.searchMoviesByTitle(searchTerm);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("The Matrix", result.get(0).getTitle());
    verify(movieRepository, times(1)).findByTitleContainingIgnoreCase(searchTerm);
  }

  @Test
  void testSearchMoviesByTitle_withNoMatches_shouldReturnEmptyList() {
    // Given
    when(movieRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieService.searchMoviesByTitle("NonExistent");

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchMoviesByTitleSearchTerms_withMultipleTerms_shouldReturnMovies() {
    // Given
    List<String> terms = Arrays.asList("Matrix", "Reality");
    List<Movie> movies = Arrays.asList(testMovie);
    when(movieRepository.searchByWords("Matrix,Reality")).thenReturn(movies);
    when(movieMapper.toMovieDetailDTO(any(Movie.class))).thenReturn(testMovieDTO);

    // When
    List<MovieDetailDTO> result = movieService.searchMoviesByTitleSearchTerms(terms);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(movieRepository, times(1)).searchByWords("Matrix,Reality");
  }

  @Test
  void testSearchMoviesByTitleSearchTerms_withSingleTerm_shouldWork() {
    // Given
    List<String> terms = Arrays.asList("Matrix");
    List<Movie> movies = Arrays.asList(testMovie);
    when(movieRepository.searchByWords("Matrix")).thenReturn(movies);
    when(movieMapper.toMovieDetailDTO(any(Movie.class))).thenReturn(testMovieDTO);

    // When
    List<MovieDetailDTO> result = movieService.searchMoviesByTitleSearchTerms(terms);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(movieRepository, times(1)).searchByWords("Matrix");
  }

  @Test
  void testSearchMoviesByTitleSearchTerms_withEmptyList_shouldHandleGracefully() {
    // Given
    when(movieRepository.searchByWords("")).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieService.searchMoviesByTitleSearchTerms(Arrays.asList());

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(movieRepository, times(1)).searchByWords("");
  }
}
