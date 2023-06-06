package cs3500.pa04.NewStuff;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.model.Coord;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHandler {

  /**
   * Handles request made by the server to get json and returns a request back.
   * @param json - The json object as a string
   * @return json object to be sent back to the server
   * @throws JsonProcessingException
   */
  public ObjectNode handleJoinRequest(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode joinRequest = mapper.createObjectNode(); // the main json node that will eventually be returned.

    joinRequest.put("method-name","join"); // adds the method-name field in the Json object

    ObjectNode arguments = mapper.createObjectNode(); // adds another nested layer to the Json object for the arguments
    arguments.put("name","shoumik123majumdar"); // adds our github username as the name
    arguments.put("game-type","SINGLE"); // initializes our gametype to be single

    joinRequest.set("arguments",arguments);

    return joinRequest;
  }

  /**
   * Converts a coordinate into a Json Object
   * @param coord coordinate to be converted
   * @return coordinate as a Json Object
   */
  public ObjectNode createCoordJson(Coord coord){
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode coordinate = mapper.createObjectNode();

    String xCoordinate = String.valueOf(coord.getCoordinateX());
    String yCoordinate = String.valueOf(coord.getCoordinateY());

    coordinate.put("x",xCoordinate);
    coordinate.put("y",yCoordinate);

    return coordinate;
  }

  /**
   * Creates an ArrayNode object to represent a volley of shots
   * @param shotVolley the List of all of the volley's coordinates.
   * @returns an ArrayNode object
   */
  public ArrayNode createVolleyJson(ArrayList<Coord> shotVolley){
    ObjectMapper mapper = new ObjectMapper();
    JsonNodeFactory jsonNodeFactory = mapper.getNodeFactory();

    ArrayNode volleyArrayNode = jsonNodeFactory.arrayNode();

    for(Coord coord: shotVolley)
    {
      ObjectNode coordinate = createCoordJson(coord);
      volleyArrayNode.add(coordinate);
    }
    return volleyArrayNode;
  }



  public void handleJson(String json) {

  }
  public String generateResponseJson() {
    return "";
  }
}
