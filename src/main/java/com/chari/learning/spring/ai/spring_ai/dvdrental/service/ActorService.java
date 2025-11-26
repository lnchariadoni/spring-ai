package com.chari.learning.spring.ai.spring_ai.dvdrental.service;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.mapper.ActorMapper;
import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Actor;
import com.chari.learning.spring.ai.spring_ai.dvdrental.repository.ActorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ActorService {
  private final ActorRepository actorRepository;
  private final ActorMapper actorMapper;

  public ActorService(ActorRepository actorRepository,
                      ActorMapper actorMapper) {
    this.actorRepository = actorRepository;
    this.actorMapper = actorMapper;
  }

  public List<ActorDetailDTO> getAllActors() {
    return actorRepository.findAll().stream().map(actorMapper::toActorDetailDTO).toList();
  }

  public ActorDetailDTO getActorByID(Long id) {
    Optional<Actor> actor = actorRepository.findById(id);

    return actor.map(actorMapper::toActorDetailDTO).orElse(null);
  }

  public List<ActorDetailDTO> searchActorByName(String name) {
    return actorRepository.searchByName(name)
        .stream()
        .map(actorMapper::toActorDetailDTO)
        .toList();
  }

  public List<ActorDetailDTO> searchActorByNames(List<String> names) {
    return actorRepository.searchByNames(String.join(",", names))
        .stream()
        .map(actorMapper::toActorDetailDTO)
        .toList();
  }
}
