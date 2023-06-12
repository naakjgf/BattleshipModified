package cs3500.pa04.model.ship;

/**
 * An enum representing the types of ships.
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int length;

  /**
   * Constructs a ShipType.
   *
   * @param length the length of the ship
   */
  ShipType(int length) {
    this.length = length;
  }

  /**
   * Gets the length of the ship.
   *
   * @return the length of the ship.
   */
  public int getLength() {
    return this.length;
  }
}
