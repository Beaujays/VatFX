package vatfx.domain;

public class Cylinder extends Shape {

    // region Constructor
    public Cylinder(String name, String shape, int volume, int radius, int height) {
        super(name, shape, volume, radius, height);

    }
    // endregion

    // region Functions
    @Override
    public String toString() {
        return name + " (radius: " + value1+ ", height: " + value2 + ", volume: " + volume + "m3)"; }
    // endregion
}