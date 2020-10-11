package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import javax.swing.*;

public class ExportFileView {

    private final ShapeInterface shapes;

    public ExportFileView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label shapeText = new Label("Export file");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name shape: ");
        TextField nameField = new TextField();
        Label message = new Label();

        Button addButton = new Button("Export file!");
        addButton.setOnAction((event) -> {
            shapes.exportFile(nameField.getText());
            message.setText("Export shape '" + nameField.getText() + "' successfully. \nIn map: "
                    + shapes.getDirectory());
            nameField.clear();
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(nameField, 1, 1);
        layout.add(addButton, 1, 3, 2, 2);
        layout.add(message, 1,5);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}

