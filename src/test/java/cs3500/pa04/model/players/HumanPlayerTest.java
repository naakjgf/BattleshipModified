package cs3500.pa04.model.players;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordStatus;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
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
 * Tests for the HumanPlayer class.
 */
class HumanPlayerTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private HumanPlayer player;

  @BeforeEach
  void setUp() {
    player = new HumanPlayer("Player", 10, 10);
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void testName() {
    assertEquals("Player", player.name());
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
  void testTakeShots() {
    ArrayList<Coord> volley = new ArrayList<>();
    volley.add(new Coord(0, 0, CoordStatus.WATER));
    volley.add(new Coord(1, 1, CoordStatus.WATER));

    player.setCurrentVolley(volley);
    assertEquals(volley, player.takeShots());
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

    player.setCurrentVolley(shots);
    player.successfulHits(Collections.singletonList(new Coord(0, 0, CoordStatus.WATER)));

    assertEquals(CoordStatus.HIT, player.getOpponentBoard().getBoard()[0][0].getStatus());
    assertEquals(CoordStatus.MISS, player.getOpponentBoard().getBoard()[1][1].getStatus());
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
    player.endGame(GameResult.PLAYERAI_WINS, "Opponent sunk all your ships");
    String expectedOutput2 =
        "Sorry, you've lost. The AI has won the game. Reason: Opponent sunk all your ships\n";
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

  @Test
  void testSetPreviousVolley() {
    ArrayList<Coord> previousShots = new ArrayList<>();
    previousShots.add(new Coord(0, 0, CoordStatus.WATER));
    previousShots.add(new Coord(1, 1, CoordStatus.WATER));
    ArrayList<Coord> currentShots = new ArrayList<>();
    currentShots.add(new Coord(2, 2, CoordStatus.WATER));
    currentShots.add(new Coord(3, 3, CoordStatus.WATER));

    player.setPreviousVolleys(previousShots, currentShots);
    previousShots.addAll(currentShots);
    assertEquals(previousShots, player.getPreviousVolleys());
  }
}
