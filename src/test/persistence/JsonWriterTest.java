package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import ui.Level;
import ui.Level1;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    // Method based on JSONWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testWriterInvalidFile() {
        try {
            Level level = new Level1("test");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    // Method based on JSONWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testWriterInitialStateOfLevel1() {
        try {
            Level level = new Level1("test");
            JsonWriter writer = new JsonWriter("./data/testWriterInitialStateOfLevel1.json");
            writer.open();
            writer.write(level);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterInitialStateOfLevel1.json");
            level = reader.read("test");
            assertEquals("t_2_2", level.getPlayer().getLocation().getId());
            for (MazeStructure m : level.getMazeStructures()) {
                if (m.getClass().getSimpleName().equals("Chest")) {
                    assertEquals("locked", ((Chest) m).getStatus());
                }
            }
            assertTrue(level.getPlayer().getItemPouch().getItemPouch().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    // Method based on JSONWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    void testWriterPartiallySolvedLevel1() {
        try {
            // setting up a partially solved level
            Level level = new Level1("test");
            for (MazeStructure m : level.getMazeStructures()) {
                if (m.getId().equals("t_1_3")) {
                    level.getPlayer().addItemToPouch(((Tile) m).getItem());
                    break;
                }
            }
            for (MazeStructure m : level.getMazeStructures()) {
                if (m.getId().equals("c_3_1")) {
                    Item treasure = (new Key("c_3_1")).openChest((Chest) m);
                    level.getPlayer().addItemToPouch(treasure);
                    break;
                }
            }
            level.getPlayer().moveTo(level.getMazeStructures().get(0));
            // writing the level to the destination
            JsonWriter writer = new JsonWriter("./data/testWriterPartiallySolvedLevel1.json");
            writer.open();
            writer.write(level);
            writer.close();
            // reading the level from the source
            JsonReader reader = new JsonReader("./data/testWriterPartiallySolvedLevel1.json");
            level = reader.read("test");
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
            fail("Exception should not have been thrown");
        }
    }
}