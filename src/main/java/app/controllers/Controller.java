package app.controllers;

import app.curve.Coordinatesystem;
import app.curve.Curve;
import app.curve.CurveFactory;
import app.curve.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    Pane drawPane = null;
    @FXML
    ChoiceBox<String> cbox = null;
    @FXML
    TextField controlPoints = null;
    @FXML
    TextField knotVector = null;

    Curve c;
    Coordinatesystem cs;
    String prevType = "";

    @FXML
    public void initialize() {
        cbox.getItems().addAll("Uniform B-Spline", "Bezier");
    }

    public void drawOnAction(ActionEvent actionEvent) {
        try {
            String type = cbox.getValue();
            if (type == null) {
                throw new Exception("No Curve Type selected!");
            }
            // parsing text fields
            List<Double> cpl = parseTextField(controlPoints.getCharacters().toString());
            List<Integer> kvl = parseTextField(knotVector.getCharacters().toString()).stream().map(Double::intValue).collect(Collectors.toList());
            if (c == null || !prevType.equals(type)
                    || !c.getControlPoints().equals(convertDoubleToVertex(cpl)) || !compareKnotvector(c.getKnotvector(), kvl)) {
                        c = CurveFactory.createCurve(type, drawPane.getWidth(), drawPane.getHeight());
                        c.addAllControlPoints(cpl);
                        c.addAllKnotvalues(kvl);
                        c.calcCurve();
                        prevType = type;
            }
            updateView();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateView() {
        if (c != null) {
            drawPane.getChildren().clear();
            Group root = new Group();
            Group curve = new Group();
            Group controlPoints = new Group();
            Group knots = new Group();
            c.setDimensions(drawPane.getWidth(), drawPane.getHeight());
            c.draw(controlPoints.getChildren(), knots.getChildren(), curve.getChildren());

            cs = new Coordinatesystem(drawPane.getWidth(), drawPane.getHeight(), c.getControlPoints(),
                    c.getScale(), c.getTranslation());
            Group coordsystem = new Group();
            cs.draw(coordsystem.getChildren());

            root.getChildren().addAll(coordsystem, curve, controlPoints, knots);
            drawPane.getChildren().add(root);
        }
    }

    public static List<Double> parseTextField(String text) throws Exception {
        if (text.length() == 0) {
            throw new Exception("Textfield is empty!");
        }
        String[] s = text.trim().split(",");
        List<Double> list = new LinkedList<>();
        for (String value : s) {
            list.add(Double.parseDouble(value));
        }
        return list;
    }

    public static List<Vertex> convertDoubleToVertex(List<Double> list) {
        List<Vertex> r = new LinkedList<>();
        for (int i= 0; i< list.size()-1;i+=2) {
            Vertex v = new Vertex(list.get(i),list.get(i+1));
            r.add(v);
        }
        return r;
    }

    public static boolean compareKnotvector(List<Integer> curveKV, List<Integer> fieldKV) {
        for(int i=0;i < curveKV.size() && i < fieldKV.size();i++) {
            if (!curveKV.get(i).equals(fieldKV.get(i))) return false;
        }
        return true;
    }
}