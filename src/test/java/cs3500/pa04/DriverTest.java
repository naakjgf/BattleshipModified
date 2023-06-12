package cs3500.pa04;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver class.
 */
class DriverTest {

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

 /* @Test
  void mainTestWithMultipleArgs() {
    String[] args = new String[2];
    args[0] = "0.0.0.0";
    args[1] = "35001";
    Driver.main(args);
  }*/
}