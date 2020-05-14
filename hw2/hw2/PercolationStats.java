package hw2;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private Percolation model;
    private double[] thresholdList;
    private int T;
    private static final double CONINTERVEL = 1.96;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        //perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException
            ("size N and number T should greater than 0.");
        }

        this.T = T;
        thresholdList = new double[T];
        double threshold;
        for (int i = 0; i < this.T; i++) {
            model = pf.make(N);
            threshold = getThreshold(N, model);
            thresholdList[i] = threshold;
        }
    }

    private double getThreshold(int N, Percolation model) {
        double threshold = 0;
        double totalSites = N * N;
        double openSites;
        for (int i = 0; i < N * N; i++) {
            if (model.percolates()) {
                return threshold;
            }
            model.open(StdRandom.uniform(N), StdRandom.uniform(N));
            openSites = model.numberOfOpenSites();
            threshold =  openSites / totalSites;
        }
        return threshold;
    }

    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(thresholdList);
    }

    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholdList);
    }

    public double confidenceLow() { // low endpoint of 95% confidence interval
        double mu = mean();
        double sigma = stddev();
        double sqrtT = Math.sqrt(T);
        double confidenceLow;
        confidenceLow = mu - ((CONINTERVEL * sigma) / sqrtT);
        return confidenceLow;
    }

    public double confidenceHigh() { // high endpoint of 95% confidence interval
        double mu = mean();
        double sigma = stddev();
        double sqrtT = Math.sqrt(T);
        double confidenceHigh;
        confidenceHigh = mu + ((CONINTERVEL * sigma) / sqrtT);
        return confidenceHigh;
    }
}
