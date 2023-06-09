package cs3500.pa04.model.Ship;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum representing the orientation of a ship.
 */
public enum Orientation {
  HORIZONTAL,
  VERTICAL;

  @JsonValue
  public String toValue() {
    switch (this) {
      case HORIZONTAL: return "HORIZONTAL";
      case VERTICAL: return "VERTICAL";
      default: throw new IllegalArgumentException();
    }
  }
}
