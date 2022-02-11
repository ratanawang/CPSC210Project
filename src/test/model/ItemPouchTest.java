package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the ItemPouch class
public class ItemPouchTest {

    private ItemPouch testItemPouch;
    private Item itemA;
    private Item itemB;

    @BeforeEach
    void runBefore() {
        itemA = new Item();
        itemB = new Item();
        testItemPouch = new ItemPouch();
    }

    @Test
    void testItemPouch() {
        assertTrue(testItemPouch.getItemPouch().isEmpty());
    }

    @Test
    void testAddItem() {
        testItemPouch.addItem(itemA);
        assertEquals(itemA, testItemPouch.getItemPouch().get(0));
        assertEquals(1, testItemPouch.getItemPouch().size());
        testItemPouch.addItem(itemB);
        assertEquals(itemA, testItemPouch.getItemPouch().get(0));
        assertEquals(itemB, testItemPouch.getItemPouch().get(1));
        assertEquals(2, testItemPouch.getItemPouch().size());
    }

}
