package domain;

import java.util.Objects;

public class Shape {
    //region Variables
    String name;
    String shape;
    int value1;
    int value2;
    int value3;
    int value4;
    //endregion

    // region Constructors
    public Shape(String name, String shape) {
        this.name = name;
        this.shape = shape;
    }

    public Shape(String name, int value1) {
        this.name = name;
        this.value1 = value1;
    }

    public Shape(String name) {
        this.name = name;
    }

    public Shape(String name, String shape, int value1, int value2) {
        this.name = name;
        this.shape = shape;
        this.value1 = value1;
        this.value2 = value2;
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

    public int getValue1() {
        return value1;
    }

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

    //region To String
    @Override
    public String toString() {
        return "Name: " + name;
    }

    public String toStringGlobe() {
            return "Name: " + name + "\nRadius: " + value1 +"\nVolume: " + value2;
    }

    public String toStringCube() {
        return "Name: " + name + "\nLength: " + value1 + "\nHeight: " + value2 + "\nDepth: " + value3 + "\nVolume: " + value4;
    }
    //endregion
}