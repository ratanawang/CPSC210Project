package model;

import java.util.Iterator;

// A player represents the player's current
// state within the game. It has a level and
// an item pouch.
public class Player {

    private ItemPouch itemPouch;
    private MazeStructure location;

    // EFFECTS: creates a player at the given location's ID
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

    // EFFECTS: moves the player to a specific location
    public void moveTo(MazeStructure m) {
        this.location = m;
    }

    // REQUIRES: item is not null, and not already in pouch
    // MODIFIES: this
    // EFFECTS: adds item to item pouch
    public void addItemToPouch(Item i) {
        itemPouch.addItem(i);
        String itemType = i.getClass().getSimpleName();
        EventLog.getInstance().logEvent(new Event(itemType + " added to item pouch."));
    }

    // EFFECTS: logs the reading of a clue as an event
    public void readClue(Clue c) {
        String info = c.getInfo();
        EventLog.getInstance().logEvent(new Event("Clue (" + info + ") was read."));
    }

    // EFFECTS: prints event log, quits the program
    public void quitGame() {
        System.out.println("===== Event Log: =====");
        EventLog el = EventLog.getInstance();
        for (Event e : el) {
            System.out.println(e.toString());
        }
        System.out.println("===== End of Event Log =====");
        System.exit(0);
    }

    public MazeStructure getLocation() {
        return location;
    }

    public ItemPouch getItemPouch() {
        return itemPouch;
    }

}
