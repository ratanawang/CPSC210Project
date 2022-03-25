package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the Exit class
public class ExitTest {

    private Exit testExit;

    @BeforeEach
    void runBefore() {
        testExit = new Exit("pw_abc", "id_abc");
    }

    @Test
    void testExit() {
        assertEquals("pw_abc", testExit.getPassword());
        assertEquals("id_abc", testExit.getId());
    }

    @Test
    void testToJson() {
        JSONObject json = testExit.toJson();
        assertEquals(json.get("structure type"), "exit");
        assertEquals(json.get("password"), testExit.getPassword());
        assertEquals(json.get("id"), testExit.getId());
    }

}
