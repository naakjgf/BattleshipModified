package cs3500.pa04.NewStuff.SetupHandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.Map;

public class SetupArguments {
  private final int width;

  private final int height;

  @JsonProperty("fleet-spec")
  private Map<ShipType, Integer> fleetSpec;

  @JsonCreator
  public SetupArguments(@JsonProperty("width") int width, @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
    this.width = width;
    this.height = height;
    this.fleetSpec = fleetSpec;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Map<ShipType, Integer> getFleetSpec() {
    return fleetSpec;
  }
}
