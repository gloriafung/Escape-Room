package ui;

import model.EventLog;
import model.GameState;
import model.Inventory;
import model.Item;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

// GUI for game application
public class GUI {

    private JFrame gameFrame;

    private HashMap<String, JPanel> scenarioMap;

    private JPanel inventoryPanel;
    private JButton inventoryButton;

    private BufferedImage emptyRoom;
    private JLabel emptyRoomLabel;

    private BufferedImage bookAndShelf;
    private JLabel bookAndShelfLabel;

    private BufferedImage hallway;
    private JLabel hallwayLabel;

    private BufferedImage hammer;
    private JLabel hammerLabel;

    private BufferedImage keypad;
    private JLabel keypadLabel;

    private BufferedImage phone;
    private JLabel phoneLabel;

    private BufferedImage map;
    private JLabel mapLabel;

    private BufferedImage pickLock;
    private JLabel pickLockLabel;

    private BufferedImage gameover;
    private JLabel gameoverLabel;

    private BufferedImage win;
    private JLabel winLabel;

    private JButton saveButton;
    private JButton loadButton;

    private JPanel initialScenarioPanel;
    private JLabel initialScenarioText;
    private JButton initialScenarioA;
    private JButton initialScenarioB;

    private JPanel bookScenarioPanel;
    private JLabel bookScenarioText;
    private JButton bookScenarioA;
    private JButton bookScenarioB;

    private JPanel metalScenarioPanel;
    private JLabel metalScenarioText;
    private JButton metalScenarioA;
    private JButton metalScenarioB;

    private JPanel shelfScenarioPanel;
    private JLabel shelfScenarioText;
    private JButton shelfScenarioA;

    private JPanel boxScenarioPanel;
    private JLabel boxScenarioText;
    private JButton boxScenarioA;
    private JButton boxScenarioB;

    private JPanel hallwayLeftScenarioPanel;
    private JLabel hallwayLeftScenarioText;
    private JButton hallwayLeftScenarioA;
    private JButton hallwayLeftScenarioB;

    private JPanel hallwayRightScenarioPanel;
    private JLabel hallwayRightScenarioText;
    private JButton hallwayRightScenarioA;
    private JButton hallwayRightScenarioB;

    private JPanel glassScenarioPanel;
    private JLabel glassScenarioText;
    private JButton glassScenarioA;
    private JButton glassScenarioB;

    private JPanel phoneMaryScenarioPanel;
    private JLabel phoneMaryScenarioText;

    private JPanel phoneDateScenarioPanel;
    private JLabel phoneDateScenarioText;

    private JPanel badEndingScenarioPanel;
    private JLabel badEndingScenarioText;

    private JPanel goodEndingScenarioPanel;
    private JLabel goodEndingScenarioText;

    private JLabel item;

    private Inventory inventory;
    private GameState gameState;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/gamestate.json";

    // instantiates action event for each button
    InitialScenarioOptions initialScenarioOptions = new InitialScenarioOptions();
    BookScenarioOptions bookScenarioOptions = new BookScenarioOptions();
    MetalScenarioOptions metalScenarioOptions = new MetalScenarioOptions();
    ShelfScenarioOptions shelfScenarioOptions = new ShelfScenarioOptions();
    BoxScenarioOptions boxScenarioOptions = new BoxScenarioOptions();
    HallwayLeftScenarioOptions hallwayLeftScenarioOptions = new HallwayLeftScenarioOptions();
    HallwayRightScenarioOptions hallwayRightScenarioOptions = new HallwayRightScenarioOptions();
    GlassScenarioOptions glassScenarioOptions = new GlassScenarioOptions();
    SaveLoadOptions saveLoadOptions = new SaveLoadOptions();

    // EFFECTS: Starts game at first scenario and makes a new JFrame, all scenario panels, and an inventory panel
    @SuppressWarnings("methodlength")
    public GUI() {
        inventory = new Inventory();
        gameState = new GameState("a", inventory);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        gameFrame = new JFrame("Escape Room");

        gameFrame.setSize(400, 300);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.getContentPane().setBackground(Color.darkGray);
        gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.Y_AXIS));
        gameFrame.setVisible(true);

        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(100, 300, 200, 100);
        inventoryPanel.setBackground(Color.white);
        inventoryPanel.setLayout(new GridLayout(5,1));

        initialScenarioPanel = new JPanel();
        bookScenarioPanel = new JPanel();
        metalScenarioPanel = new JPanel();
        shelfScenarioPanel = new JPanel();
        boxScenarioPanel = new JPanel();
        hallwayLeftScenarioPanel = new JPanel();
        hallwayRightScenarioPanel = new JPanel();
        glassScenarioPanel = new JPanel();
        phoneMaryScenarioPanel = new JPanel();
        phoneDateScenarioPanel = new JPanel();
        goodEndingScenarioPanel = new JPanel();
        badEndingScenarioPanel = new JPanel();

        scenarioMap = new HashMap<String, JPanel>();
        scenarioMap.put("a", initialScenarioPanel);
        scenarioMap.put("aa", bookScenarioPanel);
        scenarioMap.put("ab", metalScenarioPanel);
        scenarioMap.put("aaa", shelfScenarioPanel);
        scenarioMap.put("aab", boxScenarioPanel);
        scenarioMap.put("aba", hallwayLeftScenarioPanel);
        scenarioMap.put("abb", hallwayRightScenarioPanel);
        scenarioMap.put("aaaa", glassScenarioPanel);
        scenarioMap.put("aaba", phoneMaryScenarioPanel);
        scenarioMap.put("aabb", phoneDateScenarioPanel);
        scenarioMap.put("abaa", goodEndingScenarioPanel);
        scenarioMap.put("abba", goodEndingScenarioPanel);
        scenarioMap.put("aaaaa", goodEndingScenarioPanel);
        scenarioMap.put("abab", badEndingScenarioPanel);
        scenarioMap.put("abbb", badEndingScenarioPanel);
        scenarioMap.put("aaaab", badEndingScenarioPanel);

        createPanels();
        getScenario(gameState.getScenario());

        gameFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.getDate() + "\n" + next.getDescription() + "\n");
                }
            }
        });
    }

    // EFFECTS: displays the inventory panel on screen from any scenario
    @SuppressWarnings("methodlength")
    public void openInventory() {

        if (initialScenarioPanel != null) {
            initialScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (bookScenarioPanel != null) {
            bookScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (metalScenarioPanel != null) {
            metalScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (shelfScenarioPanel != null) {
            shelfScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (boxScenarioPanel != null) {
            boxScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (hallwayLeftScenarioPanel != null) {
            hallwayLeftScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (hallwayRightScenarioPanel != null) {
            hallwayRightScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }
        if (glassScenarioPanel != null) {
            glassScenarioPanel.setVisible(false);
            gameFrame.add(inventoryPanel);
        }

        for (Item i : inventory.getInventory()) {
            item = new JLabel(i.getName());
            inventoryPanel.add(item);
        }
    }

    // EFFECTS: displays the corresponding scenario panel on screen from its scenario string
    @SuppressWarnings("methodlength")
    public void getScenario(String scenarioString) {
        if (scenarioString.equals("a")) {
            initialScenario(gameState.getScenario());
        } else if (scenarioString.equals("aa")) {
            bookScenario(gameState.getScenario());
        } else if (scenarioString.equals("ab")) {
            metalScenario(gameState.getScenario());
        } else if (scenarioString.equals("aaa")) {
            shelfScenario(gameState.getScenario());
        } else if (scenarioString.equals("aab")) {
            boxScenario(gameState.getScenario());
        } else if (scenarioString.equals("aba")) {
            hallwayLeftScenario(gameState.getScenario());
        } else if (scenarioString.equals("abb")) {
            hallwayRightScenario(gameState.getScenario());
        } else if (scenarioString.equals("aaaa")) {
            glassScenario(gameState.getScenario());
        } else if (scenarioString.equals("aaba")) {
            phoneMaryScenario(gameState.getScenario());
        } else if (scenarioString.equals("aabb")) {
            phoneDateScenario(gameState.getScenario());
        } else if (scenarioString.equals("abaa") || scenarioString.equals("abba") || scenarioString.equals("aaaaa")) {
            goodEndingScenario(gameState.getScenario());
        } else if (scenarioString.equals("abab") || scenarioString.equals("abbb") || scenarioString.equals("aaaab")) {
            badEndingScenario(gameState.getScenario());
        }
    }

    // EFFECTS: adds respective buttons and labels for every scenario panel
    public void createPanels() {
        createInitialPanel();
        createBookPanel();
        createMetalPanel();
        createShelfPanel();
        createBoxPanel();
        createHallwayLeftPanel();
        createHallwayRightPanel();
        createGlassPanel();
        createPhoneMaryPanel();
        createPhoneDatePanel();
        createGoodEndingPanel();
        createBadEndingPanel();
    }

    //EFFECTS: adds buttons and labels onto the initial scenario panel
    @SuppressWarnings("methodlength")
    public void createInitialPanel() {
        initialScenarioText = new JLabel("<html>You wake up in a dark room with no idea how to leave.<br/>"
                + " You see a book on a desk but also a piece of metal on the ground.<html>");

        initialScenarioA = new JButton();
        initialScenarioA.setText("Pick up the book");
        initialScenarioA.addActionListener(initialScenarioOptions);
        initialScenarioA.setActionCommand("book scenario");

        initialScenarioB = new JButton();
        initialScenarioB.setText("Pick up the metal");
        initialScenarioB.addActionListener(initialScenarioOptions);
        initialScenarioB.setActionCommand("metal scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(initialScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            emptyRoom = ImageIO.read(new File("./data/emptyroom.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emptyRoomLabel = new JLabel(new ImageIcon(emptyRoom));

        initialScenarioPanel.add(emptyRoomLabel);
        initialScenarioPanel.add(initialScenarioText);
        initialScenarioPanel.add(initialScenarioA);
        initialScenarioPanel.add(initialScenarioB);
        initialScenarioPanel.add(saveButton);
        initialScenarioPanel.add(loadButton);
        initialScenarioPanel.add(inventoryButton);
    }

    //EFFECTS: adds buttons and labels onto the book scenario panel
    @SuppressWarnings("methodlength")
    public void createBookPanel() {
        bookScenarioText = new JLabel("<html>A book has been added to your inventory<br/>"
                + "In the book, there is a photo of a girl called 'Mary.'<br/> Below is a date: 09/07/1864. <br/>"
                + "You noticed beside the desk, there is a shelf, and under the desk, a box<html>");

        bookScenarioA = new JButton();
        bookScenarioA.setText("Inspect the shelf");
        bookScenarioA.addActionListener(bookScenarioOptions);
        bookScenarioA.setActionCommand("shelf scenario");

        bookScenarioB = new JButton();
        bookScenarioB.setText("Peer inside the box");
        bookScenarioB.addActionListener(bookScenarioOptions);
        bookScenarioB.setActionCommand("box scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(bookScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            bookAndShelf = ImageIO.read(new File("./data/bookAndShelf.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookAndShelfLabel = new JLabel(new ImageIcon(bookAndShelf));
        bookAndShelfLabel.setPreferredSize(new Dimension(325,225));

        bookScenarioPanel.add(bookAndShelfLabel);
        bookScenarioPanel.add(bookScenarioText);
        bookScenarioPanel.add(inventoryButton);
        bookScenarioPanel.add(bookScenarioA);
        bookScenarioPanel.add(bookScenarioB);
        bookScenarioPanel.add(saveButton);
        bookScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the metal scenario panel
    @SuppressWarnings("methodlength")
    public void createMetalPanel() {
        metalScenarioText = new JLabel("<html>Hairpin (front) and hairpin (back) have been added to your inventory."
                + "<br/>You crouch down to pick up the piece of metal. When you do, it breaks into 2 pieces: "
                + "the front and the back.<br/>When you stand up, you see 2 hallways: one to your left, "
                + "one to your right<html>");

        metalScenarioA = new JButton();
        metalScenarioA.setText("Head down the hallway to your left");
        metalScenarioA.addActionListener(metalScenarioOptions);
        metalScenarioA.setActionCommand("hallway left scenario");

        metalScenarioB = new JButton();
        metalScenarioB.setText("Head down the hallway to your right");
        metalScenarioB.addActionListener(metalScenarioOptions);
        metalScenarioB.setActionCommand("hallway right scenario");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            hallway = ImageIO.read(new File("./data/hallway.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hallwayLabel = new JLabel(new ImageIcon(hallway));
        hallwayLabel.setPreferredSize(new Dimension(325,225));

        metalScenarioPanel.add(hallwayLabel);
        metalScenarioPanel.add(metalScenarioText);
        metalScenarioPanel.add(inventoryButton);
        metalScenarioPanel.add(metalScenarioA);
        metalScenarioPanel.add(metalScenarioB);
        metalScenarioPanel.add(saveButton);
        metalScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the shelf scenario panel
    @SuppressWarnings("methodlength")
    public void createShelfPanel() {
        shelfScenarioText = new JLabel("<html>A hammer has been added into your inventory"
                + "<br/>You pick up the hammer on the shelf.<br/> On your right, you see a keypad next to a door."
                + "<br/> However, the keypad is covered by a layer of glass.<html>");

        shelfScenarioA = new JButton();
        shelfScenarioA.setText("Use the hammer to break the glass");
        shelfScenarioA.addActionListener(shelfScenarioOptions);
        shelfScenarioA.setActionCommand("glass scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(shelfScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            hammer = ImageIO.read(new File("./data/hammer.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hammerLabel = new JLabel(new ImageIcon(hammer));
        hammerLabel.setPreferredSize(new Dimension(325,225));

        shelfScenarioPanel.add(hammerLabel);
        shelfScenarioPanel.add(shelfScenarioText);
        shelfScenarioPanel.add(inventoryButton);
        shelfScenarioPanel.add(shelfScenarioA);
        shelfScenarioPanel.add(saveButton);
        shelfScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the box scenario panel
    @SuppressWarnings("methodlength")
    public void createBoxPanel() {
        boxScenarioText = new JLabel("<html>A phone has been added into your inventory."
                + "<br/> You pick up the phone that is in the box. It has 10% battery."
                + "<br/> Unfortunately, you cannot call 911 without inputting the password.<html>");

        boxScenarioA = new JButton();
        boxScenarioA.setText("Input 'Mary'");
        boxScenarioA.addActionListener(boxScenarioOptions);
        boxScenarioA.setActionCommand("phone mary scenario");

        boxScenarioB = new JButton();
        boxScenarioB.setText("Input '09071864'");
        boxScenarioB.addActionListener(boxScenarioOptions);
        boxScenarioB.setActionCommand("phone date scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(boxScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            phone = ImageIO.read(new File("./data/phone.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        phoneLabel = new JLabel(new ImageIcon(phone));
        phoneLabel.setPreferredSize(new Dimension(325,225));

        boxScenarioPanel.add(phoneLabel);
        boxScenarioPanel.add(boxScenarioText);
        boxScenarioPanel.add(inventoryButton);
        boxScenarioPanel.add(boxScenarioA);
        boxScenarioPanel.add(boxScenarioB);
        boxScenarioPanel.add(saveButton);
        boxScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the left hallway scenario panel
    @SuppressWarnings("methodlength")
    public void createHallwayLeftPanel() {
        hallwayLeftScenarioText = new JLabel("<html>A map has been added into your inventory."
                + "<br/> You head down the left hallway and see a dusty map on the way."
                + "<br/> It guides you to a door with a keyhole.<html>");

        hallwayLeftScenarioA = new JButton();
        hallwayLeftScenarioA.setText("Pick the lock with the front piece of the hairpin");
        hallwayLeftScenarioA.addActionListener(hallwayLeftScenarioOptions);
        hallwayLeftScenarioA.setActionCommand("good ending scenario");

        hallwayLeftScenarioB = new JButton();
        hallwayLeftScenarioB.setText("Pick the lock with the back piece of the hairpin");
        hallwayLeftScenarioB.addActionListener(hallwayLeftScenarioOptions);
        hallwayLeftScenarioB.setActionCommand("bad ending scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(hallwayLeftScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            map = ImageIO.read(new File("./data/map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapLabel = new JLabel(new ImageIcon(map));
        mapLabel.setPreferredSize(new Dimension(325,225));

        hallwayLeftScenarioPanel.add(mapLabel);
        hallwayLeftScenarioPanel.add(hallwayLeftScenarioText);
        hallwayLeftScenarioPanel.add(inventoryButton);
        hallwayLeftScenarioPanel.add(hallwayLeftScenarioA);
        hallwayLeftScenarioPanel.add(hallwayLeftScenarioB);
        hallwayLeftScenarioPanel.add(saveButton);
        hallwayLeftScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the right hallway scenario panel
    @SuppressWarnings("methodlength")
    public void createHallwayRightPanel() {
        hallwayRightScenarioText = new JLabel("<html>You head down the right hallway and you see a shadow."
                + "<br/> It seems to be approaching you at a fast pace!!! <br/>You run the other way until you reach"
                + "a door. <br/>Without thinking, you use the back piece of the hairpin to pick the lock.<html>");

        hallwayRightScenarioA = new JButton();
        hallwayRightScenarioA.setText("Insert the hairpin and twist to the left");
        hallwayRightScenarioA.addActionListener(hallwayRightScenarioOptions);
        hallwayRightScenarioA.setActionCommand("good ending scenario");

        hallwayRightScenarioB = new JButton();
        hallwayRightScenarioB.setText("Insert the hairpin and twist to the right");
        hallwayRightScenarioB.addActionListener(hallwayRightScenarioOptions);
        hallwayRightScenarioB.setActionCommand("bad ending scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(hallwayRightScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            pickLock = ImageIO.read(new File("./data/picklock.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pickLockLabel = new JLabel(new ImageIcon(pickLock));
        pickLockLabel.setPreferredSize(new Dimension(325,225));

        hallwayRightScenarioPanel.add(pickLockLabel);
        hallwayRightScenarioPanel.add(hallwayRightScenarioText);
        hallwayRightScenarioPanel.add(inventoryButton);
        hallwayRightScenarioPanel.add(hallwayRightScenarioA);
        hallwayRightScenarioPanel.add(hallwayRightScenarioB);
        hallwayRightScenarioPanel.add(saveButton);
        hallwayRightScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the glass scenario panel
    @SuppressWarnings("methodlength")
    public void createGlassPanel() {
        glassScenarioText = new JLabel("<html>The glass breaks!!! <br/>What do enter into the keypad?<html>");

        glassScenarioA = new JButton();
        glassScenarioA.setText("Enter 'Mary' into the keypad");
        glassScenarioA.addActionListener(glassScenarioOptions);
        glassScenarioA.setActionCommand("good ending scenario");

        glassScenarioB = new JButton();
        glassScenarioB.setText("Enter '09071864' into the keypad");
        glassScenarioB.addActionListener(glassScenarioOptions);
        glassScenarioB.setActionCommand("bad ending scenario");

        inventoryButton = new JButton();
        inventoryButton.setText("Inventory");
        inventoryButton.addActionListener(glassScenarioOptions);
        inventoryButton.setActionCommand("check inventory");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            keypad = ImageIO.read(new File("./data/keypad.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        keypadLabel = new JLabel(new ImageIcon(keypad));
        keypadLabel.setPreferredSize(new Dimension(325,225));

        glassScenarioPanel.add(keypadLabel);
        glassScenarioPanel.add(glassScenarioText);
        glassScenarioPanel.add(inventoryButton);
        glassScenarioPanel.add(glassScenarioA);
        glassScenarioPanel.add(glassScenarioB);
        glassScenarioPanel.add(saveButton);
        glassScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the phone Mary scenario panel
    public void createPhoneMaryPanel() {
        phoneMaryScenarioText = new JLabel("<html>The password does not work. <br/>You try again and again"
                + " until you end up locking yourself out of the phone.<br/> You feel a presence behind you, and"
                + " suddenly, you black out. <br/>GAME OVER<html>");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            gameover = ImageIO.read(new File("./data/gameover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameoverLabel = new JLabel(new ImageIcon(gameover));
        gameoverLabel.setPreferredSize(new Dimension(325,225));

        phoneMaryScenarioPanel.add(gameoverLabel);
        phoneMaryScenarioPanel.add(phoneMaryScenarioText);
        phoneMaryScenarioPanel.add(saveButton);
        phoneMaryScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the phone date scenario panel
    public void createPhoneDatePanel() {
        phoneDateScenarioText = new JLabel("<html>The password works and the phone unlocks!! <br/>You call 911"
                + " and send them your location.<br/> YOU ESCAPE!<html>");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            win = ImageIO.read(new File("./data/win.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        winLabel = new JLabel(new ImageIcon(win));
        winLabel.setPreferredSize(new Dimension(325,225));

        phoneDateScenarioPanel.add(winLabel);
        phoneDateScenarioPanel.add(phoneDateScenarioText);
        phoneDateScenarioPanel.add(saveButton);
        phoneDateScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the good ending scenario panel
    public void createGoodEndingPanel() {
        goodEndingScenarioText = new JLabel("<html>The door unlocks!!! <br/>You run away as fast as you can!!!"
                + "<br/> YOU ESCAPE!<html>");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");
        try {
            win = ImageIO.read(new File("./data/win.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        winLabel = new JLabel(new ImageIcon(win));
        winLabel.setPreferredSize(new Dimension(325,225));

        goodEndingScenarioPanel.add(winLabel);
        goodEndingScenarioPanel.add(goodEndingScenarioText);
        goodEndingScenarioPanel.add(saveButton);
        goodEndingScenarioPanel.add(loadButton);
    }

    //EFFECTS: adds buttons and labels onto the bad ending scenario panel
    public void createBadEndingPanel() {
        badEndingScenarioText = new JLabel("<html>The door does not unlock. <br/>Before you can try again, you feel"
                + " a presence behind you, and suddenly, you black out.<br/> GAME OVER<html>");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(saveLoadOptions);
        saveButton.setActionCommand("save");

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.addActionListener(saveLoadOptions);
        loadButton.setActionCommand("load");

        try {
            gameover = ImageIO.read(new File("./data/gameover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameoverLabel = new JLabel(new ImageIcon(gameover));
        gameoverLabel.setPreferredSize(new Dimension(325,225));

        badEndingScenarioPanel.add(gameoverLabel);
        badEndingScenarioPanel.add(badEndingScenarioText);
        badEndingScenarioPanel.add(saveButton);
        badEndingScenarioPanel.add(loadButton);
    }

    // EFFECTS: moves from previous panel to initial scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void initialScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("a");
        gameFrame.add(initialScenarioPanel);
        initialScenarioPanel.setVisible(true);
    }

    // action events for save and load
    public class SaveLoadOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("save")) {
                saveButtonAction();
            } else if (clicked.equals("load")) {
                loadButtonAction();
            }
        }
    }

    // action events for the initial scenario
    public class InitialScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("book scenario")) {
                getScenario("aa");
                inventory.addItem("Book");
            } else if (clicked.equals("metal scenario")) {
                getScenario("ab");
                inventory.addItem("Hairpin Front Piece");
                inventory.addItem("Hairpin Back Piece");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to book scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void bookScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aa");
        gameFrame.add(bookScenarioPanel);
        bookScenarioPanel.setVisible(true);
    }

    // action events for the book scenario
    public class BookScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("shelf scenario")) {
                getScenario("aaa");
                inventory.addItem("Hammer");
            } else if (clicked.equals("box scenario")) {
                getScenario("aab");
                inventory.addItem("Phone");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to metal scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void metalScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("ab");
        gameFrame.add(metalScenarioPanel);
        metalScenarioPanel.setVisible(true);
    }

    // action events for the metal scenario
    public class MetalScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("hallway left scenario")) {
                getScenario("aba");
                inventory.addItem("Map");
            } else if (clicked.equals("hallway right scenario")) {
                getScenario("abb");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to shelf scenario panel while updating scenario string
    public void shelfScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aaa");
        gameFrame.add(shelfScenarioPanel);
        shelfScenarioPanel.setVisible(true);
    }

    // action events for the shelf scenario
    public class ShelfScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("glass scenario")) {
                getScenario("aaaa");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to box scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void boxScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aab");
        gameFrame.add(boxScenarioPanel);
        boxScenarioPanel.setVisible(true);
    }

    // action events for the box scenario
    public class BoxScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("phone mary scenario")) {
                getScenario("aaba");
            } else if (clicked.equals("phone date scenario")) {
                getScenario("aabb");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to left hallway scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void hallwayLeftScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aba");
        gameFrame.add(hallwayLeftScenarioPanel);
        hallwayLeftScenarioPanel.setVisible(true);

    }

    // action events for the left hallway scenario
    public class HallwayLeftScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("good ending scenario")) {
                getScenario("abaa");
            } else if (clicked.equals("bad ending scenario")) {
                getScenario("abab");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to right hallway scenario panel while updating scenario string
    @SuppressWarnings("methodlength")
    public void hallwayRightScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("abb");
        gameFrame.add(hallwayRightScenarioPanel);
        hallwayRightScenarioPanel.setVisible(true);
    }

    // action events for the right hallway scenario
    public class HallwayRightScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("good ending scenario")) {
                getScenario("abba");
            } else if (clicked.equals("bad ending scenario")) {
                getScenario("abbb");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to glass scenario panel while updating scenario string
    public void glassScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aaaa");
        gameFrame.add(glassScenarioPanel);
        glassScenarioPanel.setVisible(true);
    }

    // action events for the glass scenario
    public class GlassScenarioOptions implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Sets each button to its respective action
        @Override
        public void actionPerformed(ActionEvent e) {

            String clicked = e.getActionCommand();

            if (clicked.equals("good ending scenario")) {
                getScenario("aaaaa");
            } else if (clicked.equals("bad ending scenario")) {
                getScenario("aaaab");
            } else if (clicked.equals("check inventory")) {
                openInventory();
            }
        }
    }

    // EFFECTS: moves from previous panel to phone Mary scenario panel while updating scenario string
    public void phoneMaryScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aaba");
        gameFrame.add(phoneMaryScenarioPanel);
        phoneMaryScenarioPanel.setVisible(true);
    }

    // EFFECTS: moves from previous panel to phone date scenario panel while updating scenario string
    public void phoneDateScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("aabb");
        gameFrame.add(phoneDateScenarioPanel);
        phoneDateScenarioPanel.setVisible(true);

    }

    // EFFECTS: moves from previous panel to good ending scenario panel while updating scenario string
    public void goodEndingScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("abaa");
        gameFrame.add(goodEndingScenarioPanel);
        goodEndingScenarioPanel.setVisible(true);

    }

    // EFFECTS: moves from previous panel to bad ending scenario panel while updating scenario string
    public void badEndingScenario(String currentScenario) {
        JPanel currentPanel = scenarioMap.get(currentScenario);
        currentPanel.setVisible(false);
        gameState.updateScenario("abab");
        gameFrame.add(badEndingScenarioPanel);
        badEndingScenarioPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: saves the GameState to file
    public void saveButtonAction() {
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
    // EFFECTS: loads the GameState from file
    private void loadButtonAction() {
        try {
            GameState temp = jsonReader.read();
            inventory = temp.getInventory();
            getScenario(temp.getScenario());
            System.out.println("Loaded game state from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}

