package cs3500.pa04.model.ship;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.coord.Coord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A class representing a randomizer for ship placement.
 */
public class ShipPlacementRandomizer {
  private final Board board;
  private HashMap<Ship, ArrayList<Coord>> shipPlacement;

  /**
   * Constructs a ShipPlacementRandomizer.
   *
   * @param board the board to place ships on.
   */
  public ShipPlacementRandomizer(Board board) {
    this.board = board;
  }

  /**
   * Randomly generates ships of the given types which are contained in the given specifications.
   * Makes use of random number generation and backtracking to ensure that ships are placed in
   * unique locations with no overlap.
   *
   * @param height the height of the board.
   * @param width the width of the board.
   * @param specifications a map of ship type to the number of occurrences each ship should have.
   * @return a list of ships that should be placed on the board.
   */
  public ArrayList<Ship> setupShips(int height, int width, Map<ShipType, Integer> specifications) {
    ArrayList<Ship> ships = new ArrayList<>();
    shipPlacement = new HashMap<>();

    int totalNumberOfShips = specifications.values().stream().reduce(0, Integer::sum);
    int maxAttemptsPerShip = 100;

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        int attempts = 0;
        while (attempts < maxAttemptsPerShip * (totalNumberOfShips - ships.size() + 1)) {
          Ship newShip =
              generateRandomShip(entry.getKey(), width, height);

          ArrayList<Coord> prospectiveLocations = newShip.getLocation();
          if (validLocation(prospectiveLocations)) {
            shipPlacement.put(newShip, prospectiveLocations);
            ships.add(newShip);
            break;
          }
          attempts++;
        }

        if (attempts == maxAttemptsPerShip * (totalNumberOfShips - ships.size() + 1)) {
          System.out.println(
              "Restarting ship placement due to difficulty finding valid positions...");
          shipPlacement.clear();
          ships.clear();
          i = -1;
        }
      }
    }

    return ships;
  }

  /**
   * Generates a random ship of the given type. Helper function that provides the randomness for
   * setupShips.
   *
   * @param shipType the type of ship to generate.
   * @param boardWidth the width of the board.
   * @param boardHeight the height of the board.
   * @return a ship of the given type with a random location and orientation.
   */
  private Ship generateRandomShip(ShipType shipType, int boardWidth, int boardHeight) {
    Random random = new Random();
    int length = shipType.getLength();

    boolean isVertical = random.nextBoolean();

    int x;
    int y;

    if (isVertical) {
      x = random.nextInt(boardWidth);
      y = random.nextInt(boardHeight - length + 1);
    } else {
      x = random.nextInt(boardWidth - length + 1);
      y = random.nextInt(boardHeight);
    }

    Coord startCoord = board.getCoord(y, x);
    Orientation orientation = isVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL;

    return new Ship(shipType, startCoord, orientation, board);
  }

  /**
   * Determines whether the given list of coordinates is a valid location for a ship. Helper
   * function that validates the location of a ship to see if it is already populated
   * for setupShips.
   *
   * @param prospectiveLocations the list of coordinates to check.
   * @return true if the given list of coordinates is a valid location for a ship, false otherwise.
   */
  private boolean validLocation(ArrayList<Coord> prospectiveLocations) {
    for (ArrayList<Coord> existingCoords : shipPlacement.values()) {
      for (Coord prospectiveCoord : prospectiveLocations) {
        if (existingCoords.contains(prospectiveCoord)) {
          return false;
        }
      }
    }
    return true;
  }
}
