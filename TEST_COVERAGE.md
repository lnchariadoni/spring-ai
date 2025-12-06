# Test Coverage Summary

## Overview
Comprehensive test suite for the Spring AI MCP (Model Context Protocol) application, covering all MCP tools and services.

## Test Statistics
- **Total Tests**: 42
- **Test Classes**: 6
- **Test Coverage**: All MCP tools and services

## Test Classes

### 1. CourseServiceTest (5 tests)
Unit tests for the in-memory CourseService with MCP tools.

**Test Cases:**
- `testGetAllCourses_shouldReturnAllCourses` - Verifies all 4 sample courses are returned
- `testGetCourseById_withValidId_shouldReturnCourse` - Tests successful course retrieval by ID
- `testGetCourseById_withInvalidId_shouldReturnNotFoundCourse` - Tests error handling for non-existent courses
- `testGetCourseById_multipleIds_shouldReturnCorrectCourses` - Validates multiple course retrievals
- `testGetAllCourses_shouldReturnUnmodifiableList` - Ensures list immutability

**MCP Tools Tested:**
- `get_all_courses`
- `get_course_by_id`

### 2. ActorServiceTest (7 tests)
Unit tests for ActorService with mocked repository and mapper.

**Test Cases:**
- `testGetAllActors_shouldReturnAllActors` - Tests retrieval of all actors
- `testGetActorByID_withValidId_shouldReturnActor` - Tests successful actor lookup
- `testGetActorByID_withInvalidId_shouldThrowException` - Tests exception handling
- `testSearchActorByName_shouldReturnMatchingActors` - Tests name search functionality
- `testSearchActorByName_withNoMatches_shouldReturnEmptyList` - Tests empty result handling
- `testSearchActorByNames_withMultipleNames_shouldReturnMatchingActors` - Tests multiple name search
- `testSearchActorByNames_withEmptyList_shouldHandleGracefully` - Tests edge case with empty input

**Coverage:**
- Repository interaction verification
- Mapper usage validation
- Exception handling for not-found scenarios
- Empty result handling

### 3. MovieServiceTest (9 tests)
Unit tests for MovieService with mocked repository and mapper.

**Test Cases:**
- `testGetMovieById_withValidId_shouldReturnMovie` - Tests movie retrieval by ID
- `testGetMovieById_withInvalidId_shouldThrowException` - Tests exception handling
- `testGetAllMovies_shouldReturnAllMovies` - Tests retrieval of all movies
- `testGetAllMovies_withEmptyRepository_shouldReturnEmptyList` - Tests empty repository
- `testSearchMoviesByTitle_withMatchingTitle_shouldReturnMovies` - Tests title search
- `testSearchMoviesByTitle_withNoMatches_shouldReturnEmptyList` - Tests no-match scenario
- `testSearchMoviesByTitleSearchTerms_withMultipleTerms_shouldReturnMovies` - Tests multi-term search
- `testSearchMoviesByTitleSearchTerms_withSingleTerm_shouldWork` - Tests single term search
- `testSearchMoviesByTitleSearchTerms_withEmptyList_shouldHandleGracefully` - Tests edge case

**Coverage:**
- Repository interaction verification
- Mapper usage validation
- Exception handling for not-found scenarios
- Empty result and edge case handling

### 4. ActorToolsTest (9 tests)
Tests for ActorTools MCP endpoint implementations.

**Test Cases:**
- `testGetActorById_shouldReturnActorDetails` - Tests get_actor_by_id tool
- `testGetActorById_withInvalidId_shouldPropagateException` - Tests error propagation
- `testGetAllActors_shouldReturnListOfActors` - Tests get_all_actors tool
- `testGetAllActors_withEmptyResult_shouldReturnEmptyList` - Tests empty result
- `testSearchForActors_shouldReturnMatchingActors` - Tests get_actor_by_name tool
- `testSearchForActors_withNoMatches_shouldReturnEmptyList` - Tests no-match scenario
- `testSearchForActorsFromMultipleNames_shouldReturnMatchingActors` - Tests get_actor_by_names tool
- `testSearchForActorsFromMultipleNames_withEmptyList_shouldHandleGracefully` - Tests edge case
- `testSearchForActorsFromMultipleNames_withMultipleResults_shouldReturnAll` - Tests multiple results

**MCP Tools Tested:**
- `get_actor_by_id`
- `get_all_actors`
- `get_actor_by_name`
- `get_actor_by_names`

### 5. MovieToolsTest (10 tests)
Tests for MovieTools MCP endpoint implementations.

**Test Cases:**
- `testGetMovieById_shouldReturnMovieDetails` - Tests get_movie_by_id tool
- `testGetMovieById_withInvalidId_shouldPropagateException` - Tests error propagation
- `testGetAllMovies_shouldReturnListOfMovies` - Tests get_all_movies tool
- `testGetAllMovies_withEmptyResult_shouldReturnEmptyList` - Tests empty result
- `testSearchForMovies_shouldReturnMatchingMovies` - Tests get_movie_by_search_title tool
- `testSearchForMovies_withNoMatches_shouldReturnEmptyList` - Tests no-match scenario
- `testSearchForMoviesByMultipleWords_shouldReturnMatchingMovies` - Tests get_movie_by_multiple_search_title tool
- `testSearchForMoviesByMultipleWords_withEmptyList_shouldHandleGracefully` - Tests edge case
- `testSearchForMoviesByMultipleWords_withSingleSearchTerm_shouldWork` - Tests single term
- `testSearchForMoviesByMultipleWords_withMultipleResults_shouldReturnAll` - Tests multiple results

**MCP Tools Tested:**
- `get_movie_by_id`
- `get_all_movies`
- `get_movie_by_search_title`
- `get_movie_by_multiple_search_title`

### 6. SpringAiApplicationTests (2 tests)
Integration tests for application context loading.

**Test Cases:**
- `contextLoads` - Verifies Spring application context loads successfully
- `testCourseServiceInitialized` - Verifies CourseService is properly initialized

## Running Tests

### Using Maven with Java 21:
```bash
export JAVA_HOME=~/.sdkman/candidates/java/21.0.4-oracle
mvn test
```

### Using Maven (if Java 21 is default):
```bash
mvn test
```

## Test Dependencies

The following dependencies are required (already configured in pom.xml):
- `spring-boot-starter-test` - JUnit 5, Mockito, AssertJ, Spring Test
- `h2` (test scope) - In-memory database for integration tests

## Testing Strategy

### Unit Tests
- **Service Layer**: Uses Mockito to mock repositories and mappers
- **Tool Layer**: Uses Mockito to mock service dependencies
- **Isolation**: Each layer tested independently

### Integration Tests
- **Minimal Scope**: Tests only essential beans to avoid configuration complexity
- **In-Memory Database**: Uses H2 for database-dependent tests (when needed)

## Test Patterns Used

1. **AAA Pattern**: Arrange-Act-Assert structure in all tests
2. **Builder Pattern**: Uses Lombok builders for test data creation
3. **Mocking**: Mockito for external dependencies
4. **Verification**: Mockito verify() for interaction testing
5. **Edge Cases**: Tests for null, empty, and invalid inputs

## Coverage Areas

✅ All MCP tool endpoints  
✅ Service layer business logic  
✅ Repository interaction  
✅ Mapper usage  
✅ Exception handling  
✅ Empty result scenarios  
✅ Edge cases  
✅ Application context loading  

## Notes

- Tests are independent and can run in any order
- No external database required for running tests
- All tests use Java 21 features
- Mockito inline mock maker is used (with expected warnings)
