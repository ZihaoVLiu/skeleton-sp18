public class OffByN implements CharacterComparator {
    private int N;
    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char c1, char c2) {
        int diff = c1 - c2;
        return diff == N || diff == -N;
    }
}
