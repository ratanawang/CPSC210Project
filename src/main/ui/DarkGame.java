package ui;

import model.Clue;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// The main game, which shows the main menu
// and allows the player to play different levels.
public class DarkGame implements ActionListener {

    protected static final int SIDE_LENGTH = 300;
    protected JFrame frame;
    protected TitledBorder titledBorder;
    protected EmptyBorder emptyBorder;
    protected CompoundBorder compoundBorder;
    protected JPanel currentPanel;
    protected KeyboardListener keyListener;

    // main menu
    protected JPanel panelMainMenu;
    protected JButton buttonPlay;
    protected JButton buttonRules;
    protected JButton buttonQuit;

    // levels
    protected JPanel panelLevels;
    protected JButton buttonLevel1;
    protected JButton buttonBackToMainMenu;
    protected Level level;

    // level 1
    protected JPanel panelLevel1;
    protected JButton buttonViewItems;
    protected JButton buttonSaveAndQuit;
    protected JLabel movement;

    // items
    protected JPanel panelItems;
    protected JButton buttonBackToLevel1;

    // data persistence
    protected static final String JSON_STORE = "./data/level.json";
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;

    // constructor
    // EFFECTS: creates a new game by initializing the GUI and other
    // necessary data persistence objects, and shows the main menu
    public DarkGame() {
        initializeEverything();
        showPanel("Main Menu", panelMainMenu);
    }

    // constructor for subclass purposes
    // EFFECTS: creates a new game, but prevents any GUI from forming
    public DarkGame(String subclass) {
        initializeEverything();
    }

    // EFFECTS: listens for closing of window, then quits application
    private void addCloseListenerToFrame() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (level != null) {
                    level.player.quitGame();
                }
                quitWithNoEventLog();
            }
        });
    }

    // REQUIRES: level is null
    // EFFECTS: prints statement that clarifies lack of event log
    private void quitWithNoEventLog() {
        System.out.println("No level was created.");
        System.out.println("===== Event Log: N/A =====");
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: initializes all GUI and data persistence elements
    public void initializeEverything() {
        initializeBorders();
        initializeMainMenuButtons();
        initializeMainMenuPanel();
        initializeLevelsButtons();
        initializeLevelsPanel();
        initializeLevel1Buttons();
        initializeLevel1Panel();
        initializeFrame();
        addCloseListenerToFrame();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        currentPanel = new JPanel(); // throwaway panel
        keyListener = new KeyboardListener();
    }

    // MODIFIES: this
    // EFFECTS: initializes the borders of the frame
    private void initializeBorders() {
        titledBorder = BorderFactory.createTitledBorder("");
        emptyBorder = new EmptyBorder(20, 20, 20, 20);
        compoundBorder = new CompoundBorder(emptyBorder, titledBorder);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu panel with
    // its layout, border, logo, and buttons
    private void initializeMainMenuPanel() {
        panelMainMenu = new JPanel();
        panelMainMenu.setLayout(new BoxLayout(panelMainMenu, BoxLayout.PAGE_AXIS));
        panelMainMenu.setBorder(compoundBorder);
        panelMainMenu.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 10)));
        try {
            BufferedImage logo = ImageIO.read(new File("./data/game_logo.jpg"));
            JLabel logoLabel = new JLabel(new ImageIcon(logo));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMainMenu.add(logoLabel);
            panelMainMenu.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 10)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panelMainMenu.add(buttonPlay);
        panelMainMenu.add(buttonRules);
        panelMainMenu.add(buttonQuit);
        panelMainMenu.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 10)));
    }

    // MODIFIES: this
    // EFFECTS: sets up the levels (menu) panel with
    // its layout, border, and buttons
    private void initializeLevelsPanel() {
        panelLevels = new JPanel();
        panelLevels.setLayout(new BoxLayout(panelLevels, BoxLayout.PAGE_AXIS));
        panelLevels.setBorder(compoundBorder);
        panelLevels.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 3)));
        panelLevels.add(buttonLevel1);
        panelLevels.add(buttonBackToMainMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets up the level 1 panel with
    // its layout, border, and buttons
    private void initializeLevel1Panel() {
        panelLevel1 = new JPanel();
        panelLevel1.setLayout(new BoxLayout(panelLevel1, BoxLayout.PAGE_AXIS));
        panelLevel1.setBorder(compoundBorder);
        panelLevel1.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 10)));
        panelLevel1.add(movement);
        panelLevel1.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 3)));
        panelLevel1.add(buttonViewItems);
        panelLevel1.add(buttonSaveAndQuit);
        panelLevel1.add(Box.createRigidArea(new Dimension(SIDE_LENGTH, SIDE_LENGTH / 10)));
    }

    // MODIFIES: this
    // EFFECTS: sets up the item-viewing panel with
    // its layout, border, buttons, and items to view
    private void initializeItemsPanel() {
        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.PAGE_AXIS));
        panelItems.setBorder(compoundBorder);
        panelItems.add(buttonBackToLevel1);
        if (level.player.getItemPouch().getItemPouch().isEmpty()) {
            JLabel noItems = new JLabel("item pouch is empty");
            noItems.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelItems.add(noItems);
        } else {
            listItems();
        }
    }

    // REQUIRES: player's item pouch is not empty
    // MODIFIES: this
    // EFFECTS: lists clues as hyperlinks and other items as labels
    private void listItems() {
        List<Item> itemList = level.player.getItemPouch().getItemPouch();
        for (Item i : itemList) {
            if (i instanceof Clue) {
                JLabel readClue = new JLabel("- Clue (click to read)");
                readClue.setForeground(Color.BLUE.darker());
                readClue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                readClue.setAlignmentX(Component.LEFT_ALIGNMENT);
                readClue.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        level.player.readClue((Clue) i);
                        showMessage(((Clue) i).getInfo(), "Read clue:");
                    }
                });
                panelItems.add(readClue);
            } else {
                JLabel tempLabel = new JLabel("- " + i.getClass().getSimpleName());
                tempLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelItems.add(tempLabel);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the main frame
    private void initializeFrame() {
        frame = new JFrame("Dark");
        frame.setMinimumSize(new Dimension(SIDE_LENGTH, SIDE_LENGTH));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.addKeyListener(keyListener);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu buttons
    private void initializeMainMenuButtons() {
        buttonPlay = new JButton("play");
        buttonRules = new JButton("rules");
        buttonQuit = new JButton("quit");
        buttonPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonRules.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPlay.addActionListener(this);
        buttonRules.addActionListener(this);
        buttonQuit.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up the levels (menu) buttons
    private void initializeLevelsButtons() {
        buttonLevel1 = new JButton("level 1");
        buttonBackToMainMenu = new JButton("back to main menu");
        buttonLevel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBackToMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonLevel1.addActionListener(this);
        buttonBackToMainMenu.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up the level 1 buttons
    private void initializeLevel1Buttons() {
        buttonViewItems = new JButton("view items");
        buttonSaveAndQuit = new JButton("save and quit");
        buttonViewItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSaveAndQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonViewItems.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        buttonSaveAndQuit.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        buttonViewItems.addActionListener(this);
        buttonSaveAndQuit.addActionListener(this);

        movement = new JLabel("Use the arrow keys to move around.");
        movement.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the buttons seen when viewing items
    private void initializeItemsButtons() {
        buttonBackToLevel1 = new JButton("back to level 1");
        buttonBackToLevel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonBackToLevel1.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: shows the new panel, changes the border title
    protected void showPanel(String title, JPanel newPanel) {
        changeBorderTitle(title);
        frame.remove(currentPanel);
        currentPanel = newPanel;
        frame.getContentPane().add(newPanel, BorderLayout.CENTER);
        refreshFrame();
    }

    // EFFECTS: shows the rules in a pop-up message
    protected void displayRules() {
        showMessage("Here are the rules:\n"
                + "You are trapped in a dark room.\n"
                + "Explore the room to find useful items and clues.\n"
                + "Use the arrow keys to move around.\n"
                + "Good luck.", "Rules");
    }

    // MODIFIES: this
    // EFFECTS: changes the title of the border
    protected void changeBorderTitle(String newTitle) {
        titledBorder.setTitle(newTitle);
        frame.repaint();
    }

    // EFFECTS: shows a pop-up message with a title
    protected void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: presents the option to save and quit the game, then
    // saves or quits based on user choice
    protected void tryToQuitGame() {
        Object[] options = {"Yes, quit.", "No, continue."};
        int choice = JOptionPane.showOptionDialog(null, "You are about to quit the game.",
                "Save and quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (choice == 0) { // yes, save and quit
            if (level != null) {
                level.saveLevel();
                level.player.quitGame();
            }
            quitWithNoEventLog();
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes frame so that changes are shown
    protected void refreshFrame() {
        frame.pack();
        frame.setVisible(true);
    }

    // Method based on WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: loads level from file if it exists;
    // otherwise, create a new level 1
    protected Level1 loadLevel1() {
        try {
            return (Level1) jsonReader.read();
        } catch (FileNotFoundException e) {
            showMessage("A new level will be created.", "File not found");
        } catch (IOException e) {
            showMessage("A new level will be created.", "Unable to read from file: " + JSON_STORE);
        }
        return new Level1();
    }

    // MODIFIES: this
    // EFFECTS: performs the relevant action based on
    // which button was pressed (defaults to save/quit);
    // activates/deactivates the keyListener depending on
    // whether the level is currently running
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("play")) {
            showPanel("Levels", panelLevels);
        } else if (command.equals("rules")) {
            displayRules();
        } else if (command.equals("level 1")) {
            setUpLevel1();
        } else if (command.equals("back to level 1")) {
            showPanel("Level 1", panelLevel1);
        } else if (command.equals("back to main menu")) {
            showPanel("Main Menu", panelMainMenu);
        } else if (command.equals("view items")) {
            initializeItemsButtons();
            initializeItemsPanel();
            showPanel("Items", panelItems);
        } else {
            tryToQuitGame();
        }
        controlKeyListener();
    }

    // MODIFIES: this
    // EFFECTS: initializes the level if null, shows the GUI
    private void setUpLevel1() {
        if (level == null) {
            level = loadLevel1();
        }
        keyListener.setLevel(level);
        showPanel("Level 1", panelLevel1);
        showMessage("You are now playing Level 1.", "start!");
    }

    // MODIFIES: this
    // EFFECTS: deactivates the keyListener if the current
    // panel is not playing a level
    private void controlKeyListener() {
        if (currentPanel != panelLevel1) {
            frame.removeKeyListener(keyListener);
        } else {
            frame.addKeyListener(keyListener);
        }
    }

    // REQUIRES: currentPanel is a playing level
    // MODIFIES: this
    // EFFECTS: prints out player movement based on arrow key input
    protected void showMovement(String direction, boolean moved) {
        if (moved) {
            movement.setText("You moved " + direction + "!");
        } else {
            movement.setText("You can't go " + direction + ".");
            showMessage(movement.getText(), "oh no!");
        }
        System.out.println(movement.getText());
        frame.repaint();
    }
}
