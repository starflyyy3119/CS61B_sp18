package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static Random RANDOM;
    public TETile[][] preWorld;

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
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representaworld[player.x][player.y] = Tileset.PLAYER;tion of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        String[] s = input.split("[\\d]+");
        int seed = Integer.parseInt(input.split("([a-z]+)")[1]);

        RANDOM = new Random(seed);

        String[] operation = s[1].split(":");

            TETile[][] world;

            WorldGenerator wg = new WorldGenerator();
        world = wg.getWorld();
        System.out.println(TETile.toString(world));

        String opt = operation[0];
        Point player = wg.getPlayer();
        Point lockedDoor = wg.getLockedDoor();
        for (int i = 0; i < opt.length(); i++) {
            if (opt.charAt(i) == 'w') {
                if (world[player.x][player.y + 1].equals(Tileset.FLOOR)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    player = new Point(player.x, player.y + 1);
                    world[player.x][player.y] = Tileset.PLAYER;

                }
            } else if (opt.charAt(i) == 'a') {
                if (world[player.x-1][player.y].equals(Tileset.FLOOR)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    player = new Point(player.x - 1, player.y);
                    world[player.x][player.y] = Tileset.PLAYER;
                }
            } else if (opt.charAt(i) == 's') {
                if (world[player.x][player.y - 1].equals(Tileset.FLOOR)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    player = new Point(player.x, player.y - 1);
                    world[player.x][player.y] = Tileset.PLAYER;
                }
            } else {    // opt.charAt(i) == 'd'
                if (world[player.x+1][player.y].equals(Tileset.FLOOR)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    player = new Point(player.x + 1, player.y);
                    world[player.x][player.y] = Tileset.PLAYER;
                }
            }
        }

        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }
}
