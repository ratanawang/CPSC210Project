package model;

// A player represents the player's current
// state within the game. It has a level and
// an item pouch.
public class Player {

    private ItemPouch itemPouch;
    private MazeStructure location;

    // EFFECTS: creates a player at the given location
    // and an empty item pouch
    public Player(MazeStructure location) {
        this.location = location;
        this.itemPouch = new ItemPouch();
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
            default:
                location = location.getRight();
                break;
        }
    }

    // REQUIRES: item is not null, and not already in pouch
    // MODIFIES: this
    // EFFECTS: adds item to item pouch
    public void addItemToPouch(Item i) {
        itemPouch.addItem(i);
    }

    public MazeStructure getLocation() {
        return location;
    }

    public ItemPouch getItemPouch() {
        return itemPouch;
    }

}
