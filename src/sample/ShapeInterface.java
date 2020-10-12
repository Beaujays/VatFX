
package sample;

import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public interface ShapeInterface {
    //region Add shape
    void save(Shape shape);
    void addGlobe(String name, int radius);
    void addCube(String name, int length, int depth, int height);
    //endregion

    //region Search shape
    String search(String name, String shape); // Shape search (String name)
    ArrayList<Shape> searchShape(String shape);
    void buildData(String shape);
    //endregion

    //region Import/export files
    ArrayList<Shape> importFile(String file);
    void exportFile(String shape);
    File getDirectory();
    //endregion

    //region Delete shape
    void delete(String name);
    void deleteAll(String shape);
    //endregion

    //Collection<Shape> getAll(); //Collection <Shapes> getAll()
}
