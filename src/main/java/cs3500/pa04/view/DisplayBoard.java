package cs3500.pa04.view;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A class which displays the board primary actions involving it to the user.
 */
public class DisplayBoard {

  /**
   * Displays the results of the turn to the user.
   *
   * @param opponentHits the list of hits that landed on the opponent's board.
   * @param playerHits the list of hits that landed on the player's board.
   */
  public void displayTurnResults(List<Coord> opponentHits, List<Coord> playerHits) {
    if (opponentHits.size() != 0) {
      System.out.println("You hit your opponent's ship at: ");
      for (Coord coord : opponentHits) {
        System.out.println(coord.toString());
      }
    } else {
      System.out.println("You Missed All Your Shots!! \n");
    }
    if (playerHits.size() != 0) {
      System.out.println("Opponent hit your ship at: ");
      for (Coord coord : playerHits) {
        System.out.println(coord.toString());
      }
    } else {
      System.out.println("Opponent Missed Their Volley!! \n");
    }
  }

  /**
   * Displays the board to the user.
   *
   * @param board the board to display coming user.
   * @param playerName the name of the player whose board is being displayed.
   */
  public void displayBoard(Board board, String playerName) {
    Coord[][] thisBoard = board.getBoard();
    System.out.println(playerName + "'s known board:"
        + " \n _______________________________________________");
    for (Coord[] coords : thisBoard) {
      for (Coord coord : coords) {
        String letterRepresentation = switch (coord.getStatus()) {
          case WATER -> "W";
          case SHIP -> "S";
          case HIT -> "H";
          case MISS -> "M";
        };
        System.out.print(letterRepresentation + " ");
      }
      System.out.println();
    }
    System.out.println("_______________________________________________");
  }

  /**
   * Gets int inputs from the user. Helper of promptForAttack.
   *
   * @param scanner the scanner to get input with.
   * @param message the message to display to the user.
   * @return the int that the user entered.
   */
  private int getIntInput(Scanner scanner, String message) {
    int value;
    do {
      System.out.println(message);
      while (!scanner.hasNextInt()) {
        System.out.println("Invalid input! Please enter a valid number.");
        scanner.next();
        scanner.nextLine();
      }
      value = scanner.nextInt();
      scanner.nextLine();
    } while (value < 0);
    return value;
  }

  /**
   * Gets coordinate inputs from the user. Helper of promptForAttack.
   *
   * @param scanner the scanner to get input with.
   * @param boardWidth the width of the board.
   * @param boardHeight the height of the board.
   * @param previousShots the previous shots that have been taken.
   * @return the coordinate that the user entered.
   */
  private Coord getCoordinate(Scanner scanner, int boardWidth, int boardHeight,
                              ArrayList<Coord> previousShots) {
    Coord shot;
    do {
      int x = getIntInput(scanner, "Enter the x coordinate: ");
      int y = getIntInput(scanner, "Enter the y coordinate: ");

      shot = new Coord(y, x, CoordStatus.WATER);

      if (!checkIfShotValid(shot, boardWidth, boardHeight, previousShots)) {
        shot = null;
      } else {
        System.out.println("Attack coordinates received! \n Next entry: ");
      }
    } while (shot == null);
    return shot;
  }

  /**
   * Checks if the shot is valid. Helper of promptForAttack.
   *
   * @param shot the shot to check.
   * @param boardWidth the width of the board.
   * @param boardHeight the height of the board.
   * @param previousShots the previous shots that have been taken.
   * @return true if the shot is valid, false otherwise.
   */
  private boolean checkIfShotValid(Coord shot, int boardWidth, int boardHeight,
                                   ArrayList<Coord> previousShots) {
    int x = shot.getCoordinateX();
    int y = shot.getCoordinateY();

    if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
      System.out.println("Invalid coordinates. Please try again.");
      return false;
    } else if (previousShots != null && previousShots.contains(shot)) {
      System.out.println("You've already targeted these coordinates. Please try again.");
      return false;
    }

    return true;
  }

  /**
   * Prompts the user for a coordinate to attack.
   *
   * @param board         the board to attack
   * @param playerName    the name of the player who is attacking.
   * @param numShips      the number of ships the player has left to attack with.
   * @param scanner       the scanner to read input with.
   * @param tilesLeft     the number of tiles left to attack.
   * @param previousShots the previous shots that have been taken.
   * @return the coordinates to attack.
   */
  public ArrayList<Coord> promptForAttack(Board board, String playerName, Integer numShips,
                                          Scanner scanner, int tilesLeft,
                                          ArrayList<Coord> previousShots) {
    Coord[][] thisBoard = board.getBoard();
    int boardWidth = thisBoard[0].length;
    int boardHeight = thisBoard.length;
    ArrayList<Coord> attackCoords = new ArrayList<>(numShips);

    System.out.println(playerName + ", we are launching another attack! You have " + numShips
        + " shots.\nPlease enter the coordinates of your attack: ");

    for (int i = 0; i < Math.min(numShips, tilesLeft); i++) {
      try {
        Coord shot = getCoordinate(scanner, boardWidth, boardHeight, previousShots);
        attackCoords.add(shot);
      } catch (InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid number.");
        scanner.nextLine();
        i--;
      }
    }
    return attackCoords;
  }
}