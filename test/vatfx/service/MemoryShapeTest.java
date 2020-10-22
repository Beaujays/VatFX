package vatfx.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryShapeTest {

    public final static MemoryShape sut = new MemoryShape();

    @BeforeAll
    public static void init() {
        // Add shape to sut
        sut.saveGlobe("Globe","globe",3);
    }

    @Test
    void delete() {
        // Delete from ArrayList expect 0 in
        sut.saveGlobe("Globe1","globe",3);
        sut.saveCube("Cube1","cube",2,2,3);
        sut.saveCilinder("Cylinder1","cylinder",3,4);
        sut.saveHemisphere("Hemisphere1","hemisphere",3);
        sut.savePiramide("Pyramid1","pyramid",3,3,2);
        sut.delete("Globe1", "globe");
        sut.delete("Cube1", "cube");
        sut.delete("Cylinder1", "cylinder");
        sut.delete("Hemisphere1", "hemisphere");
        sut.delete("Pyramid1", "pyramid");
        assertEquals(1, sut.getAll().size());
    }

    @Test
    void getAll() {
        // Check if sut is empty expect outcome false because sut contains 1 shape
        assertFalse(sut.getAll().isEmpty());
    }

    @Test
    void search() {
        // Search for a shape expect outcome "Name: Globe"
        assertEquals("Name: Globe, Volume: 113", String.valueOf(sut.search("globe", "Globe")));
    }

    @Test
    void saveGlobe() {
        // Before test ArrayList.size() list size 1 expect 2
        sut.saveGlobe("Globe1", "globe",4);
        assertEquals(2, sut.getAll().size());
    }

    @Test
    void importFile(){
        // Import file with 2 shapes added to the list with already 2 shapes init
        sut.importFile("vorm.csv");
        assertEquals(4,sut.getAll().size());
    }
}