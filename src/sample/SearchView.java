package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

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
        Button getAll = new Button("Get all shapes");
        TableView tableView = new TableView();
        Label found = new Label();
        found.setFont(new Font(16));

        Button searchButton = new Button("Search shape");
        searchButton.setOnAction((event) -> {

            String shape = shapes.search(String.valueOf(nameField.getText()),String.valueOf(chooseShape.getValue()));
            found.setText("Found: " + shape);
        });
        getAll.setOnAction((event)->{

            TableColumn<String, Shape> value1 = new TableColumn<>("Name");
            TableColumn<String, Shape> value2 = new TableColumn<>("Value 1");
            TableColumn<String, Shape> value3 = new TableColumn<>("Value 2");
            TableColumn<String, Shape> value4 = new TableColumn<>("Value 3");

            tableView.getColumns().add(value1);
            tableView.getColumns().add(value2);
            tableView.getColumns().add(value3);
            tableView.getColumns().add(value4);


            ArrayList<Shape> shapeList = shapes.searchShape(String.valueOf(chooseShape.getValue()));
            found.setText("Found: " + shapeList);
        });

        Button deleteButton = new Button("Delete shape");
        deleteButton.setOnAction((event)->{
            shapes.delete(String.valueOf(nameField.getText()));
            System.out.println("Shape " + nameField.getText() + " deleted.");
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0,0);
        layout.add(chooseShape,0,1);
        layout.add(nameText, 1, 0);
        layout.add(nameField, 1, 1);
        layout.add(searchButton, 2, 1);
        layout.add(getAll,3,1);
        layout.add(found, 0, 3,3,3);
        layout.add(deleteButton, 0, 12);
        layout.add(tableView, 0,4,4,4);


        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
