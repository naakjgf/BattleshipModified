package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.model.ship.ShipType;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the GameView class.
 */
class GameViewTest {
  private GameView gameView;

  @BeforeEach
  void setUp() {
    gameView = new GameView();
  }

  @Test
  void testGetPlayerName() {
    String simulatedUserInput = "ValidName\n";
    String simulatedUserInputName = "I Mean$Wrong?\nI Valid Tho\n";
    Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    Scanner scanner2 = new Scanner(new ByteArrayInputStream(simulatedUserInputName.getBytes()));
    String name2 = gameView.getPlayerName(scanner2);
    assertEquals("I Valid Tho", name2);
    String name = gameView.getPlayerName(scanner);
    assertEquals("ValidName", name);
  }

  @Test
  void testGetShipTypes() {
    String simulatedUserInput2 = "\n1\n1\n2\n3\n";
    Scanner scanner2 = new Scanner(new ByteArrayInputStream(simulatedUserInput2.getBytes()));
    Map<ShipType, Integer> shipTypes2 = gameView.getShipTypes(scanner2, 6);
    assertEquals(Integer.valueOf(1), shipTypes2.get(ShipType.CARRIER));
    assertEquals(Integer.valueOf(1), shipTypes2.get(ShipType.BATTLESHIP));
    assertEquals(Integer.valueOf(2), shipTypes2.get(ShipType.DESTROYER));
    String simulatedUserInput = "\n-1\n\n0\n2\n2\n2\n\n1\n1\n1\n1\n1\n1\n1\n2\n1\n2\n2\n1";
    Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    Map<ShipType, Integer> shipTypes = gameView.getShipTypes(scanner, 4);
    assertEquals(Integer.valueOf(1), shipTypes.get(ShipType.SUBMARINE));
    assertEquals(Integer.valueOf(1), shipTypes.get(ShipType.DESTROYER));
    assertEquals(Integer.valueOf(1), shipTypes.get(ShipType.BATTLESHIP));
    assertEquals(Integer.valueOf(1), shipTypes.get(ShipType.CARRIER));
  }

  @Test
  void testGetNumberOfShips() {
    String simulatedUserInput = "6\n";
    String simulatedUserInput2 = "0\n50\nabc\n7\n";
    Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    Scanner scanner2 = new Scanner(new ByteArrayInputStream(simulatedUserInput2.getBytes()));
    int numShips2 = gameView.getNumberOfShips(scanner2, 10, 10);
    assertEquals(7, numShips2);
    int numShips = gameView.getNumberOfShips(scanner, 10, 10);
    assertEquals(6, numShips);
  }

  @Test
  void testGetBoardWidth() {
    String simulatedUserInput = "10\n";
    String simulatedUserInput2 = "0\n50\nabc\n7\n";
    Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    Scanner scanner2 = new Scanner(new ByteArrayInputStream(simulatedUserInput2.getBytes()));
    int width2 = gameView.getBoardWidth(scanner2);
    assertEquals(7, width2);
    int width = gameView.getBoardWidth(scanner);
    assertEquals(10, width);
  }

  @Test
  void testGetBoardHeight() {
    String simulatedUserInput = "10\n";
    String simulatedUserInput2 = "0\n50\nabc\n7\n";
    Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    Scanner scanner2 = new Scanner(new ByteArrayInputStream(simulatedUserInput2.getBytes()));
    int height2 = gameView.getBoardHeight(scanner2);
    assertEquals(7, height2);
    int height = gameView.getBoardHeight(scanner);
    assertEquals(10, height);
  }
}