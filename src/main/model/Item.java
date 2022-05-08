package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item
public class Item implements Writable {
    private String name;

    // EFFECTS: constructs an item with its name
    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}

