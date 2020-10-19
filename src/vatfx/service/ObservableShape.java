package vatfx.service;

import vatfx.domain.Shape;
import javafx.collections.ObservableList;

public interface ObservableShape extends ShapeInterface {

    ObservableList<Shape> getObservableList();
}
