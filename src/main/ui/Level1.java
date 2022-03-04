package ui;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Represents the set-up for level 1, including
// all of its structural features.
public class Level1 extends Level implements Writable {

    private Chest ch0;
    private Clue cl0;
    private Key k0;
    private Tile t0;
    private Tile t1;
    private Tile t2;
    private Tile t3;
    private Tile t4;
    private Tile t5;
    private Tile t6;

    // Constructs the level
    // REQUIRES: test = "test" if testing, to avoid console prompt
    // EFFECTS: creates a Level1() object, which also
    // initializes the key, clue, player, and creates the maze
    public Level1(String test) {
        super();
        System.out.println("You are now playing Level 1.");
        k0 = new Key("c_3_1");
        cl0 = new Clue("47 days after Christmas [mm/dd].");
        constructMaze();
        connectMaze();
        this.player = new Player(t4);
        if (test.equals("test")) {
            return;
        }
        nextStep();
    }

    // Constructs the level from saved data
    // REQUIRES: test = "test" if testing, to avoid console prompt
    // EFFECTS: creates a Level1() object, which also
    // initializes the key, clue, and creates the maze,
    // and places the given player at its previous location
    // if chestUnlocked, then the chest is set to unlocked
    public Level1(Player player, boolean chestUnlocked, String test) {
        super();
        System.out.println("You are now playing Level 1.");
        k0 = new Key("c_3_1");
        cl0 = new Clue("47 days after Christmas [mm/dd].");
        constructMaze();
        connectMaze();
        this.player = player;
        connectLastLocation();
        if (chestUnlocked) {
            ch0.setStatus("unlocked");
        }
        if (test.equals("test")) {
            return;
        }
        nextStep();
    }

    // MODIFIES: this
    // EFFECTS: initializes each maze structure and adds
    // it to the mazeStructures list
    protected void constructMaze() {
        t0 = new Tile("t_1_1");
        mazeStructures.add(t0);
        t1 = new Tile("t_1_2");
        mazeStructures.add(t1);
        t2 = new Tile(k0, "t_1_3");
        mazeStructures.add(t2);
        t3 = new Tile("t_2_1");
        mazeStructures.add(t3);
        t4 = new Tile("t_2_2");
        mazeStructures.add(t4);
        t5 = new Tile("t_2_3");
        mazeStructures.add(t5);
        ch0 = new Chest(cl0, "c_3_1");
        mazeStructures.add(ch0);
        t6 = new Tile("t_3_2");
        mazeStructures.add(t6);
        exit = new Exit("0210", "e_3_3");
        mazeStructures.add(exit);
    }

    // MODIFIES: this
    // EFFECTS: establishes the relationships between maze structures
    protected void connectMaze() {
        t0.setDown(t3);
        t0.setRight(t1);
        t1.setDown(t4);
        t1.setLeft(t0);
        t1.setRight(t2);
        t2.setDown(t5);
        t2.setLeft(t1);
        t3.setUp(t0);
        t3.setDown(ch0);
        t3.setRight(t4);
        t4.setAllDirections(t1, t6, t3, t5);
        t5.setUp(t2);
        t5.setDown(exit);
        t5.setLeft(t4);
        ch0.setUp(t3);
        ch0.setRight(t6);
        t6.setUp(t4);
        t6.setLeft(ch0);
        t6.setRight(exit);
        exit.setUp(t5);
        exit.setLeft(t6);
    }

    // REQUIRES: ID of player's location is also ID of a maze structure
    // MODIFIES: player
    // EFFECTS: connects saved location with rest of maze
    protected void connectLastLocation() {
        for (MazeStructure m : mazeStructures) {
            if (m.getId().equals(player.getLocation().getId())) {
                player.moveTo(m);
                break;
            }
        }
    }

    @Override
    // Method based on WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the level to file
    protected void saveLevel() {
        System.out.println("Your progress has been saved.");
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a level into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "level 1");
        json.put("player", "default player");
        json.put("location", player.getLocation().getId());
        json.put("items", itemsToJson());
        json.put("maze", mazeStructuresToJson());
        return json;
    }

    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns player's items as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : player.getItemPouch().getItemPouch()) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns maze structures list as a JSON array
    private JSONArray mazeStructuresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MazeStructure m : mazeStructures) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

}
