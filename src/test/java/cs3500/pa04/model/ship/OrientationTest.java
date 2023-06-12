package cs3500.pa04.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrientationTest {

  @Test
  void toValue() {
    assertEquals("HORIZONTAL", Orientation.HORIZONTAL.toValue());
    assertEquals("VERTICAL", Orientation.VERTICAL.toValue());
  }
}