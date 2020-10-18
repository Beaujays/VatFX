package domain;

public class Cilinder extends Shape {

    // region Parameters
    private int radius;
    private int height;


    // endregion

    // region Constructor
    public Cilinder(String name, String shape, int radius, int height){
        super(name, shape);
        this.radius = radius;
        this.height = height;
    }

    public Cilinder(String name, int radius, int height) {
        super(name);
        this.radius = radius;
        this.height = height;

    }

    // endregion

    // region Getters
    public int getRadius() {
        return radius;
    }

    public int getHeight() {
        return height;
    }

    //endregion

    // region Functions
    public double calculateCube(){
        return Math.PI * (radius * radius) * height;
    }

    @Override
    public String toString() {
        return name + "( length: " + getRadius() + ", height: "
                + getHeight() + ", volume:" + calculateCube() + "m3";
    }
    // endregion
}