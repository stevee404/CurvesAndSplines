# Curves And Splines
A small Programm in JavaFX that can calculate and display **uniform B-Splines** and **Bezier-Curves/Splines**

## Execution
### With Gradle
You only need to have Gradle installed. Then you should be able to type either `gradle run` to execute it
or `gradle distribute` to execute it outside of Gradle 
### Just the Application
You can find a Version in the `build/distributions/` folder. Just download the .zip and execute `bin/gradle.bat`.


## Syntax
- **Control Points:** x1,y1,x2,y2,...,xn,yn
- **Knotvector**:
  - BSpline: t1,t2,t3,...,tn-2
  - Bezier: no syntax yet, just type one number for each knot (#knots = #segments + 1)

## Examples on how to use it

### A normal Bezier
- **CurveType:** Bezier
- **Control Points:** 0,0,2,4,4,4,6,0
- **Knotvector:** 0,1
![image](https://user-images.githubusercontent.com/80679352/111209187-0219cd00-85cc-11eb-86ca-f6ffe62d4b0b.png)

### A Bezier-Spline
- **CurveType:** Bezier
- **Control Points:** 0,0,6,6,2,4,6,2,10,0,7,5,6,6
- **Knotvector:** 0,1,2
![image](https://user-images.githubusercontent.com/80679352/111209752-b7e51b80-85cc-11eb-9f59-246d10a7a59c.png)

### A B-Spline
- **CurveType:** Uniform B-Spline
- **Control Points:** 1,2,1,5,4,5,2,4,3,1,3,1,5,2,6,6,6,6,6,6,6,4,6,4,8,4,9,1
- **Knotvector:** 0,1,2,3,4,5,6,7,8,9,10,11
![image](https://user-images.githubusercontent.com/80679352/111209909-e82cba00-85cc-11eb-80b1-7994f4ede589.png)
