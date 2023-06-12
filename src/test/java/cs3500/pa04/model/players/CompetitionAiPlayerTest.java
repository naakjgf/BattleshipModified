package cs3500.pa04.model.players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordStatus;
import cs3500.pa04.model.jsonhandlers.MessageJson;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class designed to test the CompetitionAiPlayer class.
 */
public class CompetitionAiPlayerTest {
  private final String name = "Test AI Player";
  private CompetitionAiPlayer aiPlayer;

  @BeforeEach
  void setUp() {
    aiPlayer = new CompetitionAiPlayer(name);
  }

  @Test
  void testPlayerName() {
    assertEquals(name, aiPlayer.name());
  }

  @Test
  void testSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 2);
    specifications.put(ShipType.BATTLESHIP, 1);

    List<Ship> ships = aiPlayer.setup(10, 10, specifications);
    assertEquals(3, ships.size());
    assertEquals(3, aiPlayer.getShipCount());
  }

  @Test
  void testDecideCurrentVolley() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    aiPlayer.decideCurrentVolley(10, 10);
    assertTrue(aiPlayer.takeShots().size() <= aiPlayer.getShipCount());
    assertFalse(aiPlayer.takeShots().isEmpty());
  }

  @Test
  void testReportDamage() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    aiPlayer.setup(10, 10, specifications);
    aiPlayer.getMyBoard().getBoard()[0][0].setStatus(CoordStatus.SHIP);
    List<Coord> hits =
        aiPlayer.reportDamage(Collections.singletonList(new Coord(0, 0, CoordStatus.WATER)));
    assertEquals(1, hits.size());
  }

  @Test
  void testSuccessfulHits() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    aiPlayer.decideCurrentVolley(10, 10);
    aiPlayer.successfulHits(Collections.singletonList(new Coord(0, 0, CoordStatus.WATER)));
    assertEquals(CoordStatus.HIT, aiPlayer.getOpponentBoard().getBoard()[0][0].getStatus());
  }

  @Test
  void testEndGame() {
    aiPlayer.endGame(GameResult.WIN, "You've won");
    aiPlayer.endGame(GameResult.LOSE, "You've lost");
    aiPlayer.endGame(GameResult.DRAW, "It's a draw");
    assertThrows(IllegalStateException.class,
        () -> aiPlayer.endGame(GameResult.IN_PROGRESS, "Invalid game result"));
  }

  @Test
  void testGetShipCount() {
    assertEquals(0, aiPlayer.getShipCount());
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    assertEquals(1, aiPlayer.getShipCount());
  }

  @Test
  void testHandleTakeShotsRequest() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    aiPlayer.decideCurrentVolley(10, 10);
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();
    MessageJson mockInput = new MessageJson("end-game", arguments);
    assertThrows(IllegalArgumentException.class, () -> aiPlayer.handleTakeShotsRequest(mockInput));
    MessageJson mockInput2 = new MessageJson("take-shots", arguments);
    assertNotNull(aiPlayer.handleTakeShotsRequest(mockInput2));
  }

  @Test
  void testHandleReportDamageRequest() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", "win");
    arguments.put("reason", "You sank all of the opposing ships!");
    MessageJson mockInput = new MessageJson("end-game", arguments);
    assertThrows(IllegalArgumentException.class,
        () -> aiPlayer.handleReportDamageRequest(mockInput));
    ObjectNode arguments2 = mapper.createObjectNode();
    ArrayNode coordinatesArray = arguments2.putArray("coordinates");
    ObjectNode coord = mapper.createObjectNode();
    coord.put("y", 1);
    coord.put("x", 1);
    coordinatesArray.add(coord);
    MessageJson mockInput2 = new MessageJson("report-damage", arguments2);
    assertNotNull(aiPlayer.handleReportDamageRequest(mockInput2));
  }

  @Test
  void testHandleSuccessfulHitsRequest() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", "win");
    arguments.put("reason", "You sank all of the opposing ships!");
    MessageJson mockInput = new MessageJson("end-game", arguments);
    assertThrows(IllegalArgumentException.class,
        () -> aiPlayer.handleSuccessfulHitsRequest(mockInput));
    ObjectNode arguments2 = mapper.createObjectNode();
    ArrayNode coordinatesArray = arguments2.putArray("coordinates");
    ObjectNode coord = mapper.createObjectNode();
    coord.put("y", 1);
    coord.put("x", 1);
    coordinatesArray.add(coord);
    MessageJson mockInput2 = new MessageJson("successful-hits", arguments2);
    assertNotNull(aiPlayer.handleSuccessfulHitsRequest(mockInput2));
  }

  @Test
  void testEndGameRequest() {
    aiPlayer.setup(10, 10, Collections.singletonMap(ShipType.DESTROYER, 1));
    ObjectMapper mapper2 = new ObjectMapper();
    ObjectNode arguments2 = mapper2.createObjectNode();
    MessageJson mockInput2 = new MessageJson("take-shots", arguments2);
    assertThrows(IllegalArgumentException.class, () -> aiPlayer.handleEndGameRequest(mockInput2));
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", "win");
    arguments.put("reason", "You sank all of the opposing ships!");
    MessageJson mockInput = new MessageJson("end-game", arguments);
    assertNotNull(aiPlayer.handleEndGameRequest(mockInput));
  }
}
