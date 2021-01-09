package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraCollectionTest {

    private Camera camera;
    private CameraCollection cameraCollection;

    @BeforeEach
    public void runBefore() {
        cameraCollection = new CameraCollection();
        camera = new Camera("Program Plus", "35mm", "Pentax");
    }

    @Test
    public void testAddCameraNoCamera() {
        cameraCollection.addCamera(camera);
        assertEquals(1, cameraCollection.getSize());
        assertEquals(camera, cameraCollection.getCamera(0));
    }

    @Test
    public void testAddCameraDuplicate() {
        cameraCollection.addCamera(camera);
        cameraCollection.addCamera(camera);
        assertEquals(1, cameraCollection.getSize());
    }

    @Test
    public void testRemoveCamera() {
        cameraCollection.addCamera(camera);
        cameraCollection.removeCamera(camera);
        assertEquals(0, cameraCollection.getSize());
    }

    @Test
    public void testGetCamera() {
        cameraCollection.addCamera(camera);
        assertEquals(camera, cameraCollection.getCamera(0));
    }

    @Test
    public void testFilterByFilmType() {

        for (int x = 0; x < 9; x++) {
            camera = new Camera(Integer.toString(x), "35mm", "Pentax");
            cameraCollection.addCamera(camera);
        }
        for (int x = 0; x < 5; x++) {
            camera = new Camera(Integer.toString(x), "medium format", "Pentax");
            cameraCollection.addCamera(camera);
        }

        ArrayList<Camera> filtered = cameraCollection.filterByFilmType("35mm");
        assertEquals(9, filtered.size());
    }

    @Test
    public void testFilterByName() {

        for (int x = 0; x < 9; x++) {
            camera = new Camera("Program Plus", "35mm", Integer.toString(x));
            cameraCollection.addCamera(camera);
        }
        for (int x = 0; x < 5; x++) {
            camera = new Camera("Impulse AF", "instant film", Integer.toString(x));
            cameraCollection.addCamera(camera);
        }

        ArrayList<Camera> filtered = cameraCollection.filterByName("Impulse AF");
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterByManufacturer() {

        for (int x = 0; x < 9; x++) {
            camera = new Camera(Integer.toString(x), "35mm", "Pentax");
            cameraCollection.addCamera(camera);
        }
        for (int x = 0; x < 5; x++) {
            camera = new Camera(Integer.toString(x), "medium format", "Polaroid");
            cameraCollection.addCamera(camera);
        }

        ArrayList<Camera> filtered = cameraCollection.filterByManufacturer("Pentax");
        assertEquals(9, filtered.size());
    }

    @Test
    public void testGetCollection() {
        Camera c = new Camera("Program Plus", "35mm", "Pentax");
        cameraCollection.addCamera(c);
        assertEquals(1, cameraCollection.getCollection().size());
    }
}
