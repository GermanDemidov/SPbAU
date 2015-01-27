package ru.spbau.demidovg.geometry.chan_algo;

import ru.spbau.demidovg.geometry.chan_algo.convex_hull.Edge;
import ru.spbau.demidovg.geometry.chan_algo.convex_hull.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GUI {
    private static final int POINT_RADIUS = 2;

    private final Canvas canvas;
    private final int width;
    private final int height;

    public GUI(final GUIDelegate delegate, int width, int height) {
        this.width = width;
        this.height = height;
        assert delegate != null;

        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        canvas = new Canvas();
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                delegate.keyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    delegate.mouseClicked(e.getX(), convert(e.getY()));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        canvas.setSize(width, height-200);

        frame.add(canvas);
        frame.setVisible(true);
    }

    private int convert(int y) {
        return this.canvas.getHeight() - y;
    }

    public void drawPoints(ArrayList<Point> points, Color color) {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(color);
        for (Point p : points) {
            graphics.drawOval((int) p.getX() - POINT_RADIUS / 2, convert((int) p.getY()) - POINT_RADIUS / 2, POINT_RADIUS, POINT_RADIUS);
        }
    }

    public void drawLines(ArrayList<Point> points, Color color) {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(color);

        int[] x = new int[points.size()];
        int[] y = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            x[i] = (int) points.get(i).getX();
            y[i] = convert((int) points.get(i).getY());
        }
        graphics.drawPolyline(x, y, points.size());
    }

    public void drawEdges(ArrayList<Edge> tmpEdges, Color color) {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(color);

        for (int i = 0; i < tmpEdges.size(); i++) {
            graphics.drawLine((int)tmpEdges.get(i).firstPoint().getX(),
                    convert((int)tmpEdges.get(i).firstPoint().getY()),
                    (int)tmpEdges.get(i).secondPoint().getX(),
                    convert((int)tmpEdges.get(i).secondPoint().getY()));
        }

    }

    public void clear() {
        Graphics graphics = canvas.getGraphics();
        graphics.clearRect(0, 0, width, height);
    }

}
