package cs3500.pa04.NewStuff.JsonHandlers.SetupHandling;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Ship.Ship;
import java.util.List;

public class FleetArguments {
  @JsonProperty("fleet")
  private final List<Ship> fleet;

  public FleetArguments(List<Ship> fleet) {
    this.fleet = fleet;
  }
}
