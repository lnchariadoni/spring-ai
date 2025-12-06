package com.chari.learning.spring.ai.spring_ai.dvdrental.tools;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.service.ActorService;
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
class ActorToolsTest {

  @Mock
  private ActorService actorService;

  @InjectMocks
  private ActorTools actorTools;

  private ActorDetailDTO testActorDTO;

  @BeforeEach
  void setUp() {
    testActorDTO = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();
  }

  @Test
  void testGetActorById_shouldReturnActorDetails() {
    // Given
    when(actorService.getActorByID(1L)).thenReturn(testActorDTO);

    // When
    ActorDetailDTO result = actorTools.getActorById(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getActorID());
    assertEquals("Tom", result.getFirstName());
    assertEquals("Hanks", result.getLastName());
    verify(actorService, times(1)).getActorByID(1L);
  }

  @Test
  void testGetActorById_withInvalidId_shouldPropagateException() {
    // Given
    when(actorService.getActorByID(anyLong())).thenThrow(new RuntimeException("Actor not found"));

    // When & Then
    assertThrows(RuntimeException.class, () -> actorTools.getActorById(999L));
    verify(actorService, times(1)).getActorByID(999L);
  }

  @Test
  void testGetAllActors_shouldReturnListOfActors() {
    // Given
    List<ActorDetailDTO> actors = Arrays.asList(testActorDTO);
    when(actorService.getAllActors()).thenReturn(actors);

    // When
    List<ActorDetailDTO> result = actorTools.getAllActors();

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Tom", result.get(0).getFirstName());
    verify(actorService, times(1)).getAllActors();
  }

  @Test
  void testGetAllActors_withEmptyResult_shouldReturnEmptyList() {
    // Given
    when(actorService.getAllActors()).thenReturn(Arrays.asList());

    // When
    List<ActorDetailDTO> result = actorTools.getAllActors();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForActors_shouldReturnMatchingActors() {
    // Given
    String searchTerm = "Tom";
    List<ActorDetailDTO> actors = Arrays.asList(testActorDTO);
    when(actorService.searchActorByName(searchTerm)).thenReturn(actors);

    // When
    List<ActorDetailDTO> result = actorTools.searchForActors(searchTerm);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Tom", result.get(0).getFirstName());
    verify(actorService, times(1)).searchActorByName(searchTerm);
  }

  @Test
  void testSearchForActors_withNoMatches_shouldReturnEmptyList() {
    // Given
    when(actorService.searchActorByName(anyString())).thenReturn(Arrays.asList());

    // When
    List<ActorDetailDTO> result = actorTools.searchForActors("Unknown");

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForActorsFromMultipleNames_shouldReturnMatchingActors() {
    // Given
    List<String> searchNames = Arrays.asList("Tom", "Hanks");
    List<ActorDetailDTO> actors = Arrays.asList(testActorDTO);
    when(actorService.searchActorByNames(searchNames)).thenReturn(actors);

    // When
    List<ActorDetailDTO> result = actorTools.searchForActorsFromMultipleNames(searchNames);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(actorService, times(1)).searchActorByNames(searchNames);
  }

  @Test
  void testSearchForActorsFromMultipleNames_withEmptyList_shouldHandleGracefully() {
    // Given
    when(actorService.searchActorByNames(anyList())).thenReturn(Arrays.asList());

    // When
    List<ActorDetailDTO> result = actorTools.searchForActorsFromMultipleNames(Arrays.asList());

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchForActorsFromMultipleNames_withMultipleResults_shouldReturnAll() {
    // Given
    ActorDetailDTO actor2 = ActorDetailDTO.builder()
        .actorID(2L)
        .firstName("Meryl")
        .lastName("Streep")
        .build();
    List<String> searchNames = Arrays.asList("Tom", "Meryl");
    List<ActorDetailDTO> actors = Arrays.asList(testActorDTO, actor2);
    when(actorService.searchActorByNames(searchNames)).thenReturn(actors);

    // When
    List<ActorDetailDTO> result = actorTools.searchForActorsFromMultipleNames(searchNames);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.stream().anyMatch(a -> a.getFirstName().equals("Tom")));
    assertTrue(result.stream().anyMatch(a -> a.getFirstName().equals("Meryl")));
  }
}
