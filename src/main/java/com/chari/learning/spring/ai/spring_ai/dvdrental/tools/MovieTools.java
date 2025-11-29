package com.chari.learning.spring.ai.spring_ai.dvdrental.tools;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.MovieDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.service.MovieService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieTools {
  private final MovieService movieService;

  public MovieTools(MovieService movieService) {
    this.movieService = movieService;
  }

  @McpTool(name = "get_movie_by_id", description = "Get movie details for the provided id.", title = "get_movie_by_id")
  public MovieDetailDTO getMovieById(Long id) {
    log.info("Fetching movie for id: {}", id);

    MovieDetailDTO result = movieService.getMovieById(id);

    return result;
  }

  @McpTool(name = "get_all_movies", description = "Get all available movies.", title = "get_all_movies")
  public List<MovieDetailDTO> getAllMovies() {
    log.info("Fetching all movies.");

    List<MovieDetailDTO> result = movieService.getAllMovies();

    return result;
  }

  @McpTool(name = "get_movie_by_search_title", description = "Get all available movies by applying the provided search term as contains filter in the `titile`.", title = "get_movie_by_search_title")
  public List<MovieDetailDTO>  searchForMovies(
      @McpToolParam(description = "search term to be used to in filter for column 'title'", required = true) String searchTerm) {
    log.info("Searching for movies with title containing: {}", searchTerm);

    List<MovieDetailDTO> result = movieService.searchMoviesByTitle(searchTerm);

    return result;
  }


  @McpTool(name = "get_movie_by_multiple_search_title", description = "Get all available movies by searching for all the provided words in the title. The search is done using or condition. i.e every `titile` is searched for all the provided words.", title = "get_movie_by_multiple_search_title")
  public List<MovieDetailDTO> searchForMoviesByMultipleWords(
      @McpToolParam(description = "one or more search words to be used in searching for movies in the column 'title'", required = true) List<String> searchTerms) {
    log.info("Searching for movies with title containing: {}", searchTerms);

    List<MovieDetailDTO> result = movieService.searchMoviesByTitleSearchTerms(searchTerms);

    return result;
  }
}
