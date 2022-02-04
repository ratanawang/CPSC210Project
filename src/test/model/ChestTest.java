package model;

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
    }

}
