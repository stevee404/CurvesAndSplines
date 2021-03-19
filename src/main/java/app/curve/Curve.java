package app.curve;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.Collection;
import java.util.List;

/**
 * @author stevee404
 * @version 1.0
 */
public interface Curve {

    void addControlPoint(Vertex v);
    void addControlPoint(double x, double y);
    void addAllControlPoints(List<Double> list);
    void addKnotvalue(int t);
    void addAllKnotvalues(List<Double> list);

    List<Vertex> getControlPoints();
    List<Knot> getKnots();
    List<List<Vertex>> getSegments();
    int getM();

    void calcCurve() throws Exception;

    void draw(ObservableList<Node> obsCP, ObservableList<Node> obsKnots, ObservableList<Node> obsCurve);
}
