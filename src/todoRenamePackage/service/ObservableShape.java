package todoRenamePackage.service;

import todoRenamePackage.domain.Shape;
import javafx.collections.ObservableList;

public interface ObservableShape extends ShapeInterface {

    ObservableList<Shape> getObservableList();
}
