package model;

// A chest represents a maze structure that has a lock
// that can be opened with a key and an item inside.
public class Chest extends MazeStructure {

    private Item item;
    private String status;

    // Constructs a chest
    // EFFECTS: creates a locked chest with an item and an ID
    public Chest(Item item, String id) {
        this.item = item;
        this.id = id;
        this.status = "locked";
    }

    public Item getItem() {
        return item;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
