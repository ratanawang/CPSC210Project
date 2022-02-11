package ui;

import java.util.Scanner;

// The main game, which shows the main menu
// and allows the player to play different levels.
public class DarkGame {

    private Scanner scanner;

    // Constructs a game
    // EFFECTS: creates a new game, shows the main menu
    public DarkGame() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the Dark.");
        mainMenu();
    }

    // EFFECTS: shows the main menu, lets the player choose what to do
    private void mainMenu() {
        System.out.println("You are currently viewing the Main Menu.");
        System.out.println("What would you like to do? (e.g. type '1' to play)");
        System.out.println("> 1. play");
        System.out.println("> 2. read the rules");
        System.out.println("> 3. quit");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                play();
                break;
            case "2":
                readTheRules();
                break;
            case "3":
                System.exit(0);
            default:
                invalidInputMessage();
                mainMenu();
                break;
        }
    }

    // EFFECTS: shows player the options for levels
    private void play() {
        System.out.println("You have chosen to play.");
        System.out.println("Please choose a level. (e.g. type '1' for Level 1)");
        showLevels();
    }

    // EFFECTS: lets player choose which level to play, if any
    private void showLevels() {
        System.out.println("> 1. Level 1");
        System.out.println("> 0. Back to Main Menu");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                new Level1();
                break;
            case "0":
                mainMenu();
                break;
            default:
                invalidInputMessage();
                showLevels();
                break;
        }
        mainMenu();
    }

    // EFFECTS: prints out the rules of the game, then returns to main menu
    private void readTheRules() {
        System.out.println("Here are the rules:");
        System.out.println("You are trapped in a dark, dark room.");
        System.out.println("Explore the room to find useful items and clues!");
        System.out.println("Use the keys 'w', 'a', 's', and 'd' to move up, left, down, and right.");
        System.out.println("But make sure not to walk into a wall...");
        System.out.println("Good luck.");
        mainMenu();
    }

    // EFFECTS: prints out the invalid input message
    private void invalidInputMessage() {
        System.out.println("Sorry, your input was invalid. Please try again.");
    }

    public Scanner getScanner() {
        return scanner;
    }

}
