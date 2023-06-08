package cs3500.pa04.NewStuff.JsonHandlers;

public enum JsonMethodNames {
  JOIN("join"),
  SETUP("setup"),
  TAKE_SHOTS("take-shots"),
  REPORT_DAMAGE("report-damage"),
  SUCCESSFUL_HITS("successful-hits"),
  END_GAME("end-game");

  private final String name;

  JsonMethodNames(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
