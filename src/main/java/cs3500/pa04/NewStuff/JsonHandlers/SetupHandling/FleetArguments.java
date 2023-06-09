package cs3500.pa04.NewStuff.JsonHandlers.SetupHandling;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Ship.ShipInformation;
import java.util.List;

public class FleetArguments {
  @JsonProperty("fleet")
  private final List<ShipInformation> fleet;

  public FleetArguments(List<ShipInformation> fleet) {
    this.fleet = fleet;
  }
}
