package cs3500.pa04.NewStuff;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import cs3500.pa04.NewStuff.JsonHandlers.JsonHandler;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ProxyController {
  private CompetitionAiPlayer aiPlayer;
  private JsonHandler handler;
  private Scanner thisScanner;
  private Socket server;
  private final InputStream in;
  OutputStream outputStream;
  private final ObjectMapper mapper = new ObjectMapper();
  private JsonHandler jsonHandler;
  private boolean gameOver;
  OutputStreamWriter writer;


  public ProxyController(Scanner scanner, Socket socket) {
    this.aiPlayer = new CompetitionAiPlayer("Admiral Ackbar (Resident AI StarWars Fanboy)");
    this.thisScanner = scanner;
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
    this.server = socket;
  }

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
          sleep(1000);
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

  public void gameIsOver() {
    gameOver = true;
  }

  public void sendResponse(MessageJson responseJson) {
    System.out.println("it reached sendResponse");
    String methodName = responseJson.messageName();
    if (methodName.equalsIgnoreCase("end-game")) {
      gameIsOver();
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
    System.out.println(responseJson);
  }
}
