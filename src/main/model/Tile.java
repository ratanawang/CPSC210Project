package model;

// A tile represents a maze structure through which
// the player can walk, and where items may lie.
public class Tile extends MazeStructure {

    private Item item;

    // EFFECTS: creates a tile with a specified item and unique ID
    public Tile(Item item, String id) {
        this.item = item;
        this.id = id;
    }

    // EFFECTS: creates a tile with a unique ID
    public Tile(String id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

}
