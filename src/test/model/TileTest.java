package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Tile class
public class TileTest {

    private Tile testTileA;
    private Tile testTileB;
    private Item item;

    @BeforeEach
    void runBefore() {
        item = new Item();
        testTileA = new Tile(item, "id_abc");
        testTileB = new Tile("id_xyz");
    }

    @Test
    void testTileWithItem() {
        assertEquals(item, testTileA.getItem());
        assertEquals("id_abc", testTileA.getId());
    }

    @Test
    void testTileWithoutItem() {
        assertEquals("id_xyz", testTileB.getId());
    }

}
