package vatfx.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MemoryShapeTest {

    public final static MemoryShape sut = new MemoryShape();

    @BeforeAll
    public static void init() {
        // Add shape to sut
        sut.saveGlobe("Globe", "globe", 3);
    }

    @Test
    void saveCube() {
        // Add an object expect 2
        sut.saveCube("Cube1", "cube", 2, 2, 3);
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void delete() {
        // Add an object and delete an object expect 2
        sut.saveGlobe("GlobeDelete", "globe", 3);
        sut.delete("GlobeDelete", "globe");
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void getAll() {
        // Check if sut is empty expect outcome false because sut contains 2 shape
        assertFalse(sut.getAll().isEmpty());
    }

    @Test
    void search() {
        // Search for a shape expect outcome "Name: Globe"
        assertEquals("Globe (radius: 3, volume:113m3)", String.valueOf(sut.search("globe", "Globe")));
    }

    @Test
    void saveCylinder() {
        // Add an object expect 3
        sut.saveCylinder("Cylinder", "cylinder", 3, 3);
        assertEquals(3, sut.getAll().size());
    }

    @Test
    void deleteAll() {
        // Delete a shape Cube expect 2
        sut.deleteAll("cube");
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void saveGlobe() {
        // Add an object expect 3
        sut.saveGlobe("Globe1", "globe", 4);
        assertEquals(3, sut.getAll().size());
    }

    @Test
    void saveHemisphere() {
        // Add an object
        sut.saveHemisphere("Hemisphere1", "hemisphere", 4);
        assertEquals(4, sut.getAll().size());
    }

    @Test
    void savePyramid() {
        // Add an object
        sut.savePyramid("Pyramid1", "pyramid", 3, 4, 5);
        assertEquals(5, sut.getAll().size());
    }

    @Test
    void getDirectory() {
        // Check of directory is the correct directory
        assertEquals("C:\\Users\\beaujays\\Documents\\GitHub\\VatFX", String.valueOf(sut.getDirectory()));
    }

    @Test
    void getObservableList() {
        // Check if observable list is empty, list is not empty
        assertFalse(sut.getObservableList().isEmpty());
    }

    @Test
    void importFile() {
        // Import file with 2 shapes added to the list with already 5 shapes init
        sut.importFile("vorm.csv");
        assertEquals(7, sut.getAll().size());
    }
}