package cs3500.pa04.model;

import java.util.ArrayList;

/**
 * A class representing a ship in the game of battleship.
 */
public class Ship {
  private final ShipType type;
  private ArrayList<Coord> locations;
  private final Orientation orientation;
  private ShipStatus status;

  /**
   * Constructs a ship with the given type, starting coordinate, orientation, and board.
   *
   * @param type the type of the ship.
   * @param coord the starting coordinate of the ship.
   * @param orientation the orientation of the ship.
   * @param board the board on which the ship is placed.
   */
  public Ship(ShipType type, Coord coord, Orientation orientation, Board board) {
    this.type = type;
    this.orientation = orientation;
    this.locations = calculateShipCoords(coord, board);
    this.status = ShipStatus.INTACT;
  }

  /**
   * Determines whether the ship has been sunk. if all of the ship's locations have been hit, then
   * the ship is sunk.
   *
   * @return true if the ship has been sunk, false otherwise.
   */
  public boolean isSunk() {
    for (Coord c : locations) {
      if (c.getStatus() != CoordStatus.HIT) {
        return false;
      }
    }
    this.status = ShipStatus.SUNK;
    return true;
  }

  /**
   * Gets the locations of the ship.
   *
   * @return an ArrayList of the Coordinates which hold this ship.
   */
  public ArrayList<Coord> getLocation() {
    return locations;
  }

  /**
   * Calculates the coordinates of the ship based on the starting coordinate and orientation. Used
   * for calculation purposes mainly as the name implies, not for setting the locations of the ship.
   *
   * @param startCoord the starting coordinate of the ship.
   * @param board the board on which the ship is placed.
   * @return an ArrayList of the Coordinates which hold this ship.
   */
  public ArrayList<Coord> calculateShipCoords(Coord startCoord, Board board) {
    ArrayList<Coord> shipCoords = new ArrayList<>();
    shipCoords.add(startCoord);
    int x = startCoord.getCoordinateX();
    int y = startCoord.getCoordinateY();

    for (int i = 1; i < this.getType().getLength(); i++) {
      if (this.getOrientation() == Orientation.HORIZONTAL) {
        shipCoords.add(board.getCoord(y, x + i));
      } else {
        shipCoords.add(board.getCoord(y + i, x));
      }
    }

    return shipCoords;
  }

  /**
   * Sets the locations of the ship based on given coordinates.
   *
   * @param coords the coordinates which will hold this ship.
   */
  public void setLocation(ArrayList<Coord> coords) {
    for (Coord thisCoord : coords) {
      thisCoord.setStatus(CoordStatus.SHIP);
    }
    this.locations = coords;
  }

  /**
   * Gets the status of the ship.
   *
   * @return a ShipStatus representing the status of the ship.
   */
  public ShipStatus getShipStatus() {
    return status;
  }

  /*
   * Gets the orientation of the ship.
   *
   * @return an Orientation representing the orientation of the ship.
   */
  public Orientation getOrientation() {
    return orientation;
  }

  /**
   * Gets the type of the ship.
   *
   * @return a ShipType representing the type of the ship.
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Updates the status of the ship based on the status of its coordinates, meaning how many of
   * the parts of the ship have been hit.
   */
  public void updateShipStatus() {
    int hitPartsCount = 0;
    for (Coord c : locations) {
      if (c.getStatus() == CoordStatus.HIT) {
        hitPartsCount++;
      }
    }
    if (hitPartsCount == 0) {
      this.status = ShipStatus.INTACT;
    } else if (hitPartsCount < this.type.getLength()) {
      this.status = ShipStatus.DAMAGED;
    } else if (hitPartsCount == this.type.getLength()) {
      this.status = ShipStatus.SUNK;
    }
  }

  public Coord getStartingCoord()
  {
    return locations.get(0);
  }
}
