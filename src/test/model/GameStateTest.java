package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState g1;
    Inventory i1;

    @BeforeEach
    public void runBefore() {
        i1 = new Inventory();
        g1 = new GameState("a", i1);
    }

    @Test
    public void testUpdateScenario() {
        assertEquals("a", g1.getScenario());
        g1.updateScenario("ab");
        assertEquals("ab", g1.getScenario());
    }
}