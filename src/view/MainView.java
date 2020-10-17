package view;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import domain.Shape;

import javafx.scene.layout.GridPane;
import service.ShapeInterface;

public class MainView {
    private final ShapeInterface shapeInterface;

    public MainView(ShapeInterface shapeInterface) {
        this.shapeInterface = shapeInterface;
    }

    public Node getView() {
        TextField selectedItem = new TextField();
        TextField value1 = new TextField();

        Label text = new Label("Welcome");
        ListView<Shape> listview = new ListView<>();
        listview.setItems(FXCollections.observableList(shapeInterface.getAll()));

        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Your action here
            System.out.println("Selected item: " + newValue + " "+newValue.getValue1());
            selectedItem.setText(String.valueOf(newValue.getName()));
            value1.setText(String.valueOf(newValue.getValue1()));

        });
        GridPane layout = new GridPane();
        layout.add(listview,1,5);
        layout.add(text,0,0);
        layout.add(selectedItem, 0,1);
        layout.add(value1,0,2);
        return layout;
    }
}
