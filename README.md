# CurvesAndSplines
Small Programm in JavaFX that can calculate and display uniform B-Splines and Bezier-Curves/Splines

## Syntax
- **Control Points:** x1,y1,x2,y2,...,xn,yn
- **Knotvector**:
  - BSpline: t1,t2,t3,...,tn-2
  - Bezier: (no syntax yet, just type one number for each segment)

## Examples on how to use it

### A normal Bezier
- **CurveType:** Bezier
- **Control Points:** 0,0,2,4,4,4,6,0
- **Knotvector:** 0,1

### A Bezier-Spline
- **CurveType:** Bezier
- **Control Points:** 0,0,2,4,4,4,6,0,8,-2,10,4,12,0
- **Knotvector:** 0,1,2

### A B-Spline
- **CurveType:** Uniform B-Spline
- **Control Points:** 1,2,1,5,4,5,2,4,3,1,3,1,5,2,6,6,6,6,6,6,6,4,6,4,8,4,9,1
- **Knotvector:** 0,1,2,4,5,6,7,8,9,10,11
