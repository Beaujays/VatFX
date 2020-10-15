package domain;

import java.util.Objects;

public class Shape {
    //region Variables
    String name;
    String shape;
    //endregion

    // region Constructors
    public Shape(String name, String shape) {
        this.name = name;
        this.shape = shape;
    }

    public Shape(String name) {
        this.name = name;
    }
    // endregion

    // region Getters
    public String getName() {
        return name;
    }

    public String getShape() { return shape; }
    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape1 = (Shape) o;
        return Objects.equals(name, shape1.name) &&
                Objects.equals(shape, shape1.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shape);
    }

    @Override
    public String toString() {
        return "'" + name + "'";
    }
}