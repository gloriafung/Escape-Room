package persistence;

import model.GameState;
import model.Inventory;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFIle() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameState gs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderInitialGameState() {
        JsonReader reader = new JsonReader("./data/testReaderInitialGameState.json");
        try {
            GameState gs = reader.read();
            assertEquals("a", gs.getScenario());
            assertEquals(0, gs.getInventory().getInventoryCount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameState() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameState.json");
        try {
            GameState gs = reader.read();
            assertEquals("aaa", gs.getScenario());
            Inventory inventory = gs.getInventory();
            checkItem("Book", inventory.getItemFromIndex(0));
            checkItem("Hammer", inventory.getItemFromIndex(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
