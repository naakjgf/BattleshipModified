package cs3500.pa04.model.Players;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord.Coord;
import cs3500.pa04.model.Coord.CoordStatus;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipPlacementRandomizer;
import cs3500.pa04.model.Ship.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class representing a human player in the game of battleship.
 */
public class HumanPlayer implements Player {
  private final Board myBoard;
  private final Board opponentBoard;
  private final String name;
  private ArrayList<Coord> currentVolley;
  private int shipCount;
  private ArrayList<Coord> previousVolleys;

  /**
   * sets the current volley of the player based on the given volley.
   *
   * @param currentVolley the volley to set the current volley to.
   */
  public void setCurrentVolley(ArrayList<Coord> currentVolley) {
    this.currentVolley = currentVolley;
  }

  /**
   * Constructs a human player with the given name, height, and width.
   *
   * @param name the name of the player.
   * @param height the height of the board.
   * @param width the width of the board.
   */
  public HumanPlayer(String name, int height, int width) {
    this.name = name;
    this.myBoard = new Board(height, width);
    this.opponentBoard = new Board(height, width);
    this.currentVolley = new ArrayList<>();
  }

  /**
   * Gets the name of this specific player.
   *
   * @return the name of this player.
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Sets up the board for the player based on the given specifications. Placing the ships who's
   * locations are determined by another class randomly.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return a list of ships that were placed on the board.
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    ShipPlacementRandomizer randomizer = new ShipPlacementRandomizer(this.myBoard);
    ArrayList<Ship> ships = randomizer.setupShips(height, width, specifications);
    myBoard.placeShips(ships);
    shipCount = ships.size();
    return ships;
  }

  /**
   * Returns the current volley of shots to take. This is a list of coordinates. Will go to
   * the opponent's reportDamage method.
   *
   * @return the current volley of shots being taken.
   */
  @Override
  public List<Coord> takeShots() {
    return currentVolley;
  }

  /**
   * Reports the damage done to the player's board by the opponent's volley. This is a list of
   * coordinates. Will go to the opponent's successfulHits method.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of coordinates that were hit by the opponent's volley
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hits = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      Coord actualCoord = myBoard.getBoard()[coord.getCoordinateY()][coord.getCoordinateX()];
      myBoard.updatePersonalBoard(actualCoord);
      if (actualCoord.getStatus() == CoordStatus.HIT) {
        hits.add(actualCoord);
        Ship hitShip = myBoard.getShipAt(actualCoord);
        if (hitShip == null) {
          System.out.println("hitShip is null AI");
        }
        /*assert hitShip != null;
        hitShip.updateShipStatus();
        System.out.println(hitShip.getShipStatus() + "Human" + hitShip.getType());*/
        if (hitShip != null && hitShip.isSunk()) {
          shipCount--;
        }
      }
    }
    return hits;
  }

  /**
   * Recieves a report on which shots successfully hit the opponent's ships. This is a list of
   * coordinates. It Will be applied to this player's boards.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    List<Coord> actualHits = new ArrayList<>();
    List<Coord> actualVolley = new ArrayList<>();
    for (Coord c : shotsThatHitOpponentShips) {
      Coord actualCoord = myBoard.getBoard()[c.getCoordinateY()][c.getCoordinateX()];
      currentVolley.remove(c);
      actualHits.add(actualCoord);
    }

    for (Coord c : currentVolley) {
      Coord actualCoord = myBoard.getBoard()[c.getCoordinateY()][c.getCoordinateX()];
      actualVolley.add(actualCoord);
    }

    opponentBoard.updateOpponentBoard(actualHits, CoordStatus.HIT);
    opponentBoard.updateOpponentBoard(actualVolley, CoordStatus.MISS);
  }

  /**
   * Reports the result of the game to the player.
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    String message = switch (result) {
      case PLAYERMANUAL_WINS -> "Congratulations! You've won the game. ";
      case PLAYERAI_WINS -> "Sorry, you've lost. The AI has won the game. ";
      case DRAW -> "The game is a draw. ";
      default -> "The game is still in progress. ";
    };
    System.out.println(message + "Reason: " + reason);
  }

  /**
   * Gets the board of this player.
   *
   * @return the board of this player.
   */
  public Board getMyBoard() {
    return myBoard;
  }

  /**
   * Gets the opponent's known board of this player.
   *
   * @return the opponent's board of this player.
   */
  public Board getOpponentBoard() {
    return opponentBoard;
  }

  /**
   * Gets the count of ships that are still not sunk.
   *
   * @return an integer representing the number of ships that are still not sunk.
   */
  public int getShipCount() {
    return shipCount;
  }

  /**
   * Gets the cumulative list of all previous volleys.
   *
   * @return an ArrayList of Coords which represent all previous volleys.
   */
  public ArrayList<Coord> getPreviousVolleys() {
    return previousVolleys;
  }

  /**
   * Sets the cumulative list of all previous volleys by adding it to the current volley.
   *
   * @param previousVolleys the previous volleys.
   * @param currentVolley  the current volley.
   */
  public void setPreviousVolleys(ArrayList<Coord> previousVolleys, ArrayList<Coord> currentVolley) {
    ArrayList<Coord> temp = new ArrayList<>();
    if (previousVolleys != null) {
      temp.addAll(previousVolleys);
    }
    temp.addAll(currentVolley);
    this.previousVolleys = temp;
  }
}
