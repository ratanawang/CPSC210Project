package ui;

import model.*;

import java.util.Scanner;

public abstract class Level {

    protected Player player;
    protected Exit exit;
    protected Scanner scanner = new Scanner(System.in);

    protected abstract void constructMaze();

    protected abstract void connectMaze();

    protected void nextStep() {
        System.out.println("Move up (w), down (s), left (a), right (d), or view item pouch (v).");
        String input = scanner.nextLine();
        if (input.equals("v")) {
            viewItems();
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

    protected void viewItems() {
        if (player.getItemPouch().isEmpty()) {
            System.out.println("You currently have no items.");
            nextStep();
            return;
        }
        System.out.println("You have the following items in your item pouch:");
        for (Item i : player.getItemPouch()) {
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

    protected void pickUpItem() {
        Item i = ((Tile) player.getLocation()).getItem();
        System.out.println("You found a " + i.getClass().getSimpleName() + "!");
        System.out.println("It has been added to your item pouch.");
        player.addItemToPouch(i);
        nextStep();
    }

    protected void tryToOpenChest() {
        Chest chest = (Chest) player.getLocation();
        if (player.getItemPouch().contains(chest.getItem())) {
            System.out.println("You've already opened this chest.");
            nextStep();
            return;
        }
        System.out.println("You found a chest!");
        for (Item i : player.getItemPouch()) {
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


    protected void tryToExit() {
        System.out.println("You have arrived at the exit.");
        System.out.println("Please type in the password:");
        String input = scanner.nextLine();
        if (input.equals(exit.getPassword())) {
            exitMaze();
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

    protected void exitMaze() {
        System.out.println("Success!");
        System.exit(0);
    }

}
