package cs3500.pa04.NewStuff;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.util.List;
import java.util.Map;

public class CompetitionAiPlayer implements Player {
  List<Coord> currentVolley;


  public void decideCurrentVolley() {

  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    return null;
  }

  @Override
  public List<Coord> takeShots() {
    return null;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return null;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
