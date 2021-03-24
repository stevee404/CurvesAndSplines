package app.curve;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.LinkedList;
import java.util.List;

public class Coordinatesystem {
    private double width;
    private double height;

    private Vertex min;
    private Vertex max;

    Vertex scale;
    Vertex translation;

    private LinkedList<Vertex> horizontalLines = new LinkedList<>();
    private LinkedList<Vertex> verticalLines = new LinkedList<>();
    private List<Vertex> controlPoints;

    public Coordinatesystem(double width, double height, List<Vertex> controlPoints, Vertex scale, Vertex translation) {
        this.width = width;
        this.height = height;
        this.controlPoints = controlPoints;
        this.scale = scale;
        this.translation = translation;

        min = new Vertex(width, height);
        max = new Vertex(0, 0);

        for (Vertex v : this.controlPoints) {
            if (v.getX() < min.getX()) min.setX(v.getX());
            if (v.getY() < min.getY()) min.setY(v.getY());

            if (v.getX() > max.getX()) max.setX(v.getX());
            if (v.getY() > max.getY()) max.setY(v.getY());
        }
    }

    private void calcLines() {
        double x = Math.abs(max.getX() - min.getX());
        double y = Math.abs(max.getY() - min.getY());
        double maxAxis = Math.max(x,y);

        int pow;
        for (pow=0;maxAxis > 10;pow++) {
            maxAxis = maxAxis / 10;

        }

        int step = (int)Math.pow(10,pow);
        for(int i=(int)(-width+(width%step)); i < (int)(width*2)-(width*2%step);i+=step) {
            verticalLines.add(new Vertex(i,-height));
            verticalLines.add(new Vertex(i,2*height));
        }
        for(int i=(int)(-height+(height%step)); i < (int)(height*2)-(height*2%step);i+=step) {
            horizontalLines.add(new Vertex(-width,i));
            horizontalLines.add(new Vertex(2*width,i));
        }
    }


    public void draw(ObservableList<Node> children) {
        calcLines();
        drawLine(children, verticalLines);
        drawLine(children, horizontalLines);
    }

    private void drawLine(ObservableList<Node> children, LinkedList<Vertex> linesList) {
        for(int i = 0; i< linesList.size()-1; i+=2) {
            var p1 = new Vertex(linesList.get(i).getX(), linesList.get(i).getY());
            p1.scale(scale);
            p1.translate(translation);

            var p2 = new Vertex(linesList.get(i+1).getX(), linesList.get(i+1).getY());
            p2.scale(scale);
            p2.translate(translation);

            Polyline polyline = new Polyline();
            polyline.getPoints().addAll(p1.getX(),p1.getY(),p2.getX(),p2.getY());

            if(linesList.get(i).getX() == 0 || linesList.get(i).getY() == 0) {
                polyline.setStroke(Color.BLACK);
                polyline.setStrokeWidth(2);
            } else {
                polyline.setStroke(Color.LIGHTGREY);
            }
            children.add(polyline);
        }
    }

}
