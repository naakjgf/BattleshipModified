package cs3500.pa04;

import cs3500.pa04.NewStuff.ProxyController;
import cs3500.pa04.controller.Game;
import java.io.IOException;
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
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);
    if (args.length == 0) {
      System.out.println("Welcome to the WORLD OF BATTLESALVO!!!");
      Game newBattleShipGame = new Game(scanner);
    } else {
      try {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        ProxyController newRemoteBattleShipGame = new ProxyController(socket);
        newRemoteBattleShipGame.facilitateGame();
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
}