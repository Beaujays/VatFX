package sample;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

public class Shape implements ShapeInterface {
    //region Variables
    String name;
    String shape;
    List<Shape> shapeList = new ArrayList<>();
    //endregion

    // region Constructors
    public Shape(String name, String shape) {
        this.name = name;
        this.shape = shape;
    }

    public Shape() { }
    // endregion

    // region Getters
    public String getName() {
        return name;
    }

    public String getShape() {
        return shape;
    }
    //endregion

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
        Optional<Shape> found = shapeList.stream().filter(p -> p.getName().equals(name)).findFirst();
        Shape result = found.isEmpty() ? null : found.get();

        System.out.println(result == null ? "No shape found " : "found: " + result);

        String query = "SELECT * FROM vat." + shape + " WHERE name = '" + name + "'";
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                switch (shape) {
                    case "globe" -> {
                        String nameGlobe = rs.getString("name");
                        int radius = rs.getInt("radius");
                        double calculateGlobe = ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                        return "\nName: " + nameGlobe + ". Radius: " + radius + "\nTotal volume: " + calculateGlobe;
                    }
                    case "cube" -> {
                        String nameCube = rs.getString("name");
                        int length = rs.getInt("length");
                        int height = rs.getInt("height");
                        int depth = rs.getInt("depth");
                        double calculateCube = length * height * depth;
                        return "Name: " + nameCube + ". length: " + length + ". height:"
                                + height + ". depth:" + depth + "\nTotal volume: " + calculateCube;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Shape> searchShape(String shape) {
        String query = "SELECT * FROM vat." + shape + "";
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                switch (shape) {
                    case "globe" -> {
                        String nameGlobe = rs.getString("name");
                        int radius = rs.getInt("radius");
                        double calculateGlobe = ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                        System.out.println(nameGlobe + ";globe;" + radius);
                        //System.out.println("\nName: " + nameGlobe + ", Radius: " + radius +
                        //      "\nTotal volume: " + calculateGlobe);
                    }
                    case "cube" -> {
                        String nameCube = rs.getString("name");
                        int length = rs.getInt("length");
                        int height = rs.getInt("height");
                        int depth = rs.getInt("depth");
                        double calculateCube = length * height * depth;
                        System.out.println("Name: " + nameCube + ", length: " + length + ", height:"
                                + height + ", depth:" + depth + "\nTotal volume: " + calculateCube);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    // region Delete single row
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
    //endregion

    @Override
    public void deleteAll() {
        shapeList.clear();
        System.out.println("All globes deleted");
    }

    // region Import
    @Override
    public ArrayList<Shape> importFile(String file) {
        shapeList = new ArrayList<>();

        try (Scanner readFile = new Scanner(Paths.get(String.valueOf(file)))) {
            while (readFile.hasNextLine()) {

                String line = readFile.nextLine();
                String[] parts = line.split(";");
                if (parts[1].contains("cube")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int length = Integer.parseInt(parts[2]);
                    int depth = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);

                    try (Connection conn = MySQLJDBCUtil.getConnection()) {
                        String query = "insert into vat.cube (name, length,height, depth) " +
                                "values ('" + name + "','" + length + "','" + height + "','" + depth + "')";
                        Statement stmt = conn.createStatement();
                        stmt.execute(query);
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                    shapeList.add(new Cube(name, shape, length, height, depth));
                } else if (parts[1].contains("globe")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int globeRadius = Integer.parseInt(parts[2]);

                    try (Connection conn = MySQLJDBCUtil.getConnection()) {
                        String query = "insert into vat.globe (name, radius) " +
                                "values ('" + name + "','" + globeRadius + "')";
                        Statement stmt = conn.createStatement();
                        stmt.execute(query);
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                    shapeList.add(new Globe(name, shape, globeRadius));
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find file: '" + file + "'.");
        }
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
        return (ArrayList<Shape>) shapeList;
    }
    // endregion

    // region Export
    @Override
    public String exportFile(String shape) {

        String csvFileName = getFileName(shape.concat("_Export"));

        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "SELECT * FROM vat." + shape + "";
            Statement stmt = conn.createStatement();
            stmt.execute(query);

            ResultSet result = stmt.executeQuery(query);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFileName));

            int columnCount = writeHeaderLine(result);

            while (result.next()) {
                String line = "";

                for (int i = 2; i <= columnCount; i++) {
                    Object valueObject = result.getObject(i);
                    String valueString = "";

                    if (valueObject != null) valueString = valueObject.toString();

                    if (valueObject instanceof String) {
                        valueString = "\"" + escapeDoubleQuotes(valueString) + "\"";
                    }

                    line = line.concat(valueString);
                    if (i != columnCount) {
                        line = line.concat(",");
                    }
                }
                fileWriter.newLine();
                fileWriter.write(line);
            }
            stmt.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public File getDirectory() {
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        return chooser.getCurrentDirectory();
    }

    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.csv", dateTimeInfo));
    }

    private int writeHeaderLine(ResultSet result) throws SQLException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String headerLine = "";

        // exclude the first column which is the ID field
        for (int i = 2; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            headerLine = headerLine.concat(columnName).concat(",");
        }

        //fileWriter.write(headerLine.substring(0, headerLine.length() - 1));

        return numberOfColumns;
    }

    private String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }
    // endregion
}
