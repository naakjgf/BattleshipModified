package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.CoordStatus;
import cs3500.pa04.model.GameResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONPlayerHandler {
  private static final ObjectMapper mapper = new ObjectMapper();

  public static JsonNode parseRequest(String request) throws IOException {
    return mapper.readTree(request);
  }

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

  public static String createResponseWithCoordinates(String methodName, List<Coord> coordinates)
      throws IOException {
    ObjectNode response = mapper.createObjectNode();
    response.put("method-name", methodName);
    ArrayNode coordinatesArray = response.putArray("coordinates");
    for (Coord coordinate : coordinates) {
      ObjectNode coordinateNode = mapper.createObjectNode();
      coordinateNode.put("x", coordinate.getCoordinateX());
      coordinateNode.put("y", coordinate.getCoordinateY());
      coordinatesArray.add(coordinateNode);
    }
    return mapper.writeValueAsString(response);
  }

  public static String createEmptyResponse(String methodName) throws IOException {
    ObjectNode response = mapper.createObjectNode();
    response.put("method-name", methodName);
    response.putObject("arguments");
    return mapper.writeValueAsString(response);
  }

  public static GameResult stringToGameResult(String result) {
    switch (result.toUpperCase()) {
      case "WIN":
        return GameResult.WIN;
      case "LOSS":
        return GameResult.LOSS;
      case "DRAW":
        return GameResult.DRAW;
      default:
        throw new IllegalArgumentException("Invalid game result: " + result);
    }
  }
}
