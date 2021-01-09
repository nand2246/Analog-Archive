package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CameraTest {

    private Camera camera;

    @BeforeEach
    public void runBefore() {
        camera = new Camera("Program Plus", "35mm", "Pentax");
    }

    @Test
    public void testGetName() {
        assertEquals("Program Plus", camera.getName());
    }

    @Test
    public void testSetName() {
        camera.setName("MX");
        assertEquals("MX", camera.getName());
    }

    @Test
    public void testGetFilmType() {
        assertEquals("35mm", camera.getFilmType());
    }

    @Test
    public void testSetFilmType() {
        camera.setFilmType("medium format");
        assertEquals("medium format", camera.getFilmType());
    }

    @Test
    public void testGetManufacturer() {
        assertEquals("Pentax", camera.getManufacturer());
    }

    @Test
    public void testSetManufacturer() {
        camera.setManufacturer("Canon");
        assertEquals("Canon", camera.getManufacturer());
    }

    @Test
    public void testToString() {
        assertEquals("Pentax Program Plus", camera.toString());
    }
}
