package todoRenamePackage.domain;

public class Piramide extends Shape {

    // region Parameters
    private int length;
    private int height;
    private int depth;

    // endregion

    // region Constructor
    public Piramide(String name, String shape, int length, int height, int depth){
        super(name, shape);
        this.length = length;
        this.height = height;
        this.depth = depth;
    }

    public Piramide(String name, int length, int height, int depth) {
        super(name);
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
        return length * height * depth / 2;
    }

    @Override
    public String toString() {
        return name + "( length: " + getLength() + ", depth: " + getDepth() + ", height: "
                + getHeight() + ", volume:" + calculateCube() + "m3";
    }
    // endregion
}