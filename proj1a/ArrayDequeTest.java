import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void getTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);
        ad.addLast(7);
        int result = ad.get(7);
        int expected = 7;
        assertEquals(result, expected);
    }

    @Test
    public void randomTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        int result1 = ad.removeLast();
        int result2 = ad.removeLast();
        int result3 = ad.removeLast();
        int expected1 = 0;
        int expected2 = 1;
        int expected3 = 2;
        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
    }
}
