package cs3500.pa04.NewStuff.JsonHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.JsonHandlers.SetupHandling.SetupHandler;
import java.io.IOException;
import java.net.Socket;

public class JsonHandler {
  private final Socket socket;
  private final CompetitionAiPlayer player;

  public JsonHandler(Socket socket, CompetitionAiPlayer player) {
    this.socket = socket;
    this.player = player;
  }

  public MessageJson delegateMessage(MessageJson message) {
    MessageJson returnMessage = null;
    String name = message.messageName();
    switch (name) {
      case "join":
        JoinRequestHandler joinRequestHandler = new JoinRequestHandler();
        returnMessage = joinRequestHandler.handleJoinRequest();
        return returnMessage;
      case "setup":
        SetupHandler setupHandler = new SetupHandler(player);
        returnMessage = setupHandler.handleSetup(message);
        return returnMessage;
      case "take-shots":
        returnMessage = player.handleTakeShotsRequest(message);
        return returnMessage;
      case "report-damage":
        returnMessage = player.handleReportDamageRequest(message);
        return returnMessage;
      case "successful-hits":
        returnMessage = player.handleSuccessfulHitsRequest(message);
        return returnMessage;
      case "end-game":
        returnMessage = player.handleEndGameRequest(message);
        return returnMessage;
      default:
        throw new IllegalArgumentException("Invalid message name");
    }
  }

  /**
   * Converts a coordinate into a Json Object
   *
   * @param coord coordinate to be converted
   * @return coordinate as a Json Object
   *//*
  public ObjectNode createCoordJson(Coord coord) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode coordinate = mapper.createObjectNode();

    coordinate.put("x", coord.getCoordinateX());
    coordinate.put("y", coord.getCoordinateY());

    return coordinate;
  }

  *//**
   * Creates an ArrayNode object to represent a volley of shots
   *
   * @param shotVolley the List of the volley's coordinates.
   * @returns an ArrayNode object
   *//*
  public ObjectNode createVolleyJson(ArrayList<Coord> shotVolley) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode mainNode = mapper.createObjectNode();
    mainNode.put("method-name", "take-shots");

    ArrayNode volleyArrayNode = mapper.createArrayNode();

    for (Coord coord : shotVolley) {
      volleyArrayNode.addPOJO(coord);
      // addPOJO will use Jackson's automatic serialization to convert the Coord object into a JsonNode
    }

    ObjectNode argumentsNode = mapper.createObjectNode();
    argumentsNode.set("coordinates", volleyArrayNode);

    mainNode.set("arguments", argumentsNode);

    return mainNode;
  }

  *//**
   * creates an ObjectNode to represent a Ship object
   *
   * @param shipObj Ship object to be represented
   * @return ObjectNode
   *//*
  public ObjectNode createShipJson(Ship shipObj) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode ship = mapper.createObjectNode();

    ship.set("coord", createCoordJson(shipObj.getStartingCoord()));
    ship.put("length", shipObj.getLocation().size());
    ship.put("direction", shipObj.getOrientation().toString());

    return ship;
  }

  *//**
   * Creates an ArrayNode to represent a fleet of ships
   *
   * @param ships ArrayList of ships
   * @return ArrayNode of the fleet
   *//*
  public ArrayNode createFleetJson(ArrayList<Ship> ships) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNodeFactory jsonNodeFactory = mapper.getNodeFactory();

    ArrayNode fleet = jsonNodeFactory.arrayNode();

    for (Ship ship : ships) {
      ObjectNode shipJson = createShipJson(ship);
      fleet.add(shipJson);
    }

    return fleet;
  }*/
}
