package model;

import java.util.ArrayList;
import java.util.List;

// Item pouches store an arbitrary number of items.
public class ItemPouch {

    private List<Item> itemPouch;

    // EFFECTS: creates an empty item pouch
    public ItemPouch() {
        itemPouch = new ArrayList<>();
    }

    // REQUIRES: item is not null
    // MODIFIES: this
    // EFFECTS: adds given item to the pouch
    public void addItem(Item i) {
        itemPouch.add(i);
    }

    public List<Item> getItemPouch() {
        return itemPouch;
    }

}
