import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private int trials; // Number of trials for the Monte Carlo simulation
    private double[] thresholds; // Array to store the percolation thresholds

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Both n and trials must be greater than 0");
        this.trials = trials;
        this.thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!percolation.isOpen(row, col))
                    percolation.open(row, col);
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java PercolationStats <grid size (n)> <number of trials>");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("Percolation threshold mean       = " + String.format("%.6f", stats.mean()));
        System.out.println( "Percolation threshold stddev    = " + String.format("%.6f", stats.stddev()));
        System.out.println( "95% confidence interval         = [" + String.format("%.6f", stats.confidenceLo()) + ", " + String.format("%.6f", stats.confidenceHi()) + "]");
    }
}
