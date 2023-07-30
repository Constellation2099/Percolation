import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n; // Grid size
    private int[] id; // Array to store the parent of each site
    private int[] sz; // Array to store the size of each component
    private boolean[][] isOpen; // Grid to track open sites
    private int openSites; // Number of open sites
    private int virtualTopSite; // Virtual top site to help with percolation check
    private int virtualBottomSite; // Virtual bottom site to help with percolation check

    public Percolation(int n) { // Constructor to check if n is valid
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        this.n = n; // Initialize n
        this.id = new int[n * n + 2]; // Create the id array (+2 for virtual top and bottom sites)
        this.sz = new int[n * n + 2]; // Create the sz array (+2 for virtual top and bottom sites)
        this.isOpen = new boolean[n][n];
        this.openSites = 0;
        this.virtualTopSite = n * n; // Virtual top site index
        this.virtualBottomSite = n * n + 1; // Virtual bottom site index
        for (int i = 0; i < n * n + 2; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    private boolean isValidIndex(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }

    private int root(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]]; // Path compression
            p = id[p];
        }
        return p;
    }

    private void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ)
            return;
        if (sz[rootP] < sz[rootQ]) {
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        }
        else {
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
    }

    public void open(int row, int col) {
        if (!isValidIndex(row, col))
            throw new IndexOutOfBoundsException("Invalid row or column index");
        if (!isOpen[row - 1][col - 1]) {
            isOpen[row - 1][col - 1] = true;
            openSites++;
            int index = getIndex(row, col);
            if (row > 1 && isOpen(row - 1, col))
                union(index, getIndex(row - 1, col));
            if (row < n && isOpen(row + 1, col))
                union(index, getIndex(row + 1, col));
            if (col > 1 && isOpen(row, col - 1))
                union(index, getIndex(row, col - 1));
            if (col < n && isOpen(row, col + 1))
                union(index, getIndex(row, col + 1));
            if (row == 1)
                union(index, virtualTopSite);
            if (row == n)
                union(index, virtualBottomSite);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValidIndex(row, col))
            throw new IndexOutOfBoundsException("Invalid row or column index");
        return isOpen[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (!isValidIndex(row, col))
            throw new IndexOutOfBoundsException("Invalid row or column index");
        int index = getIndex(row, col);
        return isOpen(row, col) && root(index) == root(virtualTopSite);
    }

    public int numberOfOpenSites() { // Return the number of open sites
        return openSites;
    }

    public boolean percolates() { // Condition for percolation check
        return root(virtualTopSite) == root(virtualBottomSite);
    }

    public static void main(String[] args) {
      // Implementation code here
    }
}
