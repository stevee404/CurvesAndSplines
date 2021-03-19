package app.curve;

import java.util.Objects;

/**
 * This class is only adds the knotvalue t to a Vertex
 * @author stevee404
 * @version 1.0
 */
class Knot extends Vertex {
    private int t;

    public Knot(double x, double y, int t) {
        super(x, y);
        this.t = t;
    }
    public Knot(Vertex v, int t) {
        this(v.getX(),v.getY(),t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Knot)) return false;
        if (!super.equals(o)) return false;
        Knot knot = (Knot) o;
        return t == knot.t;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), t);
    }

    @Override
    public String toString() {
        return "Q(x="+getX()+",y="+getY()+",t="+ t + ")";
    }

    @Override
    public String toText(int i) {
        return "Q("+ t + ")";
    }

    public int getT() {
        return t;
    }
}
