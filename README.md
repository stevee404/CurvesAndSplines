# Curves And Splines
A small Programm in JavaFX that can calculate and display **uniform B-Splines** and **Bezier-Curves/Splines**.

## Usage
### Pull Repo + Gradle
You only need to have Gradle installed. Then you should be able to type either `gradle run` to execute it.
### Only the Application
You can find a version in the `build/distributions/` folder. Just download the .zip or .tar, extract it and execute `bin/gradle.bat` or `bin/gradle`, depending on your os.

## Note
The curve is only rescaled to your window size when you click draw.

## Syntax
- **Control Points:** x1,y1,x2,y2,...,xn,yn
- **Knotvector**: You only need 2 values. The other ones are added automaticaly, since they are uniform.

## Examples on how to use it

### A normal Bezier
- **CurveType:** Bezier
- **Control Points:** `0,0,2,4,4,4,6,0`
- **Knotvector:** `0,1`
![image](https://user-images.githubusercontent.com/80679352/112310765-3b75cb00-8ca5-11eb-951a-870ba5219616.png)


### A Bezier-Spline
- **CurveType:** Bezier
- **Control Points:** `0,0,6,6,2,4,6,2,10,0,7,5,6,6`
- **Knotvector:** `0,1,2`
![image](https://user-images.githubusercontent.com/80679352/112310875-59433000-8ca5-11eb-8e5e-8420fb58929b.png)

### A B-Spline
- **CurveType:** Uniform B-Spline
- **Control Points:** `1,2,1,5,4,5,2,4,3,1,3,1,5,2,6,6,6,6,6,6,6,4,6,4,8,4,9,1`
- **Knotvector:** `0,1,2,3,4,5,6,7,8,9,10,11`
![image](https://user-images.githubusercontent.com/80679352/112305883-7b39b400-8c9f-11eb-96fb-cb8d98417f35.png)
