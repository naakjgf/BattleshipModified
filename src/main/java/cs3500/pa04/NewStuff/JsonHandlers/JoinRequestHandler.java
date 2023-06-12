package cs3500.pa04.NewStuff.JsonHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles join requests
 */
public class JoinRequestHandler {

  /**
   * Handles the join request made by the server
   * @return MessageJson object to be sent back to the server
   */
  public MessageJson handleJoinRequest() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();

    arguments.put("name", "shoumik123majumdar"); // adds our github username as the name
    arguments.put("game-type", "SINGLE"); // initializes our gametype to be single

    return new MessageJson("join", arguments);
  }
}

