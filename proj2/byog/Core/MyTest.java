package byog.Core;

import byog.TileEngine.TETile;
import static org.junit.Assert.*;
import org.junit.Test;

public class MyTest {
    @Test
    public void testEqual() {
        Game game = new Game();
        TETile[][] world1 = game.playWithInputString("n8525858077324149408ssadwds:q");
        TETile[][] world2 = game.playWithInputString("n8525858077324149408ssadwds:q");
        System.out.println(TETile.toString(world1));
        System.out.println(TETile.toString(world2));
    }
}
