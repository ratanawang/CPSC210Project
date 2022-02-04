package model;

// A key represents an item that can open chests.
public class Key extends Item {

    private String id;

    // Constructs a key
    // EFFECTS: creates a key with the specified ID
    public Key(String id) {
        this.id = id;
    }

    // EFFECTS: returns true if the key is capable of
    // opening the chest (their IDs are the same)
    public boolean canOpenChest(Chest chest) {
        return this.id.equals(chest.getId());
    }

    // REQUIRES: canOpenChest == true
    // EFFECTS: gets the item within the chest
    public Item openChest(Chest chest) {
        return chest.getItem();
    }

    public String getId() {
        return id;
    }

}
