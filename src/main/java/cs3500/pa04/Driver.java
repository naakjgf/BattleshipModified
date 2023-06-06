package cs3500.pa04;

import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.ProxyController;
import cs3500.pa04.controller.Game;
import cs3500.pa04.model.Player;
import java.net.Proxy;
import java.net.Socket;
import java.util.Scanner;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the WORLD OF BATTLESALVO!!!");
    Game newBattleShipGame = new Game(scanner);
    Socket socket = new Socket();
    Player player = new CompetitionAiPlayer(10, 10);
    //will make it run this bottom option if args are passed in
    ProxyController newRemoteBattleShipGame = new ProxyController(scanner, socket, player);
    newRemoteBattleShipGame.facilitateGame("");
  }
}