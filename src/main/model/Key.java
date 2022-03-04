package model;

import org.json.JSONObject;
import persistence.Writable;

// A key represents an item that can open chests.
// All keys will have the same IDs as the chests they open.
public class Key extends Item implements Writable {

    private String id;

    // EFFECTS: creates a key with the specified ID
    public Key(String id) {
        this.id = id;
    }

    // EFFECTS: returns true if the key is capable of
    // opening the chest (their IDs are the same)
    public boolean canOpenChest(Chest chest) {
        return getId().equals(chest.getId());
    }

    // REQUIRES: canOpenChest == true
    // MODIFIES: chest
    // EFFECTS: gets the item within the chest
    // and changes its status to "unlocked"
    public Item openChest(Chest chest) {
        chest.setStatus("unlocked");
        return chest.getItem();
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a key into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("item type", "key");
        json.put("id", this.id);
        return json;
    }

    public String getId() {
        return id;
    }

}
