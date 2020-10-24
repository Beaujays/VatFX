package vatfx.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import vatfx.domain.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class MemoryShape implements ObservableShape {
    private ObservableList<Shape> shapeList = FXCollections.observableArrayList();

    //region Add shape
    @Override
    public void saveGlobe(String name, String shape, int value1) {
        double calculate = (1 * (4.0 / 3.0) * Math.PI * Math.pow(value1, 3));
        shapeList.add(new Globe(name, shape, (int) calculate, value1));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes + " " + shapes.getValue1());
        }
    }

    @Override
    public void saveCube(String name, String shape, int value1, int value2, int value3) {
        double calculateCube = value1 * value2 * value3;
        shapeList.add(new Cube(name, shape, (int) calculateCube, value1, value2, value3));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }

    @Override
    public void saveCylinder(String name, String shape, int value1, int value2) {
        double calculateCilinder = Math.PI * (value1 * value1) * value2;
        shapeList.add(new Cylinder(name, shape, (int) calculateCilinder, value1, value2));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }

    @Override
    public void savePyramid(String name, String shape, int value1, int value2, int value3) {
        double calculatePiramide = (1.0 * (value1 * value2 * value3) / 2);
        shapeList.add(new Pyramid(name, shape, (int) calculatePiramide, value1, value2, value3));

        System.out.println("Saved: " + shape);
        System.out.println("List of shapes:");
        for (Shape shapes : shapeList) {
            System.out.println("\t" + shapes);
        }
    }

    @Override
    public void saveHemisphere(String name, String shape, int value1) {
        double calculate = (2.0 / 3.0) * Math.PI * Math.pow(value1, 3);
        shapeList.add(new Hemisphere(name, shape, (int) calculate, value1));

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

        try (Scanner readFile = new Scanner(Paths.get(valueOf(file)))) {
            while (readFile.hasNextLine()) {

                String line = readFile.nextLine();
                String[] parts = line.split(";");
                if (parts[1].contains("cube")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int length = Integer.parseInt(parts[2]);
                    int depth = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);
                    double calculate = length * depth * height;
                    shapeList.add(new Cube(name, shape, (int) calculate, length, height, depth));
                } else if (parts[1].contains("globe")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int radius = Integer.parseInt(parts[2]);
                    double calculate = ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
                    shapeList.add(new Globe(name, shape, (int) calculate, radius));
                } else if (parts[1].contains("hemisphere")) {
                    String name = parts[0];
                    String shape = parts[1];
                    int radius = Integer.parseInt(parts[2]);
                    double calculate = (2 * 3.14 * 2 * radius * radius);
                    shapeList.add(new Hemisphere(name, shape, (int) calculate, radius));
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

    //region Export
    @Override
    public void exportFile(String shape) {
        String csvFileName = getFileName(shape.concat("_Export"));

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFileName));
            String[] header = {"Name", "Shape", "Volume"};
            fileWriter.write(Arrays.toString(header));
            fileWriter.newLine();
            for (Shape shape1 : shapeList) {
                if (shape1.getShape().equals(shape)) {
                    List<String> list = new ArrayList<>();
                    list.add(shape1.getName());
                    list.add(shape1.getShape());
                    list.add(valueOf(shape1.getValue1()));

                    String collect = String.join(";", list);

                    fileWriter.write(collect);
                    fileWriter.newLine();
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.csv", dateTimeInfo));
    }

    @Override
    public File getDirectory() {
        FileChooser chooser = new FileChooser();
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir);
        chooser.setInitialDirectory(file);
        return chooser.getInitialDirectory();
    }

    @Override
    public ObservableList<Shape> getObservableList() {
        return shapeList;
    }
    // endregion
}
