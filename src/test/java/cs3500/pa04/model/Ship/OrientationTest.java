package cs3500.pa04.model.Ship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrientationTest {

  @Test
  void toValue() {
    assertEquals("HORIZONTAL", Orientation.HORIZONTAL.toValue());
    assertEquals("VERTICAL", Orientation.VERTICAL.toValue());
  }
}