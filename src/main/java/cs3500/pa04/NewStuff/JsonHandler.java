package cs3500.pa04.NewStuff;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class JsonHandler {

  /**
   * Handles request made by the server to get json and returns a request back.
   * @param json - The json object as a string
   * @return json object to be sent back to the server
   * @throws JsonProcessingException
   */
  public JsonNode handleJoinRequest(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode joinRequest = mapper.createObjectNode();

    joinRequest.put("method-name","join");
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("name","shoumik123majumdar");
    arguments.put("game-type","SINGLE");

    joinRequest.put("arguments",arguments);

    return joinRequest;
  }


  public void handleJson(String json) {

  }
  public String generateResponseJson() {
    return "";
  }
}
