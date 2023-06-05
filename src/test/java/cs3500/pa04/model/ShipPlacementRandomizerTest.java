package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ShipPlacementRandomizer class.
 */
class ShipPlacementRandomizerTest {
  private ShipPlacementRandomizer shipPlacementRandomizer;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board(10, 10);
    shipPlacementRandomizer = new ShipPlacementRandomizer(board);
  }

  @Test
  void testSetupShips() {
    Map<ShipType, Integer> shipSpecification = new HashMap<>();
    shipSpecification.put(ShipType.CARRIER, 2);
    shipSpecification.put(ShipType.BATTLESHIP, 3);
    shipSpecification.put(ShipType.SUBMARINE, 4);

    var ships = shipPlacementRandomizer.setupShips(10, 10, shipSpecification);

    assertEquals(9, ships.size());

    for (var ship : ships) {
      var shipLocation = ship.getLocation();

      // Check if ships are inside the board
      for (var coord : shipLocation) {
        assertTrue(coord.getCoordinateX() >= 0);
        assertTrue(coord.getCoordinateX() < 10);
        assertTrue(coord.getCoordinateY() >= 0);
        assertTrue(coord.getCoordinateY() < 10);
      }

      // Check if ships are overlapping
      for (var anotherShip : ships) {
        if (anotherShip != ship) {
          var anotherShipLocation = anotherShip.getLocation();
          for (var coord : shipLocation) {
            assertFalse(anotherShipLocation.contains(coord));
          }
        }
      }
    }
  }

  //because of it being random generation and all this test will probably just never cover the line
  //I want it to, if it is commented out this is the reason why. Randomness in this test will be a
  //double-edged sword bcs it might fail and cost me points.
  @Test
  void testMaxAttempts() {
    Map<ShipType, Integer> shipSpecification = new HashMap<>();
    shipSpecification.put(ShipType.CARRIER, 10);

    Board smallBoard = new Board(5, 5);
    ShipPlacementRandomizer smallBoardRandomizer = new ShipPlacementRandomizer(smallBoard);

    assertThrows(IllegalArgumentException.class,
        () -> smallBoardRandomizer.setupShips(5, 5, shipSpecification));
  }
}
