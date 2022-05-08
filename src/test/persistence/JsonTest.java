package persistence;

import model.GameState;
import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, Item item) {
        assertEquals(name, item.getName());
    }

    protected void checkScenario(String scenario, GameState gameState) {
        assertEquals(scenario, gameState.getScenario());
    }
}
