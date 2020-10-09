
package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shape implements ShapeInterface {
    String name;
    String shape;
    List<Shape> shapeList = new ArrayList<>();

    public Shape(String name, String shape) {
        this.name = name;
        this.shape = shape;
    }

    public Shape() {
    }

    public String getName() {
        return name;
    }

    public String getShape() {
        return shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape1 = (Shape) o;
        return Objects.equals(name, shape1.name) &&
                Objects.equals(shape, shape1.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shape, shapeList);
    }

    @Override
    public void save(Shape shape) {
        System.out.println("Saved: " + shape);
        shapeList.add(shape);

        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
/*
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "insert into vat.globe (name, radius) " +
                    "values ('" + name + "','" + radius + "')";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

 */
    }

    @Override
    public String search(String name, String shape) {
        /*
        Optional<Shape> found = shapeList.stream().filter(p -> p.getName().equals(name)).findFirst();
        Shape result = found.isEmpty() ? null : found.get();

        System.out.println(result==null ? "No globe found " : "found: " + result);
         */

        String query = "SELECT * FROM vat." + shape + " WHERE name = '" + name + "'";
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                switch (shape) {
                    case "globe":
                        String nameGlobe = rs.getString("name");
                        int radius = rs.getInt("radius");
                        double calculateGlobe = ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                        return "\nName: " + nameGlobe + ". Radius: " + radius + "\nTotal volume: " + calculateGlobe;
                    case "cube":
                        String nameCube = rs.getString("name");
                        int length = rs.getInt("length");
                        int height = rs.getInt("height");
                        int depth = rs.getInt("depth");
                        double calculateCube = length * height * depth;
                        return "Name: " + nameCube + ". length: " + length + ". height:"
                                + height + ". depth:" + depth + "\nTotal volume: " + calculateCube;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String searchShape(String shape) {
        String query = "SELECT * FROM vat." + shape + "";
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                switch (shape) {
                    case "globe":
                        String nameGlobe = rs.getString("name");
                        int radius = rs.getInt("radius");
                        double calculateGlobe = ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                        return "\nName: " + nameGlobe + ", Radius: " + radius + "\nTotal volume: " + calculateGlobe;
                    case "cube":
                        String nameCube = rs.getString("name");
                        int length = rs.getInt("length");
                        int height = rs.getInt("height");
                        int depth = rs.getInt("depth");
                        double calculateCube = length * height * depth;
                        return "Name: " + nameCube + ", length: " + length + ", height:"
                                + height + ", depth:" + depth + "\nTotal volume: " + calculateCube;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String name) {
        shapeList = shapeList.stream().filter(p -> !p.getName().equals(name)).collect(Collectors.toList());
        System.out.println("Deleted, shape with name: " + name);

        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "DELETE FROM vat.globe WHERE name = '" + name + "'";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        shapeList.clear();
        System.out.println("All globes deleted");
    }
}
