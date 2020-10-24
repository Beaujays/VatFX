package vatfx.domain;

public class Hemisphere extends Shape {

    public Hemisphere(String name, String shape, int volume, int radius) {
        super(name, shape, volume, radius);
    }

    @Override
    public String toString() {
        return name + " (radius: " + value1 + ", volume: " + volume + "m3)";
    }

}
