package model;

// Maze structures have either nothing (null) or
// other maze structures to their upward, downward,
// leftward, and rightward directions.
// All maze structures also have their own unique ID,
// in the format "t_x_y" where t represents the first
// letter of its type, and x and y are its coordinates
public abstract class MazeStructure {

    protected MazeStructure up;
    protected MazeStructure down;
    protected MazeStructure left;
    protected MazeStructure right;
    protected String id;

    // REQUIRES: up, down, left, and right are not null
    // MODIFIES: this
    // EFFECTS: sets the surrounding maze structures
    public void setAllDirections(MazeStructure up, MazeStructure down, MazeStructure left, MazeStructure right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void setUp(MazeStructure up) {
        this.up = up;
    }

    public void setDown(MazeStructure down) {
        this.down = down;
    }

    public void setLeft(MazeStructure left) {
        this.left = left;
    }

    public void setRight(MazeStructure right) {
        this.right = right;
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

    public String getId() {
        return id;
    }

}
