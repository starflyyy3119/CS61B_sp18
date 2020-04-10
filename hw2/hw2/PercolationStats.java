package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double[] dat;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException(); 
        }

        dat = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
            }
            dat[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0;
        for (Double d : dat) {
            sum += d;
        }
        return sum / dat.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0.0;
        double mean = mean();
        for (Double d : dat) {
            sum += (d - mean) * (d - mean);
        }
        return Math.sqrt(sum / (dat.length - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(dat.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(dat.length);
    }
}
