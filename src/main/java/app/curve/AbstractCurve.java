package app.curve;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;


/**
 * @author stevee404
 * @version 1.0
 */
public abstract class AbstractCurve implements Curve {
    protected List<Vertex> controlPoints = new LinkedList<>();               // deBoor control points
    protected List<Integer> knotVector = new LinkedList<>();                // Knotvector T
    protected List<Knot> knots = new LinkedList<>();                        // List of Knots Q
    protected List<List<Vertex>> segments = new LinkedList<>();             // List of single segments
    protected double width;                                                 // width of view
    protected double height;                                                // height of view
    protected List<Vertex> centerOfSpline = new LinkedList<>();

    private double margin = 40;
    private Vertex scale;
    private Vertex translation;

    public AbstractCurve(double w, double h) {
        width = w;
        height = h;
    }

    @Override
    public void setWidth(double width) {
        assert(width >= 0);
        this.width = width;
    }

    @Override
    public void setHeight(double height) {
        assert(height >= 0);
        this.height = height;
    }

    @Override
    public void setDimensions(double width, double height) {
        setWidth(width);
        setHeight(height);
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
        for (int i = 0; i < list.size() - 1; i += 2) {
            addControlPoint(list.get(i), list.get(i + 1));
        }
    }

    @Override
    public void addKnotvalue(int t) {
        knotVector.add(t);
    }

    @Override
    public void addAllKnotvalues(List<Integer> list) {
        for (Integer t : list) {
            addKnotvalue(t);
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
    public List<List<Vertex>> getSegments() {
        return segments;
    }

    @Override
    public List<Integer> getKnotvector(){
        return knotVector;
    }
    /**
     * This function calculates how many segments this curve has, based on the number of control points.
     *
     * @return The number of segments.
     */
    @Override
    public abstract int getM();

    /**
     * This function calculates the transformation, so when drawn, the curve is correctly displayed to the user.
     *
     * @param points a list of points (x,y), often the control points
     */
    protected void calcTransformation(List<? extends Vertex> points) {
        Vertex min = new Vertex(width, height);
        Vertex max = new Vertex(0, 0);
        double w = width - margin * 2;
        double h = height - margin * 2;

        // Calculate most optimal Scale
        for (Vertex v : points) {
            if (v.getX() < min.getX()) min.setX(v.getX());
            if (v.getY() < min.getY()) min.setY(v.getY());

            if (v.getX() > max.getX()) max.setX(v.getX());
            if (v.getY() > max.getY()) max.setY(v.getY());
        }
        double s = Math.min(w / max.getX(), h / max.getY());
        scale = new Vertex(1, 1);
        scale.setX(s);
        scale.setY(-s);
        // Calculate translation to the center of the view
        Vertex center = new Vertex((min.getX() + max.getX()) / 2.0, (min.getY() + max.getY()) / 2.0);
        center.scale(scale);

        translation = new Vertex(width / 2 - center.getX(), height / 2 - center.getY());
        center.translate(translation);
        centerOfSpline.add(center);
    }

    @Override
    public Vertex getScale() {
        return scale;
    }

    @Override
    public Vertex getTranslation() {
        return translation;
    }

    protected abstract void calcSegment(int i) throws Exception;


    protected void autoCompleteKnotvector() { // for uniform Splines
        int delta = knotVector.get(knotVector.size() - 1) - knotVector.get(knotVector.size() - 2);
        int length = Math.abs(knotVector.size() - getM());
        System.out.println("m: "+getM());
        System.out.println("length: "+length);
        if (knotVector.size() < getM()) {
            for (int i = 0; i < length; i++) {
                knotVector.add(knotVector.get(knotVector.size()-1) + delta);
            }
        } else if (knotVector.size() > getM()){
            for (int i = 0; i < length; i++) {
                knotVector.remove(knotVector.size() - 1);
            }
        }

        System.out.println(knotVector);
    }

    /**
     * This function takes the first point of every segment and combines it with its certain knot value t.
     *
     * @throws Exception if a curve has no segments
     */
    protected void calcKnots() throws Exception {
        if (segments.size() == 0)
            throw new Exception("Curve has no segments");
        for (int i = 0; i < segments.size(); i++) {
            var seg = segments.get(i);
            Knot k = new Knot(seg.get(0), knotVector.get(i)); // knot at the start of segment
            knots.add(k);
            if (i == segments.size() - 1) {
                knots.add(new Knot(seg.get(seg.size() - 1), knotVector.get(knotVector.size() - 1)));
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
            default -> System.err.println("ERROR! Wrong TextPosition!");
        }

        for (int i = 0; i < list.size(); i++) {
            var cp = list.get(i);

            Vertex v;
            if (cp instanceof Knot) {
                v = new Knot(cp.getX(),cp.getY(), ((Knot) cp).getT());
            } else {
                v = new Vertex(cp.getX(),cp.getY());
            }
            v.scale(scale);     // Transformation to window coordinates
            v.translate(translation);
            Circle c = new Circle(v.getX(), v.getY(), radius, paint);

            boolean found = false;
            if (i != 0) {       // Checking if there is a circle with the same position
                for (Node n : children) {
                    Group g = (Group) n;
                    Circle c2 = (Circle) g.getChildren().get(0);
                    if (v.getX() == c2.getCenterX() && v.getY() == c2.getCenterY()) {
                        Text lastText = (Text) g.getChildren().get(1);
                        lastText.setText(lastText.getText() + "=" + v.toText(i));
                        found = true;
                        break;
                    }
                }
                if (found) continue;
            }

            Group child = new Group();
            Text t = new Text(v.getX(), v.getY() + 4 + pos * 11, v.toText(i));
            t.setFill(paint);
            t.setFont(Font.font("System", FontWeight.BOLD, 14));
            child.getChildren().addAll(c, t);
            children.add(child);
        }
    }

    protected void drawControlPoints(ObservableList<Node> children) {
        drawPoints(controlPoints, children, 6, Color.DEEPSKYBLUE, TextPosition.UP);
//        drawPoints(centerOfSpline,children,6,Color.DARKGREEN,TextPosition.UP);
    }

    protected void drawKnots(ObservableList<Node> children) {
        drawPoints(knots, children, 5, Color.DARKRED, TextPosition.DOWN);
    }

    protected void drawCurve(ObservableList<Node> children) {
        Polyline p = new Polyline();
        p.setStroke(Color.CORNFLOWERBLUE);
        p.setStrokeWidth(2);

        segments.forEach(seg ->
                seg.forEach(n -> {
                    var num = new Vertex(n.getX(), n.getY());
                    num.scale(scale);
                    num.translate(translation);
                    p.getPoints().addAll(num.toArray()[0], num.toArray()[1]);
                })
        );
        children.add(p);
    }

    /**
     * This function will add all the elements to these lists that are needed in order to display control points,
     * knots and the curve.
     *
     * @param obsCP    All elements for the control points will be added to this list.
     * @param obsKnots All elements for the knots will be added to this list.
     * @param obsCurve All elements for the curve will be added to this list.
     */
    @Override
    public void draw(ObservableList<Node> obsCP, ObservableList<Node> obsKnots, ObservableList<Node> obsCurve) {
        calcTransformation(controlPoints);
        drawControlPoints(obsCP);
        drawKnots(obsKnots);
        drawCurve(obsCurve);
    }
}
