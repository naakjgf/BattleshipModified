package cs3500.pa04.NewStuff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    ProxyController proxyController = new ProxyController(mocket);

    // Call the method that communicates with the server
    proxyController.facilitateGame();
  }

  @Test
  void testSendResponse() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream(2048);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode arguments = mapper.createObjectNode();
    MessageJson mockResponse = new MessageJson("take-shots",arguments);
    String mockResponseString = mapper.writeValueAsString(mockResponse);
    ArrayList<String> serverMessages = new ArrayList<>();
    serverMessages.add(mockResponseString);

    Mocket mocket = new Mocket(testLog,serverMessages);


    ProxyController testController = new ProxyController(mocket);
    assertEquals(testController.gameOver,false);
    testController.sendResponse(mockResponse);
  }

  @Test
  void testSendResponseEndGame() throws IOException {
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
    ProxyController testController = new ProxyController(mocket);

    assertEquals(testController.gameOver,false);
    testController.sendResponse(mockResponse);
    assertEquals(testController.gameOver,true);
  }
}