package persistence;

import model.Archive;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            Archive a = reader.read();
            fail("IOException not thrown");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testEmptyArchive() {
        JsonReader reader = new JsonReader("./data/testEmptyArchive.json");
        try {
            Archive a = reader.read();
            assertTrue(a.filmCollection.getCollection().isEmpty());
            assertTrue(a.cameraCollection.getCollection().isEmpty());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }

    @Test
    public void testGeneralArchive() {
        JsonReader reader = new JsonReader("./data/testGeneralArchive.json");
        Archive a;
        try {
            a = reader.read();
            assertEquals(3, a.filmCollection.getCollection().size());
            assertEquals(2, a.cameraCollection.getCollection().size());
            assertEquals(1, a.filmCollection.filterByType("Instant Film").size());
            assertEquals(2, a.filmCollection.filterByType("35mm").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Polaroid").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Pentax").size());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }

    @Test
    public void testFullArchive() {
        JsonReader reader = new JsonReader("./data/testFullArchive.json");
        Archive a;
        try {
            a = reader.read();
            assertEquals(2, a.filmCollection.getCollection().size());
            assertEquals(2, a.cameraCollection.getCollection().size());
            assertEquals(1, a.filmCollection.filterByType("Instant Film").size());
            assertEquals(1, a.filmCollection.filterByType("35mm").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Polaroid").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Pentax").size());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }
}
