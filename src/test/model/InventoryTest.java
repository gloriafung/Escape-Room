package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory i1;
    Inventory i2;
    Inventory i3;
    Inventory i4;
    Inventory i5;

    @BeforeEach
    public void runBefore() {
        i1 = new Inventory();
        i2 = new Inventory();
        i3 = new Inventory();
        i4 = new Inventory();
        i5 = new Inventory();
    }

    @Test
    public void testAddItemOne() {
        i1.addItem("Book");
        assertEquals(1, i1.getInventoryCount());
        assertEquals("Book ", i1.listItems());
    }

    @Test
    public void testAddItemMultiple() {
        i2.addItem("Phone");
        i2.addItem("Map");
        i2.addItem("Hammer");
        assertEquals(3, i2.getInventoryCount());
        assertEquals("Phone Map Hammer ", i2.listItems());
    }

    @Test
    public void testAddItemNone() {
        assertEquals(0, i3.getInventoryCount());
        assertEquals("", i3.listItems());
    }

    @Test
    public void testResetInventoryNone() {
        i4.resetInventory();
        assertEquals(0, i4.getInventoryCount());
        assertEquals("", i4.listItems());
    }

    @Test
    public void testResetInventorySome() {
        i5.addItem("Phone");
        i5.addItem("Map");
        i5.addItem("Hammer");
        i5.resetInventory();
        assertEquals(0, i5.getInventoryCount());
        assertEquals("", i5.listItems());
    }

    @Test
    public void testGetItemFromIndexNone() {
        try {
            i1.getItemFromIndex(0);
        } catch (IndexOutOfBoundsException e) {
            //pass
        }
    }

    @Test
    public void testGetItemFromIndexOne() {
        i1.addItem("Phone");
        assertEquals("Phone", i1.getItemFromIndex(0).getName());
    }

    @Test
    public void testGetItemFromIndexMultiple() {
        i2.addItem("Phone");
        i2.addItem("Map");
        i2.addItem("Hammer");
        assertEquals("Phone", i2.getItemFromIndex(0).getName());
        assertEquals("Map", i2.getItemFromIndex(1).getName());
        assertEquals("Hammer", i2.getItemFromIndex(2).getName());
    }

}