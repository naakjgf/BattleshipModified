package cs3500.pa04.controller;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.DisplayBoard;
import cs3500.pa04.view.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a controller for the BattleSalvo game.
 */
public class Game {
  private final int width;
  private final int height;
  private final Map<ShipType, Integer> specifications;
  private final int numShips;
  private AiPlayer bigBrain;
  private HumanPlayer human;
  private final Scanner scanner;
  private final DisplayBoard displayBoard;
  private GameResult gameResult;

  /**
   * Constructs a Game object, which is the controller for the BattleSalvo game.
   *
   * @param scanner the scanner used to read input from the user.
   */
  public Game(Scanner scanner) {
    this.scanner = scanner;
    this.displayBoard = new DisplayBoard();
    this.gameResult = GameResult.IN_PROGRESS;
    GameView view = new GameView();
    this.width = view.getBoardWidth(scanner);
    this.height = view.getBoardHeight(scanner);
    bigBrain = new AiPlayer(height, width);
    human = new HumanPlayer(view.getPlayerName(scanner), height, width);
    numShips = view.getNumberOfShips(scanner, width, height);
    specifications = view.getShipTypes(scanner, numShips);
    startGame();
  }

  /**
   * Starts an instance of the game, playing through turns until the BattleSalvo game is over.
   */
  private void startGame() {
    bigBrain.setup(height, width, specifications);
    human.setup(height, width, specifications);
    while (gameResult == GameResult.IN_PROGRESS) {
      playTurn();
      gameResult = checkGameStatus();
      if (gameResult != GameResult.IN_PROGRESS) {
        String reason = switch (gameResult) {
          case PLAYERMANUAL_WINS, PLAYERAI_WINS -> "Game is over. ";
          case DRAW -> "The game is a draw. ";
          default -> "Seems to have reached an invalid game state.";
        };
        //bigBrain.endGame(gameResult, reason);
        human.endGame(gameResult, reason);
      }
    }
  }

  /**
   * Plays through a turn of the game. A helper method for startGame.
   */
  private void playTurn() {
    /*displayBoard.displayBoard(bigBrain.getOpponentBoard(), human.name());
    displayBoard.displayBoard(bigBrain.getMyBoard(), bigBrain.name());*/
    System.out.println("Key: \n" + "W = Water \n" + "S = Ship \n" + "H = Hit \n" + "M = Miss \n");
    displayBoard.displayBoard(human.getOpponentBoard(), bigBrain.name());
    displayBoard.displayBoard(human.getMyBoard(), human.name());
    ArrayList<Coord> previousVolleys = human.getPreviousVolleys();
    ArrayList<Coord> humanVolley =
        displayBoard.promptForAttack(human.getOpponentBoard(), human.name(), human.getShipCount(),
            scanner, human.getOpponentBoard().getTilesLeft(), previousVolleys);
    human.setCurrentVolley(humanVolley);
    human.setPreviousVolleys(previousVolleys, humanVolley);
    bigBrain.decideCurrentVolley(height, width);
    List<Coord> shotsThatHitHumanShips = human.reportDamage(bigBrain.takeShots());
    List<Coord> shotsThatHitAiShips = bigBrain.reportDamage(human.takeShots());
    bigBrain.successfulHits(shotsThatHitHumanShips);
    human.successfulHits(shotsThatHitAiShips);
    displayBoard.displayTurnResults(shotsThatHitAiShips, shotsThatHitHumanShips);

  }

  /**
   * Checks the game status to see if the game is over or not.
   *
   * @return the game result.
   */
  private GameResult checkGameStatus() {
    if (human.getShipCount() == 0 && bigBrain.getShipCount() == 0) {
      return GameResult.DRAW;
    } else if (human.getShipCount() == 0) {
      return GameResult.PLAYERAI_WINS;
    } else if (bigBrain.getShipCount() == 0) {
      return GameResult.PLAYERMANUAL_WINS;
    } else {
      return GameResult.IN_PROGRESS;
    }
  }
}

