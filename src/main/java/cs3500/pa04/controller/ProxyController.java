package cs3500.pa04.controller;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import cs3500.pa04.model.jsonhandlers.JsonHandler;
import cs3500.pa04.model.jsonhandlers.MessageJson;
import cs3500.pa04.model.players.CompetitionAiPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Controller that handles input from a server
 */
public class ProxyController {
  private final InputStream in;
  private final ObjectMapper mapper = new ObjectMapper();
  private boolean gameOver;
  private OutputStream outputStream;
  private OutputStreamWriter writer;
  private CompetitionAiPlayer aiPlayer;
  private JsonHandler handler;
  private Socket server;
  private JsonHandler jsonHandler;

  /**
   * Constructs a ProxyController with the given socket.
   *
   * @param socket the socket to be used
   */
  public ProxyController(Socket socket) {
    this.aiPlayer = new CompetitionAiPlayer("Admiral Ackbar (Resident AI StarWars Fanboy)");
    this.gameOver = false;
    jsonHandler = new JsonHandler(socket, aiPlayer);
    try {
      this.server = socket;
      this.in = server.getInputStream();
      outputStream = server.getOutputStream();
      writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException("Error: " + e.getMessage());
    }
  }

  /**
   * Facilitates a game of BattleSalvo using an AIPlayer against an player from the given server.
   */
  public void facilitateGame() {
    handler = new JsonHandler(server, aiPlayer);
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!gameOver) {
        try {
          MessageJson message = parser.readValueAs(MessageJson.class);
          sendResponse(jsonHandler.delegateMessage(message));
        } catch (MismatchedInputException e) {
          System.out.println("Waiting for data...");
          sleep(500);
        }
      }
      try {
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      System.out.println("Disconnected from server");
    }
  }

  /**
   * Sends a response to the server.
   *
   * @param responseJson the response to be sent
   */
  public void sendResponse(MessageJson responseJson) {
    String methodName = responseJson.messageName();
    if (methodName.equalsIgnoreCase("end-game")) {
      gameOver = true;
    }
    // Send the JSON string to the server
    try {
      String jsonString = this.mapper.writeValueAsString(responseJson);
      System.out.println(jsonString);
      writer.write(jsonString);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns whether the game is over.
   *
   * @return whether the game is over, true or false.
   */
  public boolean isGameOver() {
    return gameOver;
  }
}
