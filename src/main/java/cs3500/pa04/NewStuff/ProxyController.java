package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProxyController {
  public void facilitateGame(String json) {
    JsonHandler handler = new JsonHandler();
    handler.handleJson(json);
    String response = handler.generateResponseJson();
    System.out.println(response);
  }

  public void sendResponse(String responseJson) {
    System.out.println(responseJson);
  }
}
