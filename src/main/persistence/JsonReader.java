package persistence;

import model.*;
import ui.Level;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;
import ui.Level1;

// Represents a reader that reads level from JSON data stored in file
public class JsonReader {
    private String source;

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads level from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Level read(String test) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLevel(jsonObject, test);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        if (contentBuilder.toString().equals("")) {
            throw new IOException();
        }

        return contentBuilder.toString();
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses level from JSON object and returns it
    private Level parseLevel(JSONObject jsonObject, String test) {
        List<Item> itemPouch = parseItems(jsonObject);
        List<MazeStructure> mazeStructures = parseMaze(jsonObject);
        String location = jsonObject.getString("location");
        Player player = new Player(new Tile("placeholder"));
        boolean chestUnlocked = false;
        for (MazeStructure m : mazeStructures) {
            if (m.getId().equals(location)) {
                player = new Player(m);
            }
            if (m.getClass().getSimpleName().equals("Chest")) {
                if (((Chest) m).getStatus().equals("unlocked")) {
                    chestUnlocked = true;
                }
            }
        }
        for (Item i : itemPouch) {
            player.addItemToPouch(i);
        }
        return new Level1(player, chestUnlocked);
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses maze structures from JSON object and returns it
    private List<MazeStructure> parseMaze(JSONObject jsonObject) {
        List<MazeStructure> mazeStructures = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("maze");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            String itemType = nextItem.getString("structure type");
            if (itemType.equals("chest")) {
                mazeStructures.add(parseChest(nextItem));
            } else if (itemType.equals("exit")) {
                mazeStructures.add(parseExit(nextItem));
            } else { // if (itemType.equals("tile")) {
                mazeStructures.add(parseTile(nextItem));
            }
        }
        return mazeStructures;
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses items from JSON object
    private List<Item> parseItems(JSONObject jsonObject) {
        List<Item> itemPouch = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            itemPouch.add(parseItem(nextItem));
        }
        return itemPouch;
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses a single item from JSON object
    private Item parseItem(JSONObject jsonObject) {
        String itemType = jsonObject.getString("item type");
        if (itemType.equals("clue")) {
            return parseClue(jsonObject);
        } else { //if (itemType.equals("key"))
            return parseKey(jsonObject);
        }
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses chest from JSON object
    private MazeStructure parseChest(JSONObject jsonObject) {
        Chest chest = new Chest(parseItem((JSONObject) jsonObject.get("item")), jsonObject.getString("id"));
        chest.setStatus(jsonObject.getString("status"));
        return chest;
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses clue from JSON object
    private Item parseClue(JSONObject jsonObject) {
        return new Clue(jsonObject.getString("info"));
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses key from JSON object
    private Item parseKey(JSONObject jsonObject) {
        return new Key(jsonObject.getString("id"));
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses exit from JSON object
    private MazeStructure parseExit(JSONObject jsonObject) {
        return new Exit(jsonObject.getString("password"), jsonObject.getString("id"));
    }

    // Method based on JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses tile from JSON object
    private MazeStructure parseTile(JSONObject jsonObject) {
        boolean hasItem = !jsonObject.get("item").equals("");
        String tileId = jsonObject.getString("id");
        if (hasItem) {
            JSONObject item = jsonObject.getJSONObject("item");
            return new Tile(parseItem(item), tileId);
        }
        return new Tile(tileId);
    }
}
