package app.curve;

import app.curve.UniformBSpline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniformBSplineTest {
    UniformBSpline b = new UniformBSpline(1000,1000);
    UniformBSpline b2 = new UniformBSpline(1000,1000);

    public void fillBSpline(UniformBSpline b) {
        b.addControlPoint(1,2);
        b.addControlPoint(1,5);
        b.addControlPoint(4,5);
        b.addKnotvalue(10);
    }

    public void fillBSpline2(UniformBSpline b) {
        b.addControlPoint(1,2);
        b.addControlPoint(1,5);
        b.addControlPoint(4,5);
        b.addControlPoint(2,4);
        b.addControlPoint(3,1);
        b.addControlPoint(3,1);
        b.addControlPoint(5,2);
        b.addControlPoint(6,6);
        b.addControlPoint(6,6);
        b.addControlPoint(6,6);
        b.addControlPoint(6,4);
        b.addControlPoint(6,4);
        b.addControlPoint(8,4);
        b.addControlPoint(9,1);
        b.addKnotvalue(10);
        b.addKnotvalue(13);
        b.addKnotvalue(16);
        b.addKnotvalue(19);
        b.addKnotvalue(22);
        b.addKnotvalue(25);
        b.addKnotvalue(28);
        b.addKnotvalue(31);
        b.addKnotvalue(34);
        b.addKnotvalue(37);
        b.addKnotvalue(40);
        b.addKnotvalue(43);
    }

    @BeforeEach
    public void setup() {
        fillBSpline(b);
        fillBSpline2(b2);
    }

    @Test
    public void getM_test1(){
        assertEquals(1, b.getM());
    }

    @Test
    public void getM_test2(){
        assertEquals(12, b2.getM());
    }

    @Test
    public void calcSegment_test1(){ //not enough fulcrums
        try {
            //b.calcSegment(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*Ü
    @Test
    public void calcSegment_test2(){ //not enough knotvalues
        b.addControlPoint(2,4);
        try {
            b.calcSegment(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calcSegment_test3(){ // knotvector != m
        b.addControlPoint(2,4);
        b.addKnotvalue(13);
        b.addKnotvalue(16);
        try {
            b.calcSegment(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void calcSegment_test4(){ //  i<3 in segment
        b.addControlPoint(2,4);
        b.addKnotvalue(13);
        try {
            b.calcSegment(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void calcSegment_test5(){ // i>m-1 in segment
        b.addControlPoint(2,4);
        b.addKnotvalue(13);
        try {
            b.calcSegment(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void calcSegment_test6(){ // i>m-1 in segment
        try {
            b2.calcSegment(13);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        fail();
    }

    @Test
    public void calcCurve_test1(){
        b.addControlPoint(2,4);
        b.addKnotvalue(13);
        try {
            b.calcCurve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1,b.segments.size());
        assertEquals(2,b.knots.size());
    }
    @Test
    public void calcCurve_test2(){
        try {
            b2.calcCurve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(11,b2.segments.size());
        assertEquals(12,b2.knots.size());
    }

    @Test
    public void calcKnots_test1() { // segments == 0 --> not working
        try {
            b.calcKnots();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/
}