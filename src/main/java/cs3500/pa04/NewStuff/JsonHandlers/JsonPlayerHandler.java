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

public class JsonPlayerHandler {
  private static final ObjectMapper mapper = new ObjectMapper();

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

  public static MessageJson createEmptyResponse(String methodName) {
    ObjectNode arguments = mapper.createObjectNode();
    return new MessageJson(methodName, arguments);
  }

  public static GameResult stringToGameResult(String result) {
    return switch (result.toUpperCase()) {
      case "WIN" -> GameResult.WIN;
      case "LOSS" -> GameResult.LOSS;
      case "DRAW" -> GameResult.DRAW;
      default -> throw new IllegalArgumentException("Invalid game result: " + result);
    };
  }
}

