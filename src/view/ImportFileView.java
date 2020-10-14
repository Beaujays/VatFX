package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import service.ShapeInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class ImportFileView extends Component {

    private final ShapeInterface shapes;

    public ImportFileView(ShapeInterface shapes) {
        this.shapes = shapes;
    }

    public Parent getView() {
        String filename = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Upload file");
        chooser.setFileFilter(new FileNameExtensionFilter(".csv","csv"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getName();
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile().getName());
        }
        else {
            System.out.println("No Selection ");
        }

        Label shapeText = new Label("Import file");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name file: ");
        TextField nameField = new TextField(filename);
        Label message = new Label();

        Button addButton = new Button("Import file!");
        addButton.setOnAction((event) -> {
            shapes.importFile(nameField.getText());
            message.setText(nameField.getText() + " is imported correctly.");
            nameField.clear();
        });


    GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(nameField, 1, 1);
        layout.add(addButton, 1, 3, 2, 2);
        layout.add(message, 1,5);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}

