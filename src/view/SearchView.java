package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import domain.Shape;
import service.ShapeInterface;

import java.util.Optional;

public class SearchView {
    private final ShapeInterface shapes;

    public SearchView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label nameText = new Label("Name: 'Leave empty to get all shapes'");
        TextField nameField = new TextField("");
        Label shapeText = new Label("Choose shape:");
        ChoiceBox chooseShape = new ChoiceBox();
        chooseShape.getItems().add("cube");
        chooseShape.getItems().add("globe");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete a record");
        alert.setContentText("Are you sure you want to delete it?");
        Label message = new Label("");
        Label found = new Label();

        Button getSingle = new Button("Search on name");
        getSingle.setOnAction((event) -> {
            String chooseShapeStr = String.valueOf(chooseShape.getValue());
            Shape shape = shapes.search(chooseShapeStr, nameField.getText());
            found.setText("Found: " + shape);

        });

        Button deleteButton = new Button("Delete shape");
        deleteButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.delete(String.valueOf(nameField.getText()), String.valueOf(chooseShape.getValue()));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });
        Button deleteAllButton = new Button("Delete all shapes");
        deleteAllButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.deleteAll(String.valueOf(String.valueOf(chooseShape.getValue())));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(chooseShape, 0, 1);
        layout.add(nameText, 1, 0);
        layout.add(nameField, 1, 1);
        layout.add(getSingle, 2, 1);
        layout.add(found,0,5,5,5);
        layout.add(deleteButton, 0, 12, 2, 2);
        layout.add(deleteAllButton, 1, 12, 2, 2);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
