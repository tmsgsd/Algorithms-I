import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET {
    private SET<Point2D> pointSet;
    public PointSET() {                              // construct an empty set of points 
       pointSet = new SET<Point2D>();
    }
    public boolean isEmpty() {                     // is the set empty? 
        return pointSet.isEmpty();
    }
    public int size() {                        // number of points in the set 
        return pointSet.size();
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException("cannot call insert() with a null point.");
        pointSet.add(p);
    }
    public boolean contains(Point2D p) {            // does the set contain point p? 
        if (p == null) throw new NullPointerException("cannot call contain() with a null point.");
        return pointSet.contains(p);
    }
    public void draw() {                        // draw all points to standard draw 
        
        for (Point2D p: pointSet) {
            StdDraw.point(p.x(), p.y());;
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        if (rect == null) throw new NullPointerException("cannot call range() with a null point.");
        Queue<Point2D> pointsInRect = new Queue<Point2D>();
        for (Point2D p: pointSet) {
            if (rect.contains(p)) {
                pointsInRect.enqueue(p);
            }
        }
        return pointsInRect;
    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new NullPointerException("cannot call nearestt() with a null point.");
        Point2D nearestP = null;
        double distance = Double.MAX_VALUE;
        for (Point2D point: pointSet) {
            if (point.distanceSquaredTo(p) < distance) {
                nearestP = point;
                distance = point.distanceSquaredTo(p);
            }
        }
        return nearestP;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
    }
}