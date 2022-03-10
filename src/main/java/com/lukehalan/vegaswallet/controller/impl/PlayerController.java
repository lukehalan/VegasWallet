package com.lukehalan.vegaswallet.controller.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukehalan.vegaswallet.controller.IPlayerController;
import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.request.PlayerRequest;
import com.lukehalan.vegaswallet.service.IPlayerService;
import com.lukehalan.vegaswallet.service.schema.SchemaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController implements IPlayerController {

  private final IPlayerService iPlayerService;
  private final SchemaValidator schemaValidator;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Player getPlayer(Integer id) {
    return iPlayerService.find(id);
  }

  @Override
  public Player createPlayer(PlayerRequest player) {
    schemaValidator.validate(
        "player-schema.json", objectMapper.convertValue(player, JsonNode.class));
    return iPlayerService.save(player);
  }
}
