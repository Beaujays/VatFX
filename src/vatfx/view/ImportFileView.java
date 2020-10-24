package vatfx.view;

import vatfx.service.ShapeInterface;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ImportFileView extends Window {

    private final ShapeInterface shapes;

    public ImportFileView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Node getView() {

        String filename;
        FileChooser chooser = new FileChooser();
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir);
        chooser.setInitialDirectory(file);
        chooser.setInitialFileName("Upload file");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        chooser.getExtensionFilters().add(extFilter);

        //Choose the file
        File chosenFile = chooser.showOpenDialog(null);
        //Make sure a file was selected, if not return default
        if(chosenFile != null) {
            filename = chosenFile.getName();
        } else {
            //default return value
            filename = null;
        }

        Label shapeText = new Label("Import file");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name file: ");
        TextField nameField = new TextField(filename);
        Label message = new Label();

        Button addButton = new Button("Import file!");
        addButton.setOnAction((event) -> {
            try {
                if (nameField.getText().isEmpty()) {
                    message.setText("No file selected.");
                } else {
                    shapes.importFile(nameField.getText());
                    message.setText(nameField.getText() + " is imported correctly.");
                    nameField.clear();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("No file selected");
                alert.setContentText("Please select or fill in filename");

                alert.showAndWait();
            }
        });


        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(nameField, 1, 1);
        layout.add(addButton, 1, 3, 2, 2);
        layout.add(message, 1, 5);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}


