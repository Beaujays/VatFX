package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Cube extends Shape{

    // region Parameters
    private final int length;
    private final int height;
    private final int depth;

    // endregion

    // region Constructor
    public Cube (String name, String shape, int length, int height, int depth){
        super(name, shape);
        this.length = length;
        this.height = height;
        this.depth = depth;
    }

    // endregion

    // region Getters
    public int getLength() {
        return length;
    }

    public int getDepth() { return depth; }

    public int getHeight() {
        return height;
    }

    //endregion

    // region Functions
    public int calculateCube(){
        return length * height * depth;
    }

    @Override
    public String toString() {
        return "The volume of '" + name + "' is: " + calculateCube() + "m3" + ".Shape is: " + getShape() + "\n" +
                "Length: " + getLength() + " Depth: " + getDepth() + " Height: " + getHeight();
    }
    // endregion
}