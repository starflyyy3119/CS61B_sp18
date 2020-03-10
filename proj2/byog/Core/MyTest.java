package byog.Core;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.*;

public class MyTest {

    @Test
    public void priorityQueueTest() {
        Queue<Room> pq = new PriorityQueue<>();
        Room a = new Room(new Point(1, 1), new Point(2, 2));
        Room b = new Room(new Point(5, 5), new Point(6, 6));
        Room c = new Room(new Point(2, 2), new Point(3, 3));
        pq.add(a);
        pq.add(b);
        pq.add(c);
        Room d = pq.remove();
        assertEquals(d, b);
        Room e = pq.remove();
        assertEquals(e, c);

    }
}
