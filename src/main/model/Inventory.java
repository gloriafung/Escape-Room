package model;

import java.util.ArrayList;
import java.util.List;

// Represents the player's inventory throughout the game
public class Inventory {

    private List<Item> inventory;

    // EFFECTS: constructs an inventory for the user
    public Inventory() {
        inventory = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the player's inventory
    public void addItem(String name) {
        Item item = new Item(name);
        inventory.add(item);
        EventLog.getInstance().logEvent(new Event(item.getName() + " added to inventory."));
    }

    // EFFECTS: returns the number of items in an inventory
    public int getInventoryCount() {
        int initialInventoryCount = 0;
        for (int i = 0; i < inventory.size(); i++) {
            initialInventoryCount++;
        }
        return initialInventoryCount;
    }

    //  EFFECTS: returns item at the given index of list
    public Item getItemFromIndex(int index) {
        return inventory.get(index);
    }

    // MODIFIES: this
    // EFFECTS: clears all items in the inventory
    public void resetInventory() {
        inventory.clear();
        EventLog.getInstance().logEvent(new Event("Inventory reset"));
    }

    // EFFECTS: returns a string of all the names of the items in an inventory
    public String listItems() {
        String inventoryString = "";
        for (int i = 0; i < inventory.size(); i++) {
            inventoryString = inventoryString + inventory.get(i).getName() + " ";
        }
        return inventoryString;
    }

    public List<Item> getInventory() {
        return inventory;
    }

}

