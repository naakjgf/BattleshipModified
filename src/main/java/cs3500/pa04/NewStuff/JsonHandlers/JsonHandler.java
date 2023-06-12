package cs3500.pa04.NewStuff.JsonHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.JsonHandlers.SetupHandling.SetupHandler;
import java.io.IOException;
import java.net.Socket;

/**
 * Helps handle Json
 */
public class JsonHandler {
  private final Socket socket;
  private final CompetitionAiPlayer player;

  public JsonHandler(Socket socket, CompetitionAiPlayer player) {
    this.socket = socket;
    this.player = player;
  }

  /**
   * Identifies what the message from the server means and sends it to helper classes to be
   * handled accordingly
   * @param message message from the server
   * @return MessageJson object to be sent back to the server
   */
  public MessageJson delegateMessage(MessageJson message) {
    MessageJson returnMessage = null;
    String name = message.messageName();
    switch (name) {
      case "join":
        JoinRequestHandler joinRequestHandler = new JoinRequestHandler();
        returnMessage = joinRequestHandler.handleJoinRequest();
        return returnMessage;
      case "setup":
        SetupHandler setupHandler = new SetupHandler(player);
        returnMessage = setupHandler.handleSetup(message);
        return returnMessage;
      case "take-shots":
        returnMessage = player.handleTakeShotsRequest(message);
        return returnMessage;
      case "report-damage":
        returnMessage = player.handleReportDamageRequest(message);
        return returnMessage;
      case "successful-hits":
        returnMessage = player.handleSuccessfulHitsRequest(message);
        return returnMessage;
      case "end-game":
        returnMessage = player.handleEndGameRequest(message);
        return returnMessage;
      default:
        throw new IllegalArgumentException("Invalid message name");
    }
  }
}
