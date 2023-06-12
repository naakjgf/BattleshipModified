package cs3500.pa04.NewStuff.JsonHandlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.GameResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles creating Json from different class objects in the model
 */
public class JsonPlayerHandler {
  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Converts server arguments from Json input into a List of coordinates
   * @param arguments List of coordinates in Json format
   * @return List of coordinates in List() format
   */
  public static List<Coord> getCoordinatesFromArguments(JsonNode arguments) {
    ArrayNode coordinatesArray = (ArrayNode) arguments.get("coordinates");
    List<Coord> coordinates = new ArrayList<>();
    for (JsonNode coordinateNode : coordinatesArray) {
      int x = coordinateNode.get("x").asInt();
      int y = coordinateNode.get("y").asInt();
      coordinates.add(new Coord(y, x, CoordStatus.WATER));
    }
    return coordinates;
  }

  /**
   * creates a MessageJson response with coordinates
   * @param methodName the method name to create the response for
   * @param coordinates the list of coordinates to format into Json
   * @return MessageJson object from coordinates in arguments parameter.
   */
  public static MessageJson createResponseWithCoordinates(String methodName, List<Coord> coordinates) {
    ObjectNode arguments = mapper.createObjectNode();
    ArrayNode coordinatesArray = arguments.putArray("coordinates");
    for (Coord coordinate : coordinates) {
      ObjectNode coordinateNode = mapper.createObjectNode();
      coordinateNode.put("x", coordinate.getCoordinateX());
      coordinateNode.put("y", coordinate.getCoordinateY());
      coordinatesArray.add(coordinateNode);
    }
    return new MessageJson(methodName, arguments);
  }

  /**
   * Creates an empty JsonResponse
   * @param methodName the method name to create the empty response for
   * @return an MessageJson with an empty arguments paramater.
   */
  public static MessageJson createEmptyResponse(String methodName) {
    ObjectNode arguments = mapper.createObjectNode();
    return new MessageJson(methodName, arguments);
  }

  /**
   * Converts a String result into a GameResult enumeration instance
   * @param result the result as a string
   * @return result as a GameResult enumeration
   */
  public static GameResult stringToGameResult(String result) {
    return switch (result.toUpperCase()) {
      case "WIN" -> GameResult.WIN;
      case "LOSE" -> GameResult.LOSE;
      case "DRAW" -> GameResult.DRAW;
      default -> throw new IllegalArgumentException("Invalid game result: " + result);
    };
  }
}

