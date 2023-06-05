package cs3500.pa04;

import cs3500.pa04.controller.Game;
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
  }
}