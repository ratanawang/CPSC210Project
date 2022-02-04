package model;

// A wall represents a maze structure through which
// a player cannot walk. Each wall has its own ID.
public class Wall extends MazeStructure {

    private String id;

    // Constructs a wall
    // EFFECTS: creates a wall with its own ID
    public Wall(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
