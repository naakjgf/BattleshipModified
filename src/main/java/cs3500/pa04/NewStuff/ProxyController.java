package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.annotation.JsonFormat;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Player;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ProxyController {
  CompetitionAiPlayer aiPlayer;
  private int width;
  private int height;
  JsonHandler handler;
  Scanner thisScanner;
  Socket socket;
  Player player;

  public ProxyController(Scanner scanner, Socket socket, Player player) {
    this.thisScanner = scanner;
    this.socket = socket;
    this.player = player;
  }

  public void facilitateGame(String json) {
    height = 10;
    width = 10;
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
