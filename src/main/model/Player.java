package model;

import java.util.ArrayList;
import java.util.List;

// A player represents the player's current
// state within the game. It has a level and
// an item pouch.
public class Player {

    private List<Item> itemPouch;
    private MazeStructure location;

    // Constructs a player
    // EFFECTS: creates a player at the given location
    // and an empty item pouch
    public Player(MazeStructure location) {
        this.location = location;
        this.itemPouch = new ArrayList<>();
    }

    // REQUIRES: dir is one of "up", "down", "left", or "right",
    // and the next location is not null
    // MODIFIES: this
    // EFFECTS: moves the player in the specified direction
    public void move(String dir) {
        switch (dir) {
            case "up":
                location = location.getUp();
                break;
            case "down":
                location = location.getDown();
                break;
            case "left":
                location = location.getLeft();
                break;
            case "right":
                location = location.getRight();
                break;
        }
    }

    // REQUIRES: item is not null, and not already in pouch
    // MODIFIES: this
    // EFFECTS: adds item to item pouch
    public void addItemToPouch(Item i) {
        itemPouch.add(i);
    }

    public MazeStructure getLocation() {
        return location;
    }

    public List<Item> getItemPouch() {
        return itemPouch;
    }

}
