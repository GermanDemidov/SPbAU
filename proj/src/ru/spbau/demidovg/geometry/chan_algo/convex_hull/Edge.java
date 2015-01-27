package ru.spbau.demidovg.geometry.chan_algo.convex_hull;

public class Edge extends Pair<Point, Point> {
    public Edge(Point first, Point second) {
        super(first, second);
    }

    public Point firstPoint() {
        return first;
    }

    public Point secondPoint() {
        return second;
    }
}
