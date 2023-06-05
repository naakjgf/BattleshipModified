package cs3500.pa04.view;

import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A class representing the view for the game. Takes in most starting inputs.
 */
public class GameView {

  /**
   * Gets the name of the player from the user.
   *
   * @param scanner the scanner to get input with.
   * @return the name of the player.
   */
  public String getPlayerName(Scanner scanner) {
    System.out.println("What would you like to name your fleet? : ");
    String name = scanner.nextLine();
    if (!name.matches("[a-zA-Z0-9!?@ ]+")) {
      System.out.println("Invalid input. Please enter a valid name.");
      return getPlayerName(scanner);
    }
    return name;
  }

  /**
   * Gets the number of each ship type from the user.
   *
   * @param scanner the scanner to get input with.
   * @param numberOfShips the number of ships the user has.
   * @return a map of the ship types to the number of each ship type.
   */
  public Map<ShipType, Integer> getShipTypes(Scanner scanner, int numberOfShips) {
    HashMap<ShipType, Integer> returnMap = new HashMap<>();
    int constantNumberOfShips = numberOfShips;
    for (ShipType shipType : ShipType.values()) {
      int numShips = 0;
      while (numShips < 1) {
        System.out.println(
            "How many " + shipType + "s would you like? (Length " + shipType.getLength() + ") : ");
        String input = scanner.nextLine();
        try {
          numShips = Integer.parseInt(input);
          if (numShips > numberOfShips) {
            System.out.println("You can't have more ships than the number of ships!");
          } else if (numShips < 1) {
            System.out.println("You must have at least one " + shipType + "!");
          } else {
            returnMap.put(shipType, numShips);
            numberOfShips -= numShips;
          }
        } catch (NumberFormatException e) {
          System.out.println("Please enter a valid number of ships!");
        }
      }
      if (numberOfShips == 0 && shipType.ordinal() < ShipType.values().length - 1) {
        System.out.println(
            "You've reached the maximum number of ships before assigning all types of ships."
                + " Please start over again.");
        returnMap.clear();
        return getShipTypes(scanner, constantNumberOfShips);
      }
    }
    return returnMap;
  }

  /**
   * Gets the number of ships from the user.
   *
   * @param scanner the scanner to get input from.
   * @param boardWidth the width of the board.
   * @param boardHeight the height of the board.
   * @return the number of ships.
   */
  public int getNumberOfShips(Scanner scanner, int boardWidth, int boardHeight) {
    int smallerSide = Math.min(boardWidth, boardHeight);
    System.out.println("How many ships would you like to have? : ");
    while (true) {
      try {
        String input = scanner.nextLine();
        int numShips = Integer.parseInt(input.split(" ")[0]);
        if (numShips > smallerSide || numShips < 4) {
          System.out.println(
              "You can't have less than 4 ships or more ships than the smaller side of the board!");
          continue;
        }
        return numShips;
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number of ships!");
      }
    }
  }

  /**
   * Gets the width of the board from the user.
   *
   * @param scanner the scanner to get input from.
   * @return the width of the board.
   */
  public int getBoardWidth(Scanner scanner) {
    System.out.println("What would you like the width of your board to be? : ");
    while (true) {
      try {
        String input = scanner.nextLine();
        int width = Integer.parseInt(input.split(" ")[0]);
        if (width >= 6 && width <= 15) {
          return width;
        } else {
          System.out.println("Please enter a valid number for the width of your board!"
              + " A valid number is between 6 and 15 INCLUSIVE.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number for the width of your board!");
      }
    }
  }

  /**
   * Gets the height of the board from the user.
   *
   * @param scanner the scanner to get input from.
   * @return the height of the board.
   */
  public int getBoardHeight(Scanner scanner) {
    System.out.println("What would you like the height of your board to be? : ");
    while (true) {
      try {
        String input = scanner.nextLine();
        int height = Integer.parseInt(input.split(" ")[0]);
        if (height >= 6 && height <= 15) {
          return height;
        } else {
          System.out.println("Please enter a valid number for the height of your board!"
              + " A valid number is between 6 and 15 INCLUSIVE.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number for the height of your board!");
      }
    }
  }
}





