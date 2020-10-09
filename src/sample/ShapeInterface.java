
package sample;

import java.util.Collection;

public interface ShapeInterface {
    void save(Shape shape);//add Globe Globe
    String search(String name, String shape); // Shape search (String name)
    String searchShape(String shape);


    void delete(String name); //delete String Shape, String Name
    void deleteAll(); //exist already

    //Collection<Shape> getAll(); //Collection <Shapes> getAll()
}
