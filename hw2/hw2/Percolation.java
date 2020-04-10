package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;

    private int numOfOpen;

    private WeightedQuickUnionUF uf;
    // avoid backwash
    private WeightedQuickUnionUF ufHelper;

    private boolean[][] marked;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        // initialize N
        this.N = N;

        // initialize numOfOpen
        numOfOpen = 0;

        // initialize uf
        // need two pesudo-site at uf[N * N] and uf[N * N + 1]
        uf = new WeightedQuickUnionUF(N * N + 2);
        // ufHelper just use the top pesudo-site uf[N * N]
        ufHelper = new WeightedQuickUnionUF(N * N + 1);

        // initialize marked array
        marked = new boolean[N][N];
    }

    // open the site(row, col) if it is not open
    public void open(int row, int col) {
        assertValid(row, col);

        // if not open
        if (!marked[row][col]) {
            marked[row][col] = true;
            numOfOpen++;
            // if belongs to the first layer
            if (row == 0) {
                uf.union(mapping(row, col), N * N);
                ufHelper.union(mapping(row, col), N * N);
            }

            // if belongs to the last layer
            if (row == N - 1) {
                uf.union(mapping(row, col), N * N + 1);
            }
        }

        // expand to left, right, up and down
        if (col - 1 >= 0 && marked[row][col - 1]) {
            doUF(row, col, row, col - 1);
        }
        if (col + 1 < N  && marked[row][col + 1]) {
            doUF(row, col, row, col + 1);
        }
        if (row - 1 >= 0 && marked[row - 1][col]) {
            doUF(row, col, row - 1, col);
        }
        if (row + 1 < N  && marked[row + 1][col]) {
            doUF(row, col, row + 1, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        assertValid(row, col);

        return marked[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        assertValid(row, col);

        return ufHelper.connected(mapping(row, col), N * N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N * N, N * N + 1);
    }

    // assert (row, col) isValid
    private void assertValid(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    // map the 2D-(row, col) to 1D-index
    private int mapping(int row, int col) {
        return row * N + col;
    }

    // do UF operations
    private void doUF(int row1, int col1, int row2, int col2) {
        uf.union(mapping(row1, col1), mapping(row2, col2));
        ufHelper.union(mapping(row1, col1), mapping(row2, col2));
    }

    // unit test
    public static void main(String[] args) {

    }
}
