package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete a record");
        alert.setContentText("Are you sure you want to delete it?");
        Label message = new Label("");

        Button searchButton = new Button("Search on name");
        searchButton.setOnAction((event) -> {
            ObservableList<ObservableList> data;
            String query = "SELECT * FROM vat." + String.valueOf(chooseShape.getValue()) + " WHERE name ='" + nameField.getText() + "' ";
            data = FXCollections.observableArrayList();
            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                Statement stmt = conn.createStatement();
                stmt.execute(query);
                ResultSet rs = stmt.getResultSet();
                // TABLE COLUMN ADDED DYNAMICALLY
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                            ObservableValue<String>>) param ->
                            new SimpleStringProperty(param.getValue().get(j).toString()));
                    tableView.getColumns().addAll(col);
                    System.out.println("Column [" + i + "] ");
                }

                //Data added to ObservableList *
                while (rs.next()) {
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);
                }

                //FINALLY ADDED TO TableView
                tableView.setItems(data);
            } catch (Exception e) {
                Alert errorData = new Alert(Alert.AlertType.ERROR);
                errorData.setTitle("Error Dialog");
                errorData.setHeaderText("Error");
                errorData.setContentText("Error on building data, please check if shape is selected");
                errorData.showAndWait();
            }
            String shape = shapes.search(String.valueOf(nameField.getText()), String.valueOf(chooseShape.getValue()));
        });
        getAll.setOnAction((event) -> {
            ObservableList<ObservableList> data;
            String query = "SELECT * FROM vat." + String.valueOf(chooseShape.getValue()) + "";
            data = FXCollections.observableArrayList();
            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                Statement stmt = conn.createStatement();
                stmt.execute(query);
                ResultSet rs = stmt.getResultSet();
                // TABLE COLUMN ADDED DYNAMICALLY
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                            ObservableValue<String>>) param ->
                            new SimpleStringProperty(param.getValue().get(j).toString()));
                    tableView.getColumns().addAll(col);
                    System.out.println("Column [" + i + "] ");
                }

                //Data added to ObservableList *
                while (rs.next()) {
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);
                }

                //FINALLY ADDED TO TableView
                tableView.setItems(data);
            } catch (Exception e) {
                Alert errorData = new Alert(Alert.AlertType.ERROR);
                errorData.setTitle("Error Dialog");
                errorData.setHeaderText("Error");
                errorData.setContentText("Error on building data, please check if shape is selected");
                errorData.showAndWait();
            }
            //ArrayList<Shape> shapeList = shapes.searchShape(String.valueOf(chooseShape.getValue()));
        });

        Button deleteButton = new Button("Delete shape");
        deleteButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.delete(String.valueOf(nameField.getText()));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });
        Button deleteAllButton = new Button("Delete all shapes");
        deleteAllButton.setOnAction((event) -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                shapes.deleteAll(String.valueOf(String.valueOf(chooseShape.getValue())));
                message.setText("Shape '" + nameField.getText() + "' is deleted.");
                nameField.clear();
            }
        });

        GridPane layout = new GridPane();
        layout.add(shapeText, 0, 0);
        layout.add(chooseShape, 0, 1);
        layout.add(nameText, 1, 0);
        layout.add(nameField, 1, 1);
        layout.add(searchButton, 1, 2);
        layout.add(getAll, 0, 2);
        layout.add(deleteButton, 0, 12, 2, 2);
        layout.add(deleteAllButton, 1, 12, 2, 2);
        layout.add(tableView, 0, 4, 12, 4);

        // Add some style to the ui
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        return layout;
    }
}
