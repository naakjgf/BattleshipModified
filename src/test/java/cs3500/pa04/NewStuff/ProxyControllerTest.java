package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {
  @Test
  void facilitateGame() throws IOException, InterruptedException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream(2048);

    // Create messages to be sent by the server
    ObjectMapper mapper = new ObjectMapper();
    //We need to add stuff to this object node in order for it to work; I don't have time
    //to do it right now though, it is essentially just adding a basic end-game response.
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("result", "win");
    arguments.put("reason", "You sank all of the opposing ships!");
    MessageJson mockResponse = new MessageJson("end-game", arguments);
    String mockResponseString = mapper.writeValueAsString(mockResponse);
    ArrayList<String> serverMessages = new ArrayList<>();
    serverMessages.add(mockResponseString);

    // Create the Mocket
    Mocket mocket = new Mocket(testLog, serverMessages);

    // Create a client and connect to the server
    ProxyController proxyController = new ProxyController(new Scanner(System.in), mocket);

    // Call the method that communicates with the server
    proxyController.facilitateGame();
  }
}