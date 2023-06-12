package cs3500.pa04.model.jsonhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.players.CompetitionAiPlayer;
import cs3500.pa04.model.ship.ShipType;
import java.net.Socket;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonHandlerTest {

  ObjectNode arguments;
  private CompetitionAiPlayer player;
  private JsonHandler jsonHandler;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    Socket socket = mock(Socket.class);
    player = new CompetitionAiPlayer("mr. robot");
    jsonHandler = new JsonHandler(socket, player);
    objectMapper = new ObjectMapper();
    arguments = objectMapper.createObjectNode();
    arguments.put("width", 10);
    arguments.put("height", 10);
    Map<ShipType, Integer> fleetSpec = Map.of(ShipType.CARRIER, 1);
    arguments.set("fleet-spec", objectMapper.valueToTree(fleetSpec));
  }

  @Test
  void delegateMessageJoin() {
    MessageJson message = new MessageJson("join", objectMapper.createObjectNode());

    MessageJson result = jsonHandler.delegateMessage(message);

    assertEquals("join", result.messageName());
  }

  @Test
  void delegateMessageSetup() {
    MessageJson message = new MessageJson("setup", arguments);
    MessageJson result = jsonHandler.delegateMessage(message);

    assertEquals("setup", result.messageName());
  }

  @Test
  void delegateMessageTakeShots() {
    //Populates the board...
    MessageJson messageHelper = new MessageJson("setup", arguments);
    jsonHandler.delegateMessage(messageHelper);

    ObjectNode arguments = objectMapper.createObjectNode();
    MessageJson message = new MessageJson("take-shots", arguments);
    MessageJson result = jsonHandler.delegateMessage(message);
    assertEquals("take-shots", result.messageName());
  }

  @Test
  void delegateMessageReportDamage() {
    //Populates the board...
    MessageJson messageHelper = new MessageJson("setup", arguments);
    jsonHandler.delegateMessage(messageHelper);
    ObjectNode arguments = objectMapper.createObjectNode();
    ArrayNode coordinatesArray = arguments.putArray("coordinates");
    coordinatesArray.add(objectMapper.createObjectNode().put(
        "x", 0).put("y", 0));
    coordinatesArray.add(objectMapper.createObjectNode().put(
        "x", 0).put("y", 1));
    MessageJson message = new MessageJson("report-damage", arguments);
    MessageJson result = jsonHandler.delegateMessage(message);
    assertEquals("report-damage", result.messageName());
  }

  @Test
  void delegateMessageSuccessfulHits() {
    //Populates the board...
    MessageJson messageHelper = new MessageJson("setup", arguments);
    jsonHandler.delegateMessage(messageHelper);
    ObjectNode arguments = objectMapper.createObjectNode();
    ArrayNode coordinatesArray = arguments.putArray("coordinates");
    coordinatesArray.add(objectMapper.createObjectNode().put(
        "x", 1).put("y", 1));
    coordinatesArray.add(objectMapper.createObjectNode().put(
        "x", 2).put("y", 1));
    MessageJson message = new MessageJson("successful-hits", arguments);
    MessageJson result = jsonHandler.delegateMessage(message);
    assertEquals("successful-hits", result.messageName());
  }

  @Test
  void delegateMessageEndGame() {
    //Populates the board...
    MessageJson messageHelper = new MessageJson("setup", arguments);
    jsonHandler.delegateMessage(messageHelper);
    ObjectNode arguments = objectMapper.createObjectNode();
    arguments.put("result", "WIN");
    arguments.put("reason", "You sunk all of the opponent's ships!");
    MessageJson message = new MessageJson("end-game", arguments);
    MessageJson result = jsonHandler.delegateMessage(message);
    assertEquals("end-game", result.messageName());
  }

  @Test
  void delegateMessageInvalid() {
    MessageJson message = new MessageJson("invalid", objectMapper.createObjectNode());

    assertThrows(IllegalArgumentException.class, () -> jsonHandler.delegateMessage(message));
  }
}
