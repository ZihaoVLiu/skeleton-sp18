package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles2D;
    private int[] tiles1D;
    private int[][] temp;
    private int[][] goal2D;
    private int[] goal1D;
    final int BLANK = 0;

    public Board(int[][] tiles) {
        this.tiles2D = tiles;
        this.tiles1D = new int[tiles.length * tiles.length];
        this.goal2D = new int[tiles.length][tiles.length];
        this.goal1D = new int[tiles.length * tiles.length];

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                tiles1D[two2One(i, j)] = tiles2D[i][j];
                goal2D[i][j] = two2One(i, j) + 1;
                goal1D[two2One(i, j)] = two2One(i, j) + 1;
            }
        }
        goal2D[size() - 1][size() - 1] = BLANK;
        goal1D[size() * size() - 1] = BLANK;
    }

    private int two2One(int m, int n) {
        return m * size() + n;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size() - 1 || j < 0 || j > size() - 1) {
            throw new IndexOutOfBoundsException("Input index of column or " +
                    "row is greater than the boundary.");
        }
        if (tiles2D[i][j] == BLANK) {
            return BLANK;
        }
        return tiles2D[i][j];
    }

    public int size() {
        return tiles2D.length;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int targetI = -1;
        int targetJ = -1;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == BLANK) {
                    targetI = i;
                    targetJ = j;
                }
            }
        }
        int[][] tempTile = new int[size()][size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                tempTile[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (Math.abs(i - targetI) + Math.abs(j - targetJ) == 1) {
                    tempTile[targetI][targetJ] = tempTile[i][j];
                    tempTile[i][j] = BLANK;
                    Board neighbor = new Board(tempTile);
                    neighbors.enqueue(neighbor);
                    tempTile[i][j] = tempTile[targetI][targetJ];
                    tempTile[targetI][targetJ] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < size() * size(); i++) {
            if (goal1D[i] != BLANK && goal1D[i] != tiles1D[i]) {
                count++;
            }
        }
        return count;
    }

    public int manhattan() {
        int[] goalIndex;
        int[] tileIndex;
        int sum = 0;
        for (int i = 0; i < size() * size(); i++) {
            for (int j = 0; j < size() * size(); j++) {
                if (goal1D[i] != BLANK && goal1D[i] == tiles1D[j]) {
                    goalIndex = one2Two(i);
                    tileIndex = one2Two(j);
                    sum += countDistance(goalIndex[0], goalIndex[1],
                            tileIndex[0], tileIndex[1]);
                }
            }
        }
        return sum;
    }

    private int[] one2Two(int index) {
        int[] ij = new int[2];
        ij[0] = index / size();
        ij[1] = index % size();
        return ij;
    }

    private int countDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Board y) {
        return this.tiles2D == ((Board) y).tiles2D;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
