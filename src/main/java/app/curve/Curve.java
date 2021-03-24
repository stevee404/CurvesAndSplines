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

    void setWidth(double width);
    void setHeight(double height);
    void setDimensions(double width, double height);
    void addControlPoint(Vertex v);
    void addControlPoint(double x, double y);
    void addAllControlPoints(List<Double> list);
    void addKnotvalue(int t);
    void addAllKnotvalues(List<Integer> list);

    List<Vertex> getControlPoints();
    List<Knot> getKnots();
    List<Integer> getKnotvector();
    List<List<Vertex>> getSegments();
    int getM();
    Vertex getScale();
    Vertex getTranslation();

    void calcCurve() throws Exception;

    void draw(ObservableList<Node> obsCP, ObservableList<Node> obsKnots, ObservableList<Node> obsCurve);
}
