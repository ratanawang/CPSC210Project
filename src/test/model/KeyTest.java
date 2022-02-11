package model;

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
        Item i = new Item();
        Chest c = new Chest(i, "id_abc");
        assertTrue(testKey.canOpenChest(c));
    }

    @Test
    void testCanOpenChestFalse() {
        Item i = new Item();
        Chest c = new Chest(i, "id_abd");
        assertFalse(testKey.canOpenChest(c));
    }

    @Test
    void testOpenChest() {
        Item i = new Item();
        Chest c = new Chest(i, "id_abc");
        assertEquals(i, testKey.openChest(c));
        assertEquals("unlocked", c.getStatus());
    }

}
