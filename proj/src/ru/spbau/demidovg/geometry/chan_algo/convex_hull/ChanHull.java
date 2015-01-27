package ru.spbau.demidovg.geometry.chan_algo.convex_hull;

import java.util.*;

/**
 * Created by german on 27.10.14.
 */
public class ChanHull {

    private ArrayList<Point> selfPoints;
    private ArrayList<Point> result;
    private ArrayList<Edge> edges;

    public ChanHull() {
        selfPoints = new ArrayList<Point>();
        result = new ArrayList<Point>();
        edges = new ArrayList<Edge>();
    }

    private class comparator implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            if (a.leq(b)) return -1;
            else return 1;
        }
    }

    private long vect(Point a, Point b) {
        return a.getX() * b.getY() - b.getX() * a.getY();
    }

    private long scal(Point a, Point b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    private double dist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public ArrayList<Point> GrahamScan(ArrayList<Point> points) {
        Point point0 = points.get(0);
        final int n = points.size();
        for (int i = 1; i < n; i++) {
            if (points.get(i).getY() < point0.getY() || (points.get(i).getY() == point0.getY() && points.get(i).getX() < point0.getX()))
                point0 = points.get(i);
        }
        long x0 = point0.getX();
        long y0 = point0.getY();

        Comparator<Point> cmp = new comparator();
        TreeSet<Point> pts = new TreeSet<>(cmp);

        for (int i = 0; i < n; i++) {
            long newX = points.get(i).getX() - x0;
            long newY = points.get(i).getY() - y0;
            pts.add(new Point(newX, newY));
        }
        /*System.out.println();
        System.out.println(selfPoints);
        System.out.println("PTS " + pts);*/
        ArrayList<Point> res = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            res.add(null);
        }

        int counter = 0;
        for (Point elem : pts) {
            res.set(counter, elem);
            counter++;
            while (counter > 2
                    &&
                    vect(new Point(res.get(counter - 2).getX() - res.get(counter - 3).getX(),
                                    res.get(counter - 2).getY() - res.get(counter - 3).getY()),
                            new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                    res.get(counter - 1).getY() - res.get(counter - 2).getY())) <= 0
                    &&
                    !(vect(new Point(res.get(counter - 2).getX() - res.get(counter - 3).getX(),
                                    res.get(counter - 2).getY() - res.get(counter - 3).getY()),
                            new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                    res.get(counter - 1).getY() - res.get(counter - 2).getY())) == 0
                            &&
                    scal(new Point(res.get(counter - 2).getX() - res.get(counter - 3).getX(),
                                    res.get(counter - 2).getY() - res.get(counter - 3).getY()),
                            new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                    res.get(counter - 1).getY() - res.get(counter - 2).getY())) < 0)
                    ) {
                counter--;
                res.set(counter - 1, res.get(counter));
                res.set(counter, null);
            }
        }
        while (counter > 1
                &&
                vect(new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                res.get(counter - 1).getY() - res.get(counter - 2).getY()),
                        new Point(res.get(0).getX() - res.get(counter - 1).getX(),
                                res.get(0).getY() - res.get(counter - 1).getY())) <= 0
                &&
                !(vect(new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                res.get(counter - 1).getY() - res.get(counter - 2).getY()),
                        new Point(res.get(0).getX() - res.get(counter - 1).getX(),
                                res.get(0).getY() - res.get(counter - 1).getY())) == 0
                        &&
                        scal(new Point(res.get(counter - 1).getX() - res.get(counter - 2).getX(),
                                        res.get(counter - 1).getY() - res.get(counter - 2).getY()),
                                new Point(res.get(0).getX() - res.get(counter - 1).getX(),
                                        res.get(0).getY() - res.get(counter - 1).getY())) < 0)
                )
            counter--;
        /*System.out.println("SELF: "+  selfPoints);
        System.out.println("Just points: " + points);*/
        ArrayList<Point> finalResult = new ArrayList<Point>();

        for (int i = 0; i < counter; i++) {
            if (res.get(i) != null)
                finalResult.add(new Point(res.get(i).getX() + x0, res.get(i).getY() + y0));
        }
        //System.out.println(finalResult);
        //System.out.println();

        return finalResult;
    }

    boolean gangle(Point p1, Point p2, Point p3, Point p4) {
        return (vect(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                new Point(p1.getX() - p2.getX(), p1.getY() - p2.getY()))) *
                vect(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                        new Point(p4.getX() - p2.getX(), p4.getY() - p2.getY())) > 0
                ||
                (vect(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                        new Point(p4.getX() - p2.getX(), p4.getY() - p2.getY())) == 0
                        &&
                        dist(p2, p4) < dist(p2, p3))
                ||
                (vect(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                        new Point(p1.getX() - p2.getX(), p1.getY() - p2.getY())) == 0
                        &&
                        vect(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                                new Point(p4.getX() - p2.getX(), p4.getY() - p2.getY())) != 0
                        &&
                scal(new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY()),
                        new Point(p1.getX() - p2.getX(), p1.getY() - p2.getY())) < 0);
    }

    Point binarySearch(Point p1, Point p2, ArrayList<Point> poly) {
        int k;
        for (k = 1; k * 2 < poly.size(); k *= 2);
        int r = 0;
        //System.out.println("ROUND");

        for (int i = k; i > 0; i /= 2) {
            int next = (r + i) % poly.size();
            int prev = (r + poly.size() - i) % poly.size();
            if (gangle(p1, p2, poly.get(next), poly.get(r))) r = next;
            if (gangle(p1, p2, poly.get(prev), poly.get(r))) r = prev;
        }
        return poly.get(r);
    }

    public void algo() {
        final int n = selfPoints.size();
        ArrayList<Point> res = new ArrayList<>();
        for (int i = 0; i < n + 2; i++){
            res.add(null);
        }

        Point point0 = selfPoints.get(0);
        for (int i = 1; i < n; i++) {
            if (selfPoints.get(i).getY() < point0.getY()
                    || (selfPoints.get(i).getY() == point0.getY() && selfPoints.get(i).getX() < point0.getX()))
                point0 = selfPoints.get(i);
        }
        res.set(0, new Point(point0.getX() - 1, point0.getY()));
        res.set(1, point0);

        int curr = 1;
        int m = 2;
        while (curr <= n) {
            int count = 0;
            int r = n / m;
            boolean notMultiplier = false;
            if (n % m != 0){
                notMultiplier = true;
            }
            if (notMultiplier)
                r = (int) Math.ceil(1.0 * n / m);
            ArrayList<ArrayList<Point>> setsOfPoints = new ArrayList<>();
            for (int i = 0; i < r; i++)
                setsOfPoints.add(new ArrayList<Point>());
            for (int i = 0; i < n / m; i++) {
                //setsOfPoints.set(i, new ArrayList<Point>());
                for (int k = 0; k < m; k++) {
                    setsOfPoints.get(i).add(selfPoints.get(count));
                    count++;
                }
                setsOfPoints.set(i, GrahamScan(setsOfPoints.get(i)));
            }
            if (notMultiplier) {
                for (int k = 0; k < n % m; k++) {
                    setsOfPoints.get(r - 1).add(selfPoints.get(count));
                    count++;
                }
                setsOfPoints.set(r - 1, GrahamScan(setsOfPoints.get(r - 1)));
            }
            for (ArrayList<Point> pt : setsOfPoints) {
                if (pt.size() > m)
                    System.err.println("The power of one of the subsets is greater than m");
            }

            for (int i = curr + 1; i <= m + 1; i++) {
                /*System.out.println();
                System.out.println(res);
                System.out.println(i);
                System.out.println();*/
                res.set(i, binarySearch(res.get(i - 2), res.get(i - 1), setsOfPoints.get(0)));
                for (int j = 1; j < r; j++) {
                    Point q = binarySearch(res.get(i - 2), res.get(i - 1), setsOfPoints.get(j));
                    if (gangle(res.get(i - 2), res.get(i - 1), q, res.get(i))) {
                        res.set(i, q);
                    }
                }
                curr++;
                result = new ArrayList<Point>();
                if (res.get(1).eq(res.get(i))) {
                    for (int j = 0; j < i - 1; j++) {
                        result.add(res.get(j + 1));
                    }
                    edges = new ArrayList<Edge>();
                    for (int k = result.size() - 1; k > 0; k--) {
                        edges.add(new Edge(result.get(k), result.get(k - 1)));
                    }

                    edges.add(new Edge(result.get(result.size() - 1), result.get(0)));
                    return;
                }
            }
            m = Math.min(m * m, n);

            int v = 0;

            for (int i = 0; i < r; i++) {
                v += setsOfPoints.get(i).size();
            }

            result = new ArrayList<Point>();

            for (int i = 0; i < v; i++)
                result.add(null);
            int cur = 0;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < setsOfPoints.get(i).size(); j++) {
                    result.set(cur, setsOfPoints.get(i).get(j));
                    cur++;
                }
            }

        }
            /*System.out.println();
            System.out.println(result);
            System.out.println(selfPoints);*/
        edges = new ArrayList<Edge>();
        for (int k = result.size() - 1; k > 0; k--) {
            edges.add(new Edge(result.get(k), result.get(k - 1)));
        }
        edges.add(new Edge(result.get(result.size() - 1), result.get(0)));
    }


    /*private Pair<Integer, Integer> find_min_point_pair(ArrayList<ArrayList<Point>> convexHulls) {
        Pair<Integer, Integer> coords_of_etalon = new Pair<Integer, Integer>(0,0);
        Point etalon = convexHulls.get(0).get(0);
        for (int i = 0; i < convexHulls.size(); i++) {
            for (int j = 0; j < convexHulls.get(i).size(); j++) {
                if (etalon.getX() > convexHulls.get(i).get(j).getX() ||
                        (etalon.getX() == convexHulls.get(i).get(j).getX() &&
                                etalon.getY() > convexHulls.get(i).get(j).getY()))
                    coords_of_etalon = new Pair<Integer, Integer>(i, j);
            }
        }
        return coords_of_etalon;
    }*/

    /*private Pair<Integer, Integer> next_hull_point_pair(ArrayList<ArrayList<Point>> convexHulls,
                                                        Pair<Integer, Integer> pair) {
        Point p = convexHulls.get(pair.first).get(pair.second);
        Pair<Integer, Integer> next = new Pair<Integer, Integer>(pair.first,(pair.second + 1) %
                convexHulls.get(pair.first).size());

        for (int i = 0; i < convexHulls.size(); i++) {
            if (i != pair.first) {
                int s = rtangent(convexHulls.get(i), p);
                Point q = convexHulls.get(next.first).get(next.second);
                Point r = convexHulls.get(i).get(s);
                int t = orientation(p, q, r);
                if (t == -1 || (t == 0 && GrahamHull.dist(p, r) > GrahamHull.dist(p, q))) {
                    next = new Pair <Integer, Integer>(i, s);
                }
            }
        }
        return next;
    }*/

    /*private int rtangent(ArrayList<Point> hull, Point p) {
        int l = 0;
        int r = hull.size() - 1;

        int l_prev = orientation(p, hull.get(0), hull.get(hull.size() -1));
        int l_next = orientation(p, hull.get(0), hull.get((l + 1) % r));

        while (l < r) {

            int c = (l + r) / 2;
            int prev_coord = 0;
            int next_coord = 0;
            if (c - 1 >= 0) {
                prev_coord = (c - 1) % hull.size();
            }
            else {
                prev_coord = hull.size() - 1;
            }

            if (c + 1 < hull.size()) {
                next_coord = (c + 1) % hull.size();
            }
            else {
                next_coord = 0;
            }
            int c_prev = orientation(p, hull.get(c), hull.get(prev_coord));
            int c_next = orientation(p, hull.get(c), hull.get(next_coord));
            int c_side = orientation(p, hull.get(l), hull.get((c)));

            if ((c_prev != -1) && (l_next == 1 || l_prev == l_next) ||
                    c_side == 1 && c_prev == 1)
                r = c;
            else {
                l = c + 1;
                if (l > hull.size() - 1)
                    break;
                l_prev = hull.size() - c_next - 1;
                l_next = orientation(p, hull.get(l), hull.get((l + 1) % hull.size()));
            }
        }
    return l;
    }*/

    public void clear() {
        result.clear();
        selfPoints.clear();
        edges.clear();
    }

    public void insertPoint(Point p){
        selfPoints.add(p);
        result.clear();
        edges.clear();
    }

    public ArrayList<Edge> getEdges(){
        if (selfPoints.size() > 2) {
            algo();
        }
        return edges;
    }

}
