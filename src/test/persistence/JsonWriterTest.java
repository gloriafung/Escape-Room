package persistence;

import model.GameState;
import model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    Inventory inventory;

    @BeforeEach
    public void runBefore() {
        inventory = new Inventory();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            GameState gs = new GameState("a", inventory);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterInitialGameState() {
        try {
            GameState gs = new GameState("a", inventory);
            JsonWriter writer = new JsonWriter("./data/testWriterInitialGameState.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGameState.json");
            gs = reader.read();
            assertEquals("a", gs.getScenario());
            assertEquals(0, gs.getInventory().getInventoryCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGameState() {
        try {
            GameState gs = new GameState("aba",inventory);
            inventory.addItem("Hairpin Front Piece");
            inventory.addItem("Hairpin Back Piece");
            inventory.addItem("Map");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameState.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameState.json");
            gs = reader.read();
            assertEquals("aba", gs.getScenario());
            Inventory inventory = gs.getInventory();
            assertEquals(3, inventory.getInventoryCount());
            checkItem("Hairpin Front Piece", inventory.getItemFromIndex(0));
            checkItem("Hairpin Back Piece", inventory.getItemFromIndex(1));
            checkItem("Map", inventory.getItemFromIndex(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
