package com.lukehalan.vegaswallet.service;

import static com.lukehalan.vegaswallet.util.JsonUtil.assertJsonMatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.util.JsonUtil;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class PlayerServiceTest extends GenericServiceUtil {

    @Test
    public void findPlayer() throws IOException {
        Mockito.when(playerRepository.findById(PLAYER_ID))
                .thenReturn(Optional.of(buildResponse("data/player1.json",Player.class)));
        Player response = playerService.find(PLAYER_ID);
        JsonNode jsonNode = JsonUtil.readStringToJsonNode(objectMapper.writeValueAsString(response));
        assertJsonMatch(jsonNode,"data/player1expected.json");
    }
}
