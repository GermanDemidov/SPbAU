package ru.spbau.demidovg.geometry.chan_algo.convex_hull;

import java.util.ArrayList;

/**
 * Created by german on 27.10.14.
 */
public class JarvisHull {

    private ArrayList<Point> selfPoints;
    private ArrayList<Edge> result;
    private ArrayList<Integer> order;

    public JarvisHull()
    {
        selfPoints = new ArrayList<Point>();
        result = new ArrayList<Edge>();
        order = new ArrayList<Integer>();
    }

    public void algo()
    {
        if (selfPoints.size() <= 2)
            return;

        for (Point p : selfPoints) {
            order.add(-1);
        }

        int min = 0;
        for (int i = 1; i < selfPoints.size(); i++) {
            if (selfPoints.get(i).getX() < selfPoints.get(min).getX() ||
                    (selfPoints.get(i).getX() == selfPoints.get(min).getX() && selfPoints.get(i).getY() < selfPoints.get(min).getY()))
                min = i;
        }

        int p = min;
        int q = 0;

        ArrayList<Point> tmpResult = new ArrayList<Point>();

        do {
            q = (p + 1) % selfPoints.size();
            for (int i = 0; i < selfPoints.size(); i++) {
                if (orientation(selfPoints.get(p), selfPoints.get(i), selfPoints.get(q)) == 1)
                    q = i;
            }
            order.set(p, q);
            tmpResult.add(selfPoints.get(p));
            p = q;
        } while (p != min);

        for (int i = 0; i < tmpResult.size() - 1; i++) {
            result.add(new Edge(tmpResult.get(i), tmpResult.get(i + 1)));
        }
        result.add(new Edge(tmpResult.get(0), tmpResult.get(tmpResult.size() - 1)));
    }


    public void insertPoint(Point p) {
        selfPoints.add(p);
        result.clear();
        order.clear();
        algo();
    }

    public ArrayList<Edge> getEdges() {
        algo();
        return result;
    }

    public void clear() {
        result.clear();
        selfPoints.clear();
        order.clear();
    }

    static int orientation(Point p, Point q, Point r)
    {
        long val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear
        return (val > 0)? -1: 1; // clock or counterclock wise
    }

}
