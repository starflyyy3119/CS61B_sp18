package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final int seed = 39392;
    private static final Random RANDOM = new Random(seed);
    /**
     * Adds a hexagon of side length to a given position in the world
     * @param sideLength is the sideLength of the Hexagon
     * @param world is the TETile
     * @param X is the x index
     * @param Y is the y index
     */
    private static void addHexagon(int sideLength, TETile[][] world, int X, int Y) {
        TETile filledWith = randomTile();

        for (int i = 0; i < 2 * sideLength; i++) {
            int X_now, Y_now, length_now;
            if (i < sideLength) {
                X_now = X - i;
                length_now = sideLength + 2 * i;
            } else { // i >= sideLength
                int cast_i = 2 * sideLength - i - 1;
                X_now = X - cast_i;
                length_now = sideLength + 2 * cast_i;
            }
            Y_now = Y - i;
            addLine(world, filledWith, X_now, Y_now, length_now);
        }
    }

    private static void addLine(TETile[][] world, TETile filledWith, int X, int Y, int length) {
        for (int i = 0; i < length; i++) {
            world[X + i][Y] = filledWith;
        }
    }

    /**
     * Determine the index's position
     * @param sideLength side length
     * @return the positions of the block
     */
    private static int[][] calPosition(int sideLength) {
        int[][] positions = new int[19][2];
        int longest = sideLength + 2 * (sideLength - 1);
        int width = 3 * longest + 2 * sideLength;
        int height = 3 * 2 * sideLength;

        int leftWidthRest = (WIDTH - width) / 2;
        int leftHeightRest = (HEIGHT - height) / 2;

        int startX = leftWidthRest;
        int startY = HEIGHT - leftHeightRest;

        int cnt = 0;
        int doubleSideLengthMinusOne = sideLength * 2 - 1;
        for (int i = 0; i < 5; i++) {
            int cast_i = (i < 3) ? i : (4 - i);
            for (int j = 0; j < (3 + cast_i); j++) {
                positions[cnt][0] = startX + i * doubleSideLengthMinusOne;
                positions[cnt][1] = startY + cast_i * sideLength - j * 2 * sideLength;
                cnt++;
            }
        }
        return positions;
    }

    /**
     * Choose a type of TETile randomly
     * @return a type of TETile
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            case 5: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    private static void draw(int[][] positions, int sideLength, TETile[][] world) {
        for (int i = 0; i < 19; i++) {
            int X = positions[i][0];
            int Y = positions[i][1];
            addHexagon(sideLength, world, X, Y);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int[][] positions = calPosition(2);
        draw(positions, 2, world);

        ter.renderFrame(world);

    }
}
