package vatfx.domain;

import java.util.Objects;

public class Shape {
    //region Variables
    final String name;
    String shape;
    int value1; //volume of a shape
    int value2; //value 1 of a shape
    int value3; //value 2 of a shape
    int value4; //value 3 of a shape
    //endregion

    // region Constructors
    public Shape(String name, String shape, int value1) {
        this.name = name;
        this.shape = shape;
        this.value1 = value1;
    }

    public Shape(String name, String shape, int value1, int value2) {
        this.name = name;
        this.shape = shape;
        this.value1 = value1;
        this.value2 = value2;
    }
    public Shape(String name, String shape, int value1, int value2, int value3) {
        this.name = name;
        this.shape = shape;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public Shape(String name, String shape, int value1, int value2, int value3, int value4) {
        this.name = name;
        this.shape = shape;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
    // endregion

    // region Getters
    public String getName() {
        return name;
    }

    public String getShape() {
        return shape;
    }

    public int getValue1() { return value1; }

    public int getValue2() { return value2; }

    public int getValue3() { return value3; }

    public int getValue4() { return value4; }
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
    public String toString() { return "Name: " + name + ", volume: " + value1; }
}