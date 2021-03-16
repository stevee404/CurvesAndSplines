package app.curve;

import java.util.LinkedList;

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

    public int getM() {
        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14     control points
        // 0 0 0 1 2 3 4 5 6 7  8  9  10 11     segments
        // 0 0 1 2 3 4 5 6 7 8  9  10 11 12     m
        return Math.max(controlPoints.size()-2,0);
    }

    @Override
    protected void calcSegment(int i) throws Exception {
        if (controlPoints.size() < 4) {
            throw new Exception("Not enough fulcrums! Number of fulcrums: "+ controlPoints.size());
        }
        if (knotVector.size() != getM()) {
            throw new Exception("Number of Knotvector T="+ knotVector.size() + " does not equal m="+getM());
        }
        if (i < 3) {
            throw new Exception("Wrong segment. It starts at 3 not "+i);
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
        calcKnots();
    }

    public void fillBSpline() throws Exception{
        addControlPoint(1,2);
        addControlPoint(1,5);
        addControlPoint(4,5);
        addControlPoint(2,4);
        addControlPoint(3,1);
        addControlPoint(3,1);
        addControlPoint(5,2);
        addControlPoint(6,6);
        addControlPoint(6,6);
        addControlPoint(6,6);
        addControlPoint(6,4);
        addControlPoint(6,4);
        addControlPoint(8,4);
        addControlPoint(9,1);
        addKnotvalue(10);
        addKnotvalue(13);
        addKnotvalue(16);
        addKnotvalue(19);
        addKnotvalue(22);
        addKnotvalue(25);
        addKnotvalue(28);
        addKnotvalue(31);
        addKnotvalue(34);
        addKnotvalue(37);
        addKnotvalue(40);
        addKnotvalue(43);
        calcCurve();
    }

}
