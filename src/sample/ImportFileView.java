package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ImportFileView {

    private ShapeInterface shapes;

    public ImportFileView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label shapeText = new Label("Import file");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name file: ");
        TextField nameField = new TextField();
        Label message = new Label();

        Button addButton = new Button("Import file!");
        addButton.setOnAction((event) -> {
            shapes.importFile(nameField.getText());
            message.setText(nameField.getText() + " is imported correctly.");
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

