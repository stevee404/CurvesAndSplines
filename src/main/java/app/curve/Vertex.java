package app.curve;

import java.util.Objects;

public class Vertex {
    private double x;
    private double y;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.x, x) == 0 && Double.compare(vertex.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public String myString(int i) {
        return ""+i;
    }

    public double[] toArray() {
        return new double[] {x,y};
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void scale(double s) {
        this.x *= s;
        this.y *= s;
    }
    public void scale(double sx, double sy) {
        this.x *= sx;
        this.y *= sy;
    }

    public void translate(Vertex v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public double length() {
        return Math.sqrt(x*x + y * y);
    }
}