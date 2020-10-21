package vatfx.domain;

public class Hemisphere extends Shape {
    private final int radius;

    public Hemisphere(String name, String shape, int radius) {
        super(name, shape);
        this.radius = radius;
    }

    public Hemisphere(String name, int radius) {
        super(name);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public double calculateHemisphere() {
        return ((2.0 / 3.0) * Math.PI * Math.pow(value1, 3));
    }

    @Override
    public String toString() {
        return name + "( radius: " + getRadius() + ", volume:" + calculateHemisphere() + "m3";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hemisphere hemisphere = (Hemisphere) o;
        return radius == hemisphere.radius;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
