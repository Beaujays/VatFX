package vatfx.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vatfx.domain.Shape;
import vatfx.service.ShapeInterface;

import java.util.Optional;

public class MainView {
    private final ShapeInterface shapes;

    public MainView(ShapeInterface shapeInterface) {
        this.shapes = shapeInterface;
    }

    public Node getView() {

        Label selectedLabel = new Label("Name selected");
        TextField selectedItem = new TextField();
        Label valueLabel = new Label("Volume of selected item");
        TextField volume = new TextField();
        Label totalLabel = new Label("Total volume of list in m3");
        TextField totalField = new TextField();
        Label shapeLabel = new Label("Name selected shape");
        TextField shapeSelected = new TextField();
        Button deleteShape = new Button("Delete selected shape");
        Button refreshButton = new Button("Refresh list");
        Label emptySpace = new Label("\n");
        Label text = new Label("Welcome\n\n");
        text.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        ListView<Shape> listview = new ListView<>();
        listview.setItems(FXCollections.observableList(shapes.getAll()));

        // Select item from listview
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Get values from selected item
            if (newValue != null) {
                System.out.println("Selected item: " + newValue);
                selectedItem.setText(String.valueOf(newValue.getName()));
                volume.setText(String.valueOf(newValue.getVolume()));
                shapeSelected.setText(newValue.getShape());
            }
        });

        // Delete selected item from list
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete a record");
        alert.setContentText("Are you sure you want to delete it?");
        deleteShape.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.delete(selectedItem.getText(), shapeSelected.getText());
                shapeSelected.clear();
                volume.clear();
                selectedItem.clear();
            }
        });

        // Refresh button for refreshing the list
        refreshButton.setOnAction((event) -> {

            listview.setItems(FXCollections.observableList(shapes.getAll()));
            int total = 0;
            for (Shape shape : shapes.getAll()){
                total = total + (shape.getVolume());
                totalField.setText(String.valueOf(total));
            }
        });

        // Calculate volume of list
        int total = 0;
        for (Shape shape : shapes.getAll()) {
            total = total + (shape.getVolume());
            totalField.setText(String.valueOf(total));
        }
        VBox items = new VBox();
        items.getChildren().addAll(text, selectedLabel, selectedItem, shapeLabel, shapeSelected, valueLabel, volume,
                totalLabel, totalField, emptySpace, deleteShape, refreshButton);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setRight(listview);
        layout.setLeft(items);

        return layout;
    }
}
