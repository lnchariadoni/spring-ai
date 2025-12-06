package com.chari.learning.spring.ai.spring_ai.dvdrental.service;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.mapper.ActorMapper;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Actor;
import com.chari.learning.spring.ai.spring_ai.dvdrental.repository.ActorRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

  @Mock
  private ActorRepository actorRepository;

  @Mock
  private ActorMapper actorMapper;

  @InjectMocks
  private ActorService actorService;

  private Actor testActor;
  private ActorDetailDTO testActorDTO;

  @BeforeEach
  void setUp() {
    testActor = Actor.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();

    testActorDTO = ActorDetailDTO.builder()
        .actorID(1L)
        .firstName("Tom")
        .lastName("Hanks")
        .build();
  }

  @Test
  void testGetAllActors_shouldReturnAllActors() {
    // Given
    List<Actor> actors = Arrays.asList(testActor);
    when(actorRepository.findAll()).thenReturn(actors);
    when(actorMapper.toActorDetailDTO(any(Actor.class))).thenReturn(testActorDTO);

    // When
    List<ActorDetailDTO> result = actorService.getAllActors();

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Tom", result.get(0).getFirstName());
    verify(actorRepository, times(1)).findAll();
    verify(actorMapper, times(1)).toActorDetailDTO(testActor);
  }

  @Test
  void testGetActorByID_withValidId_shouldReturnActor() {
    // Given
    when(actorRepository.findById(1L)).thenReturn(Optional.of(testActor));
    when(actorMapper.toActorDetailDTO(testActor)).thenReturn(testActorDTO);

    // When
    ActorDetailDTO result = actorService.getActorByID(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getActorID());
    assertEquals("Tom", result.getFirstName());
    assertEquals("Hanks", result.getLastName());
    verify(actorRepository, times(1)).findById(1L);
  }

  @Test
  void testGetActorByID_withInvalidId_shouldThrowException() {
    // Given
    when(actorRepository.findById(999L)).thenReturn(Optional.empty());

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      actorService.getActorByID(999L);
    });

    assertTrue(exception.getMessage().contains("Actor not found for id: 999"));
    verify(actorRepository, times(1)).findById(999L);
    verify(actorMapper, never()).toActorDetailDTO(any());
  }

  @Test
  void testSearchActorByName_shouldReturnMatchingActors() {
    // Given
    String searchTerm = "Tom";
    List<Actor> actors = Arrays.asList(testActor);
    when(actorRepository.searchByName(searchTerm)).thenReturn(actors);
    when(actorMapper.toActorDetailDTO(any(Actor.class))).thenReturn(testActorDTO);

    // When
    List<ActorDetailDTO> result = actorService.searchActorByName(searchTerm);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Tom", result.get(0).getFirstName());
    verify(actorRepository, times(1)).searchByName(searchTerm);
  }

  @Test
  void testSearchActorByName_withNoMatches_shouldReturnEmptyList() {
    // Given
    when(actorRepository.searchByName(anyString())).thenReturn(Arrays.asList());

    // When
    List<ActorDetailDTO> result = actorService.searchActorByName("NonExistent");

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSearchActorByNames_withMultipleNames_shouldReturnMatchingActors() {
    // Given
    List<String> names = Arrays.asList("Tom", "Hanks");
    List<Actor> actors = Arrays.asList(testActor);
    when(actorRepository.searchByNames("Tom,Hanks")).thenReturn(actors);
    when(actorMapper.toActorDetailDTO(any(Actor.class))).thenReturn(testActorDTO);

    // When
    List<ActorDetailDTO> result = actorService.searchActorByNames(names);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(actorRepository, times(1)).searchByNames("Tom,Hanks");
  }

  @Test
  void testSearchActorByNames_withEmptyList_shouldHandleGracefully() {
    // Given
    when(actorRepository.searchByNames("")).thenReturn(Arrays.asList());

    // When
    List<ActorDetailDTO> result = actorService.searchActorByNames(Arrays.asList());

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(actorRepository, times(1)).searchByNames("");
  }
}
