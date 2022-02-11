package ui;

import model.*;

public class Level1 extends Level {

    private Chest ch0;
    private Clue cl0;
    private Key k0;
    private Tile t0;
    private Tile t1;
    private Tile t2;
    private Tile t3;
    private Tile t4;
    private Tile t5;
    private Tile t6;

    public Level1() {
        System.out.println("You are now playing Level 1.");
        k0 = new Key("c_3_1");
        cl0 = new Clue("47 days after Christmas [mm/dd].");
        constructMaze();
        connectMaze();
        player = new Player(t4);
        nextStep();
    }

    protected void constructMaze() {
        t0 = new Tile("t_1_1");
        t1 = new Tile("t_1_2");
        t2 = new Tile(k0, "t_1_3");
        t3 = new Tile("t_2_1");
        t4 = new Tile("t_2_2");
        t5 = new Tile("t_2_3");
        ch0 = new Chest(cl0, "c_3_1");
        t6 = new Tile("t_3_2");
        exit = new Exit("0210", "e_3_3");
    }

    protected void connectMaze() {
        t0.setDown(t3);
        t0.setRight(t1);
        t1.setDown(t4);
        t1.setLeft(t0);
        t1.setRight(t2);
        t2.setDown(t5);
        t2.setLeft(t1);
        t3.setUp(t0);
        t3.setDown(ch0);
        t3.setRight(t4);
        t4.setAllDirections(t1, t6, t3, t5);
        t5.setUp(t2);
        t5.setDown(exit);
        t5.setLeft(t4);
        ch0.setUp(t3);
        ch0.setRight(t6);
        t6.setUp(t4);
        t6.setLeft(ch0);
        t6.setRight(exit);
        exit.setUp(t5);
        exit.setLeft(t6);
    }

}
