package model;

// A clue represents an item that has useful information
// about passwords needed for exiting the room.
public class Clue extends Item {

    private String info;

    // EFFECTS: creates a clue with the given information
    public Clue(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
