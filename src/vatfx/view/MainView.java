package vatfx.view;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vatfx.domain.Shape;
import vatfx.service.ShapeInterface;

import java.util.Optional;

public class MainView {
    private final ShapeInterface shapeInterface;

    public MainView(ShapeInterface shapeInterface) {
        this.shapeInterface = shapeInterface;
    }

    public Node getView() {

        Label selectedLabel = new Label("Name selected");
        TextField selectedItem = new TextField();
        Label valueLabel = new Label("Volume of selected item");
        TextField value1 = new TextField();
        Label totalLabel = new Label("Total volume of list in m3");
        TextField totalField = new TextField();
        TextField shapeSelected = new TextField();
        Button deleteShape = new Button("Delete selected shape");
        Button refreshButton = new Button("Refresh list");
        Label text = new Label("Welcome\n\n");
        text.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        ListView<Shape> listview = new ListView<>();
        listview.setItems(FXCollections.observableList(shapeInterface.getAll()));

        // Select item from listview
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Get values from selected item
            System.out.println("Selected item: " + newValue);
            selectedItem.setText(String.valueOf(newValue.getName()));
            value1.setText(String.valueOf(newValue.getValue1()));
            shapeSelected.setText(newValue.getShape());
        });

        // Delete selected item from list
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete a record");
        alert.setContentText("Are you sure you want to delete it?");
        deleteShape.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapeInterface.delete(selectedItem.getText(), shapeSelected.getText());
                shapeSelected.clear();
                value1.clear();
                selectedItem.clear();
            }
        });

        // Refresh button for refreshing the list
        refreshButton.setOnAction(e -> listview.setItems(FXCollections.observableArrayList(shapeInterface.getAll())));

        // Calculate volume of list
        int total = 0;
        for (Shape shape : shapeInterface.getAll()) {
            total = total + (shape.getValue1());
            totalField.setText(String.valueOf(total));
        }
        VBox items = new VBox();
        items.getChildren().addAll(text, selectedLabel, selectedItem, shapeSelected, valueLabel, value1, totalLabel, totalField,
                deleteShape, refreshButton);

        BorderPane layout = new BorderPane();
        layout.setRight(listview);
        layout.setLeft(items);

        return layout;
    }
}
