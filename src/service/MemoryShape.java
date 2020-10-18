package service;

import database.MySQLJDBCUtil;
import domain.Shape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MemoryShape implements ObservableShape {
    private ObservableList<Shape> shapeList = FXCollections.observableArrayList();

    //region Add shape
    @Override
    public void saveGlobe(String name, String shape, int value1) {
        double calculateGlobe = (1* (4.0 / 3.0) * Math.PI * Math.pow(value1, 3));
        shapeList.add(new Shape(name, shape, (int) calculateGlobe, value1));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }

    @Override
    public void saveCube(String name, String shape, int value1, int value2, int value3) {
        double calculateCube = value1 * value2 * value3;
        shapeList.add(new Shape(name, shape, (int) calculateCube, value1, value2, value3));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }
    //endregion

    //region Search
    @Override
    public Shape search(String shape, String name) {
        Optional<Shape> found = shapeList.stream().filter(p -> p.getName().equals(name)).findFirst();
        Shape result = found.isEmpty() ? null : found.get();

        System.out.println(result == null ? "No shape found " : "found: " + result);

        return result;
    }
    //endregion

    // region Delete data
    @Override
    public void delete(String name, String Shape) {
        shapeList = shapeList.stream().filter(p -> !p.getName().equals(name)).collect(Collectors.toCollection(FXCollections::observableArrayList));
        System.out.println("Deleted, shape with name: " + name);
    }

    @Override
    public void deleteAll(String shape) {
        shapeList = shapeList.stream().filter(p -> !p.getShape().equals(shape)).collect(Collectors.toCollection(FXCollections::observableArrayList));
        //shapeList.clear();
        System.out.println("Deleted, shape: " + shape);
    }
    //endregion

    //region Get all
    @Override
    public List<Shape> getAll() {
        return shapeList;
    }
    //endregion

    // region Import
    @Override
    public void importFile(String file) {

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
                    double calculateCube = length * depth * height;
                    shapeList.add(new Shape(name, shape,(int) calculateCube, length, height, depth));
                } else if (parts[1].contains("globe")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int globeRadius = Integer.parseInt(parts[2]);
                    double calculateGlobe = ((4.0 / 3.0) * Math.PI * Math.pow(globeRadius, 3));
                    shapeList.add(new Shape(name, shape,(int) calculateGlobe, globeRadius));
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find file: '" + file + "'.");
        }
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }
    // endregion

    //TODO
    // region Export
    @Override
    public void exportFile(String shape) {

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

    @Override
    public ObservableList<Shape> getObservableList() {
        return shapeList;
    }

    // endregion
}
