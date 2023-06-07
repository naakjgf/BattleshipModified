package cs3500.pa04.NewStuff.SetupHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.model.Ship;
import java.io.IOException;
import java.util.List;

public class SetupHandler {
  private final ObjectMapper objectMapper;
  private final CompetitionAiPlayer competitionAiPlayer;

  public SetupHandler(CompetitionAiPlayer competitionAiPlayer) {
    this.objectMapper = new ObjectMapper();
    this.competitionAiPlayer = competitionAiPlayer;
  }

  public String handleSetup(String incomingJson) throws IOException {
    SetupRequest setupRequest = objectMapper.readValue(incomingJson, SetupRequest.class);

    List<Ship> ships = competitionAiPlayer.setup(setupRequest.getArguments().getHeight(),
        setupRequest.getArguments().getWidth(), setupRequest.getArguments().getFleetSpec());

    SetupResponse setupResponse = new SetupResponse("setup", new FleetArguments(ships));
    return objectMapper.writeValueAsString(setupResponse);
  }
}

