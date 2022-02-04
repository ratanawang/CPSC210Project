package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for the Player class
class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
    }

    @Test
    void testPlayer() {
        assertEquals(1, testPlayer.getLevel());
        assertTrue(testPlayer.getItemPouch().isEmpty());
    }

}