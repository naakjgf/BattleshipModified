package cs3500.pa04.model.ship;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.coord.Coord;

/**
 * Represents the information of a ship
 */
public class ShipInformation {
  @JsonProperty("coord")
  private Coord coord;
  @JsonProperty("length")
  private int length;
  @JsonProperty("direction")
  private String direction;

  /**
   * Constructor for ShipInformation. Takes in a coordinate, length, and direction.
   *
   * @param coord coordinate of the ship.
   * @param length length of the ship.
   * @param direction Orientation of the ship.
   */
  public ShipInformation(Coord coord, int length, String direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  /**
   * Getter for the coordinate of the ship.
   *
   * @return the coordinate of the ship.
   */
  public Coord getCoord() {
    return coord;
  }

  /**
   * Getter for the length of the ship.
   *
   * @return the length of the ship.
   */
  public int getLength() {
    return length;
  }

  /**
   * Getter for the direction of the ship.
   *
   * @return the direction of the ship.
   */
  public String getDirection() {
    return direction;
  }
}

