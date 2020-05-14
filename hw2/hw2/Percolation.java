package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int size;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wquTopOnly;
    private int length;
    private int numberOfOpenSite;
    private int virtualU;
    private int virtualB;

    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N < 1) {
            throw new java.lang.IllegalArgumentException("Input size should greater than 0");
        }
        this.size = N;
        this.length = size * size;
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }
        virtualU = length;
        virtualB = length + 1;
        wqu = new WeightedQuickUnionUF(length + 2);
        wquTopOnly = new WeightedQuickUnionUF(length + 1);
    }

    private int xy2one(int x, int y) {
        return x * size + y;
    }

    public void open(int row, int col) {
        if (row > size - 1 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException
            ("Input row or col is greater or equal than size N: " + size);
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = 1;
        numberOfOpenSite += 1;
        aroundUnion(row, col);
    }

    private void aroundUnion(int row, int col) {
        if (row == 0) {
            wqu.union(xy2one(row, col), virtualU);
            wquTopOnly.union(xy2one(row, col), virtualU);
        }
        if (row == size - 1) {
            wqu.union(xy2one(row, col), virtualB);
        }
        if (row < size - 1 && isOpen(row + 1, col)) {
            wqu.union(xy2one(row, col), xy2one(row + 1, col));
            wquTopOnly.union(xy2one(row, col), xy2one(row + 1, col));
        }
        if (row > 0 && isOpen(row - 1, col)) {
            wqu.union(xy2one(row, col), xy2one(row - 1, col));
            wquTopOnly.union(xy2one(row, col), xy2one(row - 1, col));
        }
        if (col < size - 1 && isOpen(row, col + 1)) {
            wqu.union(xy2one(row, col), xy2one(row, col + 1));
            wquTopOnly.union(xy2one(row, col), xy2one(row, col + 1));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            wqu.union(xy2one(row, col), xy2one(row, col - 1));
            wquTopOnly.union(xy2one(row, col), xy2one(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        if (row > size - 1 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException
            ("Input row or col is greater or equal than size N: " + size);
        }
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) { // is the site (row, col) full?
        if (row > size - 1 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException
            ("Input row or col is greater or equal than size N: " + size);
        }
        return wquTopOnly.connected(xy2one(row, col), virtualU) && isOpen(row, col);
    }

    public int numberOfOpenSites() { // number of open sites
        return numberOfOpenSite;
    }

    public boolean percolates() { // does the system percolate
        return wqu.connected(virtualU, virtualB);
    }
}
