package vatfx.domain;

public class Cube extends Shape {

    // region Constructor
    public Cube (String name, String shape, int volume, int length, int height, int depth){
        super(name, shape, volume, length, height, depth);
    }
    // endregion

    // region Getters
    @Override
    public int getValue1() {
        return super.getValue1();
    }

    @Override
    public int getValue2() {
        return super.getValue2();
    }

    @Override
    public int getValue3() {
        return super.getValue3();
    }

    @Override
    public int getValue4() {
        return super.getValue4();
    }
    //endregion

    @Override
    public String toString() {
        return name + " (length: " + getValue2() + ", depth: " + getValue3() + ", height: "
                + getValue4() + ", volume: " + getValue1() + "m3)";
    }
    // endregion
}