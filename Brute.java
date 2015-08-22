import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] arr = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            arr[i] = p;
        }

       
        Arrays.sort(arr);
        
        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    for (int s = r + 1; s < N; s++) {
                        if (arr[p].slopeTo(arr[q]) == arr[p].slopeTo(arr[r]) 
                            && arr[p].slopeTo(arr[q]) == arr[p].slopeTo(arr[s])
                            && arr[p].slopeTo(arr[r]) == arr[p].slopeTo(arr[s])) {
                            
                            //StdOut.println(arr[p].slopeTo(arr[q])+" "+arr[p].slopeTo(arr[r])+" "+arr[p].slopeTo(arr[s]));
                            StdOut.println(arr[p].toString() + "->" + arr[q].toString() + "->" 
                            + arr[r].toString() + "->" + arr[s].toString());
                            arr[p].drawTo(arr[s]);
                        }
                    }
                }
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
        
        
    }
}
