package cs3500.pa04.NewStuff.JsonHandlers.SetupHandling;

import cs3500.pa04.NewStuff.CompetitionAiPlayer;
import cs3500.pa04.NewStuff.JsonHandlers.MessageJson;
import cs3500.pa04.model.Ship.Ship;
import cs3500.pa04.model.Ship.ShipInformation;
import cs3500.pa04.model.Ship.ShipType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyMap;

class SetupHandlerTest {
  private SetupHandler setupHandler;
  private CompetitionAiPlayer competitionAiPlayer;
  private ObjectMapper objectMapper;
  private MessageJson incomingMessageJson;
  private Map<ShipType, Integer> fleetSpec;
  private List<Ship> ships;
  private Ship ship;
  private ShipInformation shipInformation;

  @BeforeEach
  void setUp() {
    competitionAiPlayer = Mockito.mock(CompetitionAiPlayer.class);
    objectMapper = new ObjectMapper();
    setupHandler = new SetupHandler(competitionAiPlayer);

    fleetSpec = new HashMap<>();

    ShipType shipType = ShipType.CARRIER;
    fleetSpec.put(shipType, 1);
    incomingMessageJson = Mockito.mock(MessageJson.class);

    ObjectNode arguments = objectMapper.createObjectNode();
    arguments.put("width", 10);
    arguments.put("height", 10);
    arguments.set("fleet-spec", objectMapper.valueToTree(fleetSpec));
    when(incomingMessageJson.arguments()).thenReturn(arguments);

    ship = Mockito.mock(Ship.class);
    shipInformation = Mockito.mock(ShipInformation.class);
    ships = List.of(ship);
  }

  @Test
  void handleSetupTestNormalInput() {
    try {
      when(competitionAiPlayer.setup(anyInt(), anyInt(), anyMap())).thenReturn(ships);
      when(ship.toShipInformation()).thenReturn(shipInformation);

      MessageJson result = setupHandler.handleSetup(incomingMessageJson);

      assertNotNull(result);
      assertEquals("setup", result.messageName());
      ObjectNode resultArguments =
          objectMapper.valueToTree(new FleetArguments(List.of(shipInformation)));
      assertEquals(resultArguments, result.arguments());

    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception should not be thrown.");
    }
  }

  @Test
  void handleSetupTestEmptyReturn() {
    try {
      when(competitionAiPlayer.setup(anyInt(), anyInt(), anyMap())).thenReturn(List.of());

      MessageJson result = setupHandler.handleSetup(incomingMessageJson);

      assertNotNull(result);
      assertEquals("setup", result.messageName());
      ObjectNode resultArguments = objectMapper.valueToTree(new FleetArguments(List.of()));
      assertEquals(resultArguments, result.arguments());
    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception should not be thrown.");
    }
  }

  @Test
  void handleSetupTestException() {
    MessageJson incomingMessageJson2 = new MessageJson("setup", null);
    assertThrows(RuntimeException.class, () -> setupHandler.handleSetup(incomingMessageJson2));
  }
}
