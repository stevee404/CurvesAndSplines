package app.curve;

import java.util.LinkedList;

/**
 * @author stevee404
 * @version 1.0
 */
public class UniformBSpline extends AbstractCurve{

    private final double[][] basis = {
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}
    };

    private final double BASIS_CONSTANT = 1.0 / 6.0;

    public UniformBSpline(double w, double h) {
        super(w,h);
    }

    @Override
    public int getM() {
        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14     control points
        // 0 0 0 1 2 3 4 5 6 7  8  9  10 11     segments
        // 0 0 1 2 3 4 5 6 7 8  9  10 11 12     m
        return Math.max(controlPoints.size()-2,0);
    }

    @Override
    protected void calcSegment(int i) throws Exception {
        if (controlPoints.size() < 4) {
            throw new Exception("Not enough control points! Number of control points: "+ controlPoints.size());
        }

        double [][] G = {
                controlPoints.get(i-3).toArray(),
                controlPoints.get(i-2).toArray(),
                controlPoints.get(i-1).toArray(),
                controlPoints.get(i).toArray()
        };

        LinkedList<Vertex> segPoints = new LinkedList<>();
        for (double t = 0; t <= 1; t += 0.01) {
            double[][] segT = {
                    {t * t * t, t * t, t, 1}
            };
            double[][] val = Matrix.mult(basis, G);
            val = Matrix.mult(BASIS_CONSTANT, val);
            val = Matrix.mult(segT, val);

            segPoints.add(new Vertex(val[0][0],val[0][1]));
        }
        segments.add(i-3, segPoints);
    }

    @Override
    public void calcCurve() throws Exception{
        for (int i = 0; i < getM()-1; i++) {
            calcSegment(i+3);
        }
        autoCompleteKnotvector();
        calcKnots();
    }

}
