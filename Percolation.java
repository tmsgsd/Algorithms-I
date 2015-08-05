
public class Percolation {
	private final int N;
	private final WeightedQuickUnionUF uf;
	private final WeightedQuickUnionUF nobackwash;
	private boolean [][] status;
	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		if (N <= 0) throw new IllegalArgumentException("N should be larger than 0");
		this.N = N;
		//create a array for the mapping. 
		//Array[N*N] is virtual top and Array[N*N+1] is virtual bottom
		uf = new WeightedQuickUnionUF(N * N + 2); 
		//create a array for preventing backwash
		nobackwash = new WeightedQuickUnionUF(N * N + 1);
		status = new boolean[N][N];
		for (int j = 0; j < N; j++) {
			uf.union(N * N, j);	//union top with the top row
			nobackwash.union(N * N, j);
			uf.union(N * N + 1, (N - 1) * N + j);	//union bottom with the bottom row
		}
			
	}
	
	private void checkbounds(int i, int j) {
		if (i < 1 || i > N) {
			throw new IndexOutOfBoundsException("row index i out of bounds");
		}
		if (j < 1 || j > N) {
			throw new IndexOutOfBoundsException("column index j out of bounds");
		}
	}
	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		checkbounds(i, j);
		if (isOpen(i, j)) return;
		else status[i - 1][j - 1] = true;
		if (i != 1 && isOpen(i - 1, j)) {
			uf.union((i - 1) * N + j - 1, (i - 2) * N + j - 1);	
			nobackwash.union((i - 1) * N + j - 1, (i - 2) * N + j - 1);	//connect north
		}
		if (i != N && isOpen(i + 1, j)) {
			uf.union((i - 1) * N + j - 1, (i * N + j - 1));	
			nobackwash.union((i - 1) * N + j - 1, (i * N + j - 1));	//connect south
		}
		if (j != 1 && isOpen(i, j - 1)) {
			uf.union((i - 1) * N + j - 1, (i - 1) * N + j - 2);	
			nobackwash.union((i - 1) * N + j - 1, (i - 1) * N + j - 2); //connect west
		}
		if (j != N && isOpen(i, j + 1)) {
			uf.union((i - 1) * N + j - 1, (i - 1) * N + j); 	
			nobackwash.union((i - 1) * N + j - 1, (i - 1) * N + j); 	//connect east
		}
	}
	
	public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
		checkbounds(i, j);
		return status[i - 1][j - 1];
	}
	
	
	public boolean isFull(int i, int j) {     // is site (row i, column j) full?
		checkbounds(i, j);
		if (!isOpen(i, j)) return false;
		else return nobackwash.connected((i - 1) * N + j - 1, N * N);
	}
	public boolean percolates() {            // does the system percolate?
		if (N == 1)
			return isOpen(1, 1);
		else return uf.connected(N * N, N * N + 1);
	}

	public static void main(String[] args) { }   // test client (optional)
}
