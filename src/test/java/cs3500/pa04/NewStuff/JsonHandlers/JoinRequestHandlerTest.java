package cs3500.pa04.NewStuff.JsonHandlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.NewStuff.JsonHandlers.JoinRequestHandler;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoinRequestHandlerTest {

  @Test
  void handleJoinRequestTest() {
    JoinRequestHandler joinRequestHandler = new JoinRequestHandler();
    MessageJson result = joinRequestHandler.handleJoinRequest();

    assertEquals("join", result.messageName());
    ObjectMapper mapper = new ObjectMapper();
    JsonNode expectedArguments = mapper.createObjectNode()
        .put("name", "shoumik123majumdar")
        .put("game-type", "SINGLE");

    assertEquals(expectedArguments, result.arguments());
  }
}
