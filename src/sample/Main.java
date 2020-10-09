package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private ShapeInterface shapes;

    @Override
    public void init() throws Exception {
        // 1. Create the dictionary that the application uses
        this.shapes = new Shape();

    }

    @Override
    public void start(Stage window) throws Exception {
        // moved to init()   PersonWarehouse warehouse = new MyPersonWarehouse();

        // 2. Create the views ("subviews")
        InputViewGlobe inputViewGlobe = new InputViewGlobe(shapes);
        InputViewCube inputViewCube = new InputViewCube(shapes);
        SearchView searchView = new SearchView(shapes);

        // 3. Create the higher level layout
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: LEMONCHIFFON;");

        // 3.1. Create the menu for the general layout
        HBox menu = new HBox();
        MenuBar menuBar = new MenuBar();
        menu.setStyle("-fx-background-color: LIGHTSLATEGRAY;");
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        // 3.2. Create the menu buttons
        Menu menuAdd = new Menu("Add shape");
        //Menu menuSearch = new Menu("Search shape");
        MenuItem addGlobe = new MenuItem("Add globe");
        MenuItem addCube = new MenuItem("Add cube");
        menuAdd.getItems().add(addGlobe);
        menuAdd.getItems().add(addCube);
        menuBar.getMenus().addAll(menuAdd);

        Button searchButton = new Button("Search");

        // 3.3. Add the buttons to the menu
        menu.getChildren().addAll(menuBar, searchButton);
        layout.setTop(menu);

        // 4. Connect the subviews with the buttons. Clicking menu buttons changes the subview.
        addGlobe.setOnAction((event) -> layout.setCenter(inputViewGlobe.getView()));
        addCube.setOnAction((event) -> layout.setCenter(inputViewCube.getView()));
        searchButton.setOnAction((event) -> layout.setCenter(searchView.getView()));

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
