package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents the current state of the game with inventory and scenario the user is on
public class GameState implements Writable {
    private String scenario;
    private Inventory inventory;

    //EFFECTS: constructs the game at the point of the given scenario and inventory
    public GameState(String scenario, Inventory inventory) {
        this.scenario = scenario;
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    //MODIFIES: this
    //EFFECTS: changes the current scenario string to the scenario string of our next scenario
    public void updateScenario(String newStringScenario) {
        this.scenario = newStringScenario;
    }

    public String getScenario() {
        return this.scenario;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("scenario", scenario);
        json.put("inventory", inventoryToJson());
        return json;
    }

    // EFFECTS: returns inventory in this Game as a JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : inventory.getInventory()) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}
