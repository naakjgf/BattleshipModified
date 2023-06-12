package cs3500.pa04.model.players;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.coord.Coord;
import cs3500.pa04.model.coord.CoordStatus;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipPlacementRandomizer;
import cs3500.pa04.model.ship.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an AI player in the game of Battleship.
 */
public class AiPlayer implements Player {
  private Board myBoard;
  private Board opponentBoard;
  private final String name;
  private ArrayList<Coord> previousVolleys;
  private ArrayList<Coord> currentVolley;
  private int shipCount;

  /**
   * Constructs an AI player with the given height and width.
   *
   * @param height the height of the board
   * @param width  the width of the board
   */
  public AiPlayer(int height, int width) {
    this.name = "Admiral Ackbar (Resident AI StarWars Fanboy)";
    this.myBoard = new Board(height, width);
    this.opponentBoard = new Board(height, width);
    this.previousVolleys = new ArrayList<>();
    this.currentVolley = new ArrayList<>();
  }

  /**
   * Decides the current volley of shots to take through random number generation.
   *
   * @param boardHeight the height of the board to shoot at.
   * @param boardWidth the width of the board to shoot at.
   */
  public void decideCurrentVolley(int boardHeight, int boardWidth) {
    this.currentVolley = new ArrayList<>();
    int tilesLeft = opponentBoard.getTilesLeft();

    while (this.currentVolley.size() < Math.min(shipCount, tilesLeft)) {
      int x = (int) (Math.random() * boardWidth);
      int y = (int) (Math.random() * boardHeight);
      Coord newShot = new Coord(y, x, CoordStatus.WATER);

      boolean alreadyFiredInVolley = this.currentVolley.contains(newShot);
      boolean alreadyFiredPreviously = this.previousVolleys.contains(newShot);

      if (!alreadyFiredInVolley && !alreadyFiredPreviously) {
        this.currentVolley.add(newShot);
      }
    }
    this.previousVolleys.addAll(this.currentVolley);
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
        /*assert hitShip != null;
        hitShip.updateShipStatus();
        System.out.println(hitShip.getShipStatus() + "AI" + hitShip.getType());*/
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
   * Gets the number of ships left on the board.
   *
   * @return an int representing the number of ships left on the board.
   */
  public int getShipCount() {
    return shipCount;
  }

  /**
   * Gets the AI's board.
   *
   * @return the AI's board.
   */
  public Board getMyBoard() {
    return myBoard;
  }

  /**
   * Gets the opponent's board.
   *
   * @return the opponent's board.
   */
  public Board getOpponentBoard() {
    return opponentBoard;
  }
}

