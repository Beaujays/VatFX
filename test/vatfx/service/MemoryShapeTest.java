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
        sut.saveCube("Cube1", "cube", 2, 2, 3);
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void delete() {
        // Delete from ArrayList expect 0 in
        sut.saveGlobe("GlobeDelete", "globe", 3);
        sut.delete("GlobeDelete", "globe");
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void getAll() {
        // Check if sut is empty expect outcome false because sut contains 1 shape
        assertFalse(sut.getAll().isEmpty());
    }

    @Test
    void search() {
        // Search for a shape expect outcome "Name: Globe"
        assertEquals("Globe (radius: 3, volume:113m3)", String.valueOf(sut.search("globe", "Globe")));
    }

    @Test
    void saveCylinder() {
        sut.saveCylinder("Cylinder", "cylinder", 3, 3);
        assertEquals(3, sut.getAll().size());
    }

    @Test
    void deleteAll() {
        sut.deleteAll("cube");
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void saveGlobe() {
        // Before test ArrayList.size() list size 1 expect 2
        sut.saveGlobe("Globe1", "globe", 4);
        assertEquals(3, sut.getAll().size());
    }

    @Test
    void saveHemisphere() {
        sut.saveHemisphere("Hemisphere1", "hemisphere", 4);
        assertEquals(4, sut.getAll().size());
    }

    @Test
    void savePyramid() {
        sut.savePyramid("Pyramid1", "pyramid", 3, 4, 5);
        assertEquals(5, sut.getAll().size());
    }

    @Test
    void getDirectory() {
        assertEquals("C:\\Users\\beaujays\\Documents\\GitHub\\VatFX", String.valueOf(sut.getDirectory()));
    }

    @Test
    void getObservableList() {
        assertFalse(sut.getObservableList().isEmpty());
    }

    @Test
    void importFile() {
        // Import file with 2 shapes added to the list with already 2 shapes init
        sut.importFile("vorm.csv");
        assertEquals(7, sut.getAll().size());
    }
}