public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }

    public boolean isPalindrome(Deque<Character> deque) {
        while (deque.size() > 1) {
            return deque.removeFirst() == deque.removeLast() && isPalindrome(deque);
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1) {
            char c1 = deque.removeFirst();
            char c2 = deque.removeLast();
            if (!cc.equalChars(c1, c2)) {
                return false;
            }
        }
        return true;
    }

}
