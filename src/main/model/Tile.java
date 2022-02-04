package model;

// A tile represents a maze structure through which
// the player can walk, and where items may lie.
// Tiles will have another kind of maze structure at
// its upward/downward/leftward/rightward directions.
public class Tile extends MazeStructure {

    private MazeStructure up;
    private MazeStructure down;
    private MazeStructure left;
    private MazeStructure right;
    private Item item;
    private String id;

    // Constructs a tile
    // EFFECTS: creates a tile with some maze structure to
    // its upward, downward, leftward, and rightward directions,
    // and with a specified item and unique ID
    public Tile(MazeStructure u, MazeStructure d, MazeStructure l, MazeStructure r, Item item, String id) {
        this.up = u;
        this.down = d;
        this.left = l;
        this.right = r;
        this.item = item;
        this.id = id;
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

    public Item getItem() {
        return item;
    }

    public String getId() {
        return id;
    }

}
