package model;

import org.json.JSONObject;
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

    @Test
    void testToJson() {
        JSONObject json = testClue.toJson();
        assertEquals(json.get("item type"), "clue");
        assertEquals(json.get("info"), testClue.getInfo());
    }

}
