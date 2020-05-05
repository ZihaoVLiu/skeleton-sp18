import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.**/

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
        String input1 = "wocao";
        assertFalse(palindrome.isPalindrome(input1));
        String input2 = "werrew";
        assertTrue(palindrome.isPalindrome(input2));
        String input3 = "asdsa";
        assertTrue(palindrome.isPalindrome(input3));
        String input4 = "fuck";
        assertFalse(palindrome.isPalindrome(input4));
        assertFalse(palindrome.isPalindrome("eiwocao"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("AAbAA"));
    }

    @Test
    public void testIsPalindromecc() {
        //OffByOne obo = new OffByOne();
        CharacterComparator obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertTrue(palindrome.isPalindrome("zyzy", obo));
        assertTrue(palindrome.isPalindrome("yyxz", obo));
        assertTrue(palindrome.isPalindrome("yyyxz", obo));
        assertFalse(palindrome.isPalindrome("wocaonima", obo));
        assertFalse(palindrome.isPalindrome("bb", obo));
        assertFalse(palindrome.isPalindrome("tit", obo));
        assertFalse(palindrome.isPalindrome("xzxz", obo));
    }
}
