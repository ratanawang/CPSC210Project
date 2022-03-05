package model;

import org.json.JSONObject;

// An item represents something that the player
// can put into their item pouch for later retrieval.
// Items include clues and keys.
public abstract class Item {
    // EFFECTS: converts an item into a JSONObject
    public abstract JSONObject toJson();
}
