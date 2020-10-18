package service;

import database.MySQLJDBCUtil;
import domain.Shape;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DatabaseShape extends AbstractDatabaseShape<Shape> implements ShapeInterface {

    //region Add shape
    @Override
    public void saveGlobe(String name, String shape, int value1) {
        useStatement("insert into vat.globe (name, radius, volume) value(?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setInt(2, value1);
            statement.setDouble(3, (4.0 / 3.0) * Math.PI * Math.pow(value1, 3));

            return statement.execute();
        });
    }

    @Override
    public void saveCube(String name, String shape, int value1, int value2, int value3) {
        useStatement("insert into vat.cube (name, length, height, depth, volume) value(?,?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setInt(2, value1);
            statement.setInt(3, value2);
            statement.setInt(4, value3);
            statement.setInt(5, (value1 * value2 * value3));

            return statement.execute();
        });
    }
    //endregion

    //region Search Shape
    @Override
    public Shape search(String shape, String name) {

        return useStatement("SELECT * FROM vat." + shape + " WHERE name = ?", statement -> {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (shape.equals("globe")) {
                    return recordToEntityGlobe(resultSet);
                } else
                    return recordToEntityCube(resultSet);
            } else {
                return null;
            }
        });
    }
    //endregion

    // region Delete data
    @Override
    public void delete(String name, String shape) {

        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "DELETE FROM vat." + shape + " WHERE name = '" + name + "'";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void deleteAll(String shape) {
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "DELETE FROM vat." + shape + "";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //endregion

    @Override
    public List<Shape> getAll() {
        return useStatement("SELECT name, volume FROM vat.globe UNION ALL SELECT name, volume FROM vat.cube",
                statement -> {
                    ResultSet resultSet = statement.executeQuery();
                    List<Shape> result = new ArrayList<>();

                    while (resultSet.next()) {
                        Shape shapes = recordToEntity(resultSet);
                        result.add(shapes);
                    }

                    return result;
                });
    }

    // region Import
    @Override
    public void importFile(String file) {

        try (Scanner readFile = new Scanner(Paths.get(String.valueOf(file)))) {
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                String[] parts = line.split(";");
                if (parts[1].contains("cube")) {
                    String name = parts[0];
                    int length = Integer.parseInt(parts[2]);
                    int depth = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);
                    int volume = length * height * depth;
                    useStatement("insert into vat.cube (name, length, height, depth, volume) value(?,?,?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setInt(2, length);
                        statement.setInt(3, height);
                        statement.setInt(4, depth);
                        statement.setInt(5, volume);
                        return statement.execute();
                    });
                } else if (parts[1].contains("globe")) {
                    String name = parts[0];
                    int globeRadius = Integer.parseInt(parts[2]);
                    useStatement("INSERT INTO vat.globe (name, radius, volume) VALUE(?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setInt(2, globeRadius);
                        statement.setDouble(3, (4.0 / 3.0) * Math.PI * Math.pow(globeRadius, 3));
                        return statement.execute();
                    });
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't find file: '" + file + "'.");
        }
    }
    // endregion

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
    //endregion

    //region Record to entity
    @Override
    Shape recordToEntity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int volume = resultSet.getInt("volume");
        return new Shape(name, volume);
    }

    Shape recordToEntityGlobe(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int radius = resultSet.getInt("radius");
        double calculate = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        return new Shape(name, "globe", radius, (int) calculate);
    }

    Shape recordToEntityCube(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int length = resultSet.getInt("length");
        int height = resultSet.getInt("height");
        int depth = resultSet.getInt("depth");
        int calculate = length * height * depth;
        return new Shape(name, "cube", length, height, depth, calculate);
    }
    //endregion
}