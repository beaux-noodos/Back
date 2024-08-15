package com.example.templet.endpoint.rest.controller;

import com.example.templet.endpoint.mapper.LocationMapper;
import com.example.templet.endpoint.mapper.ReactionMapper;
import com.example.templet.endpoint.rest.model.Location;
import com.example.templet.endpoint.rest.model.Reaction;
import com.example.templet.model.BoundedPageSize;
import com.example.templet.model.PageFromOne;
import com.example.templet.model.Whoami;
import com.example.templet.repository.model.LocationReaction;
import com.example.templet.security.JwtUtils;
import com.example.templet.service.LocationReactionService;
import com.example.templet.service.LocationService;
import com.example.templet.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class LocationController {
  private final UserAuthService userAuthService;
  private final JwtUtils jwtUtils;
  private final LocationService locationService;
  private final LocationReactionService locationReactionService;
  private final LocationMapper mapper;
  private final ReactionMapper reactionMapper;

  @GetMapping(value = "/locations")
  public List<Location> getLocations(
      @RequestParam(required = false, value = "is_suggest") Boolean isSuggest,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
      HttpServletRequest request) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (isSuggest != null && isSuggest) {
      String token = jwtUtils.parseJwt(request.getHeader("Authorization"));
      Whoami whoami = userAuthService.whoami(token);
      return locationService.findAll().stream().map(mapper::toRest).toList();
    }
    return locationService.findAll(pageFromOne, boundedPageSize).stream()
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping(value = "/locations/{id}")
  public Location getLocationById(@PathVariable String id) {
    return mapper.toRest(locationService.findById(id));
  }

  @PutMapping(value = "/locations/{id}")
  public Location crupdateLocation(
      @PathVariable(name = "id") String id, @RequestBody Location toUpdate) {
    return mapper.toRest(locationService.crupdateLocation(mapper.toDomain(toUpdate), id));
  }

  @DeleteMapping(value = "/locations/{id}")
  public Location deleteLocation(
      @PathVariable(name = "id") String id, @RequestBody Location toUpdate) {
    return null;
  }

  @PutMapping(value = "users/{uid}/locations/{xid}/react")
  public Reaction crupdateReactLocation(
      @PathVariable(name = "uid") String uid,
      @PathVariable(name = "xid") String xid,
      @RequestBody Reaction reaction) {
    LocationReaction locationReaction = reactionMapper.toDomainLocationReaction(reaction);
    locationReaction.setSubject(locationService.findById(reaction.getSubjectId()));
    return reactionMapper.toRestReaction(locationReactionService.react(uid, xid, locationReaction));
  }

  @GetMapping(value = "locations/{xid}/react")
  public List<Reaction> getReactLocation(
      @PathVariable(name = "xid") String xid,
      @RequestParam(required = false, value = "uid") String uid,
      @RequestParam(required = false, value = "have_like_reaction") Boolean havelikeReaction,
      @RequestParam(required = false, value = "have_vision") Boolean haveVision,
      @RequestParam(required = false, value = "have_stars_number") Boolean haveStarsNumber,
      @RequestParam(required = false, value = "have_comment") Boolean haveComment,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
    PageFromOne pageFromOne = new PageFromOne(page);
    BoundedPageSize boundedPageSize = new BoundedPageSize(pageSize);
    if (uid == null
        && haveStarsNumber == null
        && haveComment == null
        && havelikeReaction == null
        && haveVision == null) {
      List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
          locationReactionService.findAllBySubjectId(xid, pageFromOne, boundedPageSize);
      return reactions.stream().map(reactionMapper::toRestReaction).toList();
    }
    List<com.example.templet.template.sucgestIAWithReaction.Reaction> reactions =
        locationReactionService.findAll();
    return reactions.stream().map(reactionMapper::toRestReaction).toList();
  }
}
