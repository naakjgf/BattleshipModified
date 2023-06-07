package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHandler {

  /**
   * Handles request made by the server to get json and returns a request back.
   *
   * @param json - The json object as a string
   * @return json object to be sent back to the server
   * @throws JsonProcessingException
   */
  public ObjectNode handleJoinRequest(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode joinRequest =
        mapper.createObjectNode(); // the main json node that will eventually be returned.

    joinRequest.put("method-name", "join"); // adds the method-name field in the Json object

    ObjectNode arguments =
        mapper.createObjectNode(); // adds another nested layer to the Json object for the arguments
    arguments.put("name", "shoumik123majumdar"); // adds our github username as the name
    arguments.put("game-type", "SINGLE"); // initializes our gametype to be single

    joinRequest.set("arguments", arguments);

    return joinRequest;
  }

  /**
   * Converts a coordinate into a Json Object
   *
   * @param coord coordinate to be converted
   * @return coordinate as a Json Object
   */
  public ObjectNode createCoordJson(Coord coord) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode coordinate = mapper.createObjectNode();

    coordinate.put("x", coord.getCoordinateX());
    coordinate.put("y", coord.getCoordinateY());

    return coordinate;
  }

  /**
   * Creates an ArrayNode object to represent a volley of shots
   *
   * @param shotVolley the List of the volley's coordinates.
   * @returns an ArrayNode object
   */
  public ObjectNode createVolleyJson(ArrayList<Coord> shotVolley) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode mainNode = mapper.createObjectNode();
    mainNode.put("method-name", "take-shots");

    ArrayNode volleyArrayNode = mapper.createArrayNode();

    for (Coord coord : shotVolley) {
      volleyArrayNode.addPOJO(
          coord); // addPOJO will use Jackson's automatic serialization to convert the Coord object into a JsonNode
    }

    ObjectNode argumentsNode = mapper.createObjectNode();
    argumentsNode.set("coordinates", volleyArrayNode);

    mainNode.set("arguments", argumentsNode);

    return mainNode;
  }

  /**
   * creates an ObjectNode to represent a Ship object
   *
   * @param shipObj Ship object to be represented
   * @return ObjectNode
   */
  public ObjectNode createShipJson(Ship shipObj) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode ship = mapper.createObjectNode();

    ship.set("coord", createCoordJson(shipObj.getStartingCoord()));
    ship.put("length", shipObj.getLocation().size());
    ship.put("direction", shipObj.getOrientation().toString());

    return ship;
  }

  /**
   * Creates an ArrayNode to represent a fleet of ships
   *
   * @param ships ArrayList of ships
   * @return ArrayNode of the fleet
   */
  public ArrayNode createFleetJson(ArrayList<Ship> ships) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNodeFactory jsonNodeFactory = mapper.getNodeFactory();

    ArrayNode fleet = jsonNodeFactory.arrayNode();

    for (Ship ship : ships) {
      ObjectNode shipJson = createShipJson(ship);
      fleet.add(shipJson);
    }

    return fleet;
  }

  //Legacy Methods
  public void handleJson(String json) {

  }

  public String generateResponseJson() {
    return "";
  }
}
