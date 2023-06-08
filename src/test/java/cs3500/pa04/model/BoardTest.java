package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.Ship.Orientation;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Board class.
 */
class BoardTest {
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board(10, 10);
  }

  @Test
  void testConstructor() {
    assertNotNull(board);
    assertEquals(10, board.getBoard().length);
    assertEquals(10, board.getBoard()[0].length);
  }

  @Test
  void getTileCoordStatus() {
    assertEquals(CoordStatus.WATER, board.getTileCoordStatus(5, 5));
  }

  @Test
  void getTilesLeft() {
    assertEquals(100, board.getTilesLeft());
  }

  @Test
  void updatePersonalBoard() {
    board.updatePersonalBoard(board.getBoard()[5][5]);
    assertEquals(CoordStatus.MISS, board.getTileCoordStatus(5, 5));
    board.getBoard()[5][5].setStatus(CoordStatus.SHIP);
    board.updatePersonalBoard(board.getBoard()[5][5]);
    assertEquals(CoordStatus.HIT, board.getTileCoordStatus(5, 5));
  }

  @Test
  void updateOpponentBoard() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(5, 5, CoordStatus.SHIP));
    board.updateOpponentBoard(coords, CoordStatus.HIT);
    assertEquals(CoordStatus.HIT, board.getTileCoordStatus(5, 5));
  }

  @Test
  void placeShips() {
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(
        new Ship(ShipType.DESTROYER, new Coord(1, 1, CoordStatus.SHIP), Orientation.HORIZONTAL,
            board));
    board.placeShips(ships);
    assertEquals(CoordStatus.SHIP, board.getTileCoordStatus(1, 1));
    assertNotNull(board.getShipAt(board.getBoard()[1][1]));
  }

  @Test
  void getShipAt() {
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(
        new Ship(ShipType.DESTROYER, new Coord(1, 1, CoordStatus.SHIP), Orientation.HORIZONTAL,
            board));
    board.placeShips(ships);
    assertNotNull(board.getShipAt(board.getBoard()[1][1]));
    assertNull(board.getShipAt(board.getBoard()[2][2]));
  }

  @Test
  void getCoord() {
    Coord coord = board.getCoord(1, 1);
    assertEquals(1, coord.getCoordinateX());
    assertEquals(1, coord.getCoordinateY());
    assertEquals(CoordStatus.WATER, coord.getStatus());
  }
}
