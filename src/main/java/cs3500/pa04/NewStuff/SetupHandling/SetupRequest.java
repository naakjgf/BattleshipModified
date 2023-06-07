package cs3500.pa04.NewStuff.SetupHandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SetupRequest {
  @JsonProperty("method-name")
  private String methodName;

  @JsonProperty("arguments")
  private SetupArguments arguments;

  @JsonCreator
  public SetupRequest(@JsonProperty("method-name") String methodName,
                      @JsonProperty("arguments") SetupArguments arguments) {
    this.methodName = methodName;
    this.arguments = arguments;
  }

  public SetupArguments getArguments() {
    return arguments;
  }
}

