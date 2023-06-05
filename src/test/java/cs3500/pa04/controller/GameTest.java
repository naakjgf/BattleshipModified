package cs3500.pa04.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Game class.
 */
class GameTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Tests the constructor of the Game class.
   */
  @Test
  void testConstructor() {
    System.setOut(new PrintStream(outContent));
    String simulatedUserInput =
        "\n6\n6\nJef!\n4\n1\n1\n1\n1\n0\n0\n1\n0\n2\n0\n3\n0\n4\n0\n5\n0\n0\n1\n1\n1\n2\n1\n3\n1"
            + "\n4\n1\n5\n1\n0\n2\n1\n2\n2\n2\n3\n2\n4\n2\n5\n2\n0\n3\n1\n3\n2\n3\n3\n3\n4\n3\n5"
            + "\n3\n0\n4\n1\n4\n2\n4\n3\n4\n4\n4\n5\n4\n0\n5\n1\n5\n2\n5\n3\n5\n4\n5\n5\n5\n";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
    System.setIn(testIn);
    Scanner thisScanner = new Scanner(System.in);
    Game game = new Game(thisScanner);
  }
}