package ui;

import model.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// General level that includes the actions that
// a player may take at any level.
public abstract class Level extends DarkGame {

    protected static final String JSON_STORE = "./data/level.json";
    protected Player player;
    protected Exit exit;
    protected List<MazeStructure> mazeStructures;

    // constructor
    // EFFECTS: creates an empty maze structures list
    public Level() {
        super("");
        mazeStructures = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: initializes each maze structure
    protected abstract void constructMaze();

    // MODIFIES: this
    // EFFECTS: establishes the relationships between maze structures
    protected abstract void connectMaze();

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
        return canMove;
    }

    // EFFECTS: interacts with any items or maze structures in the
    // current player location; may exit, open chest, or pick up item
    protected void refresh() {
        if (player.getLocation() instanceof Exit) {
            tryToExit();
        } else if (player.getLocation() instanceof Chest) {
            tryToOpenChest();
        } else if (player.getLocation() instanceof Tile) {
            if (((Tile) player.getLocation()).getItem() != null) {
                pickUpItem();
            }
        }
    }

    // REQUIRES: current player location is a tile with an item
    // EFFECTS: adds the item to the player's item pouch if not already in pouch
    protected void pickUpItem() {
        Item item = ((Tile) player.getLocation()).getItem();
        String itemType = item.getClass().getSimpleName();
        String title = "You found a " + itemType + "!";
        if (newItem(item)) {
            showMessage("It has been added to your item pouch.", title);
            player.addItemToPouch(item);
        } else {
            showMessage("But you've already found this item before.", title);
        }
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
        String title = "You found a chest!";
        Chest chest = (Chest) player.getLocation();
        if (chest.getStatus().equals("unlocked")) {
            showMessage("You've already opened this chest.", title);
        } else {
            for (Item i : player.getItemPouch().getItemPouch()) {
                if (i instanceof Key) {
                    if (((Key) i).canOpenChest(chest)) {
                        Item treasure = ((Key) i).openChest(chest);
                        player.addItemToPouch(treasure);
                        showMessage("You opened the chest and got an item!", title);
                    } else {
                        showMessage("You don't have a key that unlocks this chest.", title);
                    }
                }
            }
        }
    }

    // REQUIRES: current player location is at the exit
    // EFFECTS: if the right password is typed in, the player successfully saves and
    // exits the game; otherwise, they have to try again or move somewhere else
    protected void tryToExit() {
        String input = (String) JOptionPane.showInputDialog(null, "Enter the password:",
                "You found the exit!", JOptionPane.PLAIN_MESSAGE, null,
                null, null);
        if (input.equals(exit.getPassword())) {
            showMessage("You have escaped!", "Success!");
            level = this;
            tryToQuitGame();
        } else {
            Object[] options = {"Yes, try again.", "No, move somewhere else."};
            int choice = JOptionPane.showOptionDialog(null, "Wrong password. Try again?",
                    "Nope.", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            if (choice == 0) { // yes, try again
                tryToExit();
            }
        }
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
