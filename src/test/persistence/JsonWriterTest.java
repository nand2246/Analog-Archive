package persistence;


import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyArchive() {
        try {
            Archive a = new Archive(new CameraCollection(), new FilmCollection());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyArchive.json");
            writer.open();
            writer.write(a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyArchive.json");
            a = reader.read();
            assertTrue(a.cameraCollection.getCollection().isEmpty());
            assertTrue(a.filmCollection.getCollection().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralArchive() {
        try {
            CameraCollection cc = new CameraCollection();
            FilmCollection fc = new FilmCollection();
            Archive a = new Archive(cc, fc);
            Camera c1 = new Camera("Program Plus", "35mm", "Pentax");
            Camera c2 = new Camera("Impulse AF", "Instant Film", "Polaroid");
            Film f1 = new Film("test", 400, "35mm", c1, "Kodak Ultramax");
            Film f2 = new Film("test2", 600, "Instant Film", c2, "Polaroid Originals 600");

            a.cameraCollection.addCamera(c1);
            a.cameraCollection.addCamera(c2);
            a.filmCollection.addFilm(f1);
            a.filmCollection.addFilm(f2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralArchive.json");
            writer.open();
            writer.write(a);
            writer.close();


            JsonReader reader = new JsonReader("./data/testWriterGeneralArchive.json");
            a = reader.read();
            assertEquals(2, a.filmCollection.getCollection().size());
            assertEquals(2, a.cameraCollection.getCollection().size());
            assertEquals(1, a.filmCollection.filterByType("Instant Film").size());
            assertEquals(1, a.filmCollection.filterByType("35mm").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Polaroid").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Pentax").size());
            assertEquals("Pentax", a.cameraCollection.getCamera(0).getManufacturer());
            assertEquals("Kodak Ultramax", a.filmCollection.getRoll(0).getBrand());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterFullArchive() {
        try {
            JsonReader reader = new JsonReader("./data/testFullArchive.json");
            Archive a = reader.read();

            JsonWriter writer = new JsonWriter("./data/testWriterFullArchive.json");
            writer.open();
            writer.write(a);
            writer.close();

            reader = new JsonReader("./data/testWriterFullArchive.json");
            a = reader.read();
            Film f = a.filmCollection.getRoll(0);
            assertEquals(2, a.filmCollection.getCollection().size());
            assertEquals(2, a.cameraCollection.getCollection().size());
            assertEquals(1, a.filmCollection.filterByType("Instant Film").size());
            assertEquals(1, a.filmCollection.filterByType("35mm").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Polaroid").size());
            assertEquals(1, a.cameraCollection.filterByManufacturer("Pentax").size());

            assertEquals("Pentax", a.cameraCollection.getCamera(0).getManufacturer());

            assertEquals("Kodak Ultramax", f.getBrand());
            assertEquals(LocalDate.of(2020, 12, 30), f.getExpiry());
            assertEquals(LocalDate.of(2020, 7, 12), f.getDevelopDate());
            assertEquals("Natural Color Lab", f.getDevelopLocation());
            assertEquals("C:\\users\\nandp\\google drive\\photography", f.getDirectory());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
