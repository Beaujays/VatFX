package vatfx.domain;

public class Globe extends Shape {

    public Globe(String name, String shape, int volume, int radius) {
        super(name, shape, volume, radius);
    }

    @Override
    public String toString() {
        return name + " (radius: " + value1 + ", volume:" + volume + "m3)";
    }
}

