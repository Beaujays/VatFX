package vatfx.service;

import javafx.stage.FileChooser;
import vatfx.database.MySQLJDBCUtil;
import vatfx.domain.*;

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
        useStatement("insert into vat.globe (name, shape, radius, volume) value(?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setString(2, shape);
            statement.setInt(3, value1);
            statement.setDouble(4, (4.0 / 3.0) * Math.PI * Math.pow(value1, 3));
            return statement.execute();
        });
    }

    @Override
    public void saveHemisphere(String name, String shape, int value1) {
        useStatement("insert into vat.hemisphere (name, shape, radius, volume) value(?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setString(2, shape);
            statement.setInt(3, value1);
            statement.setDouble(4, (2.0 / 3.0) * Math.PI * Math.pow(value1, 3));
            return statement.execute();
        });
    }

    @Override
    public void saveCube(String name, String shape, int value1, int value2, int value3) {
        useStatement("insert into vat.cube (name, shape, length, height, depth, volume) value(?,?,?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setString(2, shape);
            statement.setInt(3, value1);
            statement.setInt(4, value2);
            statement.setInt(5, value3);
            statement.setInt(6, (value1 * value2 * value3));
            return statement.execute();
        });
    }

    public void saveCylinder(String name, String shape, int value1, int value2) {
        useStatement("insert into vat.Cylinder (name, shape, radius, height, volume) value(?,?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setString(2, shape);
            statement.setInt(3, value1);
            statement.setInt(4, value2);
            statement.setDouble(5, (value1 * value1) * Math.PI * Math.pow(value2, 3));
            return statement.execute();
        });
    }

    public void savePyramid(String name, String shape, int value1, int value2, int value3) {
        useStatement("insert into vat.pyramid (name, shape, length, height, depth, volume) value(?,?,?,?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setString(3, shape);
            statement.setInt(4, value1);
            statement.setInt(5, value2);
            statement.setInt(6, value3);
            statement.setInt(7, (value1 * value2 * value3 / 2));
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
                switch (shape) {
                    case "globe":
                        return recordToEntityGlobe(resultSet);
                    case "cube":
                        return recordToEntityCube(resultSet);
                    case "hemisphere":
                        return recordToEntityHemisphere(resultSet);
                    case "cylinder":
                        return recordToEntityCylinder(resultSet);
                    case "pyramid":
                        return recordToEntityPyramid(resultSet);
                }
            }
            return null;
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
        return useStatement("SELECT name, shape, volume FROM vat.globe UNION ALL SELECT name, shape, volume FROM vat.cube " +
                "UNION ALL SELECT name, shape, volume FROM vat.cylinder UNION ALL SELECT name, shape, volume FROM vat.pyramid " +
                "UNION ALL SELECT name, shape, volume FROM vat.hemisphere", statement -> {
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
                    String shape = parts[1];
                    int length = Integer.parseInt(parts[2]);
                    int depth = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);
                    int volume = length * height * depth;
                    useStatement("insert into vat.cube (name, shape, length, height, depth, volume) value(?,?,?,?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setString(2,shape);
                        statement.setInt(3, length);
                        statement.setInt(4, height);
                        statement.setInt(5, depth);
                        statement.setInt(6, volume);
                        return statement.execute();
                    });
                } else if (parts[1].contains("globe")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int radius = Integer.parseInt(parts[2]);
                    useStatement("INSERT INTO vat.globe (name, shape, radius, volume) VALUE(?,?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setString(2,shape);
                        statement.setInt(3, radius);
                        statement.setDouble(4, (4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                        return statement.execute();
                    });
                } else if (parts[1].contains("hemisphere")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int radius = Integer.parseInt(parts[2]);
                    useStatement("INSERT INTO vat.hemisphere (name, shape, radius, volume) VALUE(?,?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setString(2, shape);
                        statement.setInt(3, radius);
                        statement.setDouble(4, (2 * 3.14 * 2 * radius * radius));
                        return statement.execute();
                    });
                } else if (parts[1].contains("Cylinder")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int radius = Integer.parseInt(parts[2]);
                    int height = Integer.parseInt(parts[3]);
                    useStatement("INSERT INTO vat.cylinder (name, shape, length, depth, height, volume) VALUE(?,?,?,?,?,?)", statement -> {
                        statement.setString(1, name);
                        statement.setString(2, shape);
                        statement.setInt(3, radius);
                        statement.setInt(4, height);
                        statement.setDouble(4, (Math.PI * (radius * radius) * height));
                        return statement.execute();
                    });
                } else if (parts[1].contains("Pyramid")) {
                            String name = parts[0];
                            String shape = parts[1];
                            int length = Integer.parseInt(parts[2]);
                            int depth = Integer.parseInt(parts[3]);
                            int height = Integer.parseInt(parts[4]);
                            int volume = length * height * depth / 2;
                            useStatement("INSERT INTO vat.pyramid (name, shape, length, depth, height, volume) VALUE(?,?,?,?,?,?)", statement -> {
                                statement.setString(1, name);
                                statement.setString(2,shape);
                                statement.setInt(3, length);
                                statement.setInt(4, height);
                                statement.setInt(5, depth);
                                statement.setInt(6, volume);
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
        FileChooser chooser = new FileChooser();
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir);
        chooser.setInitialDirectory(file);
        return chooser.getInitialDirectory();
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
        String shape = resultSet.getString("shape");
        int volume = resultSet.getInt("volume");
        return new Shape(name, shape, volume);
    }

    Globe recordToEntityGlobe(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int radius = resultSet.getInt("radius");
        double calculate = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        return new Globe(name, "globe", (int) calculate, radius);
    }

    Hemisphere recordToEntityHemisphere(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int radius = resultSet.getInt("radius");
        double calculate = (2.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        return new Hemisphere(name, "globe", (int) calculate, radius);
    }

    Cylinder recordToEntityCylinder(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int radius = resultSet.getInt("radius");
        int height = resultSet.getInt("height");
        double calculate = (radius * radius) * Math.PI * Math.pow(height, 3);
        return new Cylinder(name, "Cylinder", (int) calculate, radius, height);
    }

    Pyramid recordToEntityPyramid(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int length = resultSet.getInt("length");
        int height = resultSet.getInt("height");
        int depth = resultSet.getInt("depth");
        int calculate = length * height * depth / 2;
        return new Pyramid(name, "Pyramid",calculate, length, height, depth);
    }

    Cube recordToEntityCube(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int length = resultSet.getInt("length");
        int height = resultSet.getInt("height");
        int depth = resultSet.getInt("depth");
        int calculate = length * height * depth;
        return new Cube(name, "cube",calculate, length, height, depth);
    }
    //endregion
}