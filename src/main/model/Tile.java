package model;

import org.json.JSONObject;

// A tile represents a maze structure through which
// the player can walk, and where items may lie.
public class Tile extends MazeStructure {

    private Item item;

    // EFFECTS: creates a tile with a specified item and unique ID
    public Tile(Item item, String id) {
        this.item = item;
        this.id = id;
    }

    // EFFECTS: creates a tile with a unique ID
    public Tile(String id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a tile into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("structure type", "tile");
        if (this.item != null) {
            json.put("item", this.item.toJson());
        } else {
            json.put("item", "");
        }
        json.put("id", this.id);
        return json;
    }

}
