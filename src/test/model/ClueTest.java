package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Clue class
public class ClueTest {

    private Clue testClue;

    @BeforeEach
    void runBefore() {
        testClue = new Clue("This is a clue.");
    }

    @Test
    void testClue() {
        assertEquals("This is a clue.", testClue.getInfo());
    }

}
