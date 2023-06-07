package cs3500.pa04.NewStuff.SetupHandling;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetupResponse {
  @JsonProperty("method-name")
  private String methodName;

  @JsonProperty("arguments")
  private FleetArguments arguments;

  public SetupResponse(String methodName, FleetArguments arguments) {
    this.methodName = methodName;
    this.arguments = arguments;
  }

  public String getMethodName() {
    return methodName;
  }

  public FleetArguments getFleetSpec() {
    return arguments;
  }
}
