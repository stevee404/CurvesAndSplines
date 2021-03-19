package app.curve;

/**
 * @author stevee404
 * @version 1.0
 */
public class CurveFactory {
    private CurveFactory(){}
    public static Curve createCurve(String curveType, double width, double height) throws Exception {
        Curve c;
        switch(curveType) {
            case "Bezier"-> c = new BezierSpline(width, height);
            case "Uniform B-Spline" -> c = new UniformBSpline(width,height);
            default -> throw new Exception("Not a Scurve type");
        }
        return c;
    }

}
