package model;

// An exit represents the door of freedom
// through which players can escape the room.
// It has a password and an ID.
public class Exit extends MazeStructure {

    private String password;

    // Constructs an exit
    // EFFECTS: creates an exit with a password and ID
    public Exit(String password, String id) {
        this.password = password;
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

}
