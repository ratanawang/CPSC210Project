package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Chest class
public class ChestTest {

    private Chest testChest;
    private Item item;

    @BeforeEach
    void runBefore() {
        item = new Clue("This is a clue.");
        testChest = new Chest(item, "id_abc");
    }

    @Test
    void testChest() {
        assertEquals(item, testChest.getItem());
        assertEquals("id_abc", testChest.getId());
        assertEquals("locked", testChest.getStatus());
    }

    @Test
    void testToJson() {
        JSONObject json = testChest.toJson();
        assertEquals(json.get("structure type"), "chest");
        assertEquals(json.get("id"), testChest.getId());
        assertEquals(json.get("status"), testChest.getStatus());
    }

}
