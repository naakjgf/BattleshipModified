package cs3500.pa04.NewStuff.JsonHandlers.SetupHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import cs3500.pa04.model.Ship.Ship;
import java.io.IOException;
import java.util.List;

public class SetupHandler {
  private final ObjectMapper objectMapper;
  private final CompetitionAiPlayer competitionAiPlayer;

  public SetupHandler(CompetitionAiPlayer competitionAiPlayer) {
    this.objectMapper = new ObjectMapper();
    this.competitionAiPlayer = competitionAiPlayer;
  }

  public MessageJson handleSetup(MessageJson incomingMessageJson) {
    try {
      SetupArguments setupArguments =
          objectMapper.treeToValue(incomingMessageJson.arguments(), SetupArguments.class);

      List<Ship> ships = competitionAiPlayer.setup(setupArguments.getHeight(),
          setupArguments.getWidth(), setupArguments.getFleetSpec());

      FleetArguments fleetArguments = new FleetArguments(ships);
      ObjectNode fleetArgumentsNode = objectMapper.valueToTree(fleetArguments);

      return new MessageJson("setup", fleetArgumentsNode);
    } catch (JsonProcessingException e) {
      System.out.println("Failed due to:" + e);
    }
    return null;
  }
}


