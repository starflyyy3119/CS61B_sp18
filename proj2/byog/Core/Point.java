package byog.Core;

/**
 * Point can be accessed by the class in the same package
 */
class Point {
    int x;
    int y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != this.getClass()) { return false; }

        Point that = (Point) o;
        if (this.x != that.x) return false;
        return this.y == that.y;
    }

    /**
     * Generator point that x is in [minX, maxX), y is in [minY, maxY)
     * @param minX the lower bound of x
     * @param maxX the upper bound of x
     * @param minY the lower bound of y
     * @param maxY the upper bound of y
     * @return a point satisfies the specific requirement
     */
    static Point pointGenerator(int minX, int maxX, int minY, int maxY) {
        int x = RandomUtils.uniform(Game.RANDOM, minX, maxX);
        int y = RandomUtils.uniform(Game.RANDOM, minY, maxY);
        return new Point(x, y);
    }

    static int Hamming(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
