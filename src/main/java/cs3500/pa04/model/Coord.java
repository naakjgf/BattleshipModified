package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a coordinate on the board.
 */
public class Coord {
  private final int coordinateX;
  private final int coordinateY;
  private CoordStatus status;

  /**
   * Constructs a coordinate with the given row, column, and status.
   *
   * @param row the row of the coordinate.
   * @param col the column of the coordinate.
   * @param status the status of the coordinate.
   */
  @JsonCreator
  public Coord(@JsonProperty("y") int row, @JsonProperty("x") int col, CoordStatus status) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and column must be non-negative");
    }
    if (status == null) {
      throw new IllegalArgumentException("Status must be non-null");
    }
    this.coordinateY = row;
    this.coordinateX = col;
    this.status = status;
  }

  /**
   * gets the x coordinate of the Coord.
   *
   * @return an int representing the x coordinate of the Coord.
   */
  public int getCoordinateX() {
    return coordinateX;
  }

  /**
   * gets the y coordinate of the Coord.
   *
   * @return an int representing the y coordinate of the Coord.
   */
  public int getCoordinateY() {
    return coordinateY;
  }

  /**
   * gets the status of the Coord.
   *
   * @return a CoordStatus representing the status of the Coord.
   */
  public CoordStatus getStatus() {
    return status;
  }

  /**
   * A toString method for the Coord, which formats the Coord in a neat way.
   *
   * @return a String representing the Coord.
   */
  public String toString() {
    return "(" + this.coordinateY + ", " + this.coordinateX + ", " + this.status + ")";
  }

  /**
   * Sets the status of the Coord.
   *
   * @param status the status to set the Coord to.
   */
  public void setStatus(CoordStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status must be non-null");
    }
    this.status = status;
  }

  /**
   * A method to check if two Coords are equal.
   *
   * @param obj the object to compare to.
   * @return a boolean representing whether the two Coords are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord coord = (Coord) obj;
    return coordinateX == coord.coordinateX
        && coordinateY == coord.coordinateY;
  }
}
