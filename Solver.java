
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Solver {
    //private Queue<SearchNode> initQueue = new Queue<SearchNode>();
    //private Queue<SearchNode> twinQueue = new Queue<SearchNode>();
    private boolean isSolvable;
    private Stack<Board> solutions = new Stack<Board>();
    private int mv = 0;
     
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new NullPointerException();
        SearchNode sn = new SearchNode(initial, 0, null);
        SearchNode twinsn = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> initPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        while (true) {
            if (sn.board.isGoal()) {
                isSolvable = true;
                mv = sn.m;
                break;
            }
            if (twinsn.board.isGoal()) {
                isSolvable = false;
                mv = -1;
                break;
            }
            enqueuesn(sn, initPQ);
            enqueuesn(twinsn, twinPQ);
            sn = initPQ.delMin();    //get the most priority SearchNode from the MinPQ and make it the current sn 
            twinsn = twinPQ.delMin();
        }
        while (sn != null) {
            solutions.push(sn.board);
            sn = sn.prevB;
        }
        
    }
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prevB;
        private int priority;
        private int m;
        public SearchNode(Board b, int m, SearchNode prev) {
            board = b;
            prevB = prev;
            this.m = m;
            priority = board.manhattan() + m;    
        }
        public int compareTo(SearchNode x) {
            if (this.priority > x.priority) return 1;
            if (this.priority < x.priority) return -1;
            return 0;
        }
    }
    private void enqueuesn(SearchNode sn, MinPQ<SearchNode> pq) {    //put current searchNode's neighbors into MinPQ
        for (Board nb: sn.board.neighbors()) {
            if (sn.prevB == null || (!nb.equals(sn.prevB.board))) {
                SearchNode x = new SearchNode(nb, sn.m + 1, sn);
                pq.insert(x);
            }
        }
    }   
    public boolean isSolvable() {           // is the initial board solvable?
        return isSolvable;
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return mv;
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable) return null;
    	return solutions;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
