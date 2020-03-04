import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void randomTest() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> msg = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        while(true) {
            double randomNum = StdRandom.uniform();
            Integer item = StdRandom.uniform(1, 10);
            if (!ads.isEmpty()) {
                if (randomNum < 0.25) {
                    msg.addLast("addFirst("+ item + ")");
                    ads.addFirst(item);
                    sad.addFirst(item);
                } else if (randomNum < 0.5) {
                    msg.addLast("addLast(" + item + ")");
                    ads.addLast(item);
                    sad.addLast(item);
                } else if (randomNum < 0.75) {
                    msg.addLast("removeFirst()");
                    Integer result = sad.removeFirst();
                    Integer expected = ads.removeFirst();
                    assertEquals(printMsg(msg), expected, result);
                } else {// randomNum < 1.0
                    msg.addLast("removeLast()");
                    Integer result = sad.removeLast();
                    Integer expected = ads.removeLast();
                    assertEquals(printMsg(msg), expected, result);
                }
            } else { // ads.Empty() == true
                if (randomNum < 0.5) {
                    msg.addLast("addFirst(" + item + ")");
                    ads.addFirst(item);
                    sad.addFirst(item);
                } else { //randomNum < 1.0
                    msg.addLast("addLast(" + item + ")");
                    ads.addLast(item);
                    sad.addLast(item);
                }
            }
        }
    }

    private static String printMsg(ArrayDequeSolution<String> msg) {
        StringBuilder s = new StringBuilder();
        for (String value : msg) {
            s.append(value);
            s.append("\n");
        }
        return "" + s;
    }
}
