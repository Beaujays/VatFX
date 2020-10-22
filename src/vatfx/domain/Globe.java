package vatfx.domain;

public class Globe extends Shape {

    public Globe(String name, String shape, int volume, int radius) {
        super(name, shape, volume, radius);
    }

    @Override
    public String toString() {
        return name + " (radius: " + getValue2() + ", volume:" + getValue1() + "m3)";
    }

    @Override
    public int getValue1() { return super.getValue1(); }

    @Override
    public int getValue2() {
        return super.getValue2();
    }
}

