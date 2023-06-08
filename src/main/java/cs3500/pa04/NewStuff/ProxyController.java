package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.NewStuff.JsonHandlers.JsonHandler;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import cs3500.pa04.model.Board;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ProxyController {
  private CompetitionAiPlayer aiPlayer;
  private int width;
  private int height;
  private JsonHandler handler;
  private Scanner thisScanner;
  private Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private JsonHandler jsonHandler;
  private boolean gameOver;


  public ProxyController(Scanner scanner, Socket socket) {
    this.thisScanner = scanner;
    this.gameOver = false;
    jsonHandler = new JsonHandler(socket, aiPlayer);
    try {
      this.server = socket;
      this.in = server.getInputStream();
      this.out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      throw new IllegalStateException("Error: " + e.getMessage());
    }
    this.server = socket;
  }

  public void facilitateGame() {
    aiPlayer = new CompetitionAiPlayer();
    handler = new JsonHandler(server, aiPlayer);
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!gameOver) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        sendResponse(jsonHandler.delegateMessage(message));
      }
      try {
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      System.out.println("Disconnected from server");
    }
  }

  public void gameIsOver() {
    gameOver = true;
  }

  //Need to make it return to the server
  public void sendResponse(MessageJson responseJson) {
    String methodName = responseJson.messageName();
    if (methodName.equalsIgnoreCase("end-game")) {
      gameIsOver();
    }
    System.out.println(responseJson);
  }
}
