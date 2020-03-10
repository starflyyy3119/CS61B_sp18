package byog.Core;

public class Room implements Comparable<Room>{

    public final static int HALFMINROOMLENGTH = 1;
    public final static int HALFMAXROOMLENGTH = 5;

    /**
     * min is the left-bottom point of the room
     * max is the right-top point of the room
     * center: the center of the room, which is a point
     */
    Point min, max, center;

    Room(Point min, Point max) {
        this.min = min;
        this.max = max;

        center = new Point(min.x + (max.x - min.x) / 2, min.y + (max.y - min.y) / 2);
    }

    @Override
    public int compareTo(Room o) {  // It seems that I should use comparator to pass into the priority queue, bug need to be fixed
        return Integer.compare(Hamming(WorldGenerator.mapCenter, this.center), Hamming(WorldGenerator.mapCenter, o.center));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != this.getClass()) { return false; }

        Room that = (Room) o;
        if (!this.min.equals(that.min)) { return false; }
        return this.max.equals(that.max);
    }

    private int Hamming(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * Generator a room near the nowRoom
     * @param s the direction choice, s == null means generate a brand new Room in the world
     * @param nowRoom the room needs to be expanded
     * @return a room near the nowRoom
     */
    static Room roomGenerator(String s, Room nowRoom) {
        Point center;
        int leftHalf, rightHalf, upHalf, downHalf;
        if (s == null) {
            center = Point.pointGenerator(HALFMAXROOMLENGTH + 1, WorldGenerator.WIDTH - HALFMAXROOMLENGTH - 1, HALFMAXROOMLENGTH + 1, WorldGenerator.HEIGHT - HALFMAXROOMLENGTH - 1);
            leftHalf = generateHalf(HALFMAXROOMLENGTH + 1);
            rightHalf = generateOneHalf(leftHalf, 1);
            downHalf = generateHalf(HALFMAXROOMLENGTH + 1);
            upHalf = generateOneHalf(downHalf, 1);
            return new Room(new Point(center.x - leftHalf, center.y - downHalf), new Point(center.x + rightHalf, center.y + upHalf));
        }
        Point min = nowRoom.min;
        Point max = nowRoom.max;
        if (s.equals("left") || s.equals("right")) {
            if (s.equals("left")) {
                center = Point.pointGenerator(min.x - 1 - HALFMAXROOMLENGTH, min.x - 1, min.y, max.y + 1);
                rightHalf = generateHalf(min.x - center.x);
                leftHalf = generateOneHalf(rightHalf, -1);
            } else { // s.equals("right")
                center = Point.pointGenerator(max.x + 2, max.x + 1 + HALFMAXROOMLENGTH + 1, min.y, max.y + 1);
                leftHalf = generateHalf(center.x - max.x);
                rightHalf = generateOneHalf(leftHalf, 1);
            }
            upHalf = generateHalf( HALFMAXROOMLENGTH + 1);
            downHalf = generateOneHalf(upHalf, -1);
        } else {  // s.equals("up") || s.equals("down")
            if (s.equals("up")) {
                center = Point.pointGenerator(min.x, max.x + 1, max.y + 2, max.y + 1 + HALFMAXROOMLENGTH + 1);
                downHalf = generateHalf(center.y - max.y);
                upHalf = generateOneHalf(downHalf, 1);
            } else { // s.equals("down")
                center = Point.pointGenerator(min.x, max.x + 1, min.y - 1 - HALFMAXROOMLENGTH, min.y - 1);
                upHalf = generateHalf(min.y - center.y);
                downHalf = generateOneHalf(upHalf, -1);
            }
            leftHalf = generateHalf( HALFMAXROOMLENGTH + 1);
            rightHalf = generateOneHalf(leftHalf, 1);
        }
        return new Room(new Point(center.x - leftHalf, center.y - downHalf), new Point(center.x + rightHalf, center.y + upHalf));
    }

    /**
     * Generate half width or length of the room
     * @param bound the biggest length
     * @return a reasonable half width or half height of the room
     */
    static private int generateHalf(int bound) {
        return HALFMINROOMLENGTH + Point.RANDOM.nextInt(bound - HALFMINROOMLENGTH);
    }

    /**
     * Amend the half by +-1 or none
     * @param half given
     * @param amend 1 or -1
     * @return the amended half
     */
    static private int generateOneHalf(int half, int amend) {
        if (half == 1) { return half; }
        return (Point.RANDOM.nextInt(2) == 0) ? half : half + amend;
    }
}