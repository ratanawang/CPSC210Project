package persistence;

import model.Chest;
import model.Item;
import model.MazeStructure;
import org.junit.jupiter.api.Test;
import ui.Level;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    // Method based on JSONReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Level level = reader.read("test");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/empty.json");
        try {
            Level level = reader.read("test");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    // Method based on JSONReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testReaderInitialStateOfLevel1() {
        JsonReader reader = new JsonReader("./data/testReaderInitialStateOfLevel1.json");
        try {
            Level level = reader.read("test");
            assertEquals("t_2_2", level.getPlayer().getLocation().getId());
            for (MazeStructure m : level.getMazeStructures()) {
                if (m.getClass().getSimpleName().equals("Chest")) {
                    assertEquals("locked", ((Chest) m).getStatus());
                }
            }
            assertTrue(level.getPlayer().getItemPouch().getItemPouch().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    // Method based on JSONReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testReaderPartiallySolvedLevel1() {
        JsonReader reader = new JsonReader("./data/testReaderPartiallySolvedLevel1.json");
        try {
            Level level = reader.read("test");
            for (MazeStructure m : level.getMazeStructures()) {
                if (m.getClass().getSimpleName().equals("Chest")) {
                    assertEquals("unlocked", ((Chest) m).getStatus());
                }
            }
            List<Item> items = level.getPlayer().getItemPouch().getItemPouch();
            assertEquals(2, items.size());
            assertEquals("Key", items.get(0).getClass().getSimpleName());
            assertEquals("Clue", items.get(1).getClass().getSimpleName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}