package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class HorizontalHighWay extends HighWay {
    final int y;

    HorizontalHighWay(int y, int start, int end) {
        super(start, end);
        this.y = y;
    }

    @Override
    void addHighWay(TETile[][] world, boolean[][] isOccupyChecker) {
        for (int i = start; i <= end; i++) {
            world[i][y] = Tileset.FLOOR;
            isOccupyChecker[i][y] = true;
        }
    }

    static HorizontalHighWay hHWGenerator(Room left, Room right) {
        int lowerBound = Math.max(left.min.y, right.min.y);
        int upperBound = Math.min(left.max.y, right.max.y) + 1;
        int randNum = Game.RANDOM.nextInt(upperBound - lowerBound);
        return new HorizontalHighWay(lowerBound + randNum, left.max.x + 1, right.min.x - 1);
    }
}
