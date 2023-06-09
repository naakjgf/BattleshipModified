package cs3500.pa04.NewStuff.JsonHandlers;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.NewStuff.JsonHandlers.JsonPlayerHandler;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonPlayerHandlerTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Test
  void getCoordinatesFromArguments() {
    ObjectNode arguments = mapper.createObjectNode();
    ArrayNode arrayNode = arguments.putArray("coordinates");

    ObjectNode coordinate1 = mapper.createObjectNode();
    coordinate1.put("x", 1);
    coordinate1.put("y", 2);
    arrayNode.add(coordinate1);

    ObjectNode coordinate2 = mapper.createObjectNode();
    coordinate2.put("x", 3);
    coordinate2.put("y", 4);
    arrayNode.add(coordinate2);

    List<Coord> coordinates = JsonPlayerHandler.getCoordinatesFromArguments(arguments);

    assertEquals(2, coordinates.size());
    assertEquals(new Coord(2, 1, CoordStatus.WATER), coordinates.get(0));
    assertEquals(new Coord(4, 3, CoordStatus.WATER), coordinates.get(1));
  }

  @Test
  void createResponseWithCoordinates() {
    List<Coord> coordinates = List.of(new Coord(2, 1, CoordStatus.WATER),
        new Coord(4, 3, CoordStatus.WATER));
    MessageJson response = JsonPlayerHandler.createResponseWithCoordinates("test", coordinates);

    assertEquals("test", response.messageName());
    assertEquals(2, response.arguments().get("coordinates").size());
    assertEquals(1, response.arguments().get("coordinates").get(0).get("x").asInt());
    assertEquals(2, response.arguments().get("coordinates").get(0).get("y").asInt());
    assertEquals(3, response.arguments().get("coordinates").get(1).get("x").asInt());
    assertEquals(4, response.arguments().get("coordinates").get(1).get("y").asInt());
  }

  @Test
  void createEmptyResponse() {
    MessageJson response = JsonPlayerHandler.createEmptyResponse("test");

    assertEquals("test", response.messageName());
    assertTrue(response.arguments().isEmpty());
  }

  @Test
  void stringToGameResult() {
    assertEquals(GameResult.WIN, JsonPlayerHandler.stringToGameResult("WIN"));
    assertEquals(GameResult.LOSE, JsonPlayerHandler.stringToGameResult("LOSE"));
    assertEquals(GameResult.DRAW, JsonPlayerHandler.stringToGameResult("DRAW"));
    assertThrows(IllegalArgumentException.class, () -> JsonPlayerHandler.stringToGameResult("INVALID"));
  }
}
