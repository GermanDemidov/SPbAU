package ru.spbau.demidovg.geometry.chan_algo;

import ru.spbau.demidovg.geometry.chan_algo.convex_hull.Point;
import ru.spbau.demidovg.geometry.chan_algo.convex_hull.Polygon;

import java.awt.*;

class Main implements GUIDelegate {
    private final Polygon polygon;
    private final GUI gui;

    private Main() {
        gui = new GUI(this, 800, 600);
        polygon = new Polygon();
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void mouseClicked(int x, int y) {
        polygon.addPoint(new Point(x, y));
        redrawPoints();
    }

    @Override
    public void keyPressed(int keyCode) {

        if (keyCode == 27) {
            reset();
        }

        if (keyCode == 74) {
            redrawPolygonJarvis();
        }

        if (keyCode == 71) {
            redrawPolygonGraham();
        }

        if (keyCode == 67) {
            redrawPolygonChan();
        }
    }

    private void reset() {
        gui.clear();
        polygon.clear();
    }

    private void redrawPoints() {
        gui.clear();
        gui.drawPoints(polygon.getPoints(), Color.RED);
    }

    private void redrawPolygonChan() {
        gui.clear();
        gui.drawPoints(polygon.getPoints(), Color.BLACK);
        gui.drawEdges(polygon.getChanEdges(), Color.BLUE);
    }

    private void redrawPolygonGraham() {
        gui.clear();
        gui.drawPoints(polygon.getPoints(), Color.BLACK);
        gui.drawEdges(polygon.getGrahamEdges(), Color.GREEN);
    }

    private void redrawPolygonJarvis() {
        gui.clear();
        gui.drawPoints(polygon.getPoints(), Color.BLACK);
        gui.drawEdges(polygon.getJarvisEdges(), Color.CYAN);
    }
}
