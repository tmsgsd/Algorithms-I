import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        if (N < 4) {
            return;
        }
        
        Point[] arr = new Point[N];
        Point[] slopeOrders = new Point[arr.length];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            arr[i] = p;
            slopeOrders[i] = p;
        }
        
        //slopeOrders = Arrays.copyOf(arr, arr.length);
        for (Point p : arr) {
            Arrays.sort(slopeOrders, p.SLOPE_ORDER);
            Point[] lines = new Point[arr.length];
            lines[0] = p;
            double prevslope = p.slopeTo(slopeOrders[1]);
            int num = 0;    //number of points in a line segment. 
            
            for (int i = 1; i < arr.length; i++) {
                double currslope = p.slopeTo(slopeOrders[i]);
                if (currslope == prevslope) {
                    lines[++num] = slopeOrders[i];
                } else {
                    if (num >= 3) {                
                        drawLines(lines, num);
                    }
                    num = 1;
                    lines[1] = slopeOrders[i];
                }
                prevslope = currslope;
            }
            
            if (num >= 3) {                
                drawLines(lines, num);
            }
            
        } 
    }
    
    private static void drawLines(Point[] lines, int num) {
        Arrays.sort(lines, 1, num+1);
        if (lines[0].compareTo(lines[1]) < 0) {
            StdOut.print(lines[0].toString());
            for (int i = 1; i < num+1; i++) {
                StdOut.print("->" + lines[i].toString());                    
            }
            StdOut.println();
            lines[0].drawTo(lines[num]);
        }
    }
}
