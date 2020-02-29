/**
 * A class for palindrome operations
 */
public class Palindrome {
    /**
     * Change a String to a Deque
     * @param word String that needs to be changed
     * @return a deque where the characters appear in the same order as in the String
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> lld = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            lld.addLast(word.charAt(i));
        }
        return lld;
    }

    /**
     * Check if this word is a palindrome (loop implementation)
     * @param word needs to be checked
     * @return true if the word is a palindrome, otherwise false
     */

//    public boolean isPalindrome(String word) {
//        if (word.length() < 2) {
//            return true;
//        }
//        Deque<Character> lld = wordToDeque(word);
//        while (lld.size() > 1) {
//            if (lld.removeFirst() != lld.removeLast()) {
//                return false;
//            }
//        }
//        return true;
//    }

    /* recursion implementation */
    public boolean isPalindrome(String word) {
        Deque<Character> lld = wordToDeque(word);
        return isPalindrome(lld);
    }

    private boolean isPalindrome(Deque<Character> lld) {
        if (lld.size() < 2) {
            return true;
        }
        if (lld.removeFirst() != lld.removeLast()) {
            return false;
        }
        return isPalindrome(lld);
    }

    /**
     * Generalized isPalindrome
     * @param word needs to be checked
     * @param cc defines the rules of character equals
     * @return true if word is Palindrome in specific cc, otherwise false
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> lld = wordToDeque(word);
        return isPalindrome(lld, cc);
    }

    private boolean isPalindrome(Deque<Character> lld, CharacterComparator cc) {
        if (lld.size() < 2) {
            return true;
        }
        if (!cc.equalChars(lld.removeFirst(),lld.removeLast())) {
            return false;
        }
        return isPalindrome(lld, cc);
    }

}
