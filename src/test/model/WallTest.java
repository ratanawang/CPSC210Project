package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Wall class
public class WallTest {

    private Wall testWall;

    @BeforeEach
    void runBefore() {
        testWall = new Wall("id_abc");
    }

    @Test
    void testWall() {
        assertEquals("id_abc", testWall.getId());
    }

}
