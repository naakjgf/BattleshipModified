package cs3500.pa04.model.jsonhandlers.setuphandling;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ship.ShipInformation;
import java.util.List;

/**
 * A class which represents the specific fleet arguments for the setup request.
 */
public class FleetArguments {
  @JsonProperty("fleet")
  private final List<ShipInformation> fleet;

  /**
   * Constructs a FleetArguments object. As a Json Property.
   *
   * @param fleet a list of ShipInformation objects representing the fleet.
   */
  public FleetArguments(List<ShipInformation> fleet) {
    this.fleet = fleet;
  }
}
