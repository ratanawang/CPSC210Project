package model;

import java.util.ArrayList;
import java.util.List;

// A player represents the player's current
// state within the game. It has a level and
// an item pouch.
public class Player {

    private List<Item> itemPouch;
    private int level;

    // Constructs a player
    // EFFECTS: creates a player with level 1
    // and an empty item pouch
    public Player() {
        this.level = 1;
        this.itemPouch = new ArrayList<>();
    }

    public List<Item> getItemPouch() {
        return itemPouch;
    }

    public int getLevel() {
        return level;
    }

}
