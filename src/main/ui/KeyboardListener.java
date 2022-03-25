package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// a KeyboardListener listens for arrow key player movements
public class KeyboardListener implements KeyListener {

    private Level level;

    // MODIFIES: level
    // EFFECTS: interprets the arrow key pressed, and then
    // moves in the specified direction if possible
    @Override
    public void keyPressed(KeyEvent e) {
        String direction = determineDirection(e.getKeyCode());
        if (direction != null) {
            if (level.canMove(direction)) {
                level.player.move(direction);
                level.showMovement(direction, true);
                level.refresh();
            } else {
                level.showMovement(direction, false);
            }
        }
    }

    // EFFECTS: returns a String direction
    // based on the key pressed
    private String determineDirection(int key) {
        if (key == KeyEvent.VK_UP) {
            return "up";
        } else if (key == KeyEvent.VK_DOWN) {
            return "down";
        } else if (key == KeyEvent.VK_LEFT) {
            return "left";
        } else if (key == KeyEvent.VK_RIGHT) {
            return "right";
        }
        return null;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void setLevel(Level level) {
        this.level = level;
    }

}
