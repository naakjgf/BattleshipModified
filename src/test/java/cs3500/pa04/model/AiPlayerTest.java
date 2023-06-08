package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.Players.AiPlayer;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AIPlayer class.
 */
class AiPlayerTest {
  private AiPlayer player;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    player = new AiPlayer(10, 10);
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void testName() {
    assertEquals("Admiral Ackbar (Resident AI StarWars Fanboy)", player.name());
  }

  @Test
  void testSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 2);
    specifications.put(ShipType.BATTLESHIP, 1);

    List<Ship> ships = player.setup(10, 10, specifications);
    assertEquals(3, ships.size());
    assertEquals(3, player.getShipCount());
  }

  @Test
  void testDecideCurrentVolley() {
    player.decideCurrentVolley(10, 10);
    assertTrue(player.takeShots().size() <= player.getShipCount());
    //testing this along with successful hits a small portion here, because populating current
    //volley but cannot access it directly since it is private so one of the few ways to test it
    //and also get two birds with one stone.
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.CARRIER, 2);
    player.setup(10, 10, specifications);
    player.decideCurrentVolley(10, 10);
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0, CoordStatus.WATER));
    shots.add(new Coord(1, 1, CoordStatus.WATER));
    //Board oldOpponentBoard = player.getOpponentBoard();
    player.successfulHits(shots);
    //assertNotEquals(oldOpponentBoard.getBoard(), player.getOpponentBoard().getBoard());
  }

  @Test
  void testReportDamage() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0, CoordStatus.WATER));

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);

    player.setup(10, 10, specifications);
    player.getMyBoard().getBoard()[0][0].setStatus(CoordStatus.SHIP);

    List<Coord> hits = player.reportDamage(shots);
    assertEquals(1, hits.size());
    assertEquals(1, player.getShipCount());
  }

  @Test
  void testSuccessfulHits() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0, CoordStatus.WATER));
    shots.add(new Coord(1, 1, CoordStatus.WATER));

    player.successfulHits(Collections.singletonList(new Coord(0, 0, CoordStatus.WATER)));

    assertEquals(CoordStatus.HIT, player.getOpponentBoard().getBoard()[0][0].getStatus());
  }

  @Test
  void testEndGameManualWins() {
    player.endGame(GameResult.PLAYERMANUAL_WINS, "Player sunk all the opponent's ships");
    String expectedOutput =
        "Congratulations! You've won the game. Reason: Player sunk all the opponent's ships\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  void testEndGameAiWins() {
    player.endGame(GameResult.PLAYERAI_WINS, "AI sunk all the player's ships");
    String expectedOutput2 =
        "Sorry, you've lost. The AI has won the game. Reason: AI sunk all the player's ships\n";
    assertEquals(expectedOutput2, outContent.toString());
  }

  @Test
  void testEndGameDraw() {
    player.endGame(GameResult.DRAW, "Both players have sunk all the ships");
    String expectedOutput3 =
        "The game is a draw. Reason: Both players have sunk all the ships\n";
    assertEquals(expectedOutput3, outContent.toString());
  }

  @Test
  void testEndGameInProgressJustForCoverage() {
    player.endGame(GameResult.IN_PROGRESS, "The game is still in progress");
    String expectedOutput4 =
        "The game is still in progress. Reason: The game is still in progress\n";
    assertEquals(expectedOutput4, outContent.toString());
  }
}
