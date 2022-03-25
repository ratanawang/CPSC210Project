package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Tile class
public class TileTest {

    private Tile testTileA;
    private Tile testTileB;
    private Tile testTileC;
    private Tile testTileD;
    private Tile testTileE;
    private Item item;

    @BeforeEach
    void runBefore() {
        item = new Clue("A");
        testTileA = new Tile(item, "id_abc");
        testTileB = new Tile("id_xyz");
        testTileC = new Tile("c");
        testTileD = new Tile("d");
        testTileE = new Tile("e");
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

    @Test
    void testSetAllDirections() {
        testTileA.setAllDirections(testTileB, testTileC, testTileD, testTileE);
        assertEquals(testTileB, testTileA.getUp());
        assertEquals(testTileC, testTileA.getDown());
        assertEquals(testTileD, testTileA.getLeft());
        assertEquals(testTileE, testTileA.getRight());
    }

    @Test
    void testToJsonWithItem() {
        JSONObject json = testTileA.toJson();
        assertEquals(json.get("structure type"), "tile");
        assertEquals(json.get("id"), testTileA.getId());
    }

    @Test
    void testToJsonWithoutItem() {
        JSONObject json = testTileB.toJson();
        assertEquals(json.get("structure type"), "tile");
        assertEquals(json.get("item"), "");
        assertEquals(json.get("id"), testTileB.getId());
    }
}
