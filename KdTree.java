import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    //private static final boolean VERTICAL = true;
    //private static final boolean HORIZONTAL = false;
    private int N = 0; //size of the KdTree
    private Node root;
    
    private static class Node implements Comparable<Point2D>{
        private Point2D p;
        private RectHV rect;
        private Node left,right;
        public boolean direction;
        
        private Node(Point2D p, boolean direction, RectHV r) {
            this.p = p;
            this.direction = direction;
            this.rect = r;
        }
        
        
		public int compareTo(Point2D that) {
			if (direction) {
				return Point2D.X_ORDER.compare(this.p, that);
			} else {
				return Point2D.Y_ORDER.compare(this.p, that);
			}
		}
    }
    
    public KdTree() {                              // construct an empty set of points 
        
    }
    public boolean isEmpty() {                      // is the set empty? 
        return size() == 0;
    }
    
    public int size() {                        // number of points in the set 
        return N;
    }
    
    /*public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException("cannot call insert() with a null point.");
        root = put(root, p);        
    }
    
    private Node put(Node x, Point2D p) {
        if (x == null) {
            if (N == 0) {
                x = new Node(p);
                x.direction = VERTICAL;    //set the direction of root
                x.rect = new RectHV(0, 0, 1.0, 1.0);
                N++;
                return x;
            }
            N++;
            return new Node(p);
        }
        if (p.equals(x.p)) return x;        //for points that are already in kdtree, return the existed node
        if (x.direction) {                        //vertical node
            if (p.x() < x.p.x()) {
                x.left = put(x.left, p);
                x.left.direction = HORIZONTAL;
                RectHV leftRectOfX = new RectHV(0, 0, x.p.x(), 1.0);
                x.left.rect = overlapRect(leftRectOfX, x.rect);
            }
            if (p.x() > x.p.x()) {
                x.right = put(x.right, p);
                x.right.direction = HORIZONTAL;
                RectHV rightRectOfX = new RectHV(x.p.x(), 0, 1.0, 1.0);
                x.right.rect = overlapRect(rightRectOfX, x.rect);
            }
            
        }
        if (!x.direction) {                      //horizontal node
            if (p.y() < x.p.y()) {
                x.left = put(x.left, p);
                x.left.direction = VERTICAL;
                RectHV bottomRectOfX = new RectHV(0, 0, 1.0, x.p.y());
                x.left.rect = overlapRect(bottomRectOfX, x.rect);
            }
            if (p.y() > x.p.y()) {
                x.right = put(x.right, p);
                x.right.direction = VERTICAL;
                RectHV topRectOfX = new RectHV(0, x.p.y(), 1.0, 1.0);
                x.right.rect = overlapRect(topRectOfX, x.rect);
            }
        }
        return x;
    }
    
    private RectHV overlapRect(RectHV rect1, RectHV rect2) {
        double xmin = Math.max(rect1.xmin(), rect2.xmin());
        double ymin = Math.max(rect1.ymin(), rect2.ymin());
        double xmax = Math.min(rect1.xmax(), rect2.xmax());
        double ymax = Math.min(rect1.ymax(), rect2.ymax());
        return new RectHV(xmin, ymin, xmax, ymax);
    }*/
    
    public void insert(Point2D p) {
		RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
		root = put(root, p, true, r);
	}

	private Node put(Node n, Point2D p, boolean vertical, RectHV r) {
		if (n == null) {
			//StdOut.println(p + ": adding " + (vertical?"vertical":"horizontal") + " node");
			N++;
			return new Node(p, vertical, r);
		} 
		if (p.equals(n.p)) {
			return n;
		} else {
			int cmp = n.compareTo(p);
			if (cmp > 0) {
				//StdOut.println(p + ": going left at " + n.p);
				if (n.direction) {
					r = new RectHV(r.xmin(), r.ymin(), n.p.x(), r.ymax());
				} else {
					r = new RectHV(r.xmin(), r.ymin(), r.xmax(), n.p.y());
				}
				n.left  = put(n.left, p, !vertical, r);
			} else { 
				//StdOut.println(p + ": going right at " + n.p);
				if (n.direction) {
					r = new RectHV(n.p.x(), r.ymin(), r.xmax(), r.ymax());
				} else {
					r = new RectHV(r.xmin(), n.p.y(), r.xmax(), r.ymax());
				}
				n.right = put(n.right, p, !vertical, r);
			}
			return n;
		}
	}
    
    public boolean contains(Point2D p) {            // does the set contain point p?
        //return getPoint(root, p);
    	return getPoint(p, root);
    }
    
    /*private boolean getPoint(Node x, Point2D p) {
        if (x == null) return false;
        //StdOut.println("Node of " + x.p + "direction is " + x.direction);
        if (p.equals(x.p)) return true;
        if (x.direction) {                        //vertical node
            if (p.x() < x.p.x()) {
                return getPoint(x.left, p);
            }
            if (p.x() > x.p.x()) {
                return getPoint(x.right, p);
            }
        } else {                      //horizontal node
            if (p.y() < x.p.y()) {
                return getPoint(x.left, p);
            }
            if (p.y() > x.p.y()) {
                return getPoint(x.right, p);
            }
        }

    }*/
    
    private boolean getPoint(Point2D p, Node n) {
		if (n == null) return false;
		if (n.p.equals(p)) return true;
		if (n.direction) {
			return getPoint(p, (p.x() < n.p.x() ? n.left : n.right));
		} else {
			return getPoint(p, (p.y() < n.p.y() ? n.left : n.right));
		}
	}
    
    public void draw() {                         // draw all points to standard draw 
        //StdDraw.setScale(0.0, 1.0);
        drawNode(root);
    }
    
    private void drawNode(Node x) {
        if (x == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.p.x(), x.p.y());
        //x.p.draw();        //call Point2D.draw() will open a new draw pad
        StdDraw.setPenRadius();
        if (x.direction) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        if (!x.direction) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        drawNode(x.left);
        drawNode(x.right);
    }
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle 
        Queue<Point2D> pointsInRect = new Queue<Point2D>();
        findPoints(root, rect, pointsInRect);
        return pointsInRect;
    }
    
    private void findPoints(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null) return;
        if (rect.intersects(x.rect)) {
            if (rect.contains(x.p)) queue.enqueue(x.p);
            findPoints(x.left, rect, queue);
            findPoints(x.right, rect, queue);
        }
    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new NullPointerException("cannot call nearest() with a null point.");
        return findNearest(root, p, null);
    }
    
    private Point2D findNearest(Node x, Point2D p, Point2D tmpNearest) {
        if (x == null) return tmpNearest;
        double shortDist;
        if (tmpNearest == null) {
            tmpNearest = x.p;
            shortDist = Double.MAX_VALUE;
        } else shortDist = p.distanceSquaredTo(tmpNearest);
        
        if (shortDist > x.rect.distanceSquaredTo(p)){
            double distance = p.distanceSquaredTo(x.p);
            if (distance < p.distanceSquaredTo(tmpNearest)) {
                tmpNearest = x.p;            
            }
            
            if (x.left != null && x.left.rect.contains(p)) {
                tmpNearest = findNearest(x.left, p, tmpNearest);
                tmpNearest = findNearest(x.right, p, tmpNearest);
            } else {
                tmpNearest = findNearest(x.right, p, tmpNearest);
                tmpNearest = findNearest(x.left, p, tmpNearest);
            }
        }

        return tmpNearest;
        
    }
        

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(0.2, 0.2);
        Point2D p2 = new Point2D(0.3, 0.3);
        //StdOut.println(tree.contains(p1));
        tree.insert(p1);
        tree.insert(p2);
        //StdOut.println(tree.contains(p1));
        //StdOut.println(tree.contains(p2));
        Point2D p3 = new Point2D(0.4, 0.4);
        Point2D p4 = new Point2D(0.5, 0.5);
        Point2D p5 = new Point2D(0.1, 0.1);
        Point2D p6 = new Point2D(0.1, 0.05);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        tree.insert(p6);
        //StdOut.println(tree.contains(p3));    
        StdOut.println(tree.contains(p6));    
        //tree.draw();
        StdOut.println(tree.nearest(new Point2D(0.1, 0.15)).toString());
        StdOut.println(tree.size());
        //for (Point2D p: tree.range(new RectHV(0.15, 0.15, 0.35, 0.35))) {
        //    StdOut.println(p.toString());
        //}
    }
}
