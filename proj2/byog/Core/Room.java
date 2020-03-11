package byog.Core;

import java.util.Comparator;

public class Room {

    public final static int HALFMINROOMLENGTH = 0;
    public final static int HALFMAXROOMLENGTH = 5;

    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT;
    private static final Point mapCenter = new Point(WIDTH / 2, HEIGHT / 2);

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
            center = Point.pointGenerator(HALFMAXROOMLENGTH + 1, WIDTH - HALFMAXROOMLENGTH - 1, HALFMAXROOMLENGTH + 1, HEIGHT - HALFMAXROOMLENGTH - 1);
            leftHalf = generateHalf(HALFMAXROOMLENGTH + 1);
            rightHalf = generateOneHalf(leftHalf, 1);
            int bound = (Math.max(leftHalf, rightHalf) >= HALFMAXROOMLENGTH / 2) ? HALFMAXROOMLENGTH / 2 : HALFMAXROOMLENGTH;
            downHalf = generateHalf(bound + 1);
            upHalf = generateOneHalf(downHalf, 1);
            return new Room(new Point(center.x - leftHalf, center.y - downHalf), new Point(center.x + rightHalf, center.y + upHalf));
        }
        Point min = nowRoom.min;
        Point max = nowRoom.max;
        if (s.equals("left") || s.equals("right")) {
            if (s.equals("left")) {
                int centerRightBound = min.x - 2;
                center = Point.pointGenerator(centerRightBound - 2 * HALFMAXROOMLENGTH, centerRightBound, min.y, max.y + 1);
                rightHalf = generateHalf(Math.min(centerRightBound - center.x + 1, HALFMAXROOMLENGTH));
                leftHalf = generateOneHalf(rightHalf, -1);
            } else { // s.equals("right")
                int centerLeftBound = max.x + 2;
                center = Point.pointGenerator(centerLeftBound + 1, centerLeftBound + 2 * HALFMAXROOMLENGTH + 1, min.y, max.y + 1);
                leftHalf = generateHalf(Math.min(center.x - centerLeftBound + 1, HALFMAXROOMLENGTH));
                rightHalf = generateOneHalf(leftHalf, 1);
            }
            int bound = (Math.max(leftHalf, rightHalf) >= HALFMAXROOMLENGTH / 2) ? HALFMAXROOMLENGTH / 2 : HALFMAXROOMLENGTH;
            upHalf = generateHalf(bound + 1);
            downHalf = generateOneHalf(upHalf, -1);
        } else {  // s.equals("up") || s.equals("down")
            if (s.equals("up")) {
                int centerLowerBound = max.y + 2;
                center = Point.pointGenerator(min.x, max.x + 1, centerLowerBound + 1, centerLowerBound + 2 * HALFMAXROOMLENGTH + 1);
                downHalf = generateHalf(Math.min(center.y - centerLowerBound + 1, HALFMAXROOMLENGTH));
                upHalf = generateOneHalf(downHalf, 1);
            } else { // s.equals("down")
                int centerUpperBound = min.y - 2;
                center = Point.pointGenerator(min.x, max.x + 1, centerUpperBound - 2 * HALFMAXROOMLENGTH, centerUpperBound);
                upHalf = generateHalf(Math.min(centerUpperBound - center.y + 1, HALFMAXROOMLENGTH));
                downHalf = generateOneHalf(upHalf, -1);
            }
            int bound = (Math.max(upHalf, downHalf) >= HALFMAXROOMLENGTH / 2) ? HALFMAXROOMLENGTH / 2 : HALFMAXROOMLENGTH;
            leftHalf = generateHalf( bound + 1);
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
        return HALFMINROOMLENGTH + Game.RANDOM.nextInt(bound - HALFMINROOMLENGTH);
    }

    /**
     * Amend the half by +-1 or none
     * @param half given
     * @param amend 1 or -1
     * @return the amended half
     */
    private static int generateOneHalf(int half, int amend) {
        if (half == 0 && amend == -1) { return half; }
        if (half == HALFMAXROOMLENGTH) { return half - 1; }
        return (Game.RANDOM.nextInt(2) == 0) ? half : half + amend;
    }

    static Comparator<Room> roomComparator() {
        return new roomComparator();
    }

    private static class roomComparator implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            return Integer.compare(Hamming(o1.center, mapCenter), Hamming(o2.center, mapCenter));
        }
    }

    private static int Hamming(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}