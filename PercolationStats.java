
public class PercolationStats {
	private int counter, total;
	private final int T;
	private double[] threshold;
	
	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) throw new IllegalArgumentException("input invalid");
		total = N * N;
		this.T = T;
		threshold = new double[T];
		for (int m = 0; m < T; m++) {
			Percolation perc = new Percolation(N);
			counter = 0;
			while (!perc.percolates()) {
				int i = StdRandom.uniform(1, N + 1);	//row
				int j = StdRandom.uniform(1, N + 1);	//column
				if (!perc.isOpen(i, j)) {
					counter++;
				}
				perc.open(i, j);
			}
			threshold[m] = (double) counter / total;
			//StdOut.println(threshold[m]);
		}
	}
	
	// sample mean of percolation threshold
	public double mean() {
		if (T == 1) return threshold[0];
		else { 
			return StdStats.mean(threshold);
		}
	}
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(T);
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(T);
	}

	public static void main(String[] args) {    // test client (described below)
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats test = new PercolationStats(N, T);
		StdOut.println("mean                    = " + test.mean());
		StdOut.println("stddev                  = " + test.stddev());
		StdOut.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());
		
	}
}

