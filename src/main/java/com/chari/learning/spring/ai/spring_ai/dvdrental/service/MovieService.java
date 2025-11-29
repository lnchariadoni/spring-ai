package com.chari.learning.spring.ai.spring_ai.dvdrental.service;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.mapper.MovieMapper;
import com.chari.learning.spring.ai.spring_ai.dvdrental.repository.MovieRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;

  public MovieService(MovieRepository movieRepository,
                      MovieMapper movieMapper) {
    this.movieRepository = movieRepository;
    this.movieMapper = movieMapper;
  }

  public MovieDetailDTO getMovieById(Long id) {
    return movieRepository.findById(id)
        .map(movieMapper::toMovieDetailDTO)
        .orElseThrow(() -> new RuntimeException("Movie not found for id: " + id));
  }

  public List<MovieDetailDTO> getAllMovies() {
    return movieRepository.findAll().stream().map(movieMapper::toMovieDetailDTO).toList();
  }

  public List<MovieDetailDTO> searchMoviesByTitle(String title) {
    return movieRepository.findByTitleContainingIgnoreCase(title)
        .stream()
        .map(movieMapper::toMovieDetailDTO)
        .toList();
  }

  public List<MovieDetailDTO> searchMoviesByTitleSearchTerms(List<String> terms) {
    return movieRepository
         .searchByWords(String.join(",", terms))
        .stream()
        .map(movieMapper::toMovieDetailDTO)
        .toList();
  }
}
