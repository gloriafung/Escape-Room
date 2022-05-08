package ui;

import model.GameState;
import model.Inventory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

// Game application
public class Game {
    private static String INITIAL_SCENARIO = "a";
    private static String BOOK_SCENARIO = "aa";
    private static String METAL_SCENARIO = "ab";
    private static String SHELF_SCENARIO = "aaa";
    private static String BOX_SCENARIO = "aab";
    private static String HALLWAY_LEFT_SCENARIO = "aba";
    private static String HALLWAY_RIGHT_SCENARIO = "abb";
    private static String GLASS_SCENARIO = "aaaa";
    private static String PHONE_MARY_SCENARIO = "aaba";
    private static String PHONE_DATE_SCENARIO = "aabb";
    private static String HAIRPIN_FRONT_SCENARIO = "abaa";
    private static String HAIRPIN_BACK_SCENARIO = "abab";
    private static String HAIRPIN_LEFT_SCENARIO = "abba";
    private static String HAIRPIN_RIGHT_SCENARIO = "abbb";
    private static String KEYPAD_MARY_SCENARIO = "aaaaa";
    private static String KEYPAD_DATE_SCENARIO = "aaaab";

    private static final String JSON_STORE = "./data/gamestate.json";
    private Inventory inventory;
    private GameState gameState;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the game application
    public Game() throws FileNotFoundException {
        input = new Scanner(System.in);
        inventory = new Inventory();
        gameState = new GameState(INITIAL_SCENARIO, inventory);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayOptions(gameState.getScenario());
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("c")) {
                keepGoing = false;
            } else if (command.equals("s")) {
                saveGameState();
            } else if (command.equals("l")) {
                loadGameState();
            } else {
                processCommand(gameState.getScenario(), command);
            }
        }

        System.out.println("Goodbye.");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String question, String command) {
        if (question.equals(INITIAL_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                inventory.addItem("Book");
                System.out.println("\nA book has been added to your inventory.");
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                inventory.addItem("Hairpin Front Piece");
                inventory.addItem("Hairpin Back Piece");
                System.out.println("\nThe Hairpin's front piece has been added to your inventory.");
                System.out.println("\nThe Hairpin's back piece has been added to your inventory.");
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(BOOK_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                inventory.addItem("Hammer");
                System.out.println("\nA hammer has been added to your inventory.");
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                inventory.addItem("Phone");
                System.out.println("\nA phone has been added to your inventory.");
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(METAL_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                inventory.addItem("Map");
                System.out.println("\nA map has been added to your inventory.");
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(SHELF_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(BOX_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(HALLWAY_LEFT_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(HALLWAY_RIGHT_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                inventory.addItem("Map");
                System.out.println("\nA map has been added to your inventory.");
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(GLASS_SCENARIO)) {
            String newScenarioString = "";
            if (command.equals("a")) {
                newScenarioString = question + "a";
                gameState.updateScenario(newScenarioString);
            } else if (command.equals("b")) {
                newScenarioString = question + "b";
                gameState.updateScenario(newScenarioString);
            } else {
                System.out.println("Invalid Answer");
            }
        } else if (question.equals(PHONE_MARY_SCENARIO)
                || question.equals(PHONE_DATE_SCENARIO)
                || question.equals(HAIRPIN_FRONT_SCENARIO)
                || question.equals(HAIRPIN_LEFT_SCENARIO)
                || question.equals(KEYPAD_MARY_SCENARIO)
                || question.equals(HAIRPIN_BACK_SCENARIO)
                || question.equals(HAIRPIN_RIGHT_SCENARIO)
                || question.equals(KEYPAD_DATE_SCENARIO)) {
            if (command.equals("r")) {
                gameState.updateScenario(INITIAL_SCENARIO);
                inventory.resetInventory();
            } else {
                System.out.println("Invalid Answer");
            }
        }
    }

    // EFFECTS: displays options for each scenario for user
    @SuppressWarnings("methodlength")
    private void displayOptions(String question) {
        if (question.equals(INITIAL_SCENARIO)) {
            System.out.println("\nYou wake up in a dark room with no idea how to leave.");
            System.out.println("\nYou see a book on a desk but also a piece of metal on the ground.");
            System.out.println("\na -> Read the book. It may contain useful information.");
            System.out.println("\nb -> Pick up the piece of metal. It can physically aid your escape.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(BOOK_SCENARIO)) {
            System.out.println("\nIn the book, there is a photo of a girl called 'Mary'.");
            System.out.println("\nBelow is a date: 09/07/1864.");
            System.out.println("\nYou notice beside the desk, there is a shelf, and under the desk, a box.");
            System.out.println("\na -> Inspect the shelf.");
            System.out.println("\nb -> Peer inside the box");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(METAL_SCENARIO)) {
            System.out.println("\nYou crouch down to pick up the piece of metal.");
            System.out.println("\nWhen you do, it breaks into 2 pieces: the front and the back.");
            System.out.println("\nWhen you stand back up, you see 2 hallways: one to your left, one to your right.");
            System.out.println("\na -> Head down the hallway on your left.");
            System.out.println("\nb -> Head down the hallway on your right.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(SHELF_SCENARIO)) {
            System.out.println("\nYou pick up the hammer on the shelf.");
            System.out.println("\nOn your right, you see a keypad next to a door.");
            System.out.println("\nHowever, the keypad is covered by a layer of glass.");
            System.out.println("\na -> Use the hammer to break the glass.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(BOX_SCENARIO)) {
            System.out.println("\nYou pick up the phone that is in the box.");
            System.out.println("\nIt has 10% battery.");
            System.out.println("\nUnfortunately, you cannot call 911 without inputting the phone's password.");
            System.out.println("\na -> Input 'Mary'.");
            System.out.println("\nb -> Input 09071864.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(HALLWAY_LEFT_SCENARIO)) {
            System.out.println("\nYou head down the left hallway and see a dusty map on the way.");
            System.out.println("\nIt guides you to a door with a keyhole.");
            System.out.println("\na -> Pick the lock with the front piece of the hairpin.");
            System.out.println("\nb -> Pick the lock with the back piece of the hairpin.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(HALLWAY_RIGHT_SCENARIO)) {
            System.out.println("\nYou head down the right hallway and you see a shadow.");
            System.out.println("\nIt seems to be approaching you at a fast pace!!");
            System.out.println("\nYou run the other way until you reach a door.");
            System.out.println("\nWithout thinking, you use the back piece of the hairpin to pick the lock.");
            System.out.println("\na -> Insert the hairpin and twist to the left");
            System.out.println("\nb -> Insert the hairpin and twist to the right.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(GLASS_SCENARIO)) {
            System.out.println("\nThe glass breaks!");
            System.out.println("\na -> Enter 'Mary' into the keypad.");
            System.out.println("\nb -> Enter '09071864 into the keypad.");
            System.out.println("\nc -> quit");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
        } else if (question.equals(PHONE_MARY_SCENARIO)) {
            System.out.println("\nThe password does not work.");
            System.out.println("\nYou try again and again and you end up locking yourself out of the phone.");
            System.out.println("\nYou feel a presence behind you and suddenly, you black out.");
            System.out.println("\nGAME OVER");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
            System.out.println("\nr -> restart game");
        } else if (question.equals(PHONE_DATE_SCENARIO)) {
            System.out.println("\nThe password works and the phone unlocks!");
            System.out.println("\nYou call 911 and send them your location.");
            System.out.println("\nYOU ESCAPED!");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
            System.out.println("\nr -> restart game");
        } else if (question.equals(HAIRPIN_FRONT_SCENARIO)
                || question.equals(HAIRPIN_LEFT_SCENARIO)
                || question.equals(KEYPAD_MARY_SCENARIO)) {
            System.out.println("\nThe door unlocks!");
            System.out.println("\nYou run away as fast as you can!!!");
            System.out.println("\nYOU ESCAPED!");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
            System.out.println("\nr -> restart game");
        } else if (question.equals(HAIRPIN_BACK_SCENARIO)
                || question.equals(HAIRPIN_RIGHT_SCENARIO)
                || question.equals(KEYPAD_DATE_SCENARIO)) {
            System.out.println("\nThe door does not unlock.");
            System.out.println("\nBefore you try again, you feel a presence behind you and suddenly, you black out.");
            System.out.println("\nGAME OVER");
            System.out.println("\ns -> save game");
            System.out.println("\nl -> load game");
            System.out.println("\nr -> restart game");
        }
    }

    //MODIFIES: this
    // EFFECTS: saves the GameState to file
    private void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(gameState);
            jsonWriter.close();
            System.out.println("Saved game state to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads GameState from file
    private void loadGameState() {
        try {
            gameState = jsonReader.read();
            System.out.println("Loaded game state from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
