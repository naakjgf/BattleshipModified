package cs3500.pa04.model;

import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.Ship.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class representing a board in the game of battleship.
 */
public class Board {
  private final Coord[][] board;
  private int tilesLeft;
  private final HashMap<Coord, Ship> shipMap;

  /**
   * Constructs a board with the given height and width.
   *
   * @param boardHeight the height of the board
   * @param boardWidth the width of the board
   */
  public Board(int boardHeight, int boardWidth) {
    this.board = new Coord[boardHeight][boardWidth];
    this.shipMap = new HashMap<>();
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        this.board[i][j] = new Coord(i, j, CoordStatus.WATER);
      }
    }
  }

  /**
   * Gets the status of the tile at the given coordinates.
   *
   * @param i the first coordinate.
   * @param j the second coordinate.
   * @return the status of the tile at the given coordinates.
   */
  public CoordStatus getTileCoordStatus(int i, int j) {
    return this.board[i][j].getStatus();
  }

  /**
   * Gets the number of tiles left on the board which haven't been shot at.
   *
   * @return an int representing the number of tiles left on the board which haven't been shot at.
   */
  public int getTilesLeft() {
    this.tilesLeft = 0;
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (getTileCoordStatus(i, j).equals(CoordStatus.SHIP)
            || getTileCoordStatus(i, j).equals(CoordStatus.WATER)) {
          this.tilesLeft++;
        }
      }
    }
    return this.tilesLeft;
  }

  /**
   * Gets the board.
   *
   * @return the board.
   */
  public Coord[][] getBoard() {
    return this.board;
  }

  /**
   * Updates the player's personal board at the given coordinate to be either a hit or miss
   * depending on if a ship was there.
   *
   * @param coord the coordinate to update.
   */
  public void updatePersonalBoard(Coord coord) {
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (this.board[i][j].equals(coord)) {
          if (this.board[i][j].getStatus() == CoordStatus.SHIP) {
            this.board[i][j].setStatus(CoordStatus.HIT);
          } else {
            this.board[i][j].setStatus(CoordStatus.MISS);
          }
        }
      }
    }
  }

  /**
   * Updates the opponent's board at the given coordinates with a new status.
   *
   * @param coords the coordinates to update.
   * @param status the status to update the coordinates to.
   */
  public void updateOpponentBoard(List<Coord> coords, CoordStatus status) {
    for (Coord c : coords) {
      for (int i = 0; i < this.board.length; i++) {
        for (int j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j].equals(c)) {
            this.board[i][j].setStatus(status);
          }
        }
      }
    }
  }

  /**
   * Places the given ships on the board.
   *
   * @param ships the ships to place on the board.
   */
  public void placeShips(ArrayList<Ship> ships) {
    for (Ship ship : ships) {
      for (Coord coord : ship.getLocation()) {
        this.board[coord.getCoordinateY()][coord.getCoordinateX()].setStatus(CoordStatus.SHIP);
        this.shipMap.put(this.board[coord.getCoordinateY()][coord.getCoordinateX()], ship);
      }
    }
  }

  /**
   * Gets the ship at the given coordinate.
   *
   * @param coord the coordinate to get the ship at.
   * @return the ship at the given coordinate.
   */
  public Ship getShipAt(Coord coord) {
    return this.shipMap.get(coord);
  }

  /**
   * Gets the Coord on this board at the given x and y coordinates.
   *
   * @param y height placement being looked for.
   * @param x width placement being looked for.
   * @return the Coord at the given x and y coordinates.
   */
  public Coord getCoord(int y, int x) {
    return this.board[y][x];
  }
}

