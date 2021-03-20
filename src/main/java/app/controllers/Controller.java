package app.controllers;

import app.curve.Curve;
import app.curve.CurveFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    @FXML
    Pane drawPane = null;
    @FXML
    ChoiceBox<String> cbox = null;
    @FXML
    TextField controlPoints = null;
    @FXML
    TextField knotVector = null;

    @FXML
    public void initialize() {
        cbox.getItems().addAll("Uniform B-Spline", "Bezier");
    }

    public void drawOnAction(ActionEvent actionEvent) {
        try {
            drawPane.getChildren().clear();
            String type = cbox.getValue();
            if (type == null) {
                throw new Exception("No Curve Type selected!");
            }
            // parsing text fields
            List<Double> cpl = parseTextField(controlPoints.getCharacters().toString());
            List<Double> kvl = parseTextField(knotVector.getCharacters().toString());

            Curve c = CurveFactory.createCurve(type, drawPane.getWidth(), drawPane.getHeight());
            c.addAllControlPoints(cpl);
            c.addAllKnotvalues(kvl);
            c.calcCurve();

            Group root = new Group();
            Group curve = new Group();
            Group controlPoints = new Group();
            Group knots = new Group();
            root.getChildren().addAll(curve, controlPoints, knots);

            c.draw(controlPoints.getChildren(), knots.getChildren(), curve.getChildren());

            drawPane.getChildren().add(root);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
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
}
