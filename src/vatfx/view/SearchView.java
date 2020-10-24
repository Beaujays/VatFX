package vatfx.view;

import vatfx.domain.Shape;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import vatfx.service.ShapeInterface;

import java.util.Optional;

public class SearchView {
    private final ShapeInterface shapes;

    public SearchView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        //region General settings
        Label header = new Label("Search");
        header.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name");
        TextField nameField = new TextField("");
        Label shapeText = new Label("Choose shape");
        ChoiceBox chooseShape = new ChoiceBox();
        chooseShape.getItems().add("cube");
        chooseShape.getItems().add("globe");
        chooseShape.getItems().add("cylinder");
        chooseShape.getItems().add("pyramid");
        chooseShape.getItems().add("hemisphere");
        //endregion

        //region Search
        Label message = new Label("");
        Label foundHeader = new Label();
        foundHeader.setFont(Font.font("Verdana, FontWeight.BOLD", 20));
        Label found = new Label();

        Button getSingle = new Button("Search");
        getSingle.setOnAction((event) -> {
            String chooseShapeStr = String.valueOf(chooseShape.getValue());
            try {
                Shape shape = shapes.search(chooseShapeStr, nameField.getText());
                foundHeader.setText("Found");
                found.setText(String.valueOf(shape.toString()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Shape or name not filled in, please try again.");
                alert.showAndWait();
            }
        });
        //endregion

        //region Delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete a record");
        alert.setContentText("Are you sure you want to delete it?");
        Button deleteButton = new Button("Delete single shape");

        // Action when button has been pushed
        deleteButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.delete(String.valueOf(nameField.getText()), String.valueOf(chooseShape.getValue()));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });
        // Action when button has been pushed
        Button deleteAllButton = new Button("Delete selected shape");
        deleteAllButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.deleteAll(String.valueOf(String.valueOf(chooseShape.getValue())));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });
        //endregion

        //region Layout and return
        GridPane layout = new GridPane();
        layout.add(header, 0, 0, 2, 2);
        layout.add(shapeText, 0, 3);
        layout.add(chooseShape, 0, 4);
        layout.add(nameText, 2, 3);
        layout.add(nameField, 2, 4);
        layout.add(getSingle, 4, 4);
        layout.add(foundHeader, 0, 5);
        layout.add(found, 0, 6, 6, 6);
        layout.add(deleteButton, 0, 15, 2, 2);
        layout.add(deleteAllButton, 2, 15, 2, 2);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
        //endregion
    }
}
