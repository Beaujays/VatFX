package vatfx.view;

import javafx.scene.control.*;
import vatfx.domain.Shape;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vatfx.service.ShapeInterface;

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

        Label text = new Label("Welcome\n\n");
        text.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        ListView<Shape> listview = new ListView<>();
        listview.setItems(FXCollections.observableList(shapeInterface.getAll()));

        // Select item from listview
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Get values from selected item
            System.out.println("Selected item: " + newValue + " " + newValue.getValue1());
            selectedItem.setText(String.valueOf(newValue.getName()));
            value1.setText(String.valueOf(newValue.getValue1()));
        });

        // Calculate volume of list
        int total = 0;
        for (Shape shape : shapeInterface.getAll()) {
            total = total + (shape.getValue1());
            totalField.setText(String.valueOf(total));
        }
        VBox items = new VBox();
        items.getChildren().addAll(text,selectedLabel, selectedItem, valueLabel, value1, totalLabel, totalField);

        BorderPane layout = new BorderPane();
        layout.setRight(listview);
        layout.setLeft(items);

        return layout;
    }
}
