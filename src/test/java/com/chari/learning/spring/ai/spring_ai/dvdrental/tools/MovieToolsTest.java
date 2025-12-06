package com.chari.learning.spring.ai.spring_ai.dvdrental.tools;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieToolsTest {

  @Mock
  private MovieService movieService;

  @InjectMocks
  private MovieTools movieTools;

  private MovieDetailDTO testMovieDTO;

  @BeforeEach
  void setUp() {
    testMovieDTO = MovieDetailDTO.builder()
        .filmID(1L)
        .title("The Matrix")
        .releaseYear(1999)
        .description("A computer hacker learns about the true nature of reality")
        .build();
  }

  @Test
  void testGetMovieById_shouldReturnMovieDetails() {
    // Given
    when(movieService.getMovieById(1L)).thenReturn(testMovieDTO);

    // When
    MovieDetailDTO result = movieTools.getMovieById(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getFilmID());
    assertEquals("The Matrix", result.getTitle());
    assertEquals(1999, result.getReleaseYear());
    verify(movieService, times(1)).getMovieById(1L);
  }

  @Test
  void testGetMovieById_withInvalidId_shouldPropagateException() {
    // Given
    when(movieService.getMovieById(anyLong())).thenThrow(new RuntimeException("Movie not found"));

    // When & Then
    assertThrows(RuntimeException.class, () -> movieTools.getMovieById(999L));
    verify(movieService, times(1)).getMovieById(999L);
  }

  @Test
  void testGetAllMovies_shouldReturnListOfMovies() {
    // Given
    List<MovieDetailDTO> movies = Arrays.asList(testMovieDTO);
    when(movieService.getAllMovies()).thenReturn(movies);

    // When
    List<MovieDetailDTO> result = movieTools.getAllMovies();

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("The Matrix", result.get(0).getTitle());
    verify(movieService, times(1)).getAllMovies();
  }

  @Test
  void testGetAllMovies_withEmptyResult_shouldReturnEmptyList() {
    // Given
    when(movieService.getAllMovies()).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieTools.getAllMovies();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForMovies_shouldReturnMatchingMovies() {
    // Given
    String searchTerm = "Matrix";
    List<MovieDetailDTO> movies = Arrays.asList(testMovieDTO);
    when(movieService.searchMoviesByTitle(searchTerm)).thenReturn(movies);

    // When
    List<MovieDetailDTO> result = movieTools.searchForMovies(searchTerm);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("The Matrix", result.get(0).getTitle());
    verify(movieService, times(1)).searchMoviesByTitle(searchTerm);
  }

  @Test
  void testSearchForMovies_withNoMatches_shouldReturnEmptyList() {
    // Given
    when(movieService.searchMoviesByTitle(anyString())).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieTools.searchForMovies("Unknown");

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForMoviesByMultipleWords_shouldReturnMatchingMovies() {
    // Given
    List<String> searchTerms = Arrays.asList("Matrix", "Reality");
    List<MovieDetailDTO> movies = Arrays.asList(testMovieDTO);
    when(movieService.searchMoviesByTitleSearchTerms(searchTerms)).thenReturn(movies);

    // When
    List<MovieDetailDTO> result = movieTools.searchForMoviesByMultipleWords(searchTerms);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(movieService, times(1)).searchMoviesByTitleSearchTerms(searchTerms);
  }

  @Test
  void testSearchForMoviesByMultipleWords_withEmptyList_shouldHandleGracefully() {
    // Given
    when(movieService.searchMoviesByTitleSearchTerms(anyList())).thenReturn(Arrays.asList());

    // When
    List<MovieDetailDTO> result = movieTools.searchForMoviesByMultipleWords(Arrays.asList());

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForMoviesByMultipleWords_withSingleSearchTerm_shouldWork() {
    // Given
    List<String> searchTerms = Arrays.asList("Matrix");
    List<MovieDetailDTO> movies = Arrays.asList(testMovieDTO);
    when(movieService.searchMoviesByTitleSearchTerms(searchTerms)).thenReturn(movies);

    // When
    List<MovieDetailDTO> result = movieTools.searchForMoviesByMultipleWords(searchTerms);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("The Matrix", result.get(0).getTitle());
  }

  @Test
  void testSearchForMoviesByMultipleWords_withMultipleResults_shouldReturnAll() {
    // Given
    MovieDetailDTO movie2 = MovieDetailDTO.builder()
        .filmID(2L)
        .title("The Matrix Reloaded")
        .releaseYear(2003)
        .description("Sequel to The Matrix")
        .build();
    List<String> searchTerms = Arrays.asList("Matrix");
    List<MovieDetailDTO> movies = Arrays.asList(testMovieDTO, movie2);
    when(movieService.searchMoviesByTitleSearchTerms(searchTerms)).thenReturn(movies);

    // When
    List<MovieDetailDTO> result = movieTools.searchForMoviesByMultipleWords(searchTerms);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.stream().allMatch(m -> m.getTitle().contains("Matrix")));
  }
}
