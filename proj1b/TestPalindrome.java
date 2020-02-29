import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String s1 = "noon";
        String s2 = "horse";
        assertTrue(palindrome.isPalindrome(s1));
        assertFalse(palindrome.isPalindrome(s2));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome(" "));
    }

    @Test
    public void testIsPalindromePlus() {
        String s1 = "nopm";
        String s2 = "horse";
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(s1, cc));
        assertFalse(palindrome.isPalindrome(s2, cc));
        assertTrue(palindrome.isPalindrome("A", cc));
        assertTrue(palindrome.isPalindrome(" ", cc));
    }
}
