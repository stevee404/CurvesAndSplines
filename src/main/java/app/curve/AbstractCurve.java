package app.curve;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCurve implements Curve {
    protected List<Vertex> controlPoints = new LinkedList<>();               // deBoor control points
    protected List<Vertex> transformedControlPoints = new LinkedList<>();    // transformed deBoor control points
    protected List<Integer> knotVector = new LinkedList<>();                // Knotvector T
    protected List<Knot> knots = new LinkedList<>();                        // List of Knots Q
    protected List<List<Double>> segments = new LinkedList<>();             // List of single segments
    protected double width;                                                 // width of Scene
    protected double height;                                                // height of Scene
    protected List<Vertex> centerOfSpline = new LinkedList<>();
    private double margin = 40;

    public AbstractCurve(double w, double h) {
        width = w;
        height = h;
    }

    @Override
    public void addControlPoint(Vertex v) {
        controlPoints.add(v);
    }

    @Override
    public void addControlPoint(double x, double y) {
        addControlPoint(new Vertex(x, y));
    }
    @Override
    public void addAllControlPoints(List<Double> list) {
        for (int i= 0; i < list.size()-1;i+=2) {
            addControlPoint(list.get(i), list.get(i+1));
        }
    }

    @Override
    public void addKnotvalue(int t) {
        knotVector.add(t);
    }

    @Override
    public void addAllKnotvalues(List<Double> list) {
        for (int i= 0; i < list.size();i++) {
            addKnotvalue(list.get(i).intValue());
        }
    }

    @Override
    public List<Vertex> getControlPoints() {
        return controlPoints;
    }

    @Override
    public List<Knot> getKnots() {
        return knots;
    }

    @Override
    public List<List<Double>> getSegments() {
        return segments;
    }

    protected void transformCoordinatesystem() {
        Vertex min = new Vertex(width, height);
        Vertex max = new Vertex(0, 0);
        double w = width - margin * 2;
        double h = height - margin * 2;

        // Calculate most optimal Scale
        double s = 0;
        for (Vertex v : controlPoints) {
            if (v.getX() < min.getX()) min.setX(v.getX());
            if (v.getY() < min.getY()) min.setY(v.getY());

            if (v.getX() > max.getX()) max.setX(v.getX());
            if (v.getY() > max.getY()) max.setY(v.getY());
        }
        s = (int) Math.min(w / max.getX(), h/ max.getY());

        // Calculate translation to Center
        Vertex center = new Vertex((min.getX() + max.getX()) / 2.0, (min.getY() + max.getY()) / 2.0);
        center.scale(s, -s);

        Vertex t = new Vertex(width / 2 - center.getX(), height / 2 - center.getY());
        center.translate(t);
        centerOfSpline.add(center);

        for (Vertex v : controlPoints) {
            transformedControlPoints.add(new Vertex(v.getX() * s, v.getY() * -s));
        }
        transformedControlPoints.forEach(v -> v.translate(t));
    }

    protected abstract void calcSegment(int i) throws Exception;

    protected void calcKnots() throws Exception {
        if (segments.size() == 0) throw new Exception("Curve has no segments");
        for (int i = 0; i < segments.size(); i++) {
            var seg = segments.get(i);
            Knot k = new Knot(seg.get(0),seg.get(1), knotVector.get(i));
            knots.add(k);
            if (i == segments.size() -1) {
                knots.add(new Knot(seg.get(seg.size()-2),seg.get(seg.size()-1), knotVector.get(knotVector.size()-1)));
            }
        }
    }
    @Override
    public abstract void calcCurve() throws Exception;

    protected void drawPoints(List<? extends Vertex> list, ObservableList<Node> children, double radius, Paint paint, TextPosition ts) {
        double pos = 0;
        switch (ts) {
            case UP -> pos = -1;
            case DOWN -> pos = 1;
            default -> {
                System.out.println("ERROR! Wrong TextPosition!");
            }
        }

        for (int i = 0; i < list.size(); i++) {
            var v = list.get(i);
            Circle c = new Circle(v.getX(), v.getY(), radius, paint);

            boolean found = false;
            if (i != 0) {
                for (Node n : children) {
                    Group g = (Group) n;
                    Circle c2 = (Circle) g.getChildren().get(0);
                    if (v.getX() == c2.getCenterX() && v.getY() == c2.getCenterY()) {
                        Text lastText = (Text) g.getChildren().get(1);
                        lastText.setText(lastText.getText() + "=" + v.myString(i));
                        found = true;
                        break;
                    }
                }
                if (found) continue;
            }

            Group child = new Group();
            Text t = new Text(v.getX(), v.getY() + 4 + pos * 11, v.myString(i));
            t.setFill(paint);
            child.getChildren().addAll(c, t);
            children.add(child);
        }
    }

    @Override
    public void drawControlPoints(ObservableList<Node> children) {
        drawPoints(transformedControlPoints, children, 6, Color.DEEPSKYBLUE, TextPosition.UP);
        //drawPoints(centerOfSpline, children, 6,Color.FORESTGREEN, TextPosition.UP);
    }

    @Override
    public void drawKnots(ObservableList<Node> children) {
        drawPoints(knots, children,5, Color.DARKRED, TextPosition.DOWN);
    }

    @Override
    public void drawCurve(ObservableList<Node> children) {
        Polyline p = new Polyline();
        segments.forEach(seg ->
                seg.forEach(num -> p.getPoints().add(num))
        );
        children.add(p);
    }
}
