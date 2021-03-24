package app.curve;

import java.util.LinkedList;
import java.util.List;

/**
 * @author stevee404
 * @version 1.0
 */
public class BezierSpline extends AbstractCurve {
    private final double[][] basis = {
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 3, 0, 0},
            {1, 0, 0, 0}
    };

    public BezierSpline(double w, double h) {
        super(w, h);
    }

    @Override
    public int getM() {
        // Segments: 1 2 3  4  5  6  7  8
        //      CPs: 4 7 10 13 16 19 22 25 --> an0, an1, ...
        // an = 4 + n*3,    n={1,2,3,...}
        // (an-4)/3 = n
        return (controlPoints.size() - 4) / 3 + 1;
    }
    @Override
    protected void autoCompleteKnotvector() { // for uniform Splines
        int delta = knotVector.get(knotVector.size() - 1) - knotVector.get(knotVector.size() - 2);
        int length = Math.abs(knotVector.size() - (getM()+1));
        if (knotVector.size() < getM()+1) {
            for (int i = 0; i < length; i++) {
                knotVector.add(knotVector.get(knotVector.size()-1) + delta);
            }
        } else if (knotVector.size() > getM()+1){
            for (int i = 0; i < length; i++) {
                knotVector.remove(knotVector.size() - 1);
            }
        }
    }

    @Override
    protected void calcSegment(int i) throws Exception {
        int x = Math.max(4 * i - i, 0);
        //      x       G
        // i=0: 0       0  1  2  3
        // i=1: 3       3  4  5  6
        // i=2: 6       6  7  8  9
        // i=3: 9       9  10 11 12
        // i=4: 12      12 13 14 15
        double[][] G = {
                controlPoints.get(x).toArray(),
                controlPoints.get(x + 1).toArray(),
                controlPoints.get(x + 2).toArray(),
                controlPoints.get(x + 3).toArray()
        };

        LinkedList<Vertex> segPoints = new LinkedList<>();
        for (double t = 0; t <= 1.01; t += 0.01) {
            double[][] segT = {
                    {t * t * t, t * t, t, 1}
            };
            double[][] val = Matrix.mult(basis, G);
            val = Matrix.mult(segT, val);
            segPoints.add(new Vertex(val[0][0], val[0][1]));
        }
        segments.add(i, segPoints);
    }

    @Override
    public void calcCurve() throws Exception {
        for (int i = 0; i < getM(); i++) {
            calcSegment(i);
        }
        autoCompleteKnotvector();
        calcKnots();
    }

    public void fillBezier() throws Exception {
        addControlPoint(0, 0);
        addControlPoint(6, 6);
        addControlPoint(2, 4);
        addControlPoint(6, 2);
        addKnotvalue(0);
        addKnotvalue(1);
        calcCurve();
    }

    public void fillBezier2() throws Exception {
        addControlPoint(0, 0);
        addControlPoint(6, 6);
        addControlPoint(2, 4);
        addControlPoint(6, 2);
        addControlPoint(10, 0);
        addControlPoint(7, 5);
        addControlPoint(6, 6);
        addKnotvalue(0);
        addKnotvalue(1);
        addKnotvalue(2);
        calcCurve();
    }
}
