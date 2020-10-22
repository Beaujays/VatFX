package vatfx.domain;

public class Cilinder extends Shape {

    // region Constructor
    public Cilinder(String name, String shape, int volume, int radius, int height) {
        super(name, shape, volume, radius, height);

    }
    // endregion

    // region Getters
    @Override
    public int getValue1() { return super.getValue1(); }

    @Override
    public int getValue2() {
        return super.getValue2();
    }

    @Override
    public int getValue3() { return super.getValue3(); }

    //endregion

    // region Functions
    @Override
    public String toString() {
        return name + " (radius: " + getValue2() + ", height: "
                + getValue3() + ", volume: " + getValue1() + "m3)";
    }
    // endregion
}