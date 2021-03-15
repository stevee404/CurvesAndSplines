package app.curve;

public class CurveFactory {
    private CurveFactory(){}
    public static Curve createCurve(String curveType, double width, double height) {
        Curve c = null;
        switch(curveType) {
            case "Bezier"-> c = new BezierSpline(width, height);
            case "Uniform B-Spline" -> c = new UniformBSpline(width,height);
            default -> System.err.println("Unknown Type");
        }
        return c;
    }

}
