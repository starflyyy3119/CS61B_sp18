package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.FileNotFoundException;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 30;
    public static final int HEIGHT = 20;

    public static Random RANDOM;
    private boolean gameWin;
    private String fileName = "./byog/Core/load.txt";
    private World wd;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        // solve the input
        String opts = inputSolver(input);

        // do some operations
        for (int i = 0; i < opts.length(); i++) {
            movePlayer(wd, opts.charAt(i));
        }

        return wd.getWorld();
    }

    /**
     * Helper function for the playWithInputString, used to process the input String
     * @param input String
     * @return the operations
     */
    private String inputSolver(String input) {
        input = input.toLowerCase();

        char firstOption = input.charAt(0);

        String operations;
        if (firstOption == 'n') {
            int seed = Integer.parseInt(input.split("([a-z]+)")[1]);
            RANDOM = new Random(seed);

            operations = input.split("[\\d]+")[1];
            operations = operations.substring(1);
            wd = new World();
        } else if (firstOption == 'l') {
            operations = input.substring(1);
            try {
                wd = new World(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            wd = null;
            operations = null;
            System.exit(0);
        }

        StringBuilder sb = new StringBuilder();

        String[] opts = operations.split(":");
        for (String opt : opts) {
            sb.append(opt);
        }

        return sb.toString();
    }

    /**
     * Helper function for movePlayer
     * @param wd World Class instance
     * @param newPos the new position of the player
     */
    private void moveOneStep(World wd, Point newPos) {
        Point player = wd.getPlayer();
        Point lockedDoor = wd.getLockedDoor();
        if (wd.getTile(newPos).equals(Tileset.FLOOR)) {
            wd.setFloor(player);
            wd.setPlayer(newPos);
        } else if (newPos.equals(lockedDoor)) {
            gameWin = true;
            wd.setUnlockedDoor(newPos);
            wd.setFloor(player);
        }
    }

    /**
     * Helper function for playWithInputString, used to move the player
     * @param wd World Class instance
     * @param opt an operation
     */
    private void movePlayer(World wd, char opt) {
        Point newPos;
        Point nowPlayer = wd.getPlayer();
        switch (opt) {
            case 'w':
                newPos = new Point(nowPlayer.x, nowPlayer.y + 1);
                moveOneStep(wd, newPos);
                break;
            case 'a':
                newPos = new Point(nowPlayer.x - 1, nowPlayer.y);
                moveOneStep(wd, newPos);
                break;
            case 's':
                newPos = new Point(nowPlayer.x, nowPlayer.y - 1);
                moveOneStep(wd, newPos);
                break;
            case 'd':
                newPos = new Point(nowPlayer.x + 1, nowPlayer.y);
                moveOneStep(wd, newPos);
                break;
            case 'q':
                InOutput.write(fileName, TETile.toString(wd.getWorld()));
                System.out.println(TETile.toString(wd.getWorld()));
                System.exit(0);
        }
    }
}
