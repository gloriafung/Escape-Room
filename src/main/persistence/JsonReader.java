package persistence;

import model.GameState;
import model.Inventory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads GameState from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameState(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses GameState from JSON object and returns it
    private GameState parseGameState(JSONObject jsonObject) {
        String scenario = jsonObject.getString("scenario");
        Inventory inventory = returnInventory(jsonObject);
        GameState gs = new GameState(scenario, inventory);
        return gs;
    }

    //MODIFIES: gs
    // EFFECTS: parses inventory from JSON object and adds them to GameState
    private Inventory returnInventory(JSONObject jsonObject) {
        Inventory inventory = new Inventory();
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inventory, nextItem);
        }
        return inventory;
    }

    // MODIFIES: gs
    // EFFECTS: parses item from JSON object and adds it to GameState
    private void addItem(Inventory inventory, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        inventory.addItem(name);
    }
}
