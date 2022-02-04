package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Tile class
public class TileTest {

    private Tile testTile;
    private MazeStructure up;
    private MazeStructure down;
    private MazeStructure left;
    private MazeStructure right;
    private Item item;

    @BeforeEach
    void runBefore() {
        up = new MazeStructure();
        down = new MazeStructure();
        left = new MazeStructure();
        right = new MazeStructure();
        item = new Item();
        testTile = new Tile(up, down, left, right, item, "id_abc");
    }

    @Test
    void testTile() {
        assertEquals(up, testTile.getUp());
        assertEquals(down, testTile.getDown());
        assertEquals(left, testTile.getLeft());
        assertEquals(right, testTile.getRight());
        assertEquals(item, testTile.getItem());
        assertEquals("id_abc", testTile.getId());
    }

}
