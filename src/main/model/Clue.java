package model;

import org.json.JSONObject;
import persistence.Writable;

// A clue represents an item that has useful information
// about passwords needed for exiting the room.
public class Clue extends Item implements Writable {

    private String info;

    // EFFECTS: creates a clue with the given information
    public Clue(String info) {
        this.info = info;
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a clue into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("item type", "clue");
        json.put("info", this.info);
        return json;
    }

    public String getInfo() {
        return info;
    }

}
