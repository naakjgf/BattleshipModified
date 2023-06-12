package cs3500.pa04.model.jsonhandlers.setuphandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ship.ShipType;
import java.util.Map;

/**
 * A class which represents the arguments for the setup request.
 */
public class SetupArguments {
  @JsonProperty("width")
  private final int width;

  @JsonProperty("height")
  private final int height;

  @JsonProperty("fleet-spec")
  private Map<ShipType, Integer> fleetSpec;

  /**
   * Constructs a SetupArguments object.
   *
   * @param width width of the board.
   * @param height height of the board.
   * @param fleetSpec a map of the fleet specification.
   */
  @JsonCreator
  public SetupArguments(@JsonProperty("width") int width, @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
    this.width = width;
    this.height = height;
    this.fleetSpec = fleetSpec;
  }

  /**
   * Gets the width of the board. As a Json Property.
   *
   * @return an int representing the width of the board.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the board. As a Json Property.
   *
   * @return an int representing the height of the board.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the fleet specification. As a Json Property.
   *
   * @return a map of ShipType to Integer representing the fleet specification.
   */
  public Map<ShipType, Integer> getFleetSpec() {
    return fleetSpec;
  }
}

