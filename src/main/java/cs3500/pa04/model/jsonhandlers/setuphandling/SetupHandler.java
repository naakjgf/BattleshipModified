package cs3500.pa04.model.jsonhandlers.setuphandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.jsonhandlers.MessageJson;
import cs3500.pa04.model.players.CompetitionAiPlayer;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipInformation;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class which handles the setup request made by the server.
 */
public class SetupHandler {
  private final ObjectMapper objectMapper;
  private final CompetitionAiPlayer competitionAiPlayer;

  /**
   * Constructor for the SetupHandler class.
   *
   * @param competitionAiPlayer the CompetitionAiPlayer object that will be used to setup the game.
   */
  public SetupHandler(CompetitionAiPlayer competitionAiPlayer) {
    this.objectMapper = new ObjectMapper();
    this.competitionAiPlayer = competitionAiPlayer;
  }

  /**
   * Handles the setup request made by the server.
   *
   * @param incomingMessageJson the incoming message from the server.
   * @return MessageJson object to be sent back to the server.
   */
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
      throw new RuntimeException(e);
    }
  }
}


