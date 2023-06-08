package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the DisplayBoard class.
 */
class DisplayBoardTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private DisplayBoard displayBoard;

  @BeforeEach
  void setUp() {
    displayBoard = new DisplayBoard();
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void testDisplayTurnResults() {
    List<Coord> opponentHits = new ArrayList<>();
    opponentHits.add(new Coord(0, 0, CoordStatus.HIT));
    List<Coord> playerHits = new ArrayList<>();
    playerHits.add(new Coord(1, 1, CoordStatus.HIT));

    displayBoard.displayTurnResults(opponentHits, playerHits);

    assertTrue(outContent.toString().contains("You hit your opponent's ship at: "));
    assertTrue(outContent.toString().contains("Opponent hit your ship at: "));
  }

  @Test
  void testPromptForAttack() {
    String simulatedUserInput = "0\n0\n11\n0\n0\n11\n0\n0\nabc\n0\n1\n1\n";
    ByteArrayInputStream userInput = new ByteArrayInputStream(simulatedUserInput.getBytes());
    Scanner scanner = new Scanner(userInput);

    // Assuming boardWidth = 10, boardHeight = 10 and numShips = 2
    Board board = new Board(10, 10);
    ArrayList<Coord> attackCoords =
        displayBoard.promptForAttack(board, "Player1", 2, scanner, 20, new ArrayList<>());

    assertEquals(2, attackCoords.size());
    Coord coord1 = new Coord(0, 0, CoordStatus.WATER);
    assertEquals(coord1.getCoordinateX() + coord1.getCoordinateY(),
        attackCoords.get(0).getCoordinateX() + attackCoords.get(0).getCoordinateY());
    assertEquals(coord1.toString(), attackCoords.get(1).toString());
  }

  @Test
  void testPromptForAttackIssues() {
    String simulatedUserInput = "abc\nahh\n0\n1\n1\n";
    String simulatedUserInput2 = "0\n0\n0\n0\n1\n1\n3\n4\n2\n1\n6";
    ByteArrayInputStream userInput = new ByteArrayInputStream(simulatedUserInput.getBytes());
    ByteArrayInputStream userInput2 = new ByteArrayInputStream(simulatedUserInput2.getBytes());
    Scanner scanner2 = new Scanner(userInput2);
    Scanner scanner = new Scanner(userInput);

    assertThrows(NoSuchElementException.class,
        () -> displayBoard.promptForAttack(new Board(10, 10), "Player1", 2, scanner, 20,
            new ArrayList<>()));
    Coord coord1 = new Coord(0, 0, CoordStatus.WATER);
    ArrayList<Coord> previousCoords = new ArrayList<>();
    previousCoords.add(coord1);
    ArrayList<Coord> attackCoords =
        displayBoard.promptForAttack(new Board(10, 10), "Player1", 2, scanner2, 20, previousCoords);
    assertEquals(2, attackCoords.size());
  }

  @Test
  void testDisplayBoard() {
    Board board = new Board(10, 10);
    System.setOut(new PrintStream(outContent));
    board.getBoard()[0][0].setStatus(CoordStatus.HIT);
    board.getBoard()[1][1].setStatus(CoordStatus.SHIP);
    board.getBoard()[2][2].setStatus(CoordStatus.WATER);
    board.getBoard()[3][3].setStatus(CoordStatus.MISS);
    displayBoard.displayBoard(board, "Player1");
    String expectedOutput = """
        Player1's known board:\s
         _______________________________________________
        H W W W W W W W W W\s
        W S W W W W W W W W\s
        W W W W W W W W W W\s
        W W W M W W W W W W\s
        W W W W W W W W W W\s
        W W W W W W W W W W\s
        W W W W W W W W W W\s
        W W W W W W W W W W\s
        W W W W W W W W W W\s
        W W W W W W W W W W\s
        _______________________________________________
        """;

    assertEquals(expectedOutput, outContent.toString());
  }
}
