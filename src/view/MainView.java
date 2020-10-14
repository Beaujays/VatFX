package view;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import domain.Shape;
import service.ShapeInterface;

public class MainView {
    private final ShapeInterface shapeInterface;

    public MainView(ShapeInterface shapeInterface) {
        this.shapeInterface = shapeInterface;
    }

    public Node getView() {
        Label text = new Label("HALLO");
        ListView<Shape> listview = new ListView<>();
        listview.setItems(FXCollections.observableList(shapeInterface.getAll()));

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> listview.setItems(FXCollections.observableList(shapeInterface.getAll())));

        BorderPane layout = new BorderPane();
        layout.setRight(listview);
        layout.setTop(text);
        return layout;
    }
}
