
package sample;

public interface ShapeInterface {
    void save(Shape shape);//add Globe Globe
    String search(String name, String shape); // Shape search (String name)
    String searchShape(String shape);
    String importFile(String file);
    String exportFile(String shape);

    void delete(String name); //delete String Shape, String Name
    void deleteAll(); //exist already

    //Collection<Shape> getAll(); //Collection <Shapes> getAll()
}
