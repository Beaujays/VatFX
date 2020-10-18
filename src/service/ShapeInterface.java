
package service;

import domain.Shape;
import java.io.File;
import java.util.List;

public interface ShapeInterface {
    //region Add shape
    void saveGlobe(String name, String shape, int value1);
    void saveCube(String name, String shape, int value1, int value2, int value3);
    void saveCilinder(String name, String shape, int value1, int value2);
    void savePiramide(String name, String shape, int value1, int value2, int value3);
    //endregion

    //region Search shape
    Shape search(String name, String shape);
    //endregion

    //region Import/export files
    void importFile(String file);
    void exportFile(String shape);
    File getDirectory();
    //endregion

    //region Delete shape
    void delete(String name, String Shape);
    void deleteAll(String shape);
    //endregion

    List<Shape> getAll();
}
