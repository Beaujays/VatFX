
package service;

import domain.Cube;
import domain.Globe;
import domain.Shape;

import java.io.File;
import java.util.List;

public interface ShapeInterface {
    //region Add shape
    void saveGlobe(Globe globe);
    void saveCube(Cube cube);
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
