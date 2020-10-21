package vatfx.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import vatfx.service.ShapeInterface;

public class InputHemisphereView {

    private final ShapeInterface shapes;

    public InputHemisphereView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label shapeText = new Label("Hemisphere");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name: ");
        TextField nameField = new TextField();
        Label radiusText = new Label("Radius: ");
        TextField radiusField = new TextField();
        Label message = new Label("");

        Button addButton = new Button("Add hemisphere!");
        addButton.setOnAction((event) -> {
            try {
                int radiusInt = Integer.parseInt(radiusField.getText());
                String nameStr = String.valueOf(nameField.getText());
                String shapeStr = "hemisphere";
                shapes.saveHemisphere(nameStr, shapeStr, radiusInt);
                message.setText(nameField.getText() + " is added successfully.");
                nameField.clear();
                radiusField.clear();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Not all fields are filled in");
                alert.showAndWait();
            }
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(nameField, 1, 1);
        layout.add(radiusText, 0, 2);
        layout.add(radiusField, 1, 2);
        layout.add(addButton, 1, 3, 2, 2);
        layout.add(message, 1, 6);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
