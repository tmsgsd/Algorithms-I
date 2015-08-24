import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] blks;
    private int N;
    public Board(int[][] blocks) {          // construct a board from an N-by-N array of blocks
        if (blocks == null) throw new NullPointerException();
        N = blocks.length;
        this.blks = copyBlocks(blocks);                                    // (where blocks[i][j] = block in row i, column j)
        //StdOut.println(this.toString());
    }
                                               
    public int dimension() {                 // board dimension N
        return N;
    }
    public int hamming() {                   // number of blocks out of place
        int ham = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blks[i][j] != 0 && blks[i][j] != i * N + j + 1) {
                    ham++;
                }
            }
        }
        return ham;
    }
        
    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int mht = 0;
        int x, y;                           //blocks[x][y] is the right place for number x*N+j+1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blks[i][j] != 0 && blks[i][j] != i * N + j + 1) {
                    x = (blks[i][j] - 1) / N;
                    y = (blks[i][j] - 1) % N;
                    mht = mht + Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return mht;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
    }
    
    public Board twin() {                   // a board that is obtained by exchanging two adjacent blocks in the same row
        int tmp;
        int[][] twinBlocks = copyBlocks(blks);
        if (twinBlocks[0][0] != 0 && twinBlocks[0][1] != 0) {
            tmp = twinBlocks[0][0];
            twinBlocks[0][0] = twinBlocks[0][1];
            twinBlocks[0][1] = tmp;
        } else {
            tmp = twinBlocks[1][0];
            twinBlocks[1][0] = twinBlocks[1][1];
            twinBlocks[1][1] = tmp;
        }
        Board twin = new Board(twinBlocks);
        return twin;
    }
    
    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blks[i][j] != that.blks[i][j]) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {    // all neighboring boards
        Queue<Board> nb = new Queue<Board>();
        int i = 0, j = 0;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (blks[x][y] == 0) {
                    i = x;
                    j = y;
                    break;    
                }
            }
        }
        if (i > 0) {            //exchange space with its up block
            int[][] nbBlocks = copyBlocks(blks);
            nbBlocks[i][j] = nbBlocks[i - 1][j];
            nbBlocks[i - 1][j] = 0;
            Board nbBoard1 = new Board(nbBlocks);
            //StdOut.println("nb1 " + nbBoard1.toString());
            nb.enqueue(nbBoard1);
        }
        if (i < N - 1) {            //exchange space with its bottom block
            int[][] nbBlocks = copyBlocks(blks);
            nbBlocks[i][j] = nbBlocks[i + 1][j];
            nbBlocks[i + 1][j] = 0;
            Board nbBoard2 = new Board(nbBlocks);
            //StdOut.println("nb2 " + nbBoard2.toString());
            nb.enqueue(nbBoard2);
        }
        if (j > 0) {                //exchange space with its left block
            int[][] nbBlocks = copyBlocks(blks);
            nbBlocks[i][j] = nbBlocks[i][j - 1];
            nbBlocks[i][j - 1] = 0;
            Board nbBoard3 = new Board(nbBlocks);
            //StdOut.println("nb3 " + nbBoard3.toString());
            nb.enqueue(nbBoard3);
        }
        if (j < N - 1) {            //exchange space with its right block
            int[][] nbBlocks = copyBlocks(blks);
            nbBlocks[i][j] = nbBlocks[i][j + 1];
            nbBlocks[i][j + 1] = 0;
            Board nbBoard4 = new Board(nbBlocks);
            //StdOut.println("nb4 " + nbBoard4.toString());
            nb.enqueue(nbBoard4);
        }
        return nb;
    }
    
    
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%s ", blks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] copyBlocks(int[][] blocks) {
        int[][] a = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = blocks[i][j];
                //StdOut.print(a[i][j] + " ");
            }
            //StdOut.println();
        }
        return a;
    }
    public static void main(String[] args) {    // unit tests (not graded)
        int[][] a = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] b = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board ab = new Board(a);
        Board bb = new Board(b);
        
        StdOut.println(ab.dimension());
        StdOut.println("hamming = " + ab.hamming());
        StdOut.println("manhattan = " + ab.manhattan());
        StdOut.println(ab.isGoal() + " " + bb.isGoal());
        Board twinab = ab.twin();
        StdOut.println("ab's twin is");
        StdOut.println(twinab.toString());
        
        StdOut.println(ab.equals(bb));
        
        StdOut.println("ab's neighbors are: ");
        Iterable<Board> abnb = ab.neighbors();
        for (Board n: abnb) {
            StdOut.println(n.toString());
            StdOut.println();
        }
        
    }
}
