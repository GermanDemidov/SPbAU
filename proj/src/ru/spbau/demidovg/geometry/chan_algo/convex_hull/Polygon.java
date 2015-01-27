package ru.spbau.demidovg.geometry.chan_algo.convex_hull;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points;
    private final JarvisHull jarvis;
    private final GrahamHull graham;
    private final ChanHull chan;

    public Polygon() {
        points = new ArrayList<>();
        jarvis = new JarvisHull();
        graham = new GrahamHull();
        chan = new ChanHull();
    }

    public void addPoint(Point p) {
        if (!points.contains(p)) {
            points.add(p);
            jarvis.insertPoint(p);
            graham.insertPoint(p);
            chan.insertPoint(p);
        }
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Edge> getJarvisEdges() {
        return jarvis.getEdges();
    }

    public ArrayList<Edge> getChanEdges() {
        return chan.getEdges();
    }

    public ArrayList<Edge> getGrahamEdges() {return graham.getEdges();}

    public void clear() {
        graham.clear();
        jarvis.clear();
        chan.clear();
        points.clear();
    }
}
