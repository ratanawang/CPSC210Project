package ui;

import model.*;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// General level that includes the actions that
// a player may take at any level.
public abstract class Level {

    protected static final String JSON_STORE = "./data/level.json";
    protected Player player;
    protected Exit exit;
    protected Scanner scanner = new Scanner(System.in);
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    protected List<MazeStructure> mazeStructures;

    // constructor
    // EFFECTS: creates the necessary data persistence objects
    // and creates an empty maze structures list
    public Level() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mazeStructures = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: initializes each maze structure
    protected abstract void constructMaze();

    // MODIFIES: this
    // EFFECTS: establishes the relationships between maze structures
    protected abstract void connectMaze();

    // EFFECTS: asks the user what their next step will be, which
    // can be either moving in any direction, viewing their item pouch,
    // or saving their progress and quitting
    protected void nextStep() {
        System.out.println("Move up (w), down (s), left (a), right (d), view item pouch (v), or save and quit (q).");
        String input = scanner.nextLine();
        if (input.equals("v")) {
            viewItems();
        } else if (input.equals("q")) {
            saveLevel();
            System.exit(0);
        } else if (input.equals("w") || input.equals("s") || input.equals("a") || input.equals("d")) {
            String direction = determineDirection(input);
            if (canMove(direction)) {
                player.move(direction);
                System.out.println("You moved " + direction);
                refresh();
            } else {
                nextStep();
            }
        } else {
            System.out.println("Invalid input. Try again.");
            nextStep();
        }
    }

    // EFFECTS: prints out all items in a player's item pouch, unless empty,
    // and if an item is a clue, lets the player choose to read it
    protected void viewItems() {
        if (player.getItemPouch().getItemPouch().isEmpty()) {
            System.out.println("You currently have no items.");
            nextStep();
            return;
        }
        System.out.println("You have the following items in your item pouch:");
        for (Item i : player.getItemPouch().getItemPouch()) {
            System.out.println("- " + i.getClass().getSimpleName());
            if (i instanceof Clue) {
                System.out.println("Read clue? (y/n)");
                String input = scanner.nextLine();
                if (input.equals("y")) {
                    System.out.println("--> " + ((Clue) i).getInfo());
                }
            }
        }
        nextStep();
    }

    // REQUIRES: input is one of "w", "s", "a", or "d"
    // EFFECTS: returns a direction based on single-letter keyboard input
    protected String determineDirection(String input) {
        switch (input) {
            case "w":
                return "up";
            case "s":
                return "down";
            case "a":
                return "left";
            default:
                return "right";
        }
    }

    // EFFECTS: returns true if there is a maze structure in the
    // intended direction of movement, otherwise false
    protected boolean canMove(String direction) {
        boolean canMove = false;
        switch (direction) {
            case "up":
                canMove = player.getLocation().getUp() != null;
                break;
            case "down":
                canMove = player.getLocation().getDown() != null;
                break;
            case "left":
                canMove = player.getLocation().getLeft() != null;
                break;
            case "right":
                canMove = player.getLocation().getRight() != null;
                break;
        }
        if (canMove) {
            return true;
        } else {
            System.out.println("You can't go there. Pick a different direction.");
            return false;
        }
    }

    // EFFECTS: interacts with any items or maze structures in the
    // current player location; may exit, open chest, or pick up item
    protected void refresh() {
        if (player.getLocation() instanceof Exit) {
            tryToExit();
        } else if (player.getLocation() instanceof Chest) {
            tryToOpenChest();
        } else if (player.getLocation() instanceof Tile) {
            if (((Tile) player.getLocation()).getItem() == null) {
                nextStep();
            } else {
                pickUpItem();
            }
        }
    }

    // REQUIRES: current player location is a tile with an item
    // EFFECTS: adds the item to the player's item pouch if not already in pouch
    protected void pickUpItem() {
        Item item = ((Tile) player.getLocation()).getItem();
        String itemType = item.getClass().getSimpleName();
        if (newItem(item)) {
            System.out.println("You found a " + itemType + "!");
            System.out.println("It has been added to your item pouch.");
            player.addItemToPouch(item);
        } else {
            System.out.println("You've already found this " + itemType + ".");
        }
        nextStep();
    }

    // EFFECTS: returns true if this item has not already been
    // added to a player's item pouch
    protected boolean newItem(Item item) {
        String itemType = item.getClass().getSimpleName();
        for (Item i : player.getItemPouch().getItemPouch()) {
            String type = i.getClass().getSimpleName();
            if (itemType.equals(type)) {
                if (type.equals("Clue")) {
                    if (((Clue) item).getInfo().equals(((Clue) i).getInfo())) {
                        return false;
                    }
                } else if (type.equals("Key")) {
                    if (((Key) item).getId().equals(((Key) i).getId())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // REQUIRES: current player location is at a chest
    // EFFECTS: if this chest has not been previously opened, and if the
    // corresponding key is within the player's item pouch, open the chest
    // and add the item inside to the pouch
    protected void tryToOpenChest() {
        Chest chest = (Chest) player.getLocation();
        if (chest.getStatus().equals("unlocked")) {
            System.out.println("You've already opened this chest.");
            nextStep();
            return;
        }
        System.out.println("You found a chest!");
        for (Item i : player.getItemPouch().getItemPouch()) {
            if (i instanceof Key) {
                if (((Key) i).canOpenChest(chest)) {
                    Item treasure = ((Key) i).openChest(chest);
                    player.addItemToPouch(treasure);
                    System.out.println("You opened the chest and got an item!");
                    break;
                }
            }
        }
        if (chest.getStatus().equals("locked")) {
            System.out.println("However, you don't have a key that unlocks this chest.");
        }
        nextStep();
    }

    // REQUIRES: current player location is at the exit
    // EFFECTS: if the right password is typed in, the player successfully saves and
    // exits the game; otherwise, they have to try again or move somewhere else
    protected void tryToExit() {
        System.out.println("You have arrived at the exit.");
        System.out.println("Please type in the password:");
        String input = scanner.nextLine();
        if (input.equals(exit.getPassword())) {
            exitMaze();
            saveLevel();
        } else {
            System.out.println("Incorrect password.");
            System.out.println("1. Try again?");
            System.out.println("2. Move somewhere else");
            input = scanner.nextLine();
            if (input.equals("1")) {
                tryToExit();
            } else if (input.equals("2")) {
                nextStep();
            }
        }
    }

    // EFFECTS: prints out a congratulatory message regarding escape
    protected void exitMaze() {
        System.out.println("Success! You have escaped!");
    }

    // EFFECTS: saves the level to file
    protected abstract void saveLevel();

    // EFFECTS: converts a level into a JSONObject
    public abstract JSONObject toJson();

    // EFFECTS: returns player
    public Player getPlayer() {
        return this.player;
    }

    // EFFECTS: returns maze structures
    public List<MazeStructure> getMazeStructures() {
        return this.mazeStructures;
    }
}
