package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for the Player class
class PlayerTest {

    private Player testPlayer;
    private Tile tileMiddle;
    private Tile tileUp;
    private Tile tileDown;
    private Tile tileLeft;
    private Tile tileRight;

    @BeforeEach
    void runBefore() {
        tileMiddle = new Tile("middle");
        tileUp = new Tile("up");
        tileDown = new Tile("down");
        tileLeft = new Tile("left");
        tileRight = new Tile("right");
        tileMiddle.setUp(tileUp);
        tileMiddle.setDown(tileDown);
        tileMiddle.setLeft(tileLeft);
        tileMiddle.setRight(tileRight);
        tileUp.setDown(tileMiddle);
        tileDown.setUp(tileMiddle);
        tileLeft.setRight(tileMiddle);
        tileRight.setLeft(tileMiddle);
        testPlayer = new Player(tileMiddle);
    }

    @Test
    void testPlayer() {
        assertEquals(tileMiddle, testPlayer.getLocation());
        assertTrue(testPlayer.getItemPouch().getItemPouch().isEmpty());
    }

    @Test
    void testMoveUp() {
        testPlayer.move("up");
        assertEquals(tileUp, testPlayer.getLocation());
    }

    @Test
    void testMoveDown() {
        testPlayer.move("down");
        assertEquals(tileDown, testPlayer.getLocation());
    }

    @Test
    void testMoveLeft() {
        testPlayer.move("left");
        assertEquals(tileLeft, testPlayer.getLocation());
    }

    @Test
    void testMoveRight() {
        testPlayer.move("right");
        assertEquals(tileRight, testPlayer.getLocation());
    }

    @Test
    void testMove() {
        testPlayer.move("up");
        assertEquals(tileUp, testPlayer.getLocation());
        testPlayer.move("down");
        assertEquals(tileMiddle, testPlayer.getLocation());
        testPlayer.move("down");
        assertEquals(tileDown, testPlayer.getLocation());
        testPlayer.move("up");
        assertEquals(tileMiddle, testPlayer.getLocation());
        testPlayer.move("left");
        assertEquals(tileLeft, testPlayer.getLocation());
        testPlayer.move("right");
        assertEquals(tileMiddle, testPlayer.getLocation());
        testPlayer.move("right");
        assertEquals(tileRight, testPlayer.getLocation());
        testPlayer.move("left");
        assertEquals(tileMiddle, testPlayer.getLocation());
    }

    @Test
    void testAddItemToPouch() {
        Item itemA = new Clue("A");
        Item itemB = new Clue("B");
        testPlayer.addItemToPouch(itemA);
        assertEquals(itemA, testPlayer.getItemPouch().getItemPouch().get(0));
        assertEquals(1, testPlayer.getItemPouch().getItemPouch().size());
        testPlayer.addItemToPouch(itemB);
        assertEquals(itemA, testPlayer.getItemPouch().getItemPouch().get(0));
        assertEquals(itemB, testPlayer.getItemPouch().getItemPouch().get(1));
        assertEquals(2, testPlayer.getItemPouch().getItemPouch().size());
    }

}