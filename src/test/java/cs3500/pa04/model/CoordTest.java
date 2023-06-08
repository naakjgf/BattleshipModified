package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Coord class.
 */
class CoordTest {

  @Test
  void testConstructor() {
    assertDoesNotThrow(() -> new Coord(0, 0, CoordStatus.WATER));
    assertThrows(IllegalArgumentException.class, () -> new Coord(-1, 0, CoordStatus.WATER));
    assertThrows(IllegalArgumentException.class, () -> new Coord(0, -1, CoordStatus.WATER));
    assertThrows(IllegalArgumentException.class, () -> new Coord(0, 0, null));
  }

  @Test
  void testGetX() {
    Coord coord = new Coord(5, 7, CoordStatus.WATER);
    assertEquals(7, coord.getCoordinateX());
  }

  @Test
  void testGetY() {
    Coord coord = new Coord(5, 7, CoordStatus.WATER);
    assertEquals(5, coord.getCoordinateY());
  }

  @Test
  void testGetStatus() {
    Coord coord = new Coord(5, 7, CoordStatus.WATER);
    assertEquals(CoordStatus.WATER, coord.getStatus());
  }

  @Test
  void testToString() {
    Coord coord = new Coord(5, 7, CoordStatus.WATER);
    assertEquals("(5, 7, WATER)", coord.toString());
  }

  @Test
  void testSetStatus() {
    Coord coord = new Coord(5, 7, CoordStatus.WATER);
    coord.setStatus(CoordStatus.HIT);
    assertEquals(CoordStatus.HIT, coord.getStatus());
    assertThrows(IllegalArgumentException.class, () -> coord.setStatus(null));
  }

  @Test
  void testEquals() {
    Coord coord1 = new Coord(5, 7, CoordStatus.WATER);
    Coord coord2 = new Coord(5, 7, CoordStatus.HIT);
    assertEquals(coord1, coord2);
    coord2 = new Coord(6, 7, CoordStatus.WATER);
    assertNotEquals(coord1, coord2);
    coord2 = new Coord(5, 8, CoordStatus.WATER);
    assertNotEquals(coord1, coord2);
  }
}
