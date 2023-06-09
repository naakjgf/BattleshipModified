package cs3500.pa04.NewStuff.JsonHandlers.SetupHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipInformation;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

      // Convert Ships to ShipInformation objects
      List<ShipInformation> shipInformationList = ships.stream()
          .map(Ship::toShipInformation)
          .collect(Collectors.toList());

      FleetArguments fleetArguments = new FleetArguments(shipInformationList);
      ObjectNode fleetArgumentsNode = objectMapper.valueToTree(fleetArguments);

      return new MessageJson("setup", fleetArgumentsNode);
    } catch (JsonProcessingException e) {
      System.out.println("Failed due to:" + e);
    }
    return null;
  }
}


