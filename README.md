# Percolation
A real life implementation of WQUF to calculate the probability (p*) of percolation

WQUF basically means Weighted Quick Union Find.

Its the fastest and most efficient algorithm available to us currently for problems like percolation. Its used to check if two points are connected, either
directly, or through some other point.

What is Percolation ?

Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

How does the model work ?

We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

What is the problem stated that requires this code ?

n a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates.

When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, and when p > p*, a random n-by-n grid almost always percolates. No mathematical solution for determining the percolation threshold p* has yet been derived. Your task is to write a computer program to estimate p*.

The first java file named "Percolation.java" is used to check if a system percolates or not. It follows the following API :

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)

    // is the site (row, col) full?
    public boolean isFull(int row, int col)

    // returns the number of open sites
    public int numberOfOpenSites()

    // does the system percolate?
    public boolean percolates()

    // test client (optional)
    public static void main(String[] args)
}

Corner cases.  By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site: Throw an IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. Throw an IllegalArgumentException in the constructor if n ≤ 0.

Performance requirements.  The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number of calls to union() and find().

What is the Monte Carlo Simulation ?

To estimate the percolation threshold, consider the following computational experiment:

Initialize all sites to be blocked.
Repeat the following until the system percolates:
Choose a site uniformly at random among all blocked sites.
Open the site.
The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

For example, if sites are opened in a 20-by-20 lattice, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.

By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let xt be the fraction of open sites in computational experiment t. The sample mean x¯¯¯
 provides an estimate of the percolation threshold; the sample standard deviation s; measures the sharpness of the threshold.

The file named "PercolationStats.java" calls the Percolation class and runs the Monte Carlo Simulation t times for a grid sized n ( both taken from the user ) and returns the mean, standard deviation and the 95% confidence interval.

It follows the following API :

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)

    // sample mean of percolation threshold
    public double mean()

    // sample standard deviation of percolation threshold
    public double stddev()

    // low endpoint of 95% confidence interval
    public double confidenceLo()

    // high endpoint of 95% confidence interval
    public double confidenceHi()

   // test client (see below)
   public static void main(String[] args)

}

Throws an IllegalArgumentException in the constructor if either n ≤ 0 or trials ≤ 0.
