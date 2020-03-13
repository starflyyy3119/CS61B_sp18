package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;

import java.io.FileNotFoundException;
import java.util.*;


/**
 * @source https://github.com/GAKKI100/cs61b-sp18-Data-Structure/blob/master/proj2/byog/Core/placeRooms.java
 * During the experiment process, I faced some difficulties in dealing with rooms and highways.
 * Firstly, I treat the highway as a special case of room(In this case, room is flour + wall). But I found that it is complicated when I add rooms
 * randomly into the field, I need to make sure that every rooms are not overlapped with others. That is too
 * intricate.
 * After looking at the implementation of this code, I got the idea that I can add the flour and wall separately.
 * I can construct room(in this case, room only contains flour) and highway class.
 */
public class World {

    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT;

    private TETile[][] world;
    private boolean[][] isOccupyChecker;

    private Point player;
    private Point lockedDoor;

    // constructive code block
    {
        // initialize the world
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public World() {

        // initialize the isOccupyChecker
        isOccupyChecker = new boolean[WIDTH][HEIGHT];

        // add the first room into the container
        /*
         * Can choose to use priority queue or stack as the container
         * priority queue: generate the center part to of the map first.
         * stack:          generate deep maze first
         */
        Room firstRoom = Room.roomGenerator(null, null);
        Stack<Room> stack = new Stack<>();
        addRoom(stack, firstRoom);
        // Queue<Room> queue = new PriorityQueue<>(Room.roomComparator());
        // addRoom(queue, firstRoom);

        // use the search algorithm to expand the maze
        algorithm(stack);
        //algorithm(queue);

        // add the wall
        addWall();

        // add the player
        addPlayer();

        // add the locked door
        addLockedDoor();
    }

    /**
     * Load the world from the txt file
     * @param pathName the relative path of the txt file
     */
    public World(String pathName) throws FileNotFoundException {
        String worldString = InOutput.read(pathName);
        for (int j = HEIGHT - 1; j >= 0; j--) {
            int cast_j = HEIGHT - j - 1;
            for (int i = 0; i <= WIDTH - 1; i++) {
                switch (worldString.charAt(cast_j * WIDTH + i)) {
                    case '@':
                        setPlayer(new Point(i, j));
                        break;
                    case '█':
                        setLockedDoor(new Point(i, j));
                        break;
                    case '#':
                        world[i][j] = Tileset.WALL;
                        break;
                    case '·':
                        world[i][j] = Tileset.FLOOR;
                        break;
                    default:
                        world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

    private void algorithm(Collection<Room> container) {
        int cnt = 0;
        while (!container.isEmpty() && cnt < 30) {
            Room nowRoom;
            if (container instanceof Stack) {
                Stack<Room> stack_tmp = (Stack<Room>) container;
                nowRoom = stack_tmp.pop();
            } else {  // container instanceof Queue
                Queue<Room> queue_tmp = (Queue<Room>) container;
                nowRoom = queue_tmp.remove();
            }
            addRoom(nowRoom);

            // expand to leftRoom.roomGenerator(null, null)
            Room left = Room.roomGenerator("left", nowRoom);
            if (!Room.isOut(left) && !isOccupied(left)) {
                addRoom(container, left);
                HighWay highWay = HorizontalHighWay.hHWGenerator(left, nowRoom);
                addHighWay(highWay);
            }

            // expand to right
            Room right = Room.roomGenerator("right", nowRoom);
            if (!Room.isOut(right) && !isOccupied(right)) {
                addRoom(container, right);
                HighWay highWay = HorizontalHighWay.hHWGenerator(nowRoom, right);
                addHighWay(highWay);
            }

            // expand to up
            Room up = Room.roomGenerator("up", nowRoom);
            if (!Room.isOut(up) && !isOccupied(up)) {
                addRoom(container, up);
                HighWay highWay = VerticalHighWay.vHWGenerator(nowRoom, up);
                addHighWay(highWay);
            }

            // expand to down
            Room down = Room.roomGenerator("down", nowRoom);
            if (!Room.isOut(down) && !isOccupied(down)) {
                addRoom(container, down);
                HighWay highWay = VerticalHighWay.vHWGenerator(down, nowRoom);
                addHighWay(highWay);
            }

            cnt++;
        }
    }

    /**
     * Add a room into the container
     * @param room needs to be added
     */
    private void addRoom(Collection<Room> container, Room room) {
        container.add(room);
        addRoomOccupied(room);
    }

    /**
     * Add room to the isOccupiedChecker
     * @param room to added
     */
    private void addRoomOccupied(Room room) {
        for (int i = room.min.x; i <= room.max.x; i++) {
            for (int j = room.min.y; j <= room.max.y; j++) {
                isOccupyChecker[i][j] = true;
            }
        }
    }

    /**
     * Check if the given room contradicts with the exist ones
     * @param room to be checked
     * @return true if the room is contradicted with another one, otherwise false
     */
    private boolean isOccupied(Room room) {
        // 2 means at least we should leave some space for walls
        int xMin = Math.max(1, room.min.x - 2), xMax = Math.min(WIDTH - 2, room.max.x + 2);
        int yMin = Math.max(1, room.min.y - 2), yMax = Math.min(HEIGHT - 2, room.max.y + 2);
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (isOccupyChecker[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * draw a room in the world
     * @param room is the Object to be added
     */
    private void addRoom(Room room) {
        for (int i = room.min.x; i <= room.max.x; i++) {
            for (int j = room.min.y; j <= room.max.y; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    private void addHighWay(HighWay highWay) {
        highWay.addHighWay(world, isOccupyChecker);
    }

    /**
     * Add Walls in the world
     */
    private void addWall() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                addWall(i, j);
            }
        }
    }

    private void addWall(int x, int y) {
        if (world[x][y].equals(Tileset.FLOOR)) {
            if (world[x-1][y].equals(Tileset.NOTHING)) { world[x-1][y] = Tileset.WALL; }
            if (world[x+1][y].equals(Tileset.NOTHING)) { world[x+1][y] = Tileset.WALL; }
            if (world[x][y-1].equals(Tileset.NOTHING)) { world[x][y-1] = Tileset.WALL; }
            if (world[x][y+1].equals(Tileset.NOTHING)) { world[x][y+1] = Tileset.WALL; }

            // four nooks
            if (world[x-1][y-1].equals(Tileset.NOTHING)) { world[x-1][y-1] = Tileset.WALL; }
            if (world[x-1][y+1].equals(Tileset.NOTHING)) { world[x-1][y+1] = Tileset.WALL; }
            if (world[x+1][y-1].equals(Tileset.NOTHING)) { world[x+1][y-1] = Tileset.WALL; }
            if (world[x+1][y+1].equals(Tileset.NOTHING)) { world[x+1][y+1] = Tileset.WALL; }
        }
    }

    private void addPlayer() {
        boolean flag = true;
        while(flag) {
            Point p = Point.pointGenerator(1, WIDTH - 2, 1, HEIGHT - 2);
            if (getTile(p).equals(Tileset.FLOOR)) {
                setPlayer(p);
                flag = false;
            }
        }
    }

    private void addLockedDoor() {
        boolean flag = true;
        while(flag) {
            Point p = Point.pointGenerator(1, WIDTH - 2, 1, HEIGHT - 2);
            if (getTile(p).equals(Tileset.WALL) && isFloorNeighbor(p)) {
                setLockedDoor(p);
                flag = false;
            }
        }
    }

    private boolean isFloorNeighbor(Point p) {
        return (world[p.x-1][p.y].equals(Tileset.FLOOR) || world[p.x+1][p.y].equals(Tileset.FLOOR) || world[p.x][p.y-1].equals(Tileset.FLOOR) || world[p.x][p.y+1].equals(Tileset.FLOOR));
    }


    public Point getPlayer() {
        return player;
    }

    public Point getLockedDoor() {
        return lockedDoor;
    }

    public void setPlayer(Point pos) {
        player = pos;
        world[pos.x][pos.y] = Tileset.PLAYER;
    }

    public void setFloor(Point pos) {
        world[pos.x][pos.y] = Tileset.FLOOR;
    }

    public TETile getTile(Point pos) {
        return world[pos.x][pos.y];
    }

    public void setUnlockedDoor(Point pos) {
        world[pos.x][pos.y] = Tileset.UNLOCKED_DOOR;
    }

    public void setLockedDoor(Point pos) {
        lockedDoor = pos;
        world[pos.x][pos.y] = Tileset.LOCKED_DOOR;
    }
}
