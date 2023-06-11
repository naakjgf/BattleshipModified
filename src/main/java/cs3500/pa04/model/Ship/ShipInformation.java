package cs3500.pa04.model.Ship;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord.Coord;

public class ShipInformation {
  @JsonProperty("coord")
  private Coord coord;
  @JsonProperty("length")
  private int length;
  @JsonProperty("direction")
  private String direction;

  public ShipInformation(Coord coord, int length, String direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  public Coord getCoord() {
    return coord;
  }

  public int getLength() {
    return length;
  }

  public String getDirection() {
    return direction;
  }
}

