package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.annotation.JsonFormat;
import cs3500.pa04.NewStuff.SetupHandling.SetupArguments;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Player;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ProxyController {
  private CompetitionAiPlayer aiPlayer;
  private int width;
  private int height;
  private JsonHandler handler;
  private Scanner thisScanner;
  private Socket socket;


  public ProxyController(Scanner scanner, Socket socket) {
    this.thisScanner = scanner;
    this.socket = socket;
  }

  public void facilitateGame(String json) {
    Board board = aiPlayer.getMyBoard();
    height = board.getBoard().length;
    width = board.getBoard()[0].length;
    aiPlayer = new CompetitionAiPlayer(height, width);
    handler = new JsonHandler();
    handler.handleJson(json);
    String response = handler.generateResponseJson();
    System.out.println(response);
  }

  public void sendResponse(String responseJson) {
    System.out.println(responseJson);
  }
}
