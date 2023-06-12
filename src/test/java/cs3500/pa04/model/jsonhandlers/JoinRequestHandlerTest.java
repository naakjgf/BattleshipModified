package cs3500.pa04.model.jsonhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

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
