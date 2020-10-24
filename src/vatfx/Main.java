package vatfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import vatfx.service.DatabaseShape;
import vatfx.service.ShapeInterface;
import vatfx.view.*;

public class Main extends Application {

    private ShapeInterface shapes;

    @Override
    public void init() {
        // 1. Create the dictionary that the application uses

        // Default
        //this.shapes = new DatabaseShape();
        this.shapes = new MemoryShape();
    }

    @Override
    public void start(Stage window) {

        // 2. Create the views ("subviews")
        MainView mainView = new MainView(shapes);
        InputGlobeView inputGlobeView = new InputGlobeView(shapes);
        InputCubeView inputCubeView = new InputCubeView(shapes);
        InputPyramidView inputPyramidView = new InputPyramidView(shapes);
        InputCylinderView inputCylinderView = new InputCylinderView(shapes);
        InputHemisphereView inputHemisphereView = new InputHemisphereView(shapes);
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
        menuBar.setStyle("-fx-background-color: DARKGREY;");
        menu.setStyle("-fx-background-color: DARKGREY;");
        menu.setSpacing(10);
        menu.setPadding(new Insets(1,1,1,1));

        // Add menu for adding shapes
        Menu menuAdd = new Menu("Add shape");
        MenuItem addGlobe = new MenuItem("Add globe");
        MenuItem addCube = new MenuItem("Add cube");
        MenuItem addPiramide = new MenuItem("Add pyramid");
        MenuItem addCilinder = new MenuItem("Add cylinder");
        MenuItem addHemisphere = new MenuItem("Add hemisphere");
        menuAdd.getItems().addAll(addGlobe, addCube, addPiramide, addCilinder, addHemisphere);

        // Add menu for import/export files
        Menu menuFiles = new Menu("Files");
        MenuItem importFiles = new MenuItem("Import file");
        MenuItem exportFiles = new MenuItem("Export file");
        menuFiles.getItems().addAll(importFiles,exportFiles);
        menuBar.getMenus().addAll(menuAdd, menuFiles);

        // Add search button right in the TOP of HBox
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: DARKGREY;");
        HBox rightButton = new HBox(searchButton);
        rightButton.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        searchButton.setGraphic(new ImageView("file:search.png"));
        searchButton.setStyle("-fx-background-color: DARKGREY;");
        Button mainButton = new Button();
        mainButton.setGraphic(new ImageView("file:home.png"));
        mainButton.setStyle("-fx-background-color: DARKGREY;");

        // Add the buttons to the menu
        menu.getChildren().addAll(mainButton, menuBar, rightButton);
        layout.setTop(menu);

        // 4. Connect the subviews with the buttons. Clicking menu buttons changes the subview.
        mainButton.setOnAction((event)-> layout.setCenter(mainView.getView()));
        addGlobe.setOnAction((event) -> layout.setCenter(inputGlobeView.getView()));
        addCube.setOnAction((event) -> layout.setCenter(inputCubeView.getView()));
        addPiramide.setOnAction((event) -> layout.setCenter(inputPyramidView.getView()));
        addCilinder.setOnAction((event) -> layout.setCenter(inputCylinderView.getView()));
        addHemisphere.setOnAction((event) -> layout.setCenter(inputHemisphereView.getView()));
        searchButton.setOnAction((event) -> layout.setCenter(searchView.getView()));
        importFiles.setOnAction((event)-> layout.setCenter(importFile.getView()));
        exportFiles.setOnAction((event) -> layout.setCenter(exportFile.getView()));

        // 5. First show the input view
        layout.setCenter(mainView.getView());

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
