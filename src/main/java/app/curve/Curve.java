package app.curve;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.Collection;
import java.util.List;

public interface Curve {

    void addControlPoint(Vertex v);
    void addControlPoint(double x, double y);
    void addAllControlPoints(List<Double> list);
    void addKnotvalue(int t);
    void addAllKnotvalues(List<Double> list);

    List<Vertex> getControlPoints();
    List<Knot> getKnots();
    List<List<Double>> getSegments();

    void calcCurve() throws Exception;

    void drawControlPoints(ObservableList<Node> children);
    void drawKnots(ObservableList<Node> children);
    void drawCurve(ObservableList<Node> children);
}
