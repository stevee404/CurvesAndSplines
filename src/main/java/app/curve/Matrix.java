package app.curve;

/**
 * This class implements matrix operations with multi-dimensional arrays.
 * @author stevee404
 * @version 1.0
 */
public class Matrix {
    static double[][] mult(double[][] a, double[][] b) throws Exception{
        if (a[0].length != b.length)
            throw new Exception("A*B not possible. It does not fulfill requirements for matrix multiplication");
        double[][] c = new double [a.length][b[0].length];

        for (int t=0;t < b[0].length;t++) { // Spalte für b,c
            for (int i = 0; i < a.length; i++) { // Zeile für a,c
                for (int j = 0; j < b.length; j++) { // Spalte für a, Zeile für b
                    double x = a[i][j];
                    double y = b[j][t];
                    double z = x * y;
                    c[i][t] += z;
                }
            }
        }
        return c;
    }
    static double[][] mult(double a, double[][] b) {
        double[][] c = new double[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                c[i][j] = a * b[i][j];
            }
        }
        return c;
    }
}