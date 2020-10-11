package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {

    private ShapeInterface shapes;

    @Override
    public void init() {
        // 1. Create the dictionary that the application uses
        this.shapes = new Shape();
    }

    @Override
    public void start(Stage window) throws Exception {

        // 2. Create the views ("subviews")
        InputViewGlobe inputViewGlobe = new InputViewGlobe(shapes);
        InputViewCube inputViewCube = new InputViewCube(shapes);
        SearchView searchView = new SearchView(shapes);
        ImportFileView importFile = new ImportFileView(shapes);
        ExportFileView exportFile = new ExportFileView(shapes);
        window.setTitle("Shape analyse tool");

        // 3. Create the higher level layout
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: LIGHTGRAY;");

        // 3.1. Create the menu for the general layout
        HBox menu = new HBox();
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: WHITE;");
        menuBar.setPadding(new Insets(13,13,13,13));
        menu.setStyle("-fx-background-color: DARKOLIVEGREEN;");
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        // Create menu buttons
        // Add menu for adding shapes
        Menu menuAdd = new Menu("Add shape");
        MenuItem addGlobe = new MenuItem("Add globe");
        MenuItem addCube = new MenuItem("Add cube");
        menuAdd.getItems().addAll(addGlobe, addCube);

        // Add menu for import/export files
        Menu menuFiles = new Menu("Files");
        MenuItem importFiles = new MenuItem("Import file");
        MenuItem exportFiles = new MenuItem("Export file");
        menuFiles.getItems().addAll(importFiles,exportFiles);
        menuBar.getMenus().addAll(menuAdd, menuFiles);

        // Add search button right in the TOP of HBox
        Button searchButton = new Button("Search");
        HBox rightButton = new HBox(searchButton);
        rightButton.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        searchButton.setGraphic(new ImageView("file:white-search-icon-png-27.jpg"));
        searchButton.setStyle("-fx-background-color: WHITE;");

        // 3.3. Add the buttons to the menu
        menu.getChildren().addAll(menuBar, rightButton);
        layout.setTop(menu);

        // 4. Connect the subviews with the buttons. Clicking menu buttons changes the subview.
        addGlobe.setOnAction((event) -> layout.setCenter(inputViewGlobe.getView()));
        addCube.setOnAction((event) -> layout.setCenter(inputViewCube.getView()));
        searchButton.setOnAction((event) -> layout.setCenter(searchView.getView()));
        importFiles.setOnAction((event)-> layout.setCenter(importFile.getView()));
        exportFiles.setOnAction((event) -> layout.setCenter(exportFile.getView()));

        // 5. First show the input view
        //layout.setCenter(inputView.getView());

        // 6. Create the main view and add the high level layout
        Scene view = new Scene(layout, 500, 400);

        // 7. Show the application
        window.setScene(view);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
