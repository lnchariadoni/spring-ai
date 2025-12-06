package com.chari.learning.spring.ai.spring_ai.dvdrental.tools;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.service.ActorService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ActorFluxTools {

  private final ActorService actorService;

  public ActorFluxTools(ActorService actorService) {
    this.actorService = actorService;
  }

  @McpTool(name = "get_actor_by_flux__id",
      description = "Get actor details for the provided id.",
      title = "get_actor_by_flux__id")
  public Mono<ActorDetailDTO> getActorByIdFlux(Long id) {
//  public ActorDetailDTO getActorById(Long id) {
    log.info("Fetching actor for id: {}", id);

//    ActorDetailDTO result = actorService.getActorByID(id);
//    return result;

    ActorDetailDTO result = new ActorDetailDTO(100, "first", "last", List.of());

    return Mono.just(result);
  }

  @McpTool(name = "get_all_actors", description = "Get all available actors.", title = "get_all_actors")
  public List<ActorDetailDTO> getAllActors() {
    log.info("Fetching all actors.");

    List<ActorDetailDTO> result = actorService.getAllActors();

    return result;
  }

  @McpTool(name = "get_actor_by_name", description = "Get all actors for the given name. The given name will be searched in both first and last name.", title = "get_actor_by_name")
  public List<ActorDetailDTO>  searchForActors(
      @McpToolParam(description = "search term to be used to in filter for column 'firstName' or `lastName`", required = true) String searchTerm) {
    log.info("Searching for actors with title containing: {}", searchTerm);

    List<ActorDetailDTO> result = actorService.searchActorByName(searchTerm);

    return result;
  }


  @McpTool(name = "get_actor_by_names_flux",
      description = "Get all actors for the given list of names. The given list of names will be searched in both first and last name.",
      title = "get_actor_by_names_flux")
  public Flux<ActorDetailDTO>  searchForActorsFromMultipleNamesFlux(
      @McpToolParam(description = "search for given list of names to be used to in filter for column 'firstName' or `lastName`", required = true) List<String> searchNames) {
    log.info("Searching for actors with title containing: {}", searchNames);

//    List<ActorDetailDTO> result = actorService.searchActorByNames(searchNames);
//    return result;

    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Flux<ActorDetailDTO> result = Flux.just(1, 2, 3, 4)
        .delayElements(Duration.ofMillis(500))
        .map(counter ->
            new ActorDetailDTO(counter,
                "first_" + counter,
                "last_" +  LocalDateTime.now().format(FORMATTER), List.of()
            )
        );
    return result;

  }
}
