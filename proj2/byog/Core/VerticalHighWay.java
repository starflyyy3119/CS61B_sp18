package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class VerticalHighWay extends HighWay {
    final int x;
    VerticalHighWay(int x, int start, int end) {
        super(start, end);
        this.x = x;
    }

    @Override
    void addHighWay(TETile[][] world, boolean[][] isOccupyChecker) {
        for (int i = start; i <= end; i++) {
            world[x][i] = Tileset.FLOOR;
            isOccupyChecker[x][i] = true;
        }
    }

    static VerticalHighWay vHWGenerator(Room down, Room up) {
        int lowerBound = Math.max(down.min.x, up.min.x);
        int upperBound = Math.min(down.max.x, up.max.x) + 1;
        int randNum = Game.RANDOM.nextInt(upperBound - lowerBound);
        return new VerticalHighWay(lowerBound + randNum, down.max.y + 1, up.min.y - 1);
    }
}
