package sample;

public class Globe extends Shape {
    private int radius;

    public Globe(String name, String shape, int radiusInt){
        super(name, shape);
        this.radius = radius;
    }

    public int getRadius() { return radius; }

    public double calculateGlobe(){
        return ((4.0/3.0) * Math.PI * Math.pow(radius, 3));
    }

    @Override
    public String toString() {
        return "The volume of '" + name + "' is: " + calculateGlobe() + "m3" + ". Shape is: " + getShape() + "\n" +
                "Radius: " + getRadius();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Globe globe = (Globe) o;
        return radius == globe.radius;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
