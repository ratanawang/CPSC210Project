package model;

// A chest represents a maze structure that has a lock
// that can be opened with a key and an item inside.
// Chests will have another kind of maze structure at
// its upward/downward/leftward/rightward directions.
public class Chest extends MazeStructure {

    private Item item;
    private String id;
    private MazeStructure up;
    private MazeStructure down;
    private MazeStructure left;
    private MazeStructure right;

    // Constructs a chest
    // EFFECTS: creates a chest with an item and an ID
    public Chest(Item item, String id) {
        this.item = item;
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public String getId() {
        return id;
    }

    public MazeStructure getUp() {
        return up;
    }

    public MazeStructure getDown() {
        return down;
    }

    public MazeStructure getLeft() {
        return left;
    }

    public MazeStructure getRight() {
        return right;
    }

}
