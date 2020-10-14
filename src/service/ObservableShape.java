package service;

import domain.Shape;
import javafx.collections.ObservableList;

public interface ObservableShape extends ShapeInterface {

    ObservableList<Shape> getObservableList();
}
