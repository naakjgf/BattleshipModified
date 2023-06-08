package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.Ship.Orientation;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipStatus;
import cs3500.pa04.model.Ship.ShipType;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Ship class.
 */
class ShipTest {

  private Ship ship;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board(5, 5);
    ship = new Ship(ShipType.DESTROYER, new Coord(0, 0, CoordStatus.WATER), Orientation.HORIZONTAL,
        board);
  }

  @Test
  void testIsSunk() {
    assertFalse(ship.isSunk());

    for (Coord coord : ship.getLocation()) {
      coord.setStatus(CoordStatus.HIT);
    }

    assertTrue(ship.isSunk());
  }

  @Test
  void testCalculateShipCoords() {
    ArrayList<Coord> expectedLocation = new ArrayList<>();
    expectedLocation.add(new Coord(0, 0, CoordStatus.WATER));
    expectedLocation.add(new Coord(0, 1, CoordStatus.WATER));
    expectedLocation.add(new Coord(0, 2, CoordStatus.WATER));
    expectedLocation.add(new Coord(0, 3, CoordStatus.WATER));

    assertEquals(expectedLocation,
        ship.calculateShipCoords(new Coord(0, 0, CoordStatus.WATER), board));
  }

  @Test
  void testSetLocation() {
    ArrayList<Coord> newLocation = new ArrayList<>();
    newLocation.add(new Coord(0, 1, CoordStatus.WATER));
    newLocation.add(new Coord(0, 2, CoordStatus.WATER));
    newLocation.add(new Coord(0, 3, CoordStatus.WATER));

    ship.setLocation(newLocation);

    assertEquals(newLocation, ship.getLocation());
  }

  @Test
  void testUpdateShipStatus() {
    assertEquals(ShipStatus.INTACT, ship.getShipStatus());
    ship.updateShipStatus();

    ship.getLocation().get(0).setStatus(CoordStatus.HIT);
    ship.updateShipStatus();

    assertEquals(ShipStatus.DAMAGED, ship.getShipStatus());

    for (Coord coord : ship.getLocation()) {
      coord.setStatus(CoordStatus.HIT);
    }
    ship.updateShipStatus();

    assertEquals(ShipStatus.SUNK, ship.getShipStatus());
  }

  @Test
  void testGetOrientation() {
    assertEquals(Orientation.HORIZONTAL, ship.getOrientation());
  }

  @Test
  void testGetType() {
    assertEquals(ShipType.DESTROYER, ship.getType());
  }
}
