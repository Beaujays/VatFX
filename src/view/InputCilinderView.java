package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import service.ShapeInterface;

public class InputCilinderView {

    private final ShapeInterface shapes;

    public InputCilinderView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label shapeText = new Label("Cilinder");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name: ");
        TextField nameField = new TextField();
        Label radiusText = new Label("Radius: ");
        TextField radiusField = new TextField();
        Label heightText = new Label("Height: ");
        TextField heightField = new TextField();
        Label message = new Label("");

        Button addButton = new Button("Add cube!");
        addButton.setOnAction((event) -> {
            try {
                int radiusInt = Integer.parseInt(radiusField.getText());
                int heightInt = Integer.parseInt(heightField.getText());
                shapes.saveCilinder(nameField.getText(), "cilinder", radiusInt, heightInt);
                message.setText(nameField.getText() + " is added successfully.");
                nameField.clear();
                radiusField.clear();
                heightField.clear();

            }catch (Exception e){
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
        layout.add(heightText, 0, 3);
        layout.add(heightField, 1, 3);
        layout.add(addButton, 1, 5, 2, 2);
        layout.add(message, 1, 8);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
