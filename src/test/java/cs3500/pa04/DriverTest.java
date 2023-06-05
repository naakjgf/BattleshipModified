package cs3500.pa04;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver class.
 */
class DriverTest {

  //Only testing due to remembering hearing Professor say we don't need to test Driver but I don't
  //want to risk it based on what I "remember" hearing. If it was unnecessary, I apologize. Maybe it
  //could be looked at as an attempt at getting one of the other game results to occur in testing.
  @Test
  void mainTest() {
    String[] args = new String[0];
    String simulatedUserInput =
        "\n6\n6\nJef!\n4\n1\n1\n1\n1\n0\n2\n1\n2\n2\n2\n3\n2\n4\n2\n5\n2\n0\n1\n1\n1\n2\n1\n3\n1"
            + "\n4\n1\n5\n1\n0\n0\n1\n0\n2\n0\n3\n0\n4\n0\n5\n0\n0\n3\n1\n3\n2\n3\n3\n3\n4\n3\n5"
            + "\n3\n0\n5\n1\n5\n2\n5\n3\n5\n4\n5\n5\n5\n\n0\n4\n1\n4\n2\n4\n3\n4\n4\n4\n5\n4\n4\n3"
            + "\n4\n2\n4\n4\n4\n4\n5";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
    System.setIn(testIn);
    Driver.main(args);
  }

}