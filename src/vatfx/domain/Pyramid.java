package vatfx.domain;

public class Pyramid extends Shape {

    // region Constructor
    public Pyramid(String name, String shape, int volume, int length, int height, int depth){
        super(name, shape, volume, length, height, depth);
    }
    // endregion

    // region Functions
    @Override
    public String toString() {
        return name + " (length: " + value1 + ", depth: " + value2 + ", height: "
                + value3 + ", volume:" + volume + "m3)";
    }
    // endregion
}