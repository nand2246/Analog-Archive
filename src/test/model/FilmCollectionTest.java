package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmCollectionTest {

    private Camera c;
    private Film f;
    private FilmCollection fc;

    @BeforeEach
    public void runBefore() {
        c = new Camera("Program Plus", "35mm", "Pentax");
        f = new Film("UltraMax", 400, "35mm", c, "Kodak");
        fc = new FilmCollection();
    }

    @Test
    public void testAddFilm() {
        fc.addFilm(f);
        assertEquals(1, fc.getSize());
    }

    @Test
    public void testAddFilmDuplicate() {
        fc.addFilm(f);
        fc.addFilm(f);
        assertEquals(1, fc.getSize());
    }

    @Test
    public void testRemoveFilm() {
        fc.addFilm(f);
        fc.removeFilm(f);
        assertEquals(0, fc.getSize());
    }

    @Test
    public void testGetRoll() {
        fc.addFilm(f);
        assertEquals(fc.getRoll(0), f);
    }

    @Test
    public void testGetCollection() {
        fc.addFilm(f);
        ArrayList<Film> collection = fc.getCollection();
        assertEquals(1, collection.size());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, fc.getSize());
        fc.addFilm(f);
        assertEquals(1, fc.getSize());
    }

    @Test
    public void testFilterByCamera() {

        for (int x = 0; x < 9; x++) {
            f = new Film(Integer.toString(x), 400, "35mm", c, "Kodak");
            fc.addFilm(f);
        }

        c = new Camera("Impulse AF", "instant film", "Polaroid");

        for (int x = 0; x < 5; x++) {
            f = new Film(Integer.toString(x), 400, "instant film", c, "Kodak");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByCamera(c);
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterByName() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Superia", x, "35mm", c, "Kodak");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByName("Ultramax");
        assertEquals(9, filtered.size());
    }

    @Test
    public void testFilterByIso() {

        for (int x = 0; x < 9; x++) {
            f = new Film(Integer.toString(x), 400, "35mm", c, "Kodak");
            fc.addFilm(f);
        }

        for (int x = 0; x < 5; x++) {
            f = new Film(Integer.toString(x), 200, "35mm", c, "Kodak");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByIso(200);
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterByType() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            fc.addFilm(f);
        }
        c = new Camera("Impulse AF", "instant film", "polaroid");
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "instant film", c, "Kodak");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByType("35mm");
        assertEquals(9, filtered.size());
    }

    @Test
    public void testFilterByManufacturer() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Fujifilm");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByBrand("Fujifilm");
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterByExpiry() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setExpiry(2020, 5, 15);
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setExpiry(2001, 2, 28);
            fc.addFilm(f);
        }
        f = new Film("Ultramax", 0, "35mm", c, "Kodak");
        fc.addFilm(f);

        LocalDate date = LocalDate.of(2020, 5, 15);
        ArrayList<Film> filtered = fc.filterByExpiry(date);
        assertEquals(9, filtered.size());
    }

    @Test
    public void testFilterByDevelopDate() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setDevelopDate(2020, 5, 15);
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setDevelopDate(2001, 2, 28);
            fc.addFilm(f);
        }
        f = new Film("Ultramax", 0, "35mm", c, "Kodak");
        fc.addFilm(f);

        LocalDate date = LocalDate.of(2001, 2, 28);
        ArrayList<Film> filtered = fc.filterByDevelopDate(date);
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterByDevelopLocation() {

        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setDevelopLocation("Kerrisdale Camera");
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setDevelopLocation("Natural Color Lab");
            fc.addFilm(f);
        }
        f = new Film("Ultramax", 0, "35mm", c, "Kodak");
        fc.addFilm(f);


        ArrayList<Film> filtered = fc.filterByDevelopLocation("Kerrisdale Camera");
        assertEquals(9, filtered.size());
    }

    @Test
    public void testFilterByPhotosPathExist() {
        for (int x = 0; x < 9; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            f.setDirectory("C:\\");
            fc.addFilm(f);
        }
        for (int x = 0; x < 5; x++) {
            f = new Film("Ultramax", x, "35mm", c, "Kodak");
            fc.addFilm(f);
        }

        ArrayList<Film> filtered = fc.filterByPhotosPathExist();
        assertEquals(9, filtered.size());
    }
}
