package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InputViewCube {

    private final ShapeInterface shapes;

    public InputViewCube(ShapeInterface shapes) { this.shapes = shapes; }

    public Parent getView() {
        Label shapeText = new Label("Cube");
        shapeText.setFont(Font.font("Verdana, FontWeight.BOLD", 30));
        Label nameText = new Label("Name: ");
        TextField nameField = new TextField();
        Label lengthText = new Label("Length: ");
        TextField lengthField = new TextField();
        Label depthText = new Label("Depth: ");
        TextField depthField = new TextField();
        Label heightText = new Label("Height: ");
        TextField heightField = new TextField();
        Label message = new Label("");

        Button addButton = new Button("Add cube!");
        addButton.setOnAction((event) -> {
            int lengthInt = Integer.parseInt(lengthField.getText());
            int depthInt = Integer.parseInt(depthField.getText());
            int heightInt = Integer.parseInt(heightField.getText());
            shapes.save(new Cube(nameField.getText(), shapeText.getText(), lengthInt, depthInt, heightInt));
            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                String query = "insert into vat.cube (name, length,height, depth) " +
                        "values ('" + nameField.getText() + "','" + lengthInt + "','" + heightInt + "','" + depthInt + "')";
                Statement stmt = conn.createStatement();
                stmt.execute(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            message.setText(nameField.getText() + " is added successfully.");
            nameField.clear();
            lengthField.clear();
            depthField.clear();
            heightField.clear();
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(nameText, 0, 1);
        layout.add(nameField, 1, 1);
        layout.add(lengthText, 0, 2);
        layout.add(lengthField, 1, 2);
        layout.add(depthText, 0, 3);
        layout.add(depthField, 1, 3);
        layout.add(heightText, 0, 4);
        layout.add(heightField, 1, 4);
        layout.add(addButton, 1, 5, 2, 2);
        layout.add(message,1,8);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
