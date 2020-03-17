package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 30;
    public static Random RANDOM;
    public static Long seedLong;
    public static final int CANVASHEIGHT = HEIGHT + 2;

    private static boolean hasFlower = false;
    private static boolean gameWin = false;
    private static boolean SEED = false;
    private static boolean SAVE = false;
    private String fileName = "load.txt";
    private World wd;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, CANVASHEIGHT);

        mainMenu(null);

        setWorld(getUserInput());

        ter.renderFrame(wd.getWorld(), null);

        operations();

        System.exit(5);
    }

    private void operations() {
        while (true) {
            if (StdDraw.isMousePressed()) {
                TETile tile = wd.getTile(new Point((int) StdDraw.mouseX(), (int) StdDraw.mouseY()));
                drawHint(tile);
            }
            if (StdDraw.hasNextKeyTyped()) {
                String opt = getUserInput();
                movePlayer(wd, opt.charAt(0));
                ter.renderFrame(wd.getWorld(), null);
            }
            if (SAVE) {
                break;
            }
            if (gameWin) {
                mainMenu(null);
                StdDraw.pause(500);
                break;
            }
        }
    }

    private void drawHint(TETile tile) {
        String s;
        if (tile.equals(Tileset.FLOOR)) {
            s = "floor";
        } else if (tile.equals(Tileset.WALL)) {
            s = "wall";
        } else if (tile.equals(Tileset.LOCKED_DOOR)) {
            s = "locked door";
        } else if (tile.equals(Tileset.PLAYER)) {
            s = "you";
        } else {
            s = "outside";
        }
        ter.renderFrame(wd.getWorld(), s);
    }

    private void setWorld(String firstOption) {
        switch (firstOption) {
            case "n":
                SEED = true;
                mainMenu(null);
                seedLong = getSeed();
                RANDOM = new Random(seedLong);
                wd = new World();
                break;
            case "l":
                wd = new World(fileName);
                seedLong = Long.parseLong(InOutput.read("seed.txt"));
                RANDOM = new Random(seedLong);
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * Get length 1 string of the input
     * @return a string with length 1 of the user input
     */
    private String getUserInput() {
        String input = "";
        while(!StdDraw.hasNextKeyTyped()) {        // Finally find the bug, without this while loop, the program will check that whether I have
            StdDraw.pause(5);                  // type a character. So if I type after this check, I will miss the opportunity to get that char.
        }
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
        }
        return input.toLowerCase();
    }

    private Long getSeed() {
        StringBuilder seed = new StringBuilder();
        String now = getUserInput();
        while (!now.equals("s")) {
            seed.append(now);
            mainMenu(seed.toString());
            now = getUserInput();
        }
        SEED = false;
        return Long.parseLong(seed.toString());
    }

    private void mainMenu(String s) {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);

        // set the font
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        Font BigFont = new Font("Monaco", Font.BOLD, 30);

        if (gameWin) {
            StdDraw.setPenColor(Color.white);
            StdDraw.setFont(BigFont);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "You Win!");
        } else {
            StdDraw.setFont(BigFont);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 4.0 * 3, "CS61B: GXY's GAME");

            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "New Game (N)");
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 1.2, "Load Game (L)");
            StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0 - 2.4, "Quit (Q)");
        }

        if (SEED) {
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 4.0 + 1.5, "Please enter a seed(Press s to confirm)");
        }

        if (s != null) {
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 4.0, s);
        }
        StdDraw.show();
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
            seedLong = Long.parseLong(input.split("([a-z]+)")[1]);
            RANDOM = new Random(seedLong);

            operations = input.split("[\\d]+")[1];
            operations = operations.substring(1);
            wd = new World();
        } else if (firstOption == 'l') {
            operations = input.substring(1);
            wd = new World(fileName);
            seedLong = Long.parseLong(InOutput.read("seed.txt"));
            RANDOM = new Random(seedLong);
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
        Point flower = wd.getFlower();
        // Point monster = wd.getMonster();
        if (wd.getTile(newPos).equals(Tileset.FLOOR)) {
            wd.setFloor(player);
            wd.setPlayer(newPos);
        } else if (newPos.equals(lockedDoor)) {
            if (hasFlower) {
                gameWin = true;
                wd.setUnlockedDoor(newPos);
                wd.setFloor(player);
            }
        } else if (newPos.equals(flower)) {
            hasFlower = true;
            wd.setPlayer(newPos);
            wd.setFloor(player);
        }
    }

    /**
     * Helper function for playWithInputString, used to moAutograderSecurityManager.java)
    at java.lang.Runtime.exit:113 (Runtime.java)ve the player
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
                //InOutput.write(fileName, TETile.toString(wd.getWorld()));
                InOutput.writeObject(fileName, wd);
                InOutput.write("seed.txt", Long.toString(seedLong));
                SAVE = true;
                break;
            case ':':
                break;
        }
    }
}
