package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;



/**
 * @source https://github.com/GAKKI100/cs61b-sp18-Data-Structure/blob/master/proj2/byog/Core/placeRooms.java
 * During the experiment process, I faced some difficulties in dealing with rooms and highways.
 * Firstly, I treat the highway as a special case of room(In this case, room is flour + wall). But I found that it is complicated when I add rooms
 * randomly into the field, I need to make sure that every rooms are not overlapped with others. That is too
 * intricate.
 * After looking at the implementation of this code, I got the idea that I can add the flour and wall separately.
 * I can construct room(in this case, room only contains flour) and highway class.
 */
public class WorldGenerator {

    /* Can be used in package byog.Core */
    static final int WIDTH = 100;
    static final int HEIGHT = 50;

    static final Point mapCenter = new Point(WorldGenerator.WIDTH / 2, WorldGenerator.HEIGHT / 2);

    /* queue is a priority queue and the room which is closed to the center of the map will be firstly expanded */
    Queue<Room> queue = new PriorityQueue<>();
    TETile[][] world;
    boolean[][] isOccupyChecker;

    WorldGenerator() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize the worldaddRoomOccupied(left);
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        // initialize the isOccupyChecker
        isOccupyChecker = new boolean[WIDTH][HEIGHT];

        // add the first room into the queue
        queue.add(Room.roomGenerator(null, null));

        // dive into the priority queue based search algorithm
        pQAlgorithm();

        ter.renderFrame(world);
    }

    private void pQAlgorithm() {
        int cnt = 0;
        while (!queue.isEmpty() && cnt < 30) {
            Room nowRoom = queue.remove();
            addRoom(nowRoom);

            // expand to left
            Room left = Room.roomGenerator("left", nowRoom);
            if (!isOut(left) && !isOccupied(left)) {
                queue.add(left);
                addRoomOccupied(left);
            }

            // expand to right
            Room right = Room.roomGenerator("right", nowRoom);
            if (!isOut(right) && !isOccupied(right)) {
                queue.add(right);
                addRoomOccupied(right);
            }

            // expand to up
            Room up = Room.roomGenerator("up", nowRoom);
            if (!isOut(up) && !isOccupied(up)) {
                queue.add(up);
                addRoomOccupied(up);
            }

            // expand to down
            Room down = Room.roomGenerator("down", nowRoom);
            if (!isOut(down) && !isOccupied(down)) {
                queue.add(down);
                addRoomOccupied(down);
            }

            cnt++;
        }
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
        for (int i = room.min.x - 1; i <= room.max.x + 1; i++) {
            for (int j = room.min.y - 1; j <= room.max.y + 1; j++) {
                if (isOccupyChecker[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the room is out of the world
     * @param room to be checked
     * @return true if the room is out of the world, otherwise false
     */
    private boolean isOut(Room room) {
        return room.min.x < 1 || room.max.x > WIDTH - 3 || room.min.y < 1 || room.max.y > WIDTH - 3;
    }



    /**
     * Add a room in the world
     * @param room is the Object to be added
     */
    private void addRoom(Room room) {
        for (int i = room.min.x; i <= room.max.x; i++) {
            for (int j = room.min.y; j <= room.max.y; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    /**
     * Add Walls in the world
     * @param world is the Object to be generated
     * @param wall type of wall
     */
    private static void addWall(TETile[][] world, TETile wall) {
    }
    public static void main(String[] args) {
        new WorldGenerator();
    }
}
