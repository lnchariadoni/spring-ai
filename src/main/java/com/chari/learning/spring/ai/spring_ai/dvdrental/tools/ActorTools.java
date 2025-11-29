package com.chari.learning.spring.ai.spring_ai.dvdrental.tools;

import com.chari.learning.spring.ai.spring_ai.dvdrental.dto.ActorDetailDTO;
import com.chari.learning.spring.ai.spring_ai.dvdrental.service.ActorService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActorTools {
  private final ActorService actorService;

  public ActorTools(ActorService actorService) {
    this.actorService = actorService;
  }

  @McpTool(name = "get_actor_by_id", description = "Get actor details for the provided id.", title = "get_actor_by_id")
  public ActorDetailDTO getActorById(Long id) {
    log.info("Fetching actor for id: {}", id);

    ActorDetailDTO result = actorService.getActorByID(id);

    return result;
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


  @McpTool(name = "get_actor_by_names", description = "Get all actors for the given list of names. The given list of names will be searched in both first and last name.", title = "get_actor_by_names")
  public List<ActorDetailDTO>  searchForActorsFromMultipleNames(
      @McpToolParam(description = "search for given list of names to be used to in filter for column 'firstName' or `lastName`", required = true) List<String> searchNames) {
    log.info("Searching for actors with title containing: {}", searchNames);

    List<ActorDetailDTO> result = actorService.searchActorByNames(searchNames);

    return result;
  }
}
