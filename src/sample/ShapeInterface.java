
package sample;

import java.io.File;
import java.util.ArrayList;

public interface ShapeInterface {
    void save(Shape shape);//add Globe Globe
    String search(String name, String shape); // Shape search (String name)
    ArrayList<Shape> searchShape(String shape);
    ArrayList<Shape> importFile(String file);
    String exportFile(String shape);
    File getDirectory();

    void delete(String name); //delete String Shape, String Name
    void deleteAll(); //exist already

    //Collection<Shape> getAll(); //Collection <Shapes> getAll()
}
