package cs3500.pa04.NewStuff;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

  @Test
  void testGameIsOver() {
    // Create a mock Socket
    Socket mockSocket = new Socket();
    try
    {
      mockSocket = new Socket("0.0.0.0",35001);
    }
    catch (UnknownHostException e)
    {

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Create a ProxyController instance with the mock Socket
    ProxyController testController = new ProxyController(mockSocket);

    // Call the gameIsOver method
    testController.gameIsOver();

    // Check that the gameOver flag is set to true
    assertTrue(testController.gameOver);
  }

}