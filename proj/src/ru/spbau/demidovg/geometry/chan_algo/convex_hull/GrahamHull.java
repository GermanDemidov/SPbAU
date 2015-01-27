package ru.spbau.demidovg.geometry.chan_algo.convex_hull;


import java.util.*;

/**
 * Created by german on 28.10.14.
 */
public class GrahamHull {
    private ArrayList<Point> selfPoints;
    private ArrayList<Point> result;
    private ArrayList<Edge> tmpEdges;
    private ArrayList<Edge> edges;
    private GrahamStack stack = new GrahamStack();
    private Point p0;

    public GrahamHull()
    {
        selfPoints = new ArrayList<Point>();
        result = new ArrayList<Point>();
        tmpEdges = new ArrayList<Edge>();
        edges = new ArrayList<Edge>();
    }

    public void grahamScan()
    {
        long ymin = selfPoints.get(0).getY();
        int min = 0;
        for (int i = 1; i < selfPoints.size(); i++) {
            long y = selfPoints.get(i).getY();
            if ((y < ymin) || (ymin == y && selfPoints.get(i).getX() < selfPoints.get(min).getX())) {
                ymin = selfPoints.get(i).getY();
                min = i;
            }
        }
        Collections.swap(selfPoints, 0, min);
        p0 = selfPoints.get(0);

        Collections.sort(selfPoints, new PointComparator(p0));

        if (selfPoints.size() > 2) {
            stack.push(selfPoints.get(0));
            stack.push(selfPoints.get(1));
            addTmpEdge();
            stack.push(selfPoints.get(2));
            addTmpEdge();
        }
        else return;
        for (int i = 3; i < selfPoints.size(); i++) {
            while (stack.size() >= 2 && orientation(stack.nextToTop(), stack.top(), selfPoints.get(i)) != 2) {
                {
                    stack.pop();
                    tmpEdges.remove(tmpEdges.size() - 1);
                }
            }
            stack.push(selfPoints.get(i));
            addTmpEdge();
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        for (int i = result.size() - 1; i > 0; i--) {
            edges.add(new Edge(result.get(i), result.get(i - 1)));
        }

        edges.add(new Edge(result.get(result.size() - 1), result.get(0)));

    }

    public void insertPoint(Point p) {
        selfPoints.add(p);
        result.clear();
        edges.clear();
        tmpEdges.clear();
        stack.clear();
    }

    public void clear() {
        result.clear();
        selfPoints.clear();
        stack.clear();
    }

    public void addTmpEdge() {
        Edge toWrite = new Edge(stack.top(), stack.nextToTop());
        tmpEdges.add(toWrite);
    }

    public ArrayList<Edge> getEdges(){
        grahamScan();
        return edges;
    }

    static int orientation(Point p, Point q, Point r)
    {
        long val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }


    public static double dist(Point p1, Point p2)
    {
        return (p1.getX() - p2.getX())*(p1.getX() - p2.getX()) + (p1.getY() - p2.getY())*(p1.getY() - p2.getY());
    }

}

class PointComparator implements Comparator<Point>
{
    Point p0;
    PointComparator(Point leftMost) {
        p0 = leftMost;
    }
    public int compare(Point p1, Point p2) {
        {
            // Find orientation
            int o = GrahamHull.orientation(p0, p1, p2);
            if (o == 0)
                return (GrahamHull.dist(p0, p2) >= GrahamHull.dist(p0, p1))? -1 : 1;

            return (o == 2)? -1: 1;
        }
    }
}
class GrahamStack extends Stack<Point> {

    public Point top() {
        int length = size();
        if (length == 0) {
            throw new EmptyStackException();
        }
        return elementAt(length - 1);
    }

    public Point nextToTop() {
        int len = size();
        if (len == 0) {
            throw new EmptyStackException();
        }
        else {
            return elementAt(len - 2);
        }
    }

    @Override
    public Point pop() {
        return super.pop();
    }
}