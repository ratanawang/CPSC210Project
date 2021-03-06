package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Key class
public class KeyTest {

    private Key testKey;

    @BeforeEach
    void runBefore() {
        testKey = new Key("id_abc");
    }

    @Test
    void testKey() {
        assertEquals("id_abc", testKey.getId());
    }

    @Test
    void testCanOpenChestTrue() {
        Item i = new Clue("A");
        Chest c = new Chest(i, "id_abc");
        assertTrue(testKey.canOpenChest(c));
    }

    @Test
    void testCanOpenChestFalse() {
        Item i = new Clue("B");
        Chest c = new Chest(i, "id_abd");
        assertFalse(testKey.canOpenChest(c));
    }

    @Test
    void testOpenChest() {
        Item i = new Clue("C");
        Chest c = new Chest(i, "id_abc");
        assertEquals(i, testKey.openChest(c));
        assertEquals("unlocked", c.getStatus());
    }

    @Test
    void testToJson() {
        JSONObject json = testKey.toJson();
        assertEquals(json.get("item type"), "key");
        assertEquals(json.get("id"), testKey.getId());
    }

}
