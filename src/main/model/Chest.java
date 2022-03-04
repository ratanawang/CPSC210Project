package model;

import org.json.JSONObject;

// A chest represents a maze structure that has a lock
// that can be opened with a key and an item inside.
public class Chest extends MazeStructure {

    private Item item;
    private String status;

    // EFFECTS: creates a locked chest with an item and an ID
    public Chest(Item item, String id) {
        this.item = item;
        this.id = id;
        this.status = "locked";
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a chest into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("structure type", "chest");
        json.put("item", this.item.toJson());
        json.put("id", this.id);
        json.put("status", this.status);
        return json;
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
