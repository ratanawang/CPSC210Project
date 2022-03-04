package model;

import org.json.JSONObject;

// An exit represents the door of freedom
// through which players can escape the room.
// It has a password and an ID.
public class Exit extends MazeStructure {

    private String password;

    // EFFECTS: creates an exit with a password and ID
    public Exit(String password, String id) {
        this.password = password;
        this.id = id;
    }

    @Override
    // Method based on WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts an exit into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("structure type", "exit");
        json.put("password", this.password);
        json.put("id", this.id);
        return json;
    }

    public String getPassword() {
        return password;
    }

}
