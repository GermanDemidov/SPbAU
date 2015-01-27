package ru.spbau.demidovg.geometry.chan_algo.convex_hull;

public class Point implements Comparable<Point> {

    private final long x;
    private final long y;
    private final int chain;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
        chain = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(x) + " " + String.valueOf(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        return isEqual(point);
    }

    private boolean isEqual(Point point) {
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        long result = x;
        result = 31 * result + y;
        return (int) result;
    }

    @Override
    public int compareTo(Point other) {
        if (chain != other.chain && other.chain == 0) {
            throw new IllegalStateException("Other point must have chain");
        }
        if (x == other.x) {
            return (int) (y - other.y);
        } else return (int) (x - other.x);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public boolean leq(Point point) {
        return x * point.y > point.x * y || (x * point.y == point.x * y && x * x + y * y <= point.x * point.x + point.y * point.y);
    }

    boolean eq(Point p) {
        return x == p.getX() && y == p.getY();
    }


}
