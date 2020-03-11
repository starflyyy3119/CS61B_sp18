package byog.Core;

import byog.TileEngine.TETile;

abstract class HighWay {
    final int start;
    final int end;

    protected HighWay(int start, int end) {
        this.start = start;
        this.end = end;
    }

    abstract void addHighWay(TETile[][] world, boolean[][] isOccupyChecker);
}
