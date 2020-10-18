package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import service.ShapeInterface;

public class ExportFileView {

    private final ShapeInterface shapes;

    public ExportFileView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        Label header = new Label("Export file");
        header.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Select shape ");
        ChoiceBox chooseShape = new ChoiceBox();
        chooseShape.getItems().add("cube");
        chooseShape.getItems().add("globe");
        Label message = new Label();

        Button addButton = new Button("Export file!");
        addButton.setOnAction((event) -> {
            String chooseShapeStr = String.valueOf(chooseShape.getValue());
            if (chooseShapeStr.equals("")){
                message.setText("No shape defined.");
            } else {
                try {
                    shapes.exportFile(chooseShapeStr);
                    message.setText("Export shape '" + chooseShapeStr + "' successfully. \nSaved in map: "
                            + shapes.getDirectory());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("I have a great message for you!");
                    alert.showAndWait();
                }
            }
        });

        GridPane layout = new GridPane();
        layout.add(header, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(chooseShape, 1, 1);
        layout.add(addButton, 2, 1);
        layout.add(message, 0, 5, 5, 5);

        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}

