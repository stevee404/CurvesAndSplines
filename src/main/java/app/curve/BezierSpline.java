package app.curve;

import java.util.LinkedList;
import java.util.List;

public class BezierSpline extends AbstractCurve{
    private final double[][] basis = {
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 3, 0, 0},
            {1, 0, 0, 0}
    };

    public BezierSpline(double w, double h) {
        super(w, h);
    }

    public int getM() {
        // Segments:
        // 1 2 3  4  5  6  7  8
        // 4 7 10 13 16 19 22 25 --> an0, an1, ...
        // an = 4 + n*3
        // (an-4)/3 = n
        return (controlPoints.size()-4)/3+1;
    }

    @Override
    protected void calcSegment(int i) throws Exception{
        int x = (int)Math.pow(4,i) -1;
        double [][] G = {
                transformedControlPoints.get(x).toArray(),
                transformedControlPoints.get(x+1).toArray(),
                transformedControlPoints.get(x+2).toArray(),
                transformedControlPoints.get(x+3).toArray()
        };

        LinkedList<Vertex> segPoints = new LinkedList<>();
        for (double t = 0; t <= 1.01; t += 0.01) {
            double[][] segT = {
                    {t * t * t, t * t, t, 1}
            };
            double[][] val = Matrix.mult(basis, G);
            val = Matrix.mult(segT, val);
            segPoints.add(new Vertex(val[0][0],val[0][1]));
        }
        segments.add(i, segPoints);
    }

    @Override
    public void calcCurve() throws Exception{
//        transformCoordinatesystem();
        for (int i=0;i < getM();i++) {
            calcSegment(i);
        }
        calcKnots();
    }

    public void fillBezier() throws Exception{
        addControlPoint(0,0);
        addControlPoint(6,6);
        addControlPoint(2,4);
        addControlPoint(6,2);
        addKnotvalue(0);
        addKnotvalue(1);
        calcCurve();
    }
    public void fillBezier2() throws Exception{
        addControlPoint(0,0);
        addControlPoint(6,6);
        addControlPoint(2,4);
        addControlPoint(6,2);
        addControlPoint(10,0);
        addControlPoint(7,5);
        addControlPoint(6,6);
        addKnotvalue(0);
        addKnotvalue(1);
        addKnotvalue(2);
        calcCurve();
    }

    @Override
    public List<Vertex> getControlPoints() {
        return null;
    }

    @Override
    public List<Knot> getKnots() {
        return null;
    }
}
